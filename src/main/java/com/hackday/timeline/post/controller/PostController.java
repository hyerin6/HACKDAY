package com.hackday.timeline.post.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hackday.timeline.common.security.domain.CustomUser;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.dto.InsertPostDto;
import com.hackday.timeline.post.service.PostService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PostController {

	private PostService postService;

	public PostController(PostService postService){
		this.postService = postService;
	}

	// 첫 호출시 5개의 게시글을 보여줍니다.
	@GetMapping("/posts")
	public String myBoard(Authentication authentication, Model model) {
		// 로그인한 유저 정보
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userId = member.getUserNo();
		log.info("GET myBoard userId = " + userId);

		// posts 조회
		List<Post> posts = postService.getPosts(null, userId);

		Long lastIdOfPosts = posts.isEmpty() ?
			null : posts.get(posts.size() - 1).getId();

		// view 에서 필요한 정보 Model에 Add
		model.addAttribute("insertPostDto", new InsertPostDto());
		model.addAttribute("posts", posts);
		model.addAttribute("lastIdOfPosts", lastIdOfPosts);
		model.addAttribute("minIdOfPosts", postService.getMinIdOfPosts(userId));
		model.addAttribute("user", member);

		return "posts/myBoard";
	}

	@PostMapping("/posts")
	public String insertPost(@Valid @ModelAttribute InsertPostDto insertPostDto, BindingResult bindingResult,
		Authentication authentication, Model model) throws IOException {

		if (postService.hasErrors(insertPostDto, bindingResult)) {
			return "posts/myBoard";
		}

		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();

		postService.insertPost(insertPostDto, member);

		return "redirect:/posts";
	}

	@RequestMapping(value = "/posts/{id}", method= {RequestMethod.PATCH, RequestMethod.POST})
	public String modifyPost(@PathVariable("id") Long postId, @ModelAttribute InsertPostDto insertPostDto,
		Authentication authentication, Model model) {

		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();

		postService.modifyPost(insertPostDto, postId, member);

		return "redirect:/posts";
	}

	@GetMapping("/posts/{id}")
	public String deletePost(@PathVariable("id") Long postId, Authentication authentication) {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userId = member.getUserNo();

		postService.deletePost(postId, userId);

		return "redirect:/posts";
	}

}
