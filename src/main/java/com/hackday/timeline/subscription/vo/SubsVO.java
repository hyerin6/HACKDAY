package com.hackday.timeline.subscription.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SubsVO {

	private Long subsNo;

	private Long userNo;

	private String userId;

	private String userName;

	private Date regDate;

}
