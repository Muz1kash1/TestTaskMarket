package com.muz1kash1.webmarkettesttask;

import com.muz1kash1.webmarkettesttask.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class WebMarketTestTaskApplication {
  public static void main(String[] args) {
    SpringApplication.run(WebMarketTestTaskApplication.class, args);
  }

}
