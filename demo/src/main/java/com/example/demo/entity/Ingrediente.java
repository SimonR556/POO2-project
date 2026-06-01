package com.example.demo.entity;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ingrediente {
    private String nome;
    private int quantidadeEstoque;
    private float UnidadeMedida;
    private float precoUnico;
}
