package com.ykomarnytskyi2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TrackAndTraceApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TrackAndTraceApplication.class, args);
	}
}
