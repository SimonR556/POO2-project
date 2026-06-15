package com.example.demo.controller;

import com.example.demo.entity.Funcionario;
import com.example.demo.service.GerenteGeralService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerente-geral")
public class GerenteGeralController {

    private final GerenteGeralService gerenteGeralService;

    public GerenteGeralController(GerenteGeralService gerenteService){
        this.gerenteGeralService = gerenteService;
    }

    @PostMapping("/funcionarios")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario funcionario){
        return ResponseEntity.status(HttpStatus.CREATED).body(gerenteGeralService.contratarFuncionario(funcionario));
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<Funcionario>> listarEquipe(){
        return ResponseEntity.ok(gerenteGeralService.listarEquipe());
    }

    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<Void> demitirFuncionario(@PathVariable Long id){
        gerenteGeralService.demitirFuncionario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        return ResponseEntity.ok(gerenteGeralService.atualizarFuncionario(id, funcionario));
    }
}
