package com.example.demo.repository;
import com.example.demo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;

@Repository
public class CounterRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CounterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getStaffCount() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("STAFF_COUNT_SP")
                .declareParameters(new SqlOutParameter("p_count", Types.INTEGER));

        Map<String, Object> result = call.execute();
        return (int) result.get("p_count");
    }

    public int getBranchCount() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("BRANCH_COUNT_SP")
                .declareParameters(new SqlOutParameter("p_count", Types.INTEGER));

        Map<String, Object> result = call.execute();
        return (int) result.get("p_count");
    }

    public int getClientCount() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CLIENT_COUNT_SP")
                .declareParameters(new SqlOutParameter("p_count", Types.INTEGER));

        Map<String, Object> result = call.execute();
        return (int) result.get("p_count");
    }
}
