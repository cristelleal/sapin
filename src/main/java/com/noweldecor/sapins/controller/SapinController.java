package com.noweldecor.sapins.controller;

import com.noweldecor.sapins.entity.*;
import com.noweldecor.sapins.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sapin")
public class SapinController {

    @Autowired
    private SapinRepository sapinRepository;
    @Autowired
    private DecorationRepository decorationRepository;
    @Autowired
    private BonDeCommandeRepository bonDeCommandeRepository;
    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/create")
    public long createSapin() {
        Sapin sapin = new Sapin();
        sapinRepository.save(sapin);
        return sapinRepository.count();
    }

    @PostMapping("/addDecoration")
    public boolean addDecoration(@RequestParam Long sapinId, @RequestParam Long decorationId) {
        Optional<Sapin> sapinOpt = sapinRepository.findById(sapinId);
        Optional<Decoration> decorationOpt = decorationRepository.findById(decorationId);

        if (sapinOpt.isEmpty() || decorationOpt.isEmpty()) {
            return false;
        }

        Sapin sapin = sapinOpt.get();
        Decoration decoration = decorationOpt.get();

        if (sapin.isVendu()) {
            return false;
        }

        sapin.getDecorations().add(decoration);
        sapinRepository.save(sapin);
        return true;
    }

    @PostMapping("/vente")
    public BonDeCommande vendreSapin(@RequestParam Long sapinId, @RequestParam Long clientId) {
        Optional<Sapin> sapinOpt = sapinRepository.findById(sapinId);
        Optional<Client> clientOpt = clientRepository.findById(clientId);

        if (sapinOpt.isEmpty() || clientOpt.isEmpty()) {
            return null;
        }

        Sapin sapin = sapinOpt.get();
        Client client = clientOpt.get();

        if (sapin.isVendu()) {
            return null;
        }

        int coutTotal = 0;
        int poidsTotal = 0;

        for (Decoration decoration : sapin.getDecorations()) {
            coutTotal += decoration.getPrixEnCentime();
            poidsTotal += decoration.getPoidsEnGram();
        }

        BonDeCommande bonDeCommande = BonDeCommande.builder()
                .sapin(sapin)
                .client(client)
                .coutTotal(coutTotal)
                .poidsTotal(poidsTotal)
                .adresse(client.getPrenom() + " rue du bonjour")
                .build();

        bonDeCommandeRepository.save(bonDeCommande);

        sapin.setVendu(true);
        sapinRepository.save(sapin);

        int pointsFidelite = (int) (coutTotal * 0.1);
        client.setPointsFidelite(pointsFidelite);
        clientRepository.save(client);

        return bonDeCommande;
    }

    @GetMapping("/commande/get")
    public BonDeCommande getBonDeCommande(@RequestParam Long bonDeCommandeId) {
        Optional<BonDeCommande> bonDeCommandeOpt = bonDeCommandeRepository.findById(bonDeCommandeId);
        return bonDeCommandeOpt.orElse(null);
    }

    @PostMapping("/client")
    public Client createClient(@RequestBody Client client) {
        client.setNom("Joana Smith");
        client.setPrenom("Sponsor");
        clientRepository.save(client);
        return client;
    }

    @GetMapping("/client/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }
}
