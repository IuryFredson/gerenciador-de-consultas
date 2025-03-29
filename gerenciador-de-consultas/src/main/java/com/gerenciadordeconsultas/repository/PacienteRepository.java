package com.gerenciadordeconsultas.repository;

import com.gerenciadordeconsultas.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Busca por CPF (único)
    Optional<Paciente> findByCpf(String cpf);

    // Busca por nome (contendo)
    List<Paciente> findByNomeContainingIgnoreCase(String nome);

    // Busca pacientes com consultas futuras
    @Query("SELECT p FROM Paciente p JOIN p.consultas c WHERE c.dataHora > CURRENT_TIMESTAMP")
    List<Paciente> findPacientesComConsultasFuturas();

    // Verifica se email já existe (para validação)
    boolean existsByEmail(String email);

    // Verifica se CPF já existe (para validação)
    boolean existsByCpf(String cpf);
}