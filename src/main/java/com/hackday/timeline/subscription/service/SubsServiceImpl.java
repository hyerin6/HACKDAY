package com.hackday.timeline.subscription.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.repository.MemberRepository;
import com.hackday.timeline.subscription.domain.Subscription;
import com.hackday.timeline.subscription.repository.SubsRepository;
import com.hackday.timeline.subscription.vo.SubsVO;

@Service
@Transactional
public class SubsServiceImpl implements SubsService {

	private final SubsRepository subsRepository;
	private final MemberRepository memberRepository;

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
	@Transactional(readOnly = true)
	public List<SubsVO> memberSubsList(Long userNo) throws Exception {
		List<SubsVO> subsList = subsRepository.memberSubsList(userNo);
		return subsList;
	}

	//나를 구독한 사람
	@Override
	@Transactional(readOnly = true)
	public List<SubsVO> subsMemberList(Long userNo) throws Exception {
		List<SubsVO> subsList = subsRepository.subsMemberList(userNo);
		return subsList;
	}

	@Override
	public void remove(Long subsNo) throws Exception {
		subsRepository.deleteById(subsNo);
	}
}
