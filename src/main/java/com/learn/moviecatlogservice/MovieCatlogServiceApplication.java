package com.learn.moviecatlogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MovieCatlogServiceApplication {

	@Bean
	public WebClient.Builder getWebClient(){
		return  WebClient.builder();
	}

	//create a common instance of RestTemplate using @Bean
	@Bean
	public RestTemplate getRestTemplate(){
		return  new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieCatlogServiceApplication.class, args);
	}

}
