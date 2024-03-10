package com.ykomarnytskyi2022.excel_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.ykomarnytskyi2022.config"})
public class ApplicationStarter {
	

		
	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(ApplicationStarter.class, args);
		
		ParsingAndWritingDelegator delegator = context.getBean(ParsingAndWritingDelegator.class);
		
		delegator.readAndWrite();
		((ConfigurableApplicationContext) context).close();
			
		System.out.println("THE END");
	}

}

