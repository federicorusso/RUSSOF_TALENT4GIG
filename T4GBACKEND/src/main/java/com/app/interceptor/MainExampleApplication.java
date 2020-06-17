package com.app.interceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainExampleApplication {

	public static void main(String[] args) {
		System.out.println("Launching main application!");
		SpringApplication.run(MainExampleApplication.class, args);
	}
}