package com.hackday.timeline.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.image.domain.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	public Image findOneById(Long id);

	public Image save(Image images);

	public void deleteById(Long id);

}
