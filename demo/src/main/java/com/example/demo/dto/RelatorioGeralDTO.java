package com.example.demo.dto;

import com.example.demo.entity.Produto;
import java.util.List;

public class RelatorioGeralDTO {
    private long totalFuncionarios;
    private long totalClientes;
    private List<Produto> produtosEstoqueBaixo;

    public RelatorioGeralDTO(long totalFuncionarios, long totalClientes, List<Produto> produtosEstoqueBaixo) {
        this.totalFuncionarios = totalFuncionarios;
        this.totalClientes = totalClientes;
        this.produtosEstoqueBaixo = produtosEstoqueBaixo;
    }

    public long getTotalFuncionarios() { return totalFuncionarios; }
    public long getTotalClientes() { return totalClientes; }
    public List<Produto> getProdutosEstoqueBaixo() { return produtosEstoqueBaixo; }
}