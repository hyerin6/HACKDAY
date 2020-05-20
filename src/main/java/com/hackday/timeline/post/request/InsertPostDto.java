package com.hackday.timeline.post.request;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.image.domain.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel(description = "게시글 작성 시 insert 하기 위해 content와 image를 전달하는 DTO 입니다.")
public class InsertPostDto {

	@ApiModelProperty(value = "사용자가 작성한 게시글")
	@NotBlank(message = "내용을 입력하세요.")
	private String content;

	@ApiModelProperty(value = "사용자가 첨부한 이미지")
	private MultipartFile image;

	public Post toEntity(Member member, Image image) {
		return Post.builder()
			.content(content)
			.user(member)
			.image(null)
			.build();
	}

}
