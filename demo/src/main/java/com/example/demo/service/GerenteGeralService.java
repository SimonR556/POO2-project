package com.example.demo.service;

import com.example.demo.entity.Funcionario;
import com.example.demo.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenteGeralService {

    private final FuncionarioRepository funcionarioRepository;

    public GerenteGeralService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Funcionario contratarFuncionario(Funcionario novoFuncionario) {
        validarFuncionario(novoFuncionario);

        funcionarioRepository.findByCpf(novoFuncionario.getCpf().trim()).ifPresent(f -> {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com este CPF");
        });

        return funcionarioRepository.save(novoFuncionario);
    }

    public void demitirFuncionario(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O identificador do funcionário é obrigatório");
        }
        if (!funcionarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Funcionário não encontrado");
        }
        funcionarioRepository.deleteById(id);
    }

    public List<Funcionario> listarEquipe() {
        return funcionarioRepository.findAll();
    }

    private void validarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Os dados do funcionário são obrigatórios");
        }
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do funcionário é obrigatório");
        }
        if (funcionario.getSenha() == null || funcionario.getSenha().trim().length() < 6) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres");
        }

        String cpf = funcionario.getCpf();
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF do funcionário é obrigatório");
        }
        if (cpf.replaceAll("\\D", "").length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos numéricos");
        }
    }
}