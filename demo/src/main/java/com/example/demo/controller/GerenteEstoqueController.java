package com.example.demo.controller;

import com.example.demo.entity.Estoque;
import com.example.demo.entity.Ingrediente;
import com.example.demo.entity.Produto;
import com.example.demo.entity.RegistroCompra;
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

    @PostMapping("/produtos")
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produto){
        return ResponseEntity.status(HttpStatus.CREATED).body(gerenteEstoqueService.cadastrarProduto(produto));
    }

    @PostMapping("/ingredientes")
    public ResponseEntity<Ingrediente> cadastrarIngrediente(@RequestBody Ingrediente ingrediente){
        return ResponseEntity.status(HttpStatus.CREATED).body(gerenteEstoqueService.cadastrarIngrediente(ingrediente));
    }

    @PostMapping("/compras")
    public ResponseEntity<RegistroCompra> registrarCompraFornecedor(@RequestBody RegistroCompra compra){
        return ResponseEntity.status(HttpStatus.CREATED).body(gerenteEstoqueService.registrarCompra(compra));
    }
}
