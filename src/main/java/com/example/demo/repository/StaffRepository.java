package com.example.demo.repository;

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
import java.util.List;
import java.util.Map;

@Repository
public class StaffRepository {
	private SimpleJdbcCall jdbcCall;

    @Autowired
    public StaffRepository(DataSource dataSource) {
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withCatalogName("") // Oracle does not require a catalog
                .withProcedureName("STAFF_INFO_SP")
                .declareParameters(
                        new SqlParameter("p_staffno", Types.VARCHAR),
                        new SqlOutParameter("p_rc", Types.REF_CURSOR)
                );
    }

    public List<Staff> callStaffInfoSp(String staffno) {
        Map<String, Object> result = jdbcCall.execute(staffno);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("p_rc");
        List<Staff> staffList = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Staff staff = new Staff();
            staff.setStaffno((String) row.get("STAFFNO"));
            staff.setFname((String) row.get("FNAME"));
            staff.setLname((String) row.get("LNAME"));
            staff.setPosition((String) row.get("POSITION"));
            staff.setSex((String) row.get("SEX"));
            staff.setDob(row.get("DOB").toString());
            staff.setSalary(((Number) row.get("SALARY")).doubleValue());
            staff.setBranchno((String) row.get("BRANCHNO"));
            staff.setTelephone((String) row.get("TELEPHONE"));
            staff.setMobile((String) row.get("MOBILE"));
            staff.setEmail((String) row.get("EMAIL"));

            staffList.add(staff);
        }
        return staffList;
    }
}
