package com.hackday.timeline.subscription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.subscription.domain.Subscription;

@Repository
public interface SubsRepository extends JpaRepository<Subscription, Long> {

	//내가 구독한 사람
	//	@Query(nativeQuery = true, value = "SELECT b.subsNo, a.userNo, a.userName, b.regDate"
	//		+ "FROM  Subscription b"
	//		+ "INNER JOIN Member a ON a.userNo = b.subsMember.userNo "
	//		+ "WHERE a.userNo = ?1 "
	//		+ "ORDER BY b.regDate DESC")
	@Query(nativeQuery = true, value = "SELECT b.subs_no, c.user_no, c.user_name, b.reg_date "
		+ "FROM  subscription b, member a, member c "
		+ "WHERE a.user_no = ?1 AND "
		+ " a.user_no = b.user_no AND c.user_no = b.subs_user_no "
		+ "ORDER BY b.reg_date DESC")
	public List<Object[]> memberSubsList(Long userNo) throws Exception;

	//나를 구독한 사람
	@Query(nativeQuery = true, value = "SELECT b.subs_no, b.user_no, a.user_name, b.reg_date "
		+ "FROM  subscription b, member a "
		+ "WHERE a.user_no = ?1 AND "
		+ " a.user_no = b.user_no "
		+ "ORDER BY b.reg_date DESC")
	public List<Object[]> subsMemberList(Long userNo) throws Exception;

}
