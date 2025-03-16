package com.example.demo.service;

import com.example.demo.model.Staff;
import com.example.demo.repository.StaffRepository;
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
}
