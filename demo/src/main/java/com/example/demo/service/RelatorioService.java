package com.example.demo.service;

import com.example.demo.dto.RelatorioFinanceiroDTO;
import com.example.demo.dto.RelatorioGeralDTO;
import com.example.demo.entity.Produto;
import com.example.demo.entity.RegistroCompra;
import com.example.demo.entity.Venda;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.FuncionarioRepository;
import com.example.demo.repository.ProdutoRepository;
import com.example.demo.repository.RegistroCompraRepository;
import com.example.demo.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final VendaRepository vendaRepository;
    private final RegistroCompraRepository registroCompraRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public RelatorioService(VendaRepository vendaRepository,
                            RegistroCompraRepository registroCompraRepository,
                            FuncionarioRepository funcionarioRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.registroCompraRepository = registroCompraRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public RelatorioFinanceiroDTO gerarRelatorioFinanceiro() {
        List<Venda> vendas = vendaRepository.findAll();
        double totalReceitas = vendas.stream().mapToDouble(Venda::getValorTotal).sum();

        List<RegistroCompra> compras = registroCompraRepository.findAll();
        double totalDespesas = compras.stream().mapToDouble(RegistroCompra::getValorGasto).sum();

        return new RelatorioFinanceiroDTO(totalReceitas, totalDespesas, totalReceitas - totalDespesas);
    }

    public RelatorioGeralDTO gerarRelatorioGeral() {
        long totalFuncionarios = funcionarioRepository.count();
        long totalClientes = clienteRepository.count();

        List<Produto> estoqueBaixo = produtoRepository.findAll().stream()
                .filter(produto -> produto.getEstoqueAtual() < 10)
                .collect(Collectors.toList());

        return new RelatorioGeralDTO(totalFuncionarios, totalClientes, estoqueBaixo);
    }
}