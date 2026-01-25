package com.uisrael.consumogestionactivosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ConsumogestionactivosapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumogestionactivosapiApplication.class, args);
	}

}
