package com.halifaxcarpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Configuration
public class HalifaxCarpoolApplication {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@RequestMapping("/check")
	@ResponseBody
	String check() {
		return "check!";
	}


	public static void main(String[] args) {
		SpringApplication.run(HalifaxCarpoolApplication.class, args);
	}

}
