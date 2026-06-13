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
        if (ingrediente.getUnidadeMedida() <= 0) {
            throw new IllegalArgumentException("A unidade de medida deve ser maior que zero");
        }
    }
}
