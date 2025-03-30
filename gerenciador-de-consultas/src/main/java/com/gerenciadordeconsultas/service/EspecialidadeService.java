package com.gerenciadordeconsultas.service;

import com.gerenciadordeconsultas.entity.Especialidade;
import com.gerenciadordeconsultas.repository.EspecialidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    public Especialidade cadastrar(Especialidade especialidade) {
        if (especialidadeRepository.existsByNome(especialidade.getNome())) {
            throw new IllegalArgumentException("Especialidade já cadastrada");
        }
        return especialidadeRepository.save(especialidade);
    }

    public List<Especialidade> listarTodas() {
        return especialidadeRepository.findAll();
    }

    public Especialidade buscarPorId(Long id) {
        return especialidadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada"));
    }

    public List<Especialidade> buscarPorNome(String nome) {
        return especialidadeRepository.findByNomeContainingIgnoreCase(nome);
    }
}