package com.gerenciadordeconsultas.repository;

import com.gerenciadordeconsultas.entity.Consulta;
import com.gerenciadordeconsultas.entity.Medico;
import com.gerenciadordeconsultas.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByPaciente(Paciente paciente);

    List<Consulta> findByMedico(Medico medico);

    List<Consulta> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Consulta c WHERE c.medico = :medico AND c.dataHora = :dataHora AND c.status <> 'CANCELADA'")
    boolean existsByMedicoAndDataHora(
            @Param("medico") Medico medico,
            @Param("dataHora") LocalDateTime dataHora);

    @Query("SELECT c FROM Consulta c WHERE c.medico = :medico AND c.dataHora > CURRENT_TIMESTAMP ORDER BY c.dataHora")
    List<Consulta> findConsultasFuturasPorMedico(@Param("medico") Medico medico);
}