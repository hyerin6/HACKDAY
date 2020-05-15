package com.hackday.timeline.utils.s3;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hackday.timeline.image.domain.Image;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class S3ServiceImpl implements S3Service {

	private AmazonS3 s3Client;

	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.region.static}")
	private String region;

	@PostConstruct
	@Override
	public void setS3Client() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

		s3Client = AmazonS3ClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.withRegion(this.region)
			.build();
	}

	@Override
	public String upload(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();

		s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
			.withCannedAcl(CannedAccessControlList.PublicRead));

		return s3Client.getUrl(bucket, fileName).toString();
	}

	@Override
	public String postImageUpload(MultipartFile file, String fileName) throws IOException {
		String path = bucket.concat("/timeline-images");

		s3Client.putObject(new PutObjectRequest(path, fileName, file.getInputStream(), null)
			.withCannedAcl(CannedAccessControlList.PublicRead));

		return s3Client.getUrl(path, fileName).toString();
	}

	@Override
	public void deleteFile(Image image) {
		s3Client.deleteObject(new DeleteObjectRequest(
			bucket + "/timeline-images",
			image.getFileName()));
	}

}