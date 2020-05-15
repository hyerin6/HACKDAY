package com.hackday.timeline.subscription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.member.domain.Member;
import com.hackday.timeline.subscription.domain.Subscription;

@Repository
public interface SubsRepository extends JpaRepository<Subscription, Long> {
	//내가 구독한 사람
	@Query("SELECT b.subsNo, c.userNo, c.userId, c.userName, b.regDate "
		+ "FROM Subscription b INNER JOIN b.member a "
		+ "INNER JOIN b.subsMember c "
		+ "WHERE a.userNo = ?1 "
		+ "ORDER BY a.regDate DESC")
	public List<Object[]> memberSubsList(Long userNo) throws Exception;

	//나를 구독한 사람
	@Query("SELECT b.subsNo, a.userNo, a.userId, a.userName, b.regDate "
		+ "FROM Subscription b INNER JOIN b.member a "
		+ "INNER JOIN b.subsMember c "
		+ "WHERE c.userNo = ?1 "
		+ "ORDER BY a.regDate DESC")
	public List<Object[]> subsMemberList(Long userNo) throws Exception;

	public void deleteByMember(Member member) throws Exception;

}
