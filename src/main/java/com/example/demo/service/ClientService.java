package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients(String clientNo) {
        return clientRepository.getClients(clientNo);
    }

    public boolean addClient(Client client) {
        return clientRepository.addClient(client);
    }

    public boolean updateClient(Client client) {
        return clientRepository.updateClient(client);
    }

    public boolean deleteClient(String clientNo) {
        return clientRepository.deleteClient(clientNo);
    }
}
