package org.liferando;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class GameOfThreeApp {

    public static void main(String[] args) {
        SpringApplication.run(GameOfThreeApp.class, args);
    }
}
