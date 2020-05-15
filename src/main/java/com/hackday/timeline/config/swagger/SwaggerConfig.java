package com.hackday.timeline.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		List<ResponseMessage> responseMessages = new ArrayList<>();
		responseMessages.add(new ResponseMessageBuilder()
			.code(200)
			.message("Success")
			.build());
		responseMessages.add(new ResponseMessageBuilder()
			.code(404)
			.message("Not Found")
			.build());
		responseMessages.add(new ResponseMessageBuilder()
			.code(500)
			.message("Internal Server Error")
			.build());

		return new Docket(DocumentationType.SWAGGER_2)
			.ignoredParameterTypes(AuthenticationPrincipal.class)
			.select()
			.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
			.paths(Predicates.not(PathSelectors.regex("/error.*")))
			.build();
  .globalResponseMessage(RequestMethod.GET, responseMessages);
	}

}
