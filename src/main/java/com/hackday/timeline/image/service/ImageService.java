package com.hackday.timeline.image.service;

import com.hackday.timeline.image.domain.Image;

public interface ImageService {

	public Image saveImage(String filePath, String fileName);

	public void deleteImage(Long id);

}
