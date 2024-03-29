package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @PostConstruct
    public void onPostConstruct(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
