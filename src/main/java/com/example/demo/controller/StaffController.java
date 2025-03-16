package com.example.demo.controller;

import com.example.demo.model.Staff;
import com.example.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
