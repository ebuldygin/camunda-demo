package org.mathcuprum.camunda.demo;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
@EnableProcessApplication
public class CamundaWebDemoApplication {

  public static void main(String... args) {
    Locale.setDefault(Locale.ENGLISH); // Oracle JDBC fix
    SpringApplication.run(CamundaWebDemoApplication.class, args);
  }
}
