package com.hackday.timeline.subscription.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.timeline.common.security.domain.CustomUser;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.subscription.domain.Subscription;
import com.hackday.timeline.subscription.dto.SubsDTO;
import com.hackday.timeline.subscription.service.SubsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Controller
@Api(tags = {"구독 API"})
@SwaggerDefinition(tags = {
	@Tag(name = "구독 API", description = "구독 관리 CRUD")
})
@RequestMapping("/subs")
public class SubsController {

	private final SubsService service;

	public SubsController(SubsService service) {
		this.service = service;
	}

	@ApiOperation(value = "구독 요청", notes = "구독 요청을 합니다.")
	@GetMapping("/register")
	public ModelAndView register(Long subsUserNo, Authentication authentication, RedirectAttributes rttr,
		ModelAndView mv) throws Exception {

		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Subscription subscription = new Subscription();
		subscription.setMember(member);

		service.register(subscription, subsUserNo);

		rttr.addFlashAttribute("msg", "REGISTER");
		mv.setViewName("redirect:/user/list");
		return mv;
	}

	@ApiOperation(value = "구독 리스트 화면", notes = "구독 리스트 페이지를 보여줍니다.")
	@GetMapping("/list")
	public ModelAndView list(Model model, Authentication authentication, ModelAndView mv) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userNo = member.getUserNo();
		List<SubsDTO> memberSubsList = service.memberSubsList(userNo);
		List<SubsDTO> subsMemberList = service.subsMemberList(userNo);
		model.addAttribute("memberSubsList", memberSubsList);
		model.addAttribute("subsMemberList", subsMemberList);

		mv.setViewName("thymeleaf/subs/list");
		return mv;
	}

	@ApiOperation(value = "구독 취소 요청", notes = "구독 취소를 요청 합니다.")
	@GetMapping("/remove")
	public ModelAndView remove(Long subsNo, String redView, RedirectAttributes rttr, ModelAndView mv) throws Exception {
		String path = "redirect:/" + redView + "/list";

		rttr.addFlashAttribute("msg", "REMOVE");
		service.remove(subsNo);

		mv.setViewName(path);
		return mv;
	}

}
