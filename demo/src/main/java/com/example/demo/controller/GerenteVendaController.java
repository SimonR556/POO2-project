package com.example.demo.controller;

import com.example.demo.entity.Cardapio;
import com.example.demo.service.GerenteVendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerente-venda")
public class GerenteVendaController {

    private final GerenteVendaService gerenteVendaService;

    public GerenteVendaController(GerenteVendaService gerenteVendaService){
        this.gerenteVendaService = gerenteVendaService;
    }

    @PostMapping("/pratos")
    public ResponseEntity<Cardapio> cadastrarPrato(@RequestBody Cardapio prato){
        return ResponseEntity.status(HttpStatus.CREATED).body(gerenteVendaService.cadastrarPrato(prato));
    }

    @GetMapping("/cardapio")
    public ResponseEntity<List<Cardapio>> listarCardapio(){
        return ResponseEntity.ok(gerenteVendaService.listarCardapio());
    }

    @DeleteMapping("/pratos/{id}")
    public ResponseEntity<Void> excluirPrato(@PathVariable Long id){
        gerenteVendaService.excluirPrato(id);
        return ResponseEntity.noContent().build();
    }
}

