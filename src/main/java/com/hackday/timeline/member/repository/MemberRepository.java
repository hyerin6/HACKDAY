package com.hackday.timeline.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackday.timeline.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public List<Member> findByUserId(String username);

}
