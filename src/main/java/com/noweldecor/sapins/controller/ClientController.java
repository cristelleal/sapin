package com.noweldecor.sapins.controller;

import com.noweldecor.sapins.entity.Client;
import com.noweldecor.sapins.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/create")
    public Client createClient(@RequestParam String prenom, @RequestParam String nom) {
        Client client = new Client();
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setSponsor("John Smith");
        return clientRepository.save(client);
    }

    @GetMapping("/get")
    public Client getClient(@RequestParam Long clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    @GetMapping("/getAll")
    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }
}