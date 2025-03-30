package com.gerenciadordeconsultas.repository;

import com.gerenciadordeconsultas.entity.Medico;
import com.gerenciadordeconsultas.entity.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Optional<Medico> findByCrm(String crm);

    List<Medico> findByEspecialidade(Especialidade especialidade);

    @Query("SELECT m FROM Medico m WHERE m NOT IN " +
            "(SELECT c.medico FROM Consulta c WHERE c.dataHora = :dataHora AND c.status <> 'CANCELADA')")
    List<Medico> findMedicosDisponiveis(@Param("dataHora") java.time.LocalDateTime dataHora);

    List<Medico> findByNomeContainingIgnoreCase(String nome);

    boolean existsByCrm(String crm);
}