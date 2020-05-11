package com.hackday.sns_timeline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackday.sns_timeline.repository.SubsRepository;

@Service
public class SubsServiceImpl implements SubsService {

	@Autowired
	SubsRepository repository;
}
