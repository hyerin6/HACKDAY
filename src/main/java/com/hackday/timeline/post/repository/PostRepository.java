package com.hackday.timeline.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	public Post findOneById(Long id);

	@Query(nativeQuery = true,
		value = "SELECT * FROM Post WHERE user_id = :userId " +
			"ORDER BY reg_date DESC LIMIT 5")
	public List<Post> findByUserId(Long userId);

	@Query(nativeQuery = true,
		value = "SELECT * FROM Post " +
			"WHERE user_id = :userId AND id < :id " +
			"ORDER BY id DESC, reg_date DESC LIMIT 5")
	public List<Post> findByIdAndUserId(Long userId, Long id);

	@Query(nativeQuery = true,
		value = "SELECT MIN(id) FROM Post WHERE user_id = :userId")
	public Long findMinIdByUserId(Long userId);

	public void deleteByIdAndUser_UserNo(Long id, Long userId);

	@Query(nativeQuery = true,
		value = "SELECT * FROM Post WHERE user_id "
			+ "IN (SELECT s.subs_user_no FROM subscription s WHERE s.user_no = :userId) "
			+ "OR user_id = :userId "
			+ "ORDER BY :postId DESC, reg_date DESC LIMIT 5")
	public List<Post> findBySubscriptionsUserId(Long postId, Long userId);

	@Query(nativeQuery = true,
		value = "SELECT MIN(id) FROM Post "
			+ "WHERE user_id IN (SELECT s.subs_user_no FROM subscription s WHERE s.user_no = :userId) "
			+ "OR user_id = :userId")
	public Long findMinIdBySubsUserId(Long userId);

	@Query(nativeQuery = true,
		value = "SELECT * FROM Post "
			+ "WHERE user_id IN (SELECT s.subs_user_no FROM subscription s WHERE s.user_no = :userId) "
			+ "AND id < :postId "
			+ "OR user_id = :userId "
			+ "ORDER BY id DESC, reg_date DESC LIMIT 5")
	public List<Post> findByIdAndSubsUserId(Long postId, Long userId);


}
