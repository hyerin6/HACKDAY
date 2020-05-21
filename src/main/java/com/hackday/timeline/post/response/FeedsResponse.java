package com.hackday.timeline.post.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "게시글 목록 중 가장 작은 id 값을 전달하기 위한 모델이다.")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedsResponse implements Serializable {
	private Long minIdOfPosts;
}
