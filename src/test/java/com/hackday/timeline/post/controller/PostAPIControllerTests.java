package com.hackday.timeline.post.controller;

import static org.assertj.core.util.DateUtil.now;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.hackday.timeline.image.domain.Image;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.service.PostService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class PostAPIControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	Member member = new Member();

	Image image = Image.builder()
		.id(Long.parseLong("1"))
		.fileName("image")
		.filePath("image path")
		.build();

	Post post = Post.builder()
		.id(Long.parseLong("1"))
		.content("test content")
		.user(member)
		.image(image)
		.regDate(now())
		.updDate(now())
		.build();

	List<Post> posts;

	public PostAPIControllerTests(){
		this.member.setUserNo(Long.parseLong("1"));
		this.member.setUserId("hyerin");
		this.member.setUserPw("test123");
		this.member.setUserName("박혜린");
		this.member.setRegDate(now());
		this.member.setUpd_date(now());

		this.posts = new ArrayList<>();
		this.posts.add(this.post);
	}

	@Test
	void getMyPosts() throws Exception {

		Mockito.when(postService.getPosts(Long.parseLong("1"), Long.parseLong("1")))
			.thenReturn(this.posts);

		this.mockMvc.perform((RequestBuilder)post("/api/posts")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].posts.id", is(this.post.getId())))
			.andExpect(jsonPath("$[0].posts.content", is(this.post.getContent())))
			.andExpect(jsonPath("$[0].posts.user", is(this.post.getImage())))
			.andExpect(jsonPath("$[0].posts.image", is(this.post.getImage())))
			.andExpect(jsonPath("$[0].posts.regDate", is(this.post.getRegDate())))
			.andExpect(jsonPath("$[0].posts.updDate", is(this.post.getUpdDate())))
			.andDo(print());
	}

	@Test
	void getPosts() {
	}

	@Test
	void modifyPost() {
	}

}