package com.gerenciadordeconsultas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "medicos")
@Getter
@Setter
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String crm;

    @ManyToOne
    @JoinColumn(name = "especialidade_id", nullable = false)
    private Especialidade especialidade;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL)
    private List<Agenda> agendas;

    public Medico() {
    }

    public Medico(String nome, String crm, Especialidade especialidade, String telefone, String email) {
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
        this.telefone = telefone;
        this.email = email;
    }
}