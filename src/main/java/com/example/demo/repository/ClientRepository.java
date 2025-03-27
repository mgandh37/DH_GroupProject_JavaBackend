package com.example.demo.repository;

import com.example.demo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;

@Repository
public class ClientRepository {

    private final SimpleJdbcCall getClientCall;
    private final SimpleJdbcCall addClientCall;
    private final SimpleJdbcCall updateClientCall;
    private final SimpleJdbcCall deleteClientCall;

    @Autowired
    public ClientRepository(DataSource dataSource) {
        this.getClientCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("CLIENT_INFO_SP")
                .declareParameters(
                        new SqlParameter("p_clientno", Types.VARCHAR),
                        new SqlOutParameter("p_rc", Types.REF_CURSOR)
                );

        this.addClientCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("CLIENT_ADD_SP")
                .declareParameters(
                        new SqlParameter("p_clientno", Types.VARCHAR),
                        new SqlParameter("p_fname", Types.VARCHAR),
                        new SqlParameter("p_lname", Types.VARCHAR),
                        new SqlParameter("p_telno", Types.VARCHAR),
                        new SqlParameter("p_street", Types.VARCHAR),
                        new SqlParameter("p_city", Types.VARCHAR),
                        new SqlParameter("p_email", Types.VARCHAR),
                        new SqlParameter("p_preftype", Types.VARCHAR),
                        new SqlParameter("p_maxrent", Types.INTEGER)
                );

        this.updateClientCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("CLIENT_UPDATE_SP")
                .declareParameters(
                        new SqlParameter("p_clientno", Types.VARCHAR),
                        new SqlParameter("p_fname", Types.VARCHAR),
                        new SqlParameter("p_lname", Types.VARCHAR),
                        new SqlParameter("p_telno", Types.VARCHAR),
                        new SqlParameter("p_street", Types.VARCHAR),
                        new SqlParameter("p_city", Types.VARCHAR),
                        new SqlParameter("p_email", Types.VARCHAR),
                        new SqlParameter("p_preftype", Types.VARCHAR),
                        new SqlParameter("p_maxrent", Types.INTEGER)
                );

        this.deleteClientCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("CLIENT_DELETE_SP")
                .declareParameters(
                        new SqlParameter("p_clientno", Types.VARCHAR)
                );
    }

    public List<Client> getClients(String clientNo) {
        Map<String, Object> in = new HashMap<>();
        in.put("p_clientno", clientNo);

        Map<String, Object> result = getClientCall.execute(in);
        List<Map<String, Object>> rows = (List<Map<String, Object>>) result.get("p_rc");

        List<Client> clients = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Client client = new Client();
            client.setClientNo((String) row.get("CLIENTNO"));
            client.setFname((String) row.get("FNAME"));
            client.setLname((String) row.get("LNAME"));
            client.setTelno((String) row.get("TELNO"));
            client.setStreet((String) row.get("STREET"));
            client.setCity((String) row.get("CITY"));
            client.setEmail((String) row.get("EMAIL"));
            client.setPreftype((String) row.get("PREFTYPE"));
            client.setMaxrent(((Number) row.get("MAXRENT")).intValue());

            clients.add(client);
        }
        return clients;
    }

    public boolean addClient(Client client) {
        try {
            Map<String, Object> params = Map.of(
                    "p_clientno", client.getClientNo(),
                    "p_fname", client.getFname(),
                    "p_lname", client.getLname(),
                    "p_telno", client.getTelno(),
                    "p_street", client.getStreet(),
                    "p_city", client.getCity(),
                    "p_email", client.getEmail(),
                    "p_preftype", client.getPreftype(),
                    "p_maxrent", client.getMaxrent()
            );
            addClientCall.execute(params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateClient(Client client) {
        try {
            Map<String, Object> params = Map.of(
                    "p_clientno", client.getClientNo(),
                    "p_fname", client.getFname(),
                    "p_lname", client.getLname(),
                    "p_telno", client.getTelno(),
                    "p_street", client.getStreet(),
                    "p_city", client.getCity(),
                    "p_email", client.getEmail(),
                    "p_preftype", client.getPreftype(),
                    "p_maxrent", client.getMaxrent()
            );
            updateClientCall.execute(params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteClient(String clientNo) {
        try {
            Map<String, Object> params = Map.of("p_clientno", clientNo);
            deleteClientCall.execute(params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
