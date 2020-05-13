package com.hackday.timeline.post.service;

import java.util.List;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.dto.InsertPostDto;
import com.hackday.timeline.image.domain.Image;

public interface PostService {

	public Post insertPost(InsertPostDto insertPostDto, Member member, Image image);
	public void modifyPost(InsertPostDto insertPostDto, Long postId, Member member, Image image);
	public void deletePost(Long postId, Long userId);
	public Long getMinIdOfPosts(Long userId);
	public List<Post> getPosts(Long postId, Long userId);
}
