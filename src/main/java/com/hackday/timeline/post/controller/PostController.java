package com.hackday.timeline.post.controller;

import java.io.IOException;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.hackday.timeline.common.security.domain.CustomUser;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.service.MemberService;
import com.hackday.timeline.post.domain.Post;
import com.hackday.timeline.post.request.InsertPostDto;
import com.hackday.timeline.post.service.PostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Api(tags = {"게시글 조회 & 삭제 API"})
@SwaggerDefinition(tags = {
	@Tag(name = "게시글 조회 & 삭제 API", description = "READ & DELETE")
})
public class PostController {

	private final PostService postService;

	private final MemberService memberService;

	public PostController(PostService postService, MemberService memberService) {
		this.postService = postService;
		this.memberService = memberService;
	}

	@ApiOperation(value = "게시글 호출", notes = "스크롤이 끝나면 호출됩니다.")
	@GetMapping("/posts")
	public String myBoard(Authentication authentication, Model model) {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userId = member.getUserNo();

		List<Post> posts = postService.getPosts(null, userId);

		Long lastIdOfPosts = CollectionUtils.isEmpty(posts) ?
			null : posts.get(posts.size() - 1).getId();

		model.addAttribute("insertPostDto", new InsertPostDto());
		model.addAttribute("posts", posts);
		model.addAttribute("lastIdOfPosts", lastIdOfPosts);
		model.addAttribute("minIdOfPosts", postService.getMinIdOfPosts(userId).getMinIdOfPosts()
		);
		model.addAttribute("user", member);

		return "posts/myBoard";
	}

	@ApiOperation(value = "게시글 작성", notes = "글과 이미지를 저장합니다.")
	@PostMapping("/posts")
	public String insertPost(@Valid @ModelAttribute InsertPostDto insertPostDto, BindingResult bindingResult,
		Authentication authentication, Model model) {

		if (postService.hasErrors(insertPostDto, bindingResult)) {
			return "posts/myBoard";
		}

		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();

		try {
			postService.insertPost(insertPostDto, member);
		} catch (IOException e) {
			log.error("Insert Image Error : " + e);
			return "fail";
		}

		return "redirect:/posts";
	}

	@ApiOperation(value = "게시글 삭제", notes = "PathVariable로 받은 id 값의 post를 삭제합니다.")
	@GetMapping("/posts/{id}")
	public String deletePost(@PathVariable("id") Long postId, Authentication authentication) {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userId = member.getUserNo();

		postService.deletePost(postId, userId);

		return "redirect:/posts";
	}

	@ApiOperation(value = "다른 사용자의 게시글 조회", notes = "PathVariable로 받은 userId 값의 post 목록을 조회합니다.")
	@GetMapping("/{id}/feeds")
	public String getFeeds(@PathVariable("id") Long userId, Model model) {
		List<Post> posts = postService.getPosts(null, userId);

		if (posts.isEmpty()) {
			try {
				model.addAttribute("user", memberService.read(userId));
				return "posts/empty";
			} catch (Exception e) {
				log.error("Read Member Error : " + e);
				return "fail";
			}
		}

		Long lastIdOfPosts = posts.isEmpty() ?
			null : posts.get(posts.size() - 1).getId();

		model.addAttribute("posts", posts);
		model.addAttribute("lastIdOfPosts", lastIdOfPosts);
		model.addAttribute("minIdOfPosts", postService.getMinIdOfPosts(userId).getMinIdOfPosts());

		try {
			model.addAttribute("user", memberService.read(userId));
			return "posts/userBoard";
		} catch (Exception e) {
			log.error("Read Member Error : " + e);
			return "fail";
		}

	}

	@ApiOperation(value = "타임라인", notes = "내가 구독한 사용자들의 게시글을 모아볼 수 있습니다.")
	@GetMapping("/timeline/feeds")
	public String getTimeline(Authentication authentication, Model model) {
		CustomUser customUser = (CustomUser)authentication.getPrincipal();
		Member member = customUser.getMember();
		Long userId = member.getUserNo();

		List<Post> posts = postService.getFeeds(null, userId);

		Long lastIdOfPosts = posts.isEmpty() ?
			null : posts.get(posts.size() - 1).getId();

		model.addAttribute("posts", posts);
		model.addAttribute("lastIdOfPosts", lastIdOfPosts);
		model.addAttribute("user", member);
		model.addAttribute("minIdOfSubsPosts", postService.getMinIdOfSubsPosts(member.getUserNo()).getMinIdOfPosts());

		return "posts/timeline";
	}

	@ApiOperation(value = "Fail", notes = "서비스 기능에 오류가 발생할 경우 출력되는 화면입니다.")
	@GetMapping("/fail")
	public String fail(){
		return "/posts/fail";
	}

}