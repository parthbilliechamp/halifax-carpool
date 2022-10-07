package com.halifaxcarpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HalifaxCarpoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(HalifaxCarpoolApplication.class, args);
	}

}
