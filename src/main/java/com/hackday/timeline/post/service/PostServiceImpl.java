package com.hackday.timeline.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.dto.InsertPostDto;
import com.hackday.timeline.post.repository.PostRepository;
import com.hackday.timeline.image.domain.Image;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository){
		this.postRepository = postRepository;
	}

	@Override
	public Post insertPost(InsertPostDto insertPostDto, Member member, Image image) {
		Post post = insertPostDto.toEntity(member, null);
		return postRepository.save(post);
	}

	// TODO: validation 검사
	public boolean hasErrors(InsertPostDto insertPostDto, BindingResult bindingResult) {
		return false;
	}

	@Override
	@Transactional
	public void modifyPost(InsertPostDto insertPostDto, Long postId, Member member, Image image) {
		Post post = postRepository.findById(postId).get();
		post.setContent(insertPostDto.getContent());
		// post.setContent(image);
	}

	@Override
	@Transactional
	public void deletePost(Long postId, Long userId) {
		postRepository. deleteByIdAndUser_UserId(postId, userId);
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
