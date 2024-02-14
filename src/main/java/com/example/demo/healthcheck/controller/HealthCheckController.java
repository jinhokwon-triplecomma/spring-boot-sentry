package com.example.demo.healthcheck.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Collections.singletonMap("result", "ok"));
    }

    @GetMapping("/500")
    public void internalServerError() {
        throw new NullPointerException("dummy null pointer exception. : " + LocalDateTime.now());
    }
}
