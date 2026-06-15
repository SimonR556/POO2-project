package com.example.demo.controller;

import com.example.demo.dto.RelatorioFinanceiroDTO;
import com.example.demo.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/financeiro")
    public ResponseEntity<RelatorioFinanceiroDTO> obterRelatorio() {
        return ResponseEntity.ok(relatorioService.gerarRelatorioFinanceiro());
    }
}