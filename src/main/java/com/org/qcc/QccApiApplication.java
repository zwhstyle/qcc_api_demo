package com.org.qcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.org.qcc.config"})
public class QccApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QccApiApplication.class, args);
	}
}