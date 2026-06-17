package com.example.demo.controller;

import com.example.demo.dto.VendaDTO;
import com.example.demo.entity.Venda;
import com.example.demo.service.AtendenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendente")
public class AtendenteController {
    private final AtendenteService atendenteService;

    public AtendenteController(AtendenteService atendenteService) {
        this.atendenteService = atendenteService;
    }

    @PostMapping("/vendas")
    public ResponseEntity<Venda> registrarVenda(@RequestBody VendaDTO dto) {
        Venda vendaSalva = atendenteService.realizarVenda(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = atendenteService.listarVendas();

        return ResponseEntity.ok(vendas);
    }
}