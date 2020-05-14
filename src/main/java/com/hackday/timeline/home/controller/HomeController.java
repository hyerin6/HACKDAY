package com.hackday.timeline.home.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.timeline.member.domain.Member;

@Controller
public class HomeController {

	@GetMapping("/")
	public ModelAndView home(Locale locale, Model model) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		ModelAndView mv = new ModelAndView();
		model.addAttribute("member", new Member());
		mv.setViewName("thymeleaf/home");
		return mv;
	}

}
