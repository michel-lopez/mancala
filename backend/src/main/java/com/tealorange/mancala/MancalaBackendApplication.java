package com.tealorange.mancala;

import com.tealorange.mancala.service.GameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MancalaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MancalaBackendApplication.class, args);
    }

    @Bean
    public GameService service() {
        return new GameService();
    }
}
