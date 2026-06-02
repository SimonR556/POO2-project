package com.example.demo.entity;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prato extends Cardapio{
    private List <ItemReceita> itemReceitaList;

    public void calcularGasto(){

    }
}
