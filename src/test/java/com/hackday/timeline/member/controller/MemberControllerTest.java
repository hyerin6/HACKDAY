package com.hackday.timeline.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.service.MemberService;
import com.hackday.timeline.testAbstract.AbstractControllerTest;

class MemberControllerTest extends AbstractControllerTest {

	@Mock
	private MemberService memberService;

	@Mock
	private PasswordEncoder asswordEncoder;

	@InjectMocks
	private MemberController memberController;

	@Override
	protected Object controller() {
		return memberController;
	}

	@Test
	public void getRegistMember() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(
			get("/user/register")
				.param("member", mapper.writeValueAsString(new Member())))
			.andExpect(status().isOk())
			.andExpect(view().name("thymeleaf/user/register"))
			.andExpect(model().attributeExists("member"));
	}

	@Test
	public void postRegistMember() throws Exception {
		mockMvc.perform(
			post("/user/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("userName", "권대환")
				.param("userPw", "1234")
				.param("userId", "kwon"))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));
	}

	@Test
	public void postRemoveMember() throws Exception {
		mockMvc.perform(
			post("/user/remove")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("userNo", "1"))
			.andDo(print())
			.andExpect(redirectedUrl("/"));
	}

	@Test
	public void getModifyBoard() throws Exception {
		mockMvc.perform(
			get("/user/modify")
				.param("userNo", "1"))
			.andExpect(view().name("thymeleaf/user/modify"))
			.andExpect(status().isOk());
	}

	@Test
	public void postsModifyBoard() throws Exception {
		mockMvc.perform(
			post("/user/modify")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("userName", "권대환")
				.param("userPw", "1234")
				.param("userId", "kwon"))
			.andDo(print())
			.andExpect(redirectedUrl("/"))
			.andExpect(status().is3xxRedirection());
	}

}
