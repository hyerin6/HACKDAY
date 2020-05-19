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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Controller
@Api(tags = {"유저 API"})
@SwaggerDefinition(tags = {
	@Tag(name = "유저 API", description = "유저 관리 CRUD")
})
@RequestMapping("/user")
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
	}

	@ApiOperation(value = "회원 가입 화면", notes = "회원 가입 페이지를 보여줍니다.")
	@GetMapping("/register")
	public ModelAndView registerForm(Member member, Model model) throws Exception {
		ModelAndView mv = new ModelAndView();
		model.addAttribute("member", new Member());
		mv.setViewName("thymeleaf/user/register");
		return mv;
	}

	@ApiOperation(value = "회원 가입 요청", notes = "회원 가입을 요청합니다.")
	@PostMapping("/register")
	public ModelAndView register(@Validated Member member, BindingResult result, RedirectAttributes rttr)
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

	@ApiOperation(value = "유저 리스트 화면", notes = "유저 리스트 페이지를 보여줍니다.")
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

	@ApiOperation(value = "프로필 화면", notes = "회원 가입 페이지를 보여줍니다.")
	@GetMapping("/read")
	public ModelAndView read(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();

		Member member = customUser.getMember();
		Long userNo = member.getUserNo();

		member = memberService.read(userNo);
		model.addAttribute("member", member);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("thymeleaf/user/read");
		return mv;
	}

	@ApiOperation(value = "회원 탈퇴 요청", notes = "회원 탈퇴를 요청합니다.")
	@PostMapping("/remove")
	public String remove(Long userNo, RedirectAttributes rttr, Authentication authentication)
		throws Exception {
		memberService.remove(userNo);
		rttr.addFlashAttribute("msg", "REMOE");
		if (authentication != null) {
			authentication.setAuthenticated(false);
		}
		return "redirect:/";
	}

	@ApiOperation(value = "프로필 수정 화면", notes = "프로필 수정 페이지를 보여줍니다.")
	@GetMapping("/modify")
	public ModelAndView modifyForm(Long userNo, Model model) throws Exception {
		model.addAttribute("member", memberService.read(userNo));

		ModelAndView mv = new ModelAndView();
		mv.setViewName("thymeleaf/user/modify");
		return mv;
	}

	@ApiOperation(value = "프로필 수정 요청", notes = "프로필 수정을 요청합니다.")
	@PostMapping("/modify")
	public String modify(Member member, RedirectAttributes rttr) throws Exception {
		memberService.modify(member);
		rttr.addFlashAttribute("msg", "MODIFY");
		return "redirect:/";
	}

}