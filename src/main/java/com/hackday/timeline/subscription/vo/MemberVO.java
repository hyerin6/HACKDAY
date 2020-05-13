package com.hackday.timeline.subscription.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberVO {

	Long userNo;

	String userId;

	String userName;

	boolean isSubs = false;
}
