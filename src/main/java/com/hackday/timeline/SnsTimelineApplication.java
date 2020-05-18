package com.hackday.timeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SnsTimelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsTimelineApplication.class, args);
	}

}
