package com.hackday.timeline.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.image.domain.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	// 이미지 저장
	public Image save(Image images);

	// 이미지 조회 - one
	public Image findOneById(Long id);

	// 이미지 삭제
	public void deleteById(Long id);

}
