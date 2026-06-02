package com.example.demo.entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venda {
    private boolean entregaDelivery;
    private double valorTotal;
    private List<Cardapio> cardapioList;
    private String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    public void dataHoraTest(){
        System.out.println(dataHora);
    }
}
