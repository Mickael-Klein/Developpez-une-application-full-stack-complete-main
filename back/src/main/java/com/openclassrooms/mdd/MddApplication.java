package com.openclassrooms.mdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the MDD (Monde de Dév) application.
 */
@SpringBootApplication
public class MddApplication {

  /**
   * Main method to run the Spring Boot application.
   *
   * @param args Command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(MddApplication.class, args);
  }
}
