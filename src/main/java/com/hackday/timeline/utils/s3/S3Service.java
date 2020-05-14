package com.hackday.timeline.utils.s3;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hackday.timeline.image.domain.Image;

public interface S3Service {

    public void setS3Client();

    public String upload(MultipartFile file) throws IOException;

    public String postImageUpload(MultipartFile file, String fileName) throws IOException;

    public void deleteFile(Image image);

}
