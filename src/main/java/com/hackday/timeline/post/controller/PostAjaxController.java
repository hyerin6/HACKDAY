package com.hackday.timeline.post.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackday.timeline.common.security.domain.CustomUser;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.dto.InsertPostDto;
import com.hackday.timeline.post.service.PostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = {"게시글 조회 & 수정 REST API"})
@SwaggerDefinition(tags = {
	@Tag(name = "게시글 조회 & 수정 REST API", description = "READ & UPDATE")
})
public class PostAjaxController {

	private final PostService postService;

	public PostAjaxController(PostService postService) {
		this.postService = postService;
	}

	@ApiOperation(value = "게시글 호출", notes = "커서기반으로 5개의 게시글을 조회합니다.")
	@PostMapping("/api/posts")
	public @ResponseBody PostsResponse getMyPosts(@RequestBody GetPostsRequest getPostsRequest, Authentication authentication) {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userId = member.getUserNo();

		List<Post> posts = postService.getPosts(getPostsRequest.getLastIdOfPosts(), userId);

		Long lastIdOfPosts = posts.isEmpty() ?
			null : posts.get(posts.size() - 1).getId();

		PostsResponse result = PostsResponse.builder()
			.posts(posts)
			.lastIdOfPosts(lastIdOfPosts)
			.build();

		return result;
	}

	@ApiOperation(value = "다른 사용자가 작성한 게시글 조회", notes = "커서기반으로 5개의 게시글을 조회합니다.")
	@PostMapping("/api/feeds")
	public @ResponseBody PostsResponse getPosts(@RequestBody GetFriendPostsRequest getFriendPostsRequest) {

		List<Post> posts = postService.getPosts(getFriendPostsRequest.getLastIdOfPosts(), getFriendPostsRequest.getUserId());

		Long lastIdOfPosts = posts.isEmpty() ?
			null : posts.get(posts.size() - 1).getId();

		PostsResponse result = PostsResponse.builder()
			.posts(posts)
			.lastIdOfPosts(lastIdOfPosts)
			.build();

		return result;
	}

	@ApiOperation(value = "게시글 수정", notes = "모달창에서 받은 content 필드를 수정합니다.")
	@PatchMapping("/api/posts")
	public void modifyPost(@RequestBody ModifyPostRequest modifyPostRequest, Authentication authentication) {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();

		InsertPostDto insertPostDto = new InsertPostDto();
		insertPostDto.setContent(modifyPostRequest.getContent());

		postService.modifyPost(insertPostDto, modifyPostRequest.getId(), member);
	}

	@ApiModel(description = "게시글을 조회할 때 특정 사용자의 가장 작은 postId를 전달하기 위한 모델이다.")
	@Getter
	static class GetPostsRequest {
		@ApiModelProperty(value = "사용자가 작성한 게시글 중 가장 작은 게시글 Id")
		private Long lastIdOfPosts;
	}

	@ApiModel(description = "게시글 수정 시 content를 전달하기 위한 모델이다.")
	@Getter
	static class ModifyPostRequest{
		private Long id;
		private String content;
	}

	@ApiModel(description = "다른 사용자의 게시글을 조회할 때 특정 사용자의 가장 작은 postId를 전달하기 위한 모델이다. ")
	@Getter
	static class GetFriendPostsRequest {
		@ApiModelProperty(value = "사용자 Id")
		private Long userId;

		@ApiModelProperty(value = "사용자가 작성한 게시글 중 가장 작은 게시글 Id")
		private Long lastIdOfPosts;
	}

	@ApiModel(description = "게시글 목록을 전달하기 위한 모델이다.")
	@Getter
	@Builder
	static class PostsResponse {
		private List<Post> posts;
		private Long lastIdOfPosts;
	}

}
