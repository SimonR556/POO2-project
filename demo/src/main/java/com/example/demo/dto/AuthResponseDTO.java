package com.example.demo.dto;

public class AuthResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String tipo;
    private String cargo;

    public AuthResponseDTO(Long id, String nome, String cpf, String tipo, String cargo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.tipo = tipo;
        this.cargo = cargo;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTipo() { return tipo; }
    public String getCargo() { return cargo; }
}
