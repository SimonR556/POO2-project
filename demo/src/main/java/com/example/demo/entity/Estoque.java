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
public class Estoque extends Produto{
    private List <Ingrediente> ingredienteList;
    private List <Produto> produtoList;

    public void adicionarQuantidade(){

    }
    public void retirarQuantidade(){

    }
    public void registrarPerda(){

    }
}
