package com.csse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class CsseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsseApplication.class, args);
	}
//
//	@PostMapping("/test")
//	public String testing(){
//		return "Post mapping success";
//	}

}