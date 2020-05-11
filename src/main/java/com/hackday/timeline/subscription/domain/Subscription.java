package com.hackday.timeline.subscription.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hackday.timeline.user.domain.User;
import org.hibernate.annotations.CreationTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "subsNo")
@ToString
@Entity
@Table(name = "subscription")
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long subsNo;

	@CreationTimestamp
	Date regDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_no")
    User user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "subs_user_no")
	User subsUser;

}
