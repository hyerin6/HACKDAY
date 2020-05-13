package com.hackday.timeline.member.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MemberVO {

	Long userNo;

	String userId;

	String userName;

	boolean subsOk;
}
