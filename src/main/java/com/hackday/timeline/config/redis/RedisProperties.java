package com.hackday.timeline.config.redis;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class RedisProperties {

	private int redisPort;

	private String redisHost;

	private String redisPassword;

	public RedisProperties(
		@Value("${spring.redis.port}") int redisPort,
		@Value("${spring.redis.host}") String redisHost,
		@Value("${spring.redis.password}") String redisPassword) {

		this.redisPort = redisPort;
		this.redisHost = redisHost;
		this.redisPassword = redisPassword;
	}

}