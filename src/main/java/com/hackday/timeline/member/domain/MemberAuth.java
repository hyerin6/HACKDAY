package com.hackday.timeline.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "member_auth")
public class MemberAuth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long userAuthNo;

	@Column(name = "user_no")
	Long userNo;

	@Column(length = 50)
	String auth;

	@CreationTimestamp
	Date regDate;

	@UpdateTimestamp
	Date updDate;

}
