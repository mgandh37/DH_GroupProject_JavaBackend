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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StaffRepository {
	private final SimpleJdbcCall staffInfoCall;
    private final SimpleJdbcCall staffTerminateCall;
    private final SimpleJdbcCall staffHire;
    private final SimpleJdbcCall singlestaffInfoCall;
    private final SimpleJdbcCall staffUpdateCall;

    @Autowired
    public StaffRepository(DataSource dataSource) {
        this.staffInfoCall = new SimpleJdbcCall(dataSource)
                .withCatalogName("") // Oracle does not require a catalog
                .withProcedureName("STAFF_INFO_SP")
                .declareParameters(
                        new SqlParameter("p_staffno", Types.VARCHAR),
                        new SqlOutParameter("p_rc", Types.REF_CURSOR)
                );
        this.staffTerminateCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("STAFF_TERMINATE_SP")
                .declareParameters(
                        new SqlParameter("p_staffno", Types.VARCHAR)
                );
        this.staffHire = new SimpleJdbcCall(dataSource)
                .withProcedureName("STAFF_HIRE_SP") // Call stored procedure
                .declareParameters(
                		new SqlParameter("p_staffno", Types.VARCHAR),
                        new SqlParameter("p_fname", Types.VARCHAR),
                        new SqlParameter("p_lname", Types.VARCHAR),
                        new SqlParameter("p_position", Types.VARCHAR),
                        new SqlParameter("p_sex", Types.VARCHAR),
                        new SqlParameter("p_dob", Types.DATE),
                        new SqlParameter("p_salary", Types.DOUBLE),
                        new SqlParameter("p_branchno", Types.VARCHAR),
                        new SqlParameter("p_telephone", Types.VARCHAR),
                        new SqlParameter("p_mobile", Types.VARCHAR),
                        new SqlParameter("p_email", Types.VARCHAR)
                );
        this.singlestaffInfoCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("STAFF_INFO_SP")
                .declareParameters(
                        new SqlParameter("p_staffno", Types.VARCHAR),
                        new SqlOutParameter("p_rc", Types.REF_CURSOR)
                );
        this.staffUpdateCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("STAFF_UPDATE_SP")
                .declareParameters(
                        new SqlParameter("p_staffno", Types.VARCHAR),
                        new SqlParameter("p_salary", Types.DOUBLE),
                        new SqlParameter("p_telephone", Types.VARCHAR),
                        new SqlParameter("p_email", Types.VARCHAR)
                );
    }

    public List<Staff> callStaffInfoSp(String staffno) {
        Map<String, Object> result = staffInfoCall.execute(staffno);

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
    
    public boolean deleteStaffById(String staffno) {
        try {
            Map<String, Object> result = staffTerminateCall.execute(staffno);
            return true; // Return true if successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if an error occurs
        }
    }
    
    public boolean hireStaff(Staff staff) {
    	try {
        Map<String, Object> result = staffHire.execute(
                staff.getStaffno(),
                staff.getFname(),
                staff.getLname(),
                staff.getPosition(),
                staff.getSex(),
                java.sql.Date.valueOf(staff.getDob()),
                staff.getSalary(),
                staff.getBranchno(),
                staff.getTelephone(),
                staff.getMobile(),
                staff.getEmail()
                
        );

        return true;
    	 } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
    }
    
 // Fetch staff by staff number (single staff)
    public Staff getStaffById(String staffno) {
        Map<String, Object> params = new HashMap<>();
        params.put("p_staffno", staffno);

        Map<String, Object> result = singlestaffInfoCall.execute(params);
        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("p_rc");

        if (rows.isEmpty()) {
            return null;  // No staff found
        }

        Map<String, Object> row = rows.get(0); // Get the first (and only) result
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

        return staff;
    }
    
    public boolean updateStaffInfo(String staffno, Staff staff) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("p_staffno", staffno);
            params.put("p_salary", staff.getSalary());
            params.put("p_telephone", staff.getTelephone());
            params.put("p_email", staff.getEmail());

            staffUpdateCall.execute(params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
