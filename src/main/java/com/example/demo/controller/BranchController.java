package com.example.demo.controller;

import com.example.demo.model.Branch;
import com.example.demo.model.Staff;
import com.example.demo.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/branch")  // Base API path
@CrossOrigin(origins = "http://localhost:3000")  // Allow React frontend
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // Get All Branches or a Specific Branch
    @GetMapping
    public List<Branch> getBranches(@RequestParam(required = false) String branchNo) {
        return branchService.getBranches(branchNo);
    }
    
    @PostMapping("/add-branch")
    public ResponseEntity<?> addBranch(@RequestBody Branch branch) {
        boolean success = branchService.addBranch(branch);
        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Branch added successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Failed to add branch."));
        }
    }
    @PutMapping("/{branchNo}")
    public ResponseEntity<?> updateBranch(@PathVariable String branchNo, @RequestBody Branch branch) {
        branch.setBranchNo(branchNo); // Ensure path variable is used
        boolean success = branchService.updateBranch(branch);
        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Branch updated successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Failed to update branch."));
        }
    }
    
    @GetMapping("/{branchNo}")
    public ResponseEntity<?> getBranchById(@PathVariable String branchNo) {
        Branch branch = branchService.getBranchById(branchNo);
        if (branch != null) {
            return ResponseEntity.ok(branch);
        } else {
            return ResponseEntity.status(404).body("Branch not found.");
        }
    }

    @DeleteMapping("/{branchNo}")
    public ResponseEntity<?> deleteBranch(@PathVariable String branchNo) {
        boolean success = branchService.deleteBranch(branchNo);
        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Branch deleted successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Failed to delete branch."));
        }
    }
}
