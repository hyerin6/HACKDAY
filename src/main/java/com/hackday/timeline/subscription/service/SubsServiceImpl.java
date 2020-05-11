package com.hackday.timeline.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackday.timeline.subscription.repository.SubsRepository;

@Service
public class SubsServiceImpl implements SubsService {

	@Autowired
	SubsRepository repository;
}
