package com.noweldecor.sapins;

import com.noweldecor.sapins.entity.*;
import com.noweldecor.sapins.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class SapinsApplication {

	@Autowired
	DecorationRepository decorationRepository;
	@Autowired
	SapinRepository sapinRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	BonDeCommandeRepository bonDeCommandeRepository;

	public static void main(String[] args) {
		SpringApplication.run(SapinsApplication.class, args);
	}

	@PostConstruct
	public void init() {

		if (decorationRepository.count() == 0) {
			Decoration etoile = decorationRepository.save(
					Decoration.builder()
							.nom("Etoile")
							.prixEnCentime(500)
							.poidsEnGram(100)
							.types(Arrays.asList(EnumDecorationType.TETE_DE_SAPIN))
							.build()
			);

			Decoration ange = decorationRepository.save(
					Decoration.builder()
							.nom("Ange")
							.prixEnCentime(550)
							.poidsEnGram(110)
							.types(Arrays.asList(EnumDecorationType.TETE_DE_SAPIN))
							.build()
			);

			Sapin sapin1 = sapinRepository.save(
					Sapin.builder()
							.decorations(Arrays.asList(etoile, ange))
							.vendu(false)
							.build()
			);

			Client client1 = clientRepository.save(
					Client.builder()
							.nom("Dupont")
							.prenom("Marie")
							.sponsor("Amazon")
							.pointsFidelite(100)
							.build()
			);

			bonDeCommandeRepository.save(
					BonDeCommande.builder()
							.adresse("123 rue des Sapins")
							.coutTotal(1050)
							.poidsTotal(210)
							.sapin(sapin1)
							.client(client1)
							.build()
			);
		}
	}
}
