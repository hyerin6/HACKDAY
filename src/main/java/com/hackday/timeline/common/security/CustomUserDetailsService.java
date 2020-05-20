package com.hackday.timeline.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hackday.timeline.common.security.domain.CustomUser;
import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.member.repository.MemberRepository;

import lombok.extern.java.Log;

@Log
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	MemberRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("userName :" + username);

		Member member = repository.findByUserId(username).get(0);
		String name = member.getUserName();
		if (name == null) {
			throw new UsernameNotFoundException("User not authorized.");
		}

		log.info("user: " + member);

		return member == null ? null : new CustomUser(member);

	}
}
