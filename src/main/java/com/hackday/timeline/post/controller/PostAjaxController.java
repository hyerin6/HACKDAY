package com.hackday.timeline.post.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackday.timeline.common.security.domain.CustomUser;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.service.PostService;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PostAjaxController {

	private PostService postService;

	@Autowired
	public PostAjaxController(PostService postService) {
		this.postService = postService;
	}

	@RequestMapping(value="/api/posts", method = RequestMethod.POST)
	public @ResponseBody PostsResponse getMyPosts(@RequestBody GetPostsRequest getPostsRequest, Authentication authentication) {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userId = member.getUserNo();

		log.info("lastIdOfPosts = " + getPostsRequest.getLastIdOfPosts());
		List<Post> posts = postService.getPosts(getPostsRequest.getLastIdOfPosts(), userId);
		Long lastIdOfPosts = posts.isEmpty() ?
			null : posts.get(posts.size() - 1).getId();



		log.info("Posts = " + Arrays.toString(posts.toArray()));

		PostsResponse result = PostsResponse.builder()
			.posts(posts)
			.lastIdOfPosts(lastIdOfPosts)
			.build();
		return result;
	}

	// @ResponseBody
	// @RequestMapping(value="/friendPosts", method = RequestMethod.POST)
	// public PostsResponse getFriendPosts(@RequestBody GetPostsRequest getPostsRequest, Long userId) {
	// 	List<Post> posts = postService.getPosts(getPostsRequest.getLastIdOfPosts(), userId);
	// 	Long lastIdOfPosts = posts.isEmpty() ?
	// 		null : posts.get(posts.size() - 1).getId();
	//
	// 	PostsResponse result = PostsResponse.builder()
	// 		.posts(posts)
	// 		.lastIdOfPosts(lastIdOfPosts)
	// 		.build();
	// 	return result;
	// }

	@Getter
	static class GetPostsRequest{
		private Long lastIdOfPosts;
	}

	@Getter
	@Builder
	static class PostsResponse{
		private List<Post> posts;
		private Long lastIdOfPosts;
	}

}
