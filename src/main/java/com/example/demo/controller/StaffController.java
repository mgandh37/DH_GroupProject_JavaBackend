package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaffController {

    @GetMapping("/")
    public String hello() {
        return "Hello! This is Staff Controller!";
    }
}