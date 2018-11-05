package com.centralesupelec.osy2018.myseries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyseriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyseriesApplication.class, args);
    }
}
