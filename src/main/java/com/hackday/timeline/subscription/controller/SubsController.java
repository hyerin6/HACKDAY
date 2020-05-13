package com.hackday.timeline.subscription.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping("/register")
	public String register(Member writer, Authentication authentication, RedirectAttributes rttr)
		throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Subscription subscription = new Subscription();
		subscription.setMember(member);
		subscription.setMember(writer);

		service.register(subscription);

		rttr.addFlashAttribute("msg", "REGISTER");

		return "redirect:/subs/list";

	}

	@GetMapping("/read")
	public String read(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();

		List<SubsVO> memberSubsList = service.memberSubsList(member);
		List<SubsVO> subsMemberList = service.subsMemberList(member);
		model.addAttribute("memberSubsList", memberSubsList);
		model.addAttribute("subsMemberList", subsMemberList);

		return "subs/list";
	}

	@PostMapping("/remove")
	public String remove(Long subsNo, RedirectAttributes rttr) throws Exception {
		rttr.addFlashAttribute("msg", "REMOVE");
		service.remove(subsNo);

		return "redirect:/subs/list";
	}

}
