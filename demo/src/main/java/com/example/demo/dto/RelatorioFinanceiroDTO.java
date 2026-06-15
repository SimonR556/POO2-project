package com.example.demo.dto;

public class RelatorioFinanceiroDTO {
    private double totalVendas;
    private double totalDespesas;
    private double lucroLiquido;

    public RelatorioFinanceiroDTO(double totalVendas, double totalDespesas, double lucroLiquido) {
        this.totalVendas = totalVendas;
        this.totalDespesas = totalDespesas;
        this.lucroLiquido = lucroLiquido;
    }

    public double getTotalVendas() { return totalVendas; }
    public double getTotalDespesas() { return totalDespesas; }
    public double getLucroLiquido() { return lucroLiquido; }
}