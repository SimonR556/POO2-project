package com.example.demo.entity;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    private String CPF;
    private String telefone;
    private String nome;
    private Endereco endereco = new Endereco();
}
