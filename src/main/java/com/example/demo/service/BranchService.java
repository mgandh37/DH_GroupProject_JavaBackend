package com.example.demo.service;

import com.example.demo.model.Branch;
import com.example.demo.model.Staff;
import com.example.demo.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<Branch> getBranches(String branchNo) {
        return branchRepository.getBranchInfo(branchNo);
    }
    
    public boolean addBranch(Branch branch) {
        return branchRepository.addBranch(
            branch.getBranchNo(),
            branch.getStreet(),
            branch.getCity(),
            branch.getPostcode()
        );
    }
    
    public Branch getBranchById(String branchNo) {
        return branchRepository.getBranchById(branchNo);
    }
    
    public boolean updateBranch(Branch branch) {
        return branchRepository.updateBranch(branch);
    }

    public boolean deleteBranch(String branchNo) {
        return branchRepository.deleteBranch(branchNo);
    }
}
