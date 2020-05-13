package com.hackday.timeline.subscription.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@Autowired
	SubsService service;

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

	@GetMapping("/read")
	public ModelAndView read(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();

		List<SubsVO> memberSubsList = service.memberSubsList(member);
		List<SubsVO> subsMemberList = service.subsMemberList(member);
		model.addAttribute("memberSubsList", memberSubsList);
		model.addAttribute("subsMemberList", subsMemberList);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/subs/read");
		return mv;
	}

	@PostMapping("/remove")
	public ModelAndView remove(Long subsNo, RedirectAttributes rttr) throws Exception {
		rttr.addFlashAttribute("msg", "REMOVE");
		service.remove(subsNo);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/user/list");
		return mv;
	}

}
