package com.example.demo.controller;

import com.example.demo.model.Branch;
import com.example.demo.model.Client;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "http://localhost:3000")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClients(@RequestParam(required = false) String clientNo) {
        return clientService.getClients(clientNo);
    }
    
    @GetMapping("/{clientNo}")
    public ResponseEntity<?> getClientById(@PathVariable String clientNo) {
        Client client = clientService.getClientById(clientNo);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(404).body("Branch not found.");
        }
    }

    @PostMapping("/add-client")
    public ResponseEntity<?> addClient(@RequestBody Client client) {
        boolean success = clientService.addClient(client);
        Map<String, Object> response = new HashMap<>();
        
        if (success) {
            response.put("success", true);
            response.put("message", "Client added successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Failed to add client.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{clientNo}")
    public ResponseEntity<?> updateClient(@RequestBody Client client) {
        boolean success = clientService.updateClient(client);
        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Client updated successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Failed to update client."));
        }
    }

    @DeleteMapping("/{clientNo}")
    public ResponseEntity<?> deleteClient(@PathVariable String clientNo) {
        boolean success = clientService.deleteClient(clientNo);
        if (success) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Client deleted successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Failed to delete client."));
        }
    }
}
