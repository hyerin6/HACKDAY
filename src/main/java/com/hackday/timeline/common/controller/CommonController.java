package com.hackday.timeline.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.java.Log;

@Log
@Controller
@Api(tags = {"공통 API"})
@SwaggerDefinition(tags = {
	@Tag(name = "공통 API", description = "오류 페이지 요청 관리")
})
@RequestMapping("/error")
public class CommonController {

	private String VIEW_PATH = "thymeleaf/error";

	@ApiOperation(value = "오류 화면", notes = "오류가 발생했을 때 보여주는 에러페이지를 보여줍니다.")
	@GetMapping("/errorCommon")
	public ModelAndView handleCommonError(HttpServletRequest request, ModelAndView mv) {
		log.info("errorCommon");
		mv.setViewName(VIEW_PATH + "/errorCommon");

		return mv;
	}

	@ApiOperation(value = "접근 거부 페이지", notes = "접근 거부가 일어난 경우에 보여주는 에러페이지를 보여줍니다.")
	@GetMapping("/accessError")
	public ModelAndView accessDenied(Authentication auth, Model model, ModelAndView mv) {
		log.info("access Denied : " + auth);
		model.addAttribute("msg", "Access Denied");
		mv.setViewName(VIEW_PATH + "/accessError");
		return mv;
	}

}
