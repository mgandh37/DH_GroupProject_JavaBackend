package com.example.demo.repository;

import com.example.demo.model.Branch;
import com.example.demo.model.Staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BranchRepository {
    private final SimpleJdbcCall jdbcCall;
    private final SimpleJdbcCall addBranchCall;
    private final SimpleJdbcCall singleBranchInfoCall;
    private final SimpleJdbcCall updateBranchCall;
    private final SimpleJdbcCall deleteBranchCall;

    @Autowired
    public BranchRepository(DataSource dataSource) {
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("BRANCH_INFO_SP")  // Call the correct SP
                .declareParameters(
                        new SqlParameter("p_branchno", Types.VARCHAR),  // Input parameter
                        new SqlOutParameter("p_rc", Types.REF_CURSOR)  // Output parameter (Cursor)
                );
        this.addBranchCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("ADD_BRANCH_SP")
                .declareParameters(
                        new SqlParameter("p_branchno", Types.VARCHAR),
                        new SqlParameter("p_street", Types.VARCHAR),
                        new SqlParameter("p_city", Types.VARCHAR),
                        new SqlParameter("p_postcode", Types.VARCHAR)
                );
        this.updateBranchCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("UPDATE_BRANCH_DETAILS_SP")
                .declareParameters(
                    new SqlParameter("p_branchno", Types.VARCHAR),
                    new SqlParameter("p_street", Types.VARCHAR),
                    new SqlParameter("p_city", Types.VARCHAR),
                    new SqlParameter("p_postcode", Types.VARCHAR)
                );
        this.deleteBranchCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("DELETE_BRANCH_SP")
                .declareParameters(
                    new SqlParameter("p_branchno", Types.VARCHAR)
                );
        this.singleBranchInfoCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("BRANCH_INFO_SP")
                .declareParameters(
                        new SqlParameter("p_branchno", Types.VARCHAR),
                        new SqlOutParameter("p_rc", Types.REF_CURSOR)
                );
    }

    public List<Branch> getBranchInfo(String branchno) {
        Map<String, Object> result = jdbcCall.execute(branchno);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("p_rc");
        List<Branch> branchList = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Branch branch = new Branch();
            branch.setBranchNo((String) row.get("BRANCHNO"));
            branch.setStreet((String) row.get("STREET"));
            branch.setCity((String) row.get("CITY"));
            branch.setPostcode((String) row.get("POSTCODE"));
            branchList.add(branch);
        }
        return branchList;
    }
    
    public Branch getBranchById(String branchno) {
        Map<String, Object> params = new HashMap<>();
        params.put("p_branchno", branchno);

        Map<String, Object> result = singleBranchInfoCall.execute(params);
        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("p_rc");

        if (rows.isEmpty()) {
            return null;  // No staff found
        }

        Map<String, Object> row = rows.get(0); // Get the first (and only) result
        Branch branch = new Branch();
        branch.setBranchNo((String) row.get("BRANCHNO"));
        branch.setStreet((String) row.get("STREET"));
        branch.setCity((String) row.get("CITY"));
        branch.setPostcode((String) row.get("POSTCODE"));

        return branch;
    }
    
    public boolean addBranch(String branchNo, String street, String city, String postcode) {
        try {
            Map<String, Object> inParams = new HashMap<>();
            inParams.put("p_branchno", branchNo);
            inParams.put("p_street", street);
            inParams.put("p_city", city);
            inParams.put("p_postcode", postcode);

            addBranchCall.execute(inParams);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateBranch(Branch branch) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_branchno", branch.getBranchNo());
            params.put("p_street", branch.getStreet());
            params.put("p_city", branch.getCity());
            params.put("p_postcode", branch.getPostcode());

            updateBranchCall.execute(params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteBranch(String branchNo) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_branchno", branchNo);

            deleteBranchCall.execute(params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
}
