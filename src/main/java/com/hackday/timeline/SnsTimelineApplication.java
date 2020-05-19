package com.hackday.timeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SnsTimelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsTimelineApplication.class, args);
	}

}
