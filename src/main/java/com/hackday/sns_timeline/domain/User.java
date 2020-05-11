package com.hackday.sns_timeline.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "userNo")
@ToString
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_no")
	Long userNo;

	@NotBlank
	@Column(length = 25, nullable = false)
	String userId;

	@NotBlank
	@Column(length = 25, nullable = false)
	String password;

	@NotBlank
	@Column(length = 20, nullable = false)
	String name;

	@CreationTimestamp
	Date regDate;
	@UpdateTimestamp
	Date upd_date;
}
