package com.hackday.timeline.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.timeline.common.security.domain.CustomUser;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.service.MemberService;
import com.hackday.timeline.subscription.vo.MemberVO;

@Controller
@RequestMapping("/user")
public class MemberController {

	@Autowired
	MemberService memberService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/register")
	public void registerForm(Member member, Model model) throws Exception {}

	@PostMapping("/register")
	public String register(@Validated Member member, BindingResult result, Model model, RedirectAttributes rttr)
		throws Exception {
		if (result.hasErrors()) {
			return "user/register";
		}
		String inputPassword = member.getUserPw();
		member.setUserPw(passwordEncoder.encode(inputPassword));
		memberService.register(member);

		rttr.addFlashAttribute("msg", "SUCCESS");
		rttr.addFlashAttribute("userName", member.getUserName());

		return "redirect:/";
	}

	//유저 전체
	@GetMapping("/list")
	public String list(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userNo = member.getUserNo();
		List<MemberVO> memberList = memberService.listAll(userNo);
		model.addAttribute("memberList", memberList);
		return "list";
	}

	// 상세 화면
	@GetMapping("/read")
	public void read(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userNo = member.getUserNo();
		model.addAttribute(memberService.read(userNo));
	}

	// 삭제 처리
	@PostMapping("/remove")
	public String remove(Long userNo, RedirectAttributes rttr) throws Exception {
		memberService.remove(userNo);
		rttr.addFlashAttribute("msg", "REMOVE");

		return "redirect:/";
	}

	// 수정 화면
	@GetMapping("/modify")
	public void modifyForm(Long userNo, Model model) throws Exception {
		model.addAttribute(memberService.read(userNo));
	}

	// 수정 처리
	@PostMapping("/modify")
	public String modify(Member member, RedirectAttributes rttr) throws Exception {
		memberService.modify(member);
		rttr.addFlashAttribute("msg", "MODIFY");
		return "redirect:/";
	}
}