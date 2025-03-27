package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CounterService;

@RestController
@RequestMapping("/api/counter")
@CrossOrigin(origins = "http://localhost:3000")
public class CounterController {

    @Autowired
    private CounterService counterService;

    @GetMapping
    public ResponseEntity<Map<String, Integer>> getCounterValues() {
        return ResponseEntity.ok(counterService.getAllCounts());
    }
}

