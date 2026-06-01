package com.example.demo.entity;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cardapio {
    private String nome;
    private float precoVenda;
    private String descricao;
}
