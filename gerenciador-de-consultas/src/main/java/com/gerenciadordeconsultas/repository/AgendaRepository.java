package com.gerenciadordeconsultas.repository;

import com.gerenciadordeconsultas.entity.Agenda;
import com.gerenciadordeconsultas.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    // Busca agenda por médico
    List<Agenda> findByMedico(Medico medico);

    // Busca agenda por médico e dia da semana
    List<Agenda> findByMedicoAndDiaSemana(Medico medico, DayOfWeek diaSemana);

    // Busca médicos que atendem em um determinado dia
    @Query("SELECT a.medico FROM Agenda a WHERE a.diaSemana = :diaSemana")
    List<Medico> findMedicosPorDiaSemana(@Param("diaSemana") DayOfWeek diaSemana);
}