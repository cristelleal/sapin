package com.noweldecor.sapins.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sapin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "sapin_decoration",
            joinColumns = @JoinColumn(name = "sapin_id"),
            inverseJoinColumns = @JoinColumn(name = "decoration_id")
    )
    private List<Decoration> decorations;

    @Column(nullable = false)
    private boolean vendu;
}
