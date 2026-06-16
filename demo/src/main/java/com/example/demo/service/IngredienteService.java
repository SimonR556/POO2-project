package com.example.demo.service;

import com.example.demo.entity.Ingrediente;
import com.example.demo.repository.IngredienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    public IngredienteService(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    public Ingrediente cadastrarIngrediente(Ingrediente ingrediente) {
        validarIngrediente(ingrediente);
        return ingredienteRepository.save(ingrediente);
    }

    public List<Ingrediente> listarIngredientes() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente reporEstoque(Long ingredienteId, int quantidadeComprada) {
        if (ingredienteId == null || quantidadeComprada <= 0) {
            throw new IllegalArgumentException("Identificador inválido ou quantidade menor/igual a zero.");
        }

        Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId)
                .orElseThrow(() -> new IllegalArgumentException("Ingrediente não encontrado"));

        ingrediente.setQuantidadeEstoque(ingrediente.getQuantidadeEstoque() + quantidadeComprada);
        return ingredienteRepository.save(ingrediente);
    }

    private void validarIngrediente(Ingrediente ingrediente) {
        if (ingrediente == null) {
            throw new IllegalArgumentException("Os dados do ingrediente são obrigatórios");
        }
        if (ingrediente.getNome() == null || ingrediente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do ingrediente é obrigatório");
        }
        if (ingrediente.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa");
        }
        if (ingrediente.getPrecoUnico() <= 0) {
            throw new IllegalArgumentException("O preço do ingrediente deve ser maior que zero");
        }
        if (ingrediente.getUnidadeMedida() == null || ingrediente.getUnidadeMedida().trim().isEmpty()) {
            throw new IllegalArgumentException("A unidade de medida é obrigatória (ex: kg, L, un)");
        }
    }
}
