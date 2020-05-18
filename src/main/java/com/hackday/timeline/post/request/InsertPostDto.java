package com.hackday.timeline.post.request;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.image.domain.Image;
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
public class InsertPostDto {

	@NotBlank(message = "내용을 입력하세요.")
	private String content;

	private MultipartFile image;

	public Post toEntity(Member member, Image image) {
		return Post.builder()
			.content(content)
			.user(member)
			.image(null)
			.build();
	}

}
