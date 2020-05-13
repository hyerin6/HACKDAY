package com.hackday.timeline.member.service;

import java.util.List;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.subscription.vo.MemberVO;

public interface MemberService {

	public void register(Member member) throws Exception;

	public Member read(Long userNo) throws Exception;

	public void remove(Long userNo) throws Exception;

	public void modify(Member member) throws Exception;

	public List<MemberVO> listAll(Long userNo) throws Exception;

}
