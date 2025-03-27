package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.CounterRepository;

@Service
public class CounterService {

    @Autowired
    private CounterRepository counterRepository;

    public Map<String, Integer> getAllCounts() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("staff", counterRepository.getStaffCount());
        counts.put("branch", counterRepository.getBranchCount());
        counts.put("client", counterRepository.getClientCount());
        return counts;
    }
}
