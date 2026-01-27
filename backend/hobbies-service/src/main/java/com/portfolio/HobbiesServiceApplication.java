package com.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HobbiesServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HobbiesServiceApplication.class, args);
  }
}
