package com.hackday.timeline.subscription.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.hackday.timeline.member.domain.Member;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "subsNo")
@ToString
@Entity
@ApiModel(description = "구독")
@Table(name = "subscription")
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subsNo;

	@CreationTimestamp
	private Date regDate;

	@ManyToOne
	@JoinColumn(name = "user_no")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "subs_user_no")
	private Member subsMember;

}
