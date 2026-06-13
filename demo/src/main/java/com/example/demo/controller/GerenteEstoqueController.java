package com.example.demo.controller;

import com.example.demo.entity.Estoque;
import com.example.demo.entity.Produto;
import com.example.demo.service.GerenteEstoqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerente-estoque")
public class GerenteEstoqueController {

    private final GerenteEstoqueService gerenteEstoqueService;

    public GerenteEstoqueController(GerenteEstoqueService gerenteEstoqueService) {
        this.gerenteEstoqueService = gerenteEstoqueService;
    }

    @PutMapping("/produtos/{id}/repor")
    public ResponseEntity<Produto> reporEstoque(@PathVariable Long id, @RequestParam int quantidade){
        return ResponseEntity.ok(gerenteEstoqueService.reporEstoque(id, quantidade));
    }

    @PostMapping("/atualizacoes")
    public ResponseEntity<Estoque> registrarAtualizacao(@RequestBody Estoque estoque){
        return ResponseEntity.status(HttpStatus.CREATED).body(gerenteEstoqueService.registrarAtualizacao(estoque));
    }

    @GetMapping("/historico")
    public ResponseEntity<List<Estoque>> listarHistorico(){
        return ResponseEntity.ok(gerenteEstoqueService.listarHistorico());
    }
}
