package com.example.demo.entity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registro_compra")
public class RegistroCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataCompra;
    private float valorGasto;
    @ElementCollection
    private List<String> produtosComprados = new ArrayList<>();
    @ElementCollection
    private List<String> ingredientesComprados = new ArrayList<>();

}
