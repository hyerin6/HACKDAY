package com.hackday.timeline.mail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.timeline.mail.service.MailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Controller
@Api(tags = {"메일 API"})
@SwaggerDefinition(tags = {
	@Tag(name = "메일 API", description = "메일 관리")
})
@RequestMapping("/mail")
public class MailController {
	private final MailService service;

	public MailController(MailService service) {
		this.service = service;
	}

	@ApiOperation(value = "키 인증", notes = "회원가입 완료를 위해 키 값을 Y로 바꿔줍니다.")
	@GetMapping("/update")
	public String update(String key, String keyValue) throws Exception {
		if (!service.checkKey(key, keyValue)) {
			return "redirect:/mail/mailFailure";
		}
		service.authMemberKey(key);

		return "redirect:/mail/mailSuccess";
	}

	@ApiOperation(value = "인증 완료 화면", notes = "인증 완료 페이지를 보여줍니다.")
	@GetMapping("/mailSuccess")
	public ModelAndView getSuccess(ModelAndView mv) throws Exception {
		mv.setViewName("thymeleaf/mail/mailSuccess");
		return mv;
	}

	@ApiOperation(value = "인증 대기 화면", notes = "인증 대기 페이지를 보여줍니다.")
	@GetMapping("/mailWait")
	public ModelAndView getWait(String userId, ModelAndView mv) throws Exception {

		mv.setViewName("thymeleaf/mail/mailWait");
		return mv;
	}
}
