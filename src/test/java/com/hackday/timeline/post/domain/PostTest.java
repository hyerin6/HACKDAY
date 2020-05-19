package com.hackday.timeline.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostTest {

	@Test
	public void getContent(){
		Post post = Post.builder()
			.content("test content")
			.build();

		String content = post.getContent();

		assertEquals("test content", content);
	}

}