package com.example.demo.service;

import com.example.demo.model.Staff;
import com.example.demo.repository.StaffRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
	 @Autowired
	    private StaffRepository staffRepository;

	    public List<Staff> getStaffInfo(String staffno) {
	        return staffRepository.callStaffInfoSp(staffno);
	    }
	    
	    //this is the business logic to delete staff
	    @Transactional
	    public boolean deleteStaff(String staffno) {
	        return staffRepository.deleteStaffById(staffno);
	    }
	    
	    public boolean hireStaff(Staff staff) {
	    	try {
	            System.out.println("Hiring staff: " + staff); // Debug log
	            return staffRepository.hireStaff(staff); // Ensure this method exists
	        } catch (Exception e) {
	            e.printStackTrace(); // Print the full error in the console
	            return false;
	        }
	    }
}
