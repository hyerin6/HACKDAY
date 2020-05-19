package com.hackday.timeline.subscription.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.hackday.timeline.subscription.service.SubsService;
import com.hackday.timeline.testAbstract.AbstractControllerTest;

public class SubsControllerTest extends AbstractControllerTest {
	@Mock
	private SubsService subsService;

	@InjectMocks
	private SubsController subsController;

	@Override
	protected Object controller() {
		return subsController;
	}

	@Test
	public void postRemoveMember() throws Exception {
		mockMvc.perform(
			get("/subs/remove")
				.param("subsNo", "1")
				.param("view", "user"))
			.andDo(print())
			.andExpect(redirectedUrl("/user/list"))
			.andExpect(status().is3xxRedirection());
	}

}
