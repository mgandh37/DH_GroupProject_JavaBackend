package com.example.demo.controller;

import com.example.demo.model.Branch;
import com.example.demo.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")  // Base API path
@CrossOrigin(origins = "http://localhost:3000")  // Allow React frontend
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // ✅ Get All Branches or a Specific Branch
    @GetMapping
    public List<Branch> getBranches(@RequestParam(required = false) String branchNo) {
        return branchService.getBranches(branchNo);
    }
}
