package com.hackday.timeline.post.service;

import java.io.IOException;
import java.util.List;

import org.springframework.validation.BindingResult;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.dto.InsertPostDto;

public interface PostService {

	// post 생성
	public Post insertPost(InsertPostDto insertPostDto, Member member) throws IOException;

	// post 수정
	public void modifyPost(InsertPostDto insertPostDto, Long postId, Member member);

	// post 삭제
	public void deletePost(Long postId, Long userId);

	// user가 작성한 게시글 중 가장 작은 postId 조회
	public Long getMinIdOfPosts(Long userId);

	// user가 작성한 게시글 조회
	public List<Post> getPosts(Long postId, Long userId);

	// insert 시, content 입력했는지 validation 검사
	public boolean hasErrors(InsertPostDto insertPostDto, BindingResult bindingResult);

}
