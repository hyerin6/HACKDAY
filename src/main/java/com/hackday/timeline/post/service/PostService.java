package com.hackday.timeline.post.service;

import java.io.IOException;
import java.util.List;

import org.springframework.validation.BindingResult;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.dto.InsertPostDto;

public interface PostService {

	public Post insertPost(InsertPostDto insertPostDto, Member member) throws IOException;

	public void modifyPost(InsertPostDto insertPostDto, Long postId, Member member);

	public void deletePost(Long postId, Long userId);

	public Long getMinIdOfPosts(Long userId);

	public List<Post> getPosts(Long postId, Long userId);

	public boolean hasErrors(InsertPostDto insertPostDto, BindingResult bindingResult);

}
