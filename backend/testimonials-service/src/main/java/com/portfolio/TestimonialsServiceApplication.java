package com.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TestimonialsServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestimonialsServiceApplication.class, args);
  }
}
