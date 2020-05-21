package com.hackday.timeline.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	public List<Member> findByUserId(String username);

	@Query(nativeQuery = true, value = "SELECT EXISTS ("
		+ "SELECT m.userId "
		+ "FROM member m "
		+ "WHERE m.userId=?1"
		+ ") AS SUCCESS")
	public int userIdCheck(String userId) throws Exception;

}
