package com.gerenciadordeconsultas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String telefone;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Consulta> consultas;

    // Construtores, se não estiver usando Lombok
    public Paciente() {
    }

    public Paciente(String nome, String cpf, String email, LocalDate dataNascimento, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
    }
}