package com.example.springwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import reactor.blockhound.BlockHound;

@SpringBootApplication
public class SpringWebfluxApplication {

	public static void main(String[] args) {
//		BlockHound
//				.builder()
//				.allowBlockingCallsInside(
//						TemplateEngine.class.getCanonicalName(), "initialize"
//				)
//				.install();

		SpringApplication.run(SpringWebfluxApplication.class, args);
	}

}
