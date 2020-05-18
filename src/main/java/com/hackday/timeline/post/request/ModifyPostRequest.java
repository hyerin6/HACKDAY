package com.hackday.timeline.post.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@ApiModel(description = "게시글 수정 시 content를 전달하기 위한 모델이다.")
@Getter
public class ModifyPostRequest{
	private Long id;
	private String content;
}
