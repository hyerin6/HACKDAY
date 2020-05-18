package com.hackday.timeline.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.timeline.exception.NotFoundException;

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
public class CommonController extends AbstractErrorController {

	public CommonController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@ApiOperation(value = "일반적인 오류 화면", notes = "특정하게 처리하지 않는 오류가 발생했을 때 보여주는 에러페이지를 보여줍니다.")
	@GetMapping("/commonError")
	public ModelAndView handleCommonError(HttpServletRequest request) {
		log.info("errorCommon");
		HttpStatus status = getStatus(request);
		ModelAndView mv = new ModelAndView();

		if (status.equals(HttpStatus.NOT_FOUND)) {
			throw new NotFoundException();
		}

		mv.setViewName("thymeleaf/error/commonError");

		return mv;
	}

	@ApiOperation(value = "접근 거부 페이지", notes = "접근 거부가 일어난 경우에 보여주는 에러페이지를 보여줍니다.")
	@GetMapping("/accessError")
	public ModelAndView accessDenied(Authentication auth, Model model) {
		log.info("access Denied : " + auth);
		model.addAttribute("msg", "Access Denied");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("thymeleaf/error/accessError");
		return mv;
	}

	@Override
	public String getErrorPath() {
		return "/error/commonError";
	}

}
