package com.example.demo.service;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Funcionario;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final FuncionarioRepository funcionarioRepository;
    private final ClienteRepository clienteRepository;

    public AuthService(FuncionarioRepository funcionarioRepository, ClienteRepository clienteRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.clienteRepository = clienteRepository;
    }

    public Object autenticar(String cpf, String senha) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório para o login.");
        }

        String cpfLimpo = cpf.replaceAll("\\D", "");

        Optional<Funcionario> funcOpt = funcionarioRepository.findByCpf(cpfLimpo);
        if (funcOpt.isPresent()) {
            Funcionario funcionario = funcOpt.get();
            if (funcionario.getSenha().equals(senha)) {
                return funcionario;
            } else {
                throw new IllegalArgumentException("Senha incorreta.");
            }
        }

        Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpfLimpo);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();

            return cliente;
        }

        throw new IllegalArgumentException("Usuário não encontrado.");
    }
}