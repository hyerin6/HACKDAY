package com.hackday.timeline.subscription.service;

import java.util.List;

import com.hackday.timeline.subscription.domain.Subscription;
import com.hackday.timeline.subscription.dto.SubsDTO;

public interface SubsService {

	public void register(Subscription subscription, Long subsUserNo) throws Exception;

	public void remove(Long subsNo) throws Exception;

	public List<SubsDTO> memberSubsList(Long userNo) throws Exception;

	public List<SubsDTO> subsMemberList(Long userNo) throws Exception;

}
