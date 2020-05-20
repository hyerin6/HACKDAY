package com.hackday.timeline.mail.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.hackday.timeline.member.domain.Member;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "mailNo")
@ToString
@Entity
@NoArgsConstructor
@ApiModel(description = "이메일")
@Table(name = "mail")
public class Mail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mailNo;

	@OneToOne
	@JoinColumn(name = "user_no")
	private Member member;

	@Column(length = 25, nullable = false)
	private String mailKey;
}
