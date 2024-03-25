package com.ykomarnytskyi2022.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.ykomarnytskyi2022.excel_services.ParsingAndWritingDelegator;

@SpringBootApplication(scanBasePackages = {"com.ykomarnytskyi2022.config" , "com.ykomarnytskyi2022.web.bootstrap"})
public class ApplicationStarter {
	

		
	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(ApplicationStarter.class, args);
		
//		((ConfigurableApplicationContext) context).close();
			
	}

}

