package com.hackday.timeline.member.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.timeline.member.domain.Member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Controller
@Api(tags = {"로그인 API"})
@SwaggerDefinition(tags = {
	@Tag(name = "로그인 API", description = "login")
})
@RequestMapping("/auth")
public class LoginController {

	@ApiOperation(value = "로그인 화면", notes = "로그인 페이지를 보여줍니다.")
	@GetMapping("/login")
	public ModelAndView loginForm(String error, String logout, Model model, Authentication authentication,
		ModelAndView mv) throws Exception {

		if (authentication != null) {
			mv.setViewName("thymeleaf/home");
			return mv;
		}
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
