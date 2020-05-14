package com.hackday.timeline.subscription.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.repository.MemberRepository;
import com.hackday.timeline.subscription.domain.Subscription;
import com.hackday.timeline.subscription.repository.SubsRepository;
import com.hackday.timeline.subscription.vo.SubsVO;

import lombok.extern.java.Log;

@Service
@Log
public class SubsServiceImpl implements SubsService {

	@Autowired
	SubsRepository repository;

	@Autowired
	MemberRepository memberRepository;

	@Override
	public void register(Subscription subscription, Long subsUserNo) throws Exception {
		Member member = memberRepository.getOne(subsUserNo);
		subscription.setSubsMember(member);
		repository.save(subscription);
	}

	//내가 구독한 사람
	@Override
	public List<SubsVO> memberSubsList(Member member) throws Exception {
		Long userNo = member.getUserNo();
		String userName = member.getUserName();
		log.info(userNo + " " + userName);
		List<Object[]> userlist = repository.memberSubsList(userNo);
		List<SubsVO> subsList = new ArrayList<>();
		for (Object[] user : userlist) {
			subsList.add(new SubsVO((Long)user[0], userNo, (Long)user[1], userName, (String)user[2], (Date)user[3]));
		}
		return subsList;
	}

	//나를 구독한 사람
	@Override
	public List<SubsVO> subsMemberList(Member member) throws Exception {
		Long userNo = member.getUserNo();
		String userName = member.getUserName();
		List<Object[]> userlist = repository.memberSubsList(userNo);
		List<SubsVO> subsList = new ArrayList<>();
		for (Object[] user : userlist) {
			subsList.add(new SubsVO((Long)user[0], (Long)user[1], userNo, (String)user[2], userName, (Date)user[3]));
		}
		return subsList;
	}

	@Override
	public void remove(Long subsNo) throws Exception {
		repository.deleteById(subsNo);
	}
}
