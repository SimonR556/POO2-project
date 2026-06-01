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
public class RegistroCompra {
    private int dataCompra;
    private float valorGasto;

    private produtosComprados <List>produtosComprados;
    private IngredientesComprados <List>IngredientesComprados;

    public void confirmarEntrega(){

    }

    public void CencelarCompra(){

    }
}
