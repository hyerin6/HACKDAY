package com.hackday.timeline.image.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@ApiModel(description = "게시글에 첨부되는 이미지 모델")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ApiModelProperty(value = "이미지가 저장된 주소")
	private String filePath;

	@ApiModelProperty(value = "파일 이름 중복 방지를 위한 Random UUID")
	private String fileName;

	public Image(String filePath, String fileName){
		this.id = null;
		this.filePath = filePath;
		this.fileName = fileName;
	}

}
