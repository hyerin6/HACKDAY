package com.hackday.timeline.image.service;

import com.hackday.timeline.image.repository.ImageRepository;

public class ImageServiceImpl implements ImageService {

	private ImageRepository imageRepository;

	public ImageServiceImpl(ImageRepository imageRepository){
		this.imageRepository = imageRepository;
	}

}
