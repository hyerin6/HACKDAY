package com.hackday.timeline.post.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hackday.timeline.image.domain.Image;
import com.hackday.timeline.member.domain.Member;
import io.swagger.annotations.ApiModel;
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
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel(description = "게시글")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(length = 3000)
	private String content;

	@Column(name = "reg_date")
	@CreationTimestamp
	private Date regDate;

	@Column(name = "upd_date")
	@UpdateTimestamp
	private Date updDate;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_no")
	private Member user;

	@OneToOne
	@JoinColumn(name = "image_id", referencedColumnName = "id")
	private Image image;

}
