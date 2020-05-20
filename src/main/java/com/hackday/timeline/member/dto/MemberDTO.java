package com.hackday.timeline.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MemberDTO {

	private Long userNo;

	private String userId;

	private String userName;

	private boolean subsOk;

	private Long subsNo;

}
