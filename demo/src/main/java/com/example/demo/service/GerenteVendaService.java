package com.example.demo.service;

import com.example.demo.entity.Cardapio;
import com.example.demo.repository.CardapioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenteVendaService {

    private final CardapioRepository cardapioRepository;

    public GerenteVendaService(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    public Cardapio cadastrarPrato(Cardapio prato) {
        validarCardapio(prato);

        cardapioRepository.findByNome(prato.getNome().trim()).ifPresent(c -> {
            throw new IllegalArgumentException("Já existe um prato cadastrado com este nome no cardápio");
        });

        return cardapioRepository.save(prato);
    }

    public void excluirPrato(Long id) {
        if (id == null || !cardapioRepository.existsById(id)) {
            throw new IllegalArgumentException("Prato não encontrado para exclusão");
        }
        cardapioRepository.deleteById(id);
    }

    public List<Cardapio> listarCardapio() {
        return cardapioRepository.findAll();
    }

    private void validarCardapio(Cardapio cardapio) {
        if (cardapio == null) {
            throw new IllegalArgumentException("Os dados do prato são obrigatórios");
        }
        if (cardapio.getNome() == null || cardapio.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do prato é obrigatório");
        }
        if (cardapio.getPrecoVenda() <= 0) {
            throw new IllegalArgumentException("O preço de venda deve ser maior que zero");
        }
    }
}