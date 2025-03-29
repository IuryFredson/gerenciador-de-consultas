package com.gerenciadordeconsultas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Getter
@Setter
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusConsulta status = StatusConsulta.AGENDADA;

    @Column(length = 500)
    private String observacoes;

    public enum StatusConsulta {
        AGENDADA, CONFIRMADA, CANCELADA, REALIZADA
    }

    public Consulta() {
    }

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String observacoes) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.observacoes = observacoes;
    }
}