package com.hackday.timeline.member.service;

import com.hackday.timeline.member.domain.Member;

public interface MemberService {

	public void register(Member member) throws Exception;

	public Member read(Long userNo) throws Exception;

	public void remove(Long userNo) throws Exception;

	public void modify(Member user) throws Exception;

}
