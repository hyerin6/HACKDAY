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

	private Long userNo;

	private String userId;

	private String userName;

	private boolean subsOk;

	private Long subsNo = -1L;

	public MemberVO(Long userNo, String userId, String userName, boolean subsOk) {
		this.userNo = userNo;
		this.userId = userId;
		this.userName = userName;
		this.subsOk = subsOk;
	}

}
