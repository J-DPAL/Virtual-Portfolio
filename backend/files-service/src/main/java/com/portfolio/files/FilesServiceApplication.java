package com.portfolio.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FilesServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(FilesServiceApplication.class, args);
  }
}
