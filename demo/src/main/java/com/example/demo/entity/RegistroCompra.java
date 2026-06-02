package com.example.demo.entity;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;        

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroCompra {
    private int dataCompra;
    private float valorGasto;
    private List<String> produtosComprados = new ArrayList<>();
    private List<String> ingredientesComprados = new ArrayList<>();

    public void confirmarEntrega(){

    }

    public void CencelarCompra(){

    }
}
