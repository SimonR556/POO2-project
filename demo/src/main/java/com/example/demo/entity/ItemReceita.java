package com.example.demo.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ItemReceita {

    private double quantidade;
    @ManyToOne
    private Ingrediente ingrediente;
}