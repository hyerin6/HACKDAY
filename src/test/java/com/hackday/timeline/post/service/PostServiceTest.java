package com.hackday.timeline.post.service;

import static org.junit.Assert.*;

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

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.repository.PostRepository;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

	private List<Post> posts = new ArrayList<>();
	private Post post;
	private Member member = new Member();
	private Long lastIdOfPosts = 1L;

	@InjectMocks
	private PostServiceImpl postService;

	@Mock
	private PostRepository repo;

	@Before
	public void setUp() {
		this.member.setUserNo(1L);

		this.post = Post.builder()
			.id(1L)
			.user(this.member)
			.content("test content")
			.regDate(DateUtil.now())
			.updDate(DateUtil.now())
			.image(null)
			.build();

		this.posts.add(post);
	}

	@Test
	public void 나의_마지막_postId가_null인_경우() {
		Mockito.when(repo.findByUserId(this.member.getUserNo()))
			.thenReturn(this.posts);

		List<Post> postList = postService.get(null, this.member.getUserNo());

		Mockito.verify(repo).findByUserId(this.member.getUserNo());
		assertEquals(posts, postList);
	}

	@Test
	public void 나의_마지막_postId가_null이_아닌_경우() {
		Mockito.when(repo.findByIdAndUserId(this.member.getUserNo(), this.post.getId()))
			.thenReturn(this.posts);

		List<Post> postList = postService.get(this.member.getUserNo(), this.post.getId());

		Mockito.verify(repo).findByIdAndUserId(this.member.getUserNo(),this.post.getId());
		assertEquals(posts, postList);
	}

	@Test
	public void 게시글의_가장작은_id를_찾는지() {
		Mockito.when(repo.findMinIdByUserId(this.member.getUserNo()))
			.thenReturn(this.post.getId());

		Long minIdOfPosts = postService.getMinIdOfPosts(this.member.getUserNo());

		Mockito.verify(repo).findMinIdByUserId(this.member.getUserNo());
		assertEquals(minIdOfPosts, this.post.getId());
	}

	@Test
	public void 친구의_게시글중_가장작은_id를_찾는지() {
		Mockito.when(repo.findMinIdBySubsUserId(this.member.getUserNo()))
			.thenReturn(this.post.getId());

		Long minIdOfPosts = postService.getMinIdOfSubsPosts(this.member.getUserNo());

		Mockito.verify(repo).findMinIdBySubsUserId(this.member.getUserNo());
		assertEquals(minIdOfPosts, this.post.getId());
	}

	@Test
	public void 타임라인의_postId가_null인_경우() {
		Mockito.when(repo.findBySubscriptionsUserId(null, this.member.getUserNo()))
			.thenReturn(this.posts);

		List<Post> postList = postService.getFeeds(null, this.member.getUserNo());

		Mockito.verify(repo).findBySubscriptionsUserId(null, this.member.getUserNo());
		assertEquals(this.posts, postList);
	}

	@Test
	public void 타임라인의_postId가_null이_아닌_경우() {
		Mockito.when(repo.findByIdAndSubsUserId(lastIdOfPosts, this.member.getUserNo()))
			.thenReturn(this.posts);

		List<Post> postList = postService.getFeeds(lastIdOfPosts, this.member.getUserNo());

		Mockito.verify(repo).findByIdAndSubsUserId(lastIdOfPosts, this.member.getUserNo());
		assertEquals(this.posts, postList);
	}

}