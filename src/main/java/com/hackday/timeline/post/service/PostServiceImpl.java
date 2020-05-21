package com.hackday.timeline.post.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.hackday.timeline.image.service.ImageService;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.request.InsertPostDto;
import com.hackday.timeline.post.repository.PostRepository;
import com.hackday.timeline.image.domain.Image;
import com.hackday.timeline.post.response.FeedsResponse;
import com.hackday.timeline.utils.s3.S3Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	private final ImageService imageService;

	private final S3Service s3Service;

	public PostServiceImpl(PostRepository postRepository,
		ImageService imageService,
		S3Service s3Service) {
		this.postRepository = postRepository;
		this.imageService = imageService;
		this.s3Service = s3Service;
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "timeline", allEntries = true), @CacheEvict(value = "posts", allEntries = true),
		@CacheEvict(value = "subsPosts", allEntries = true), @CacheEvict(value = "minIdOfSubsPosts", allEntries = true), @CacheEvict(value = "minIdOfSubsPosts", allEntries = true)})
	public Post insertPost(InsertPostDto insertPostDto, Member member) throws IOException {
		Post post = insertPostDto.toEntity(member, null);

		if(insertPostDto.getImage() != null) {
			String randomUUID = UUID.randomUUID().toString();
			String path = s3Service.postImageUpload(insertPostDto.getImage(), randomUUID);
			Image image = imageService.saveImage(path, randomUUID);
			post.setImage(image);
		}

		return postRepository.save(post);
	}

	@Override
	public boolean hasErrors(InsertPostDto insertPostDto, BindingResult bindingResult) {
		return bindingResult.hasErrors() ? true : false;
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "timeline", allEntries = true), @CacheEvict(value = "posts", allEntries = true), @CacheEvict(value = "subsPosts", allEntries = true) })
	public void modifyPost(InsertPostDto insertPostDto, Long postId, Member member) {
		Post post = postRepository.findById(postId).get();
		post.setContent(insertPostDto.getContent());
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "timeline", allEntries = true), @CacheEvict(value = "posts", allEntries = true),
		@CacheEvict(value = "subsPosts", allEntries = true), @CacheEvict(value = "minIdOfSubsPosts", allEntries = true), @CacheEvict(value = "minIdOfSubsPosts", allEntries = true) })
	public void deletePost(Long postId, Long userId) {
		Post post = postRepository.findOneById(postId);

		if(post.getImage() != null) {
			imageService.deleteImage(post.getImage().getId());
		}

		postRepository.deleteByIdAndUser_UserNo(postId, userId);
	}

	@Override
	@Transactional(readOnly=true)
	@Cacheable(value = "posts", key = "'myFeeds-' + #id + '-' + #userId", unless = "#id == null")
	public List<Post> getPosts(Long id, Long userId) {
		return id == null ?
			this.postRepository.findByUserId(userId) :
			this.postRepository.findByIdAndUserId(userId, id);
	}

	@Override
	@Transactional(readOnly=true)
	@Cacheable(value = "subsPosts", key = "'feeds-' + #id + '-' + #userId", unless = "#id == null")
	public List<Post> getSubsPosts(Long id, Long userId) {
		return id == null ?
			this.postRepository.findByUserId(userId) :
			this.postRepository.findByIdAndUserId(userId, id);
	}

	@Override
	@Transactional(readOnly=true)
	@Cacheable(value = "minIdOfPosts", key = "'myMinIdOfPosts-' + #userId")
	public FeedsResponse getMinIdOfPosts(Long userId) {
		return FeedsResponse.builder()
			.minIdOfPosts(postRepository.findMinIdByUserId(userId))
			.build();
	}

	@Override
	@Transactional(readOnly=true)
	@Cacheable(value = "minIdOfSubsPosts", key = "'subsMinIdOfPosts-' + #userId")
	public FeedsResponse getMinIdOfSubsPosts(Long userId) {
		return FeedsResponse.builder()
			.minIdOfPosts(postRepository.findMinIdBySubsUserId(userId))
			.build();
	}

	@Override
	@Transactional(readOnly=true)
	@Cacheable(value = "timeline", key = "'timeline-' + #postId + '-' + #userId", unless = "#postId == null")
	public List<Post> getFeeds(Long postId, Long userId) {
		return postId == null ?
			this.postRepository.findBySubscriptionsUserId(userId) :
			this.postRepository.findByIdAndSubsUserId(postId, userId);
	}

}
