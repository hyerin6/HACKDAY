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
import com.hackday.timeline.subscription.service.SubsService;
import com.hackday.timeline.subscription.vo.SubsVO;

@Controller
@RequestMapping("/subs")
public class SubsController {

	private final SubsService service;

	public SubsController(SubsService service) {
		this.service = service;
	}

	//구독 등록
	@GetMapping("/register")
	public ModelAndView register(Long subsUserNo, Authentication authentication, RedirectAttributes rttr)
		throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Subscription subscription = new Subscription();
		subscription.setMember(member);

		service.register(subscription, subsUserNo);

		rttr.addFlashAttribute("msg", "REGISTER");

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/user/list");
		return mv;
	}

	//구독 리스트
	@GetMapping("/list")
	public ModelAndView list(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userNo = member.getUserNo();
		List<SubsVO> memberSubsList = service.memberSubsList(userNo);
		List<SubsVO> subsMemberList = service.subsMemberList(userNo);
		model.addAttribute("memberSubsList", memberSubsList);
		model.addAttribute("subsMemberList", subsMemberList);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("thymeleaf/subs/list");
		return mv;
	}

	//구독 취소
	@GetMapping("/remove")
	public ModelAndView remove(Long subsNo, String view, RedirectAttributes rttr) throws Exception {
		rttr.addFlashAttribute("msg", "REMOVE");
		service.remove(subsNo);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/" + view + "/list");
		return mv;
	}

}
