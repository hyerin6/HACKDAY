package com.hackday.timeline.member.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.timeline.common.security.domain.CustomUser;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.service.MemberService;
import com.hackday.timeline.member.vo.MemberVO;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user")
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
	}

	//가입 화면
	@GetMapping("/register")
	public ModelAndView registerForm(Member member, Model model) throws Exception {
		ModelAndView mv = new ModelAndView();
		model.addAttribute("member", new Member());
		mv.setViewName("thymeleaf/user/register");
		return mv;
	}

	//가입 요청
	@PostMapping("/register")
	public ModelAndView register(@Validated Member member, BindingResult result, Model model, RedirectAttributes rttr)
		throws Exception {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			mv.setViewName("thymeleaf/user/register");
			return mv;
		}

		String inputPassword = member.getUserPw();
		member.setUserPw(passwordEncoder.encode(inputPassword));
		memberService.register(member);

		rttr.addFlashAttribute("msg", "SUCCESS");
		rttr.addFlashAttribute("userName", member.getUserName());

		mv.setViewName("redirect:/");
		return mv;
	}

	//유저 리스트 화면
	@GetMapping("/list")
	public ModelAndView list(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userNo = member.getUserNo();
		List<MemberVO> memberList = memberService.listAll(userNo);
		model.addAttribute("memberList", memberList);
		model.addAttribute("myNo", userNo);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("thymeleaf/user/list");
		return mv;
	}

	//프로필 화면
	@GetMapping("/read")
	public ModelAndView read(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userNo = member.getUserNo();
		model.addAttribute(memberService.read(userNo));

		ModelAndView mv = new ModelAndView();
		mv.setViewName("thymeleaf/user/read");
		return mv;
	}

	@ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 요청합니다.")
	@PostMapping("/remove")
	public String remove(Long userNo, RedirectAttributes rttr) throws Exception {
		memberService.remove(userNo);
		rttr.addFlashAttribute("msg", "REMOVE");

		return "redirect:/";
	}

	// 수정 화면
	@GetMapping("/modify")
	public ModelAndView modifyForm(Long userNo, Model model) throws Exception {
		model.addAttribute(memberService.read(userNo));

		ModelAndView mv = new ModelAndView();
		mv.setViewName("thymeleaf/user/modify");
		return mv;
	}

	// 수정 처리
	@PostMapping("/modify")
	public String modify(Member member, RedirectAttributes rttr) throws Exception {
		memberService.modify(member);
		rttr.addFlashAttribute("msg", "MODIFY");
		return "redirect:/";
	}

}