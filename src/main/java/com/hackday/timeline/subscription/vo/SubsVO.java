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

	Long subsNo;
	Long userNo;
	Long subsUserNo;
	String userName;
	String subsName;
	Date regDate;

	//	public SubsVO(Long subsNo, Long userNo, Long subsUserNo, String userName, String subsName, Date user) {
	//		this.subsNo = subsNo;
	//		this.userNo = userNo;
	//		this.subsUserNo = subsUserNo;
	//		this.userName = userName;
	//		this.subsName = subsName;
	//		this.regDate = user;
	//	}

}
