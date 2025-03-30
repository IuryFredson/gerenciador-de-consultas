package com.gerenciadordeconsultas.service;

import com.gerenciadordeconsultas.entity.Especialidade;
import com.gerenciadordeconsultas.entity.Medico;
import com.gerenciadordeconsultas.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadeService especialidadeService;

    public Medico cadastrar(Medico medico) {
        if (medicoRepository.existsByCrm(medico.getCrm())) {
            throw new IllegalArgumentException("CRM já cadastrado");
        }

        Especialidade especialidade = especialidadeService
                .buscarPorId(medico.getEspecialidade().getId());

        medico.setEspecialidade(especialidade);
        return medicoRepository.save(medico);
    }

    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    public Medico buscarPorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));
    }

    public List<Medico> buscarPorEspecialidade(Long especialidadeId) {
        Especialidade especialidade = especialidadeService.buscarPorId(especialidadeId);
        return medicoRepository.findByEspecialidade(especialidade);
    }

    public List<Medico> buscarDisponiveisPorDataHora(java.time.LocalDateTime dataHora) {
        return medicoRepository.findMedicosDisponiveis(dataHora);
    }
}