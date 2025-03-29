package com.gerenciadordeconsultas.entity;

import com.gerenciadordeconsultas.entity.Medico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "especialidades")
@Getter
@Setter
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @OneToMany(mappedBy = "especialidade")
    private List<Medico> medicos;

    public Especialidade() {
    }

    public Especialidade(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}