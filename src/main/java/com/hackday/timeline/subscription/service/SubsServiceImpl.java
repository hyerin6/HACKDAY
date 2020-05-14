package com.hackday.timeline.subscription.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.repository.MemberRepository;
import com.hackday.timeline.subscription.domain.Subscription;
import com.hackday.timeline.subscription.repository.SubsRepository;
import com.hackday.timeline.subscription.vo.SubsVO;

@Service
public class SubsServiceImpl implements SubsService {

	private final SubsRepository subsRepository;
	private final MemberRepository memberRepository;

	@Autowired
	public SubsServiceImpl(SubsRepository subsRepository, MemberRepository memberRepository) {
		this.subsRepository = subsRepository;
		this.memberRepository = memberRepository;
	}

	@Override
	public void register(Subscription subscription, Long subsUserNo) throws Exception {
		Member member = memberRepository.getOne(subsUserNo);
		subscription.setSubsMember(member);
		subsRepository.save(subscription);
	}

	//내가 구독한 사람
	@Override
	public List<SubsVO> memberSubsList(Long userNo) throws Exception {
		List<Object[]> userlist = subsRepository.memberSubsList(userNo);
		List<SubsVO> subsList = new ArrayList<>();
		for (Object[] user : userlist) {
			subsList.add(new SubsVO((Long)user[0], (Long)user[1], (String)user[2], (String)user[3], (Date)user[4]));
		}
		return subsList;
	}

	//나를 구독한 사람
	@Override
	public List<SubsVO> subsMemberList(Long userNo) throws Exception {
		List<Object[]> userlist = subsRepository.subsMemberList(userNo);
		List<SubsVO> subsList = new ArrayList<>();
		for (Object[] user : userlist) {
			subsList.add(new SubsVO((Long)user[0], (Long)user[1], (String)user[2], (String)user[3], (Date)user[4]));
		}
		return subsList;
	}

	@Override
	public void remove(Long subsNo) throws Exception {
		subsRepository.deleteById(subsNo);
	}
}
