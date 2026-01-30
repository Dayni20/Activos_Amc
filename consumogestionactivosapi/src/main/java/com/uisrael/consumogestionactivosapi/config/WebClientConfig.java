package com.uisrael.consumogestionactivosapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration 
public class WebClientConfig { 
	@Bean
	WebClient WebClient(WebClient.Builder builder) {
		return builder.baseUrl("http://localhost:8080/api").build();
	}
	
}