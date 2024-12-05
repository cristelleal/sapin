package com.noweldecor.sapins.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BonDeCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private int coutTotal;

    @Column(nullable = false)
    private int poidsTotal;

    @ManyToOne
    @JoinColumn(name = "sapin_id", nullable = false)
    private Sapin sapin;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}