package com.hackday.timeline.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.timeline.member.domain.Member;

@Controller
@RequestMapping("auth")
public class LoginController {

	@GetMapping("login")
	public ModelAndView loginForm(String error, String logout, Model model) {
		ModelAndView mv = new ModelAndView();

		if (error != null) {
			model.addAttribute("error", "틀린 정보, 다시 입력해주세요.");
			mv.setViewName("thymeleaf/auth/loginForm");
			return mv;
		}
		if (logout != null) {
			mv.setViewName("thymeleaf/home");
			return mv;
		}

		model.addAttribute("member", new Member());
		mv.setViewName("thymeleaf/auth/loginForm");
		return mv;
	}
}
