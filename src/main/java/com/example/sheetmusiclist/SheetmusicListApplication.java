package com.example.sheetmusiclist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SheetmusicListApplication {
    public static void main(String[] args) {
        SpringApplication.run(SheetmusicListApplication.class, args);
    }

}
