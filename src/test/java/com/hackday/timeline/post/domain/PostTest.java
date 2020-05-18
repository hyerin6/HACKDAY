package com.hackday.timeline.post.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostTest {

	@Test
	public void getContent(){
		final Post post = Post.builder()
			.content("test content")
			.build();

		final String content = post.getContent();

		assertEquals("test content", content);
	}

}