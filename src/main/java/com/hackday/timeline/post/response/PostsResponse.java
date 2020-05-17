package com.hackday.timeline.post.response;

import java.util.List;

import com.hackday.timeline.post.domain.Post;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@ApiModel(description = "게시글 목록을 전달하기 위한 모델이다.")
@Getter
@Builder
public class PostsResponse {
	private List<Post> posts;
	private Long lastIdOfPosts;
}