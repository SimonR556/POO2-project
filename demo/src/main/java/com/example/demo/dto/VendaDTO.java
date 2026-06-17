package com.example.demo.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDTO {
    private boolean entregaDelivery;
    private double valorTotal;
    private List<Long> cardapioIds;
    private Long clienteId;
    private Long atendenteId;
}