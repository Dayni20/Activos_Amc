package com.uisrael.consumogestionactivosapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.uisrael.consumogestionactivosapi.security.AuthInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;

	public WebMvcConfig(AuthInterceptor authInterceptor) {
		this.authInterceptor = authInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns(
						"/login",
						"/logout",
						"/assets/**",
						"/css/**",
						"/js/**",
						"/images/**",
						"/error"
				);
	}
}
