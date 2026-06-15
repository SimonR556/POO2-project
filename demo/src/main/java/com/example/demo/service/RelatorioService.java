package com.example.demo.service;

import com.example.demo.dto.RelatorioFinanceiroDTO;
import com.example.demo.entity.RegistroCompra;
import com.example.demo.entity.Venda;
import com.example.demo.repository.RegistroCompraRepository;
import com.example.demo.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    private final VendaRepository vendaRepository;
    private final RegistroCompraRepository registroCompraRepository;

    public RelatorioService(VendaRepository vendaRepository, RegistroCompraRepository registroCompraRepository) {
        this.vendaRepository = vendaRepository;
        this.registroCompraRepository = registroCompraRepository;
    }

    public RelatorioFinanceiroDTO gerarRelatorioFinanceiro() {

        List<Venda> vendas = vendaRepository.findAll();
        double totalReceitas = vendas.stream()
                .mapToDouble(Venda::getValorTotal)
                .sum();

        List<RegistroCompra> compras = registroCompraRepository.findAll();
        double totalDespesas = compras.stream()
                .mapToDouble(RegistroCompra::getValorGasto)
                .sum();

        double lucroLiquido = totalReceitas - totalDespesas;

        return new RelatorioFinanceiroDTO(totalReceitas, totalDespesas, lucroLiquido);
    }
}