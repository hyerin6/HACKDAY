package com.hackday.sns_timeline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hackday.sns_timeline.service.SubsService;

@Controller
@RequestMapping("/user")
public class SubsController {

	@Autowired
	SubsService service;

}
