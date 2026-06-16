package com.example.demo.service;

import com.example.demo.entity.Venda;
import com.example.demo.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtendenteService {

    private final VendaRepository vendaRepository;

    public AtendenteService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public Venda realizarVenda(Venda venda) {
        validarVenda(venda);
        return vendaRepository.save(venda);
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    private void validarVenda(Venda venda) {
        if (venda == null) {
            throw new IllegalArgumentException("Os dados da venda são obrigatórios");
        }
        if (venda.getValorTotal() <= 0) {
            throw new IllegalArgumentException("O valor total da venda deve ser maior que zero");
        }
        if (venda.getDataHora() == null) {
            throw new IllegalArgumentException("A data e hora da venda são obrigatórias");
        }
    }
}