package com.hackday.timeline.post.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = "게시글을 조회할 때 특정 사용자의 가장 작은 postId를 전달하기 위한 모델이다.")
@Getter
public class GetPostsRequest {
	@ApiModelProperty(value = "사용자가 작성한 게시글 중 가장 작은 게시글 Id")
	private Long lastIdOfPosts;
}