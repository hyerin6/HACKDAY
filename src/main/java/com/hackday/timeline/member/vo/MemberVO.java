package com.hackday.timeline.member.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MemberVO {

	private Long userNo;

	private String userId;

	private String userName;

	private boolean subsOk;

	private Long subsNo;

}
