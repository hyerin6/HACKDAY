package com.hackday.timeline.home.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.timeline.member.domain.Member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Controller
@Api(tags = {"홈"})
@SwaggerDefinition(tags = {
	@Tag(name = "HOME", description = "사용자가 어떤 서비스를 사용할지 선택할 수 있습니다.")
})
public class HomeController {

	@ApiOperation(value = "Home", notes = "사용자 정보와 serverTime 을 보여줍니다.")
	@GetMapping("/")
	public ModelAndView home(Locale locale, Model model, ModelAndView mv) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		model.addAttribute("member", new Member());
		mv.setViewName("thymeleaf/home");
		return mv;
	}

}
