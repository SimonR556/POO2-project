package com.example.demo.entity;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prato extends Cardapio{
    private itemReceita <List> ItemReceita;

    public void calcularGasto(){

    }
}
