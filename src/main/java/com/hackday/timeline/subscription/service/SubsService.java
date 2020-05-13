package com.hackday.timeline.subscription.service;

import java.util.List;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.subscription.domain.Subscription;
import com.hackday.timeline.subscription.vo.SubsVO;

public interface SubsService {

	public void register(Subscription subscription) throws Exception;

	public void remove(Long subsNo) throws Exception;

	public List<SubsVO> memberSubsList(Member member) throws Exception;

	public List<SubsVO> subsMemberList(Member member) throws Exception;

}
