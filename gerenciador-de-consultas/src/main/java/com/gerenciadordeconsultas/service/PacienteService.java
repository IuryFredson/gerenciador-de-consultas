package com.gerenciadordeconsultas.service;

import com.gerenciadordeconsultas.entity.Paciente;
import com.gerenciadordeconsultas.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Transactional
    public Paciente cadastrar(Paciente paciente) {
        if (pacienteRepository.existsByCpf(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        if (pacienteRepository.existsByEmail(paciente.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
    }

    public List<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    public Paciente atualizar(Long id, Paciente pacienteAtualizado) {
        Paciente paciente = buscarPorId(id);

        paciente.setNome(pacienteAtualizado.getNome());
        paciente.setTelefone(pacienteAtualizado.getTelefone());
        paciente.setDataNascimento(pacienteAtualizado.getDataNascimento());

        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void excluir(Long id) {
        Paciente paciente = buscarPorId(id);
        pacienteRepository.delete(paciente);
    }
}