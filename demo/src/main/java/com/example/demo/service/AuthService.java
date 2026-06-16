package com.example.demo.service;

import com.example.demo.dto.AuthResponseDTO;
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

    public AuthResponseDTO autenticar(String cpf, String senha) {
        validarDadosLogin(cpf, senha);

        String cpfLimpo = cpf.replaceAll("\\D", "");

        Optional<Funcionario> funcOpt = funcionarioRepository.findByCpf(cpfLimpo);
        if (funcOpt.isPresent()) {
            return autenticarFuncionario(funcOpt.get(), senha);
        }

        Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpfLimpo);
        if (clienteOpt.isPresent()) {
            return autenticarCliente(clienteOpt.get());
        }

        throw new IllegalArgumentException("Usuário não encontrado.");
    }

    private void validarDadosLogin(String cpf, String senha) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório para o login.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória para o login.");
        }
    }

    private AuthResponseDTO autenticarFuncionario(Funcionario funcionario, String senha) {
        if (!funcionario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha incorreta.");
        }
        return new AuthResponseDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCpf(),
                "FUNCIONARIO",
                funcionario.getCargo()
        );
    }

    private AuthResponseDTO autenticarCliente(Cliente cliente) {
        return new AuthResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                "CLIENTE",
                null
        );
    }
}
