package com.example.demo.controller;

import com.example.demo.model.Staff;
import com.example.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    // Get all staff or a specific staff member
    @GetMapping
    public List<Staff> getStaff(@RequestParam(required = false) String staffno) {
        return staffService.getStaffInfo(staffno);
    }
    
    // adding a DELETE endpoint that will allow deleting a staff member by staffno
    @DeleteMapping("/{staffno}")
    public ResponseEntity<String> deleteStaff(@PathVariable String staffno) {
        boolean isDeleted = staffService.deleteStaff(staffno);
        if (isDeleted) {
            return ResponseEntity.ok("Staff member deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Staff member not found.");
        }
    }
    
    @PostMapping("/hire")
    public ResponseEntity<?> hireStaff(@RequestBody Staff staff) {
        System.out.println("Received staff hiring request: " + staff); // Log input

        boolean isHired = staffService.hireStaff(staff);
        if (isHired) {
            return ResponseEntity.ok().body(Map.of("success", true, "message", "Staff member hired successfully!"));
        } else {
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Failed to hire staff member."));
        }
    }
    @GetMapping("/{staffno}")
    public ResponseEntity<?> getStaffById(@PathVariable String staffno) {
        Staff staff = staffService.getStaffById(staffno);
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.status(404).body("Staff member not found.");
        }
    }
    
    @PutMapping("/{staffno}")
    public ResponseEntity<?> updateStaff(
            @PathVariable String staffno, 
            @RequestBody Staff staffDetails) {
        
        boolean isUpdated = staffService.updateStaff(staffno, staffDetails);
        
        if (isUpdated) {
            return ResponseEntity.ok().body(Map.of("success", true, "message", "Staff details updated successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "Staff member not found or update failed."));
        }
    }
    
}
