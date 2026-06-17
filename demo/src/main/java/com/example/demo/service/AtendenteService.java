package com.example.demo.service;

import com.example.demo.dto.VendaDTO;
import com.example.demo.entity.Cardapio;
import com.example.demo.entity.Venda;
import com.example.demo.repository.CardapioRepository;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.FuncionarioRepository;
import com.example.demo.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtendenteService {

    private final VendaRepository vendaRepository;
    private final CardapioRepository cardapioRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;

    public AtendenteService(VendaRepository vendaRepository,
                            CardapioRepository cardapioRepository,
                            ClienteRepository clienteRepository,
                            FuncionarioRepository funcionarioRepository) {
        this.vendaRepository = vendaRepository;
        this.cardapioRepository = cardapioRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public Venda realizarVenda(VendaDTO dto) {

        Venda novaVenda = new Venda();
        novaVenda.setEntregaDelivery(dto.isEntregaDelivery());
        novaVenda.setValorTotal(dto.getValorTotal());
        novaVenda.setDataHora(LocalDateTime.now());

        if (dto.getCardapioIds() != null && !dto.getCardapioIds().isEmpty()) {
            List<Cardapio> itens = cardapioRepository.findAllById(dto.getCardapioIds());
            novaVenda.setCardapioList(itens);
        }

        if (dto.getClienteId() != null) {
            clienteRepository.findById(dto.getClienteId())
                    .ifPresent(clienteBuscado -> novaVenda.setCliente(clienteBuscado));
        }

        if (dto.getAtendenteId() != null) {
            funcionarioRepository.findById(dto.getAtendenteId())
                    .ifPresent(atendenteBuscado -> novaVenda.setAtendente(atendenteBuscado));
        }

        validarVenda(novaVenda);
        return vendaRepository.save(novaVenda);
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    private void validarVenda(Venda novaVenda) {
        if (novaVenda == null) throw new IllegalArgumentException("Os dados da venda são obrigatórios");
        if (novaVenda.getValorTotal() <= 0) throw new IllegalArgumentException("O valor total da venda deve ser maior que zero");
        if (novaVenda.getCardapioList() == null || novaVenda.getCardapioList().isEmpty()) throw new IllegalArgumentException("A venda deve conter ao menos um item do cardápio");
        if (novaVenda.getDataHora() == null) throw new IllegalArgumentException("A data e hora da venda são obrigatórias");
    }
}