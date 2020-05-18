package com.hackday.timeline.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.java.Log;

@Log
@ControllerAdvice
public class CommonExceptionHandler {

	//접근 거부 예외 처리
	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		if (isAjax(request)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			throw ex;
		}
	}

	//일반 예외 처리
	@ExceptionHandler(Exception.class)
	public ModelAndView handle(Exception ex, Model model) {
		log.info("handle exception " + ex.toString());
		ModelAndView mv = new ModelAndView();
		model.addAttribute("exception", ex);
		mv.setViewName("thymeleaf/error/errorCommon");
		return mv;
	}

	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public void handleUsernameNotFoundException(UsernameNotFoundException ex) throws Exception {
		log.info("User is not Exist");
		throw ex;
	}

}
