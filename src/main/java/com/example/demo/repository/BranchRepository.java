package com.example.demo.repository;

import com.example.demo.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BranchRepository {
    private final SimpleJdbcCall jdbcCall;

    @Autowired
    public BranchRepository(DataSource dataSource) {
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("BRANCH_INFO_SP")  // ✅ Call the correct SP
                .declareParameters(
                        new SqlParameter("p_branchno", Types.VARCHAR),  // Input parameter
                        new SqlOutParameter("p_rc", Types.REF_CURSOR)  // Output parameter (Cursor)
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
}
