package com.sptrans.mobilidade_urbana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MobilidadeUrbanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobilidadeUrbanaApplication.class, args);
	}

}
