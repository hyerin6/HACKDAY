package com.hackday.timeline.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	//최초로 조회할 때
	@Query(nativeQuery = true,
		value = "SELECT * FROM Post WHERE user_id = :userId " +
			"ORDER BY reg_date DESC LIMIT 5")
	public List<Post> findTop5ByUserIdOrderByRegDateDesc(Long userId);

	//최초가 아닌 경우
	@Query(nativeQuery = true,
		value = "SELECT * FROM Post " +
			"WHERE user_id = :userId AND id < :id " +
			"ORDER BY id DESC, reg_date DESC LIMIT 5")
	public List<Post> findTop5ByUserIdAndIdLessThanOrderByIdDescRegDateDesc(Long userId, Long id);

	// lastId 조회
	@Query(nativeQuery = true,
		value = "SELECT MIN(id) FROM Post WHERE user_id = :userId")
	public Long findMinIdByUserId(Long userId);

	// 특정 게시글 삭제
	// @Query(nativeQuery = true,
	// 	value = "DELETE FROM Post WHERE id = :id AND user_id : userId")
	public void deleteByIdAndUser_UserId(Long id, Long userId);

}
