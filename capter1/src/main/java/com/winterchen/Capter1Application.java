package com.winterchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Capter1Application {

	public static void main(String[] args) {
		SpringApplication.run(Capter1Application.class, args);
	}
}
