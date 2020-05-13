package com.hackday.timeline.subscription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.subscription.domain.Subscription;

@Repository
public interface SubsRepository extends JpaRepository<Subscription, Long> {

	//내가 구독한 사람
	@Query(nativeQuery = true, value = "SELECT b.subsNo, a.userNo, a.userName, b.regDate"
		+ "FROM  Subscription b"
		+ "INNER JOIN Member a ON a.userNo = b.subsMember.userNo "
		+ "WHERE a.userNo = ?1 "
		+ "ORDER BY b.regDate DESC")
	public List<Object[]> memberSubsList(Long userNo) throws Exception;

	//나를 구독한 사람
	@Query(nativeQuery = true, value = "SELECT b.subsNo, a.userNo, a.userName, b.regDate"
		+ "FROM Subscription b "
		+ "INNER JOIN Member a ON a.userNo = b.member.userNo "
		+ "WHERE a.userNo = ?1 "
		+ "ORDER BY b.regDate DESC")
	public List<Object[]> subsMemberList(Long userNo) throws Exception;

}
