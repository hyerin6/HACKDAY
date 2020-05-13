package com.hackday.timeline.post.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.hackday.timeline.image.service.ImageService;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.dto.InsertPostDto;
import com.hackday.timeline.post.repository.PostRepository;
import com.hackday.timeline.image.domain.Image;
import com.hackday.timeline.utils.s3.S3Service;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;

	private ImageService imageService;

	private S3Service s3Service;

	@Autowired
	public PostServiceImpl(PostRepository postRepository, ImageService imageService, S3Service s3Service) {
		this.postRepository = postRepository;
		this.imageService = imageService;
		this.s3Service = s3Service;
	}

	@Override
	public Post insertPost(InsertPostDto insertPostDto, Member member) throws IOException {
		Post post = insertPostDto.toEntity(member, null);

		if(insertPostDto.getImage() != null) {
			// fileName 중복 방지를 위한 random UUID 생성
			String randomUUID = UUID.randomUUID().toString();
			// aws s3 이미지 저장
			String path = s3Service.postImageUpload(insertPostDto.getImage(), randomUUID);
			// Image 테이블에 저장
			Image image = imageService.saveImage(path, randomUUID);
			// post에 image 저장
			post.setImage(image);
		}

		return postRepository.save(post);
	}

	@Override
	public boolean hasErrors(InsertPostDto insertPostDto, BindingResult bindingResult) {
		return bindingResult.hasErrors() ? true : false;
	}

	@Override
	@Transactional
	public void modifyPost(InsertPostDto insertPostDto, Long postId, Member member) {
		Post post = postRepository.findById(postId).get();
		post.setContent(insertPostDto.getContent());
	}

	@Override
	@Transactional
	public void deletePost(Long postId, Long userId) {
		Post post = postRepository.findOneById(postId);
		imageService.deleteImage(post.getImage().getId());
		postRepository.deleteByIdAndUser_UserNo(postId, userId);
	}

	@Override
	public List<Post> getPosts(Long postId, Long userId) {
		final List<Post> posts = get(postId, userId);
		return posts;
	}

	private List<Post> get(Long id, Long userId) {
		return id == null ?
			this.postRepository.findTop5ByUserIdOrderByRegDateDesc(userId) :
			this.postRepository.findTop5ByUserIdAndIdLessThanOrderByIdDescRegDateDesc(userId, id);
	}

	@Override
	public Long getMinIdOfPosts(Long userId) {
		return postRepository.findMinIdByUserId(userId);
	}

}
