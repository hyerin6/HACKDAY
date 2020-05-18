package com.hackday.timeline.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hackday.timeline.image.domain.Image;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.repository.PostRepository;
import com.hackday.timeline.post.request.InsertPostDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

	@InjectMocks
	private PostServiceImpl postService;

	@Mock
	PostRepository repo;

	List<Post> posts = new ArrayList<>();
	Member member = new Member();
	Image image = Image.builder()
		.id(1L)
		.build();
	Post post = generatePost();

	@Before
	public void setUp() {
		this.member.setUserNo(1L);

		this.post = Post.builder()
			.id(1L)
			.user(this.member)
			.content("test content")
			.regDate(DateUtil.now())
			.updDate(DateUtil.now())
			.image(this.image)
			.build();

		this.posts.add(post);
	}


	@Test
	public void insertPost() {
	}

	@Test
	public void modifyPost() {
	}

	@Test
	public void deletePost() {
	}


	@Test
	public void get() {
		Mockito.when(repo.findTop5ByUserIdOrderByRegDateDesc(1L))
			.thenReturn(this.posts);

		List<Post> postList = postService.get(null, this.post.getUser().getUserNo());

		Mockito.verify(repo).findTop5ByUserIdOrderByRegDateDesc(this.post.getUser().getUserNo());
		assertEquals(posts, postList);
	}

	@Test
	public void getMinIdOfPosts() {
	}

	private Post generatePost() {
		return Post.builder()
			.id(1L)
			.content("modify test content")
			.build();
	}

	private InsertPostDto generateInsertPost() {
		return InsertPostDto.builder()
			.content("modify test content")
			.image(null)
			.build();
	}

}