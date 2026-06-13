package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        validarCliente(cliente);

        clienteRepository.findByCpf(cliente.getCpf().trim())
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF");
                });

        return clienteRepository.save(cliente);
    }
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Os dados do cliente são obrigatórios");
        }
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório");
        }

        String cpf = cliente.getCpf();
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF do cliente é obrigatório");
        }

        String cpfNumeros = cpf.replaceAll("\\D", "");
        if (cpfNumeros.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos numéricos");
        }
    }
}