package com.hackday.timeline.member.service;

import java.util.List;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.dto.MemberDTO;

public interface MemberService {

	public void register(Member member) throws Exception;

	public Member read(Long userNo) throws Exception;

	public void remove(Long userNo) throws Exception;

	public void modify(Member member) throws Exception;

	public List<MemberDTO> listAll(Long userNo) throws Exception;

	public boolean userIdCheck(String userId) throws Exception;

}
