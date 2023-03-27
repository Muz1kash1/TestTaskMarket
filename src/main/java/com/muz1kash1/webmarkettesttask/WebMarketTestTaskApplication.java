package com.muz1kash1.webmarkettesttask;

import com.muz1kash1.webmarkettesttask.config.RsaKeyProperties;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.IUserRepo;
import com.muz1kash1.webmarkettesttask.model.dto.SignUpDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class WebMarketTestTaskApplication {
  public static void main(String[] args) {
    SpringApplication.run(WebMarketTestTaskApplication.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner(IUserRepo userRepository, PasswordEncoder encoder){
    return args -> {
      userRepository.addAdmin(
        new SignUpDto(
          "admin",
          "adminMail",
          encoder.encode("adminPassword")
        )
      );
      userRepository.addUser(
        new SignUpDto(
          "user",
          "userMail",
          encoder.encode("password")
        )
      );
    };
  }
}
