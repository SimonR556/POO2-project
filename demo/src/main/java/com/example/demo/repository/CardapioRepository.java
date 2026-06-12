package com.example.demo.repository;

import com.example.demo.entity.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Long> {
    Optional<Cardapio> findByNome(String nome);
}
