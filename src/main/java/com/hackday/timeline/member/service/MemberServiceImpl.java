package com.hackday.timeline.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository repository;

	@Override
	public void register(Member member) throws Exception {
		Member userEntity = new Member();
		userEntity.setUserId(member.getUserId());
		userEntity.setUserPw(member.getUserPw());
		userEntity.setUserName(member.getUserName());
		repository.save(userEntity);
	}

	@Override
	public Member read(Long userNo) throws Exception {
		return repository.getOne(userNo);
	}

	@Override
	public void remove(Long userNo) throws Exception {
		repository.deleteById(userNo);
	}

	@Override
	public void modify(Member member) throws Exception {
		Member userEntity = repository.getOne(member.getUserNo());
		userEntity.setUserName(member.getUserName());
		repository.save(userEntity);
	}

}
