package com.example.demo.service;

import com.example.demo.entity.Estoque;
import com.example.demo.entity.Produto;
import com.example.demo.repository.EstoqueRepository;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GerenteEstoqueService {

    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    // Injetando dois repositórios no construtor
    public GerenteEstoqueService(ProdutoRepository produtoRepository, EstoqueRepository estoqueRepository) {
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public Produto reporEstoque(Long produtoId, int quantidadeComprada) {
        if (produtoId == null || quantidadeComprada <= 0) {
            throw new IllegalArgumentException("Identificador inválido ou quantidade menor/igual a zero.");
        }

        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        produto.setEstoqueAtual(produto.getEstoqueAtual() + quantidadeComprada);
        return produtoRepository.save(produto);
    }

    public Estoque registrarAtualizacao(Estoque estoque) {
        if (estoque == null || estoque.getNomeItem() == null || estoque.getNomeItem().trim().isEmpty()) {
            throw new IllegalArgumentException("Dados do estoque e nome do item são obrigatórios");
        }
        if (estoque.getQuantidadeAtual() < 0 || estoque.getQuantidadePerda() < 0) {
            throw new IllegalArgumentException("Quantidades não podem ser negativas");
        }
        if (estoque.getQuantidadePerda() > 0 && (estoque.getMotivoPerda() == null || estoque.getMotivoPerda().trim().isEmpty())) {
            throw new IllegalArgumentException("Motivo da perda é obrigatório quando há perda registrada");
        }

        estoque.setDataUltimaAtualizacao(LocalDateTime.now());
        return estoqueRepository.save(estoque);
    }

    public List<Estoque> listarHistorico() {
        return estoqueRepository.findAll();
    }
}