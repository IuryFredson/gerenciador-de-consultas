package com.gerenciadordeconsultas.service;

import com.gerenciadordeconsultas.entity.*;
import com.gerenciadordeconsultas.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final AgendaService agendaService;
    private final NotificacaoService notificacaoService;

    @Transactional
    public Consulta agendar(Consulta consulta) {
        Paciente paciente = pacienteService.buscarPorId(consulta.getPaciente().getId());
        Medico medico = medicoService.buscarPorId(consulta.getMedico().getId());

        validarHorarioConsulta(consulta.getDataHora());
        validarDisponibilidadeMedico(medico, consulta.getDataHora());
        validarAgendaMedico(medico, consulta.getDataHora());

        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setStatus(Consulta.StatusConsulta.AGENDADA);

        Consulta consultaSalva = consultaRepository.save(consulta);

        notificacaoService.enviarConfirmacaoAgendamento(consultaSalva);

        return consultaSalva;
    }

    public List<Consulta> listarPorPaciente(Long pacienteId) {
        Paciente paciente = pacienteService.buscarPorId(pacienteId);
        return consultaRepository.findByPaciente(paciente);
    }

    public List<Consulta> listarPorMedico(Long medicoId) {
        Medico medico = medicoService.buscarPorId(medicoId);
        return consultaRepository.findByMedico(medico);
    }

    public List<Consulta> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return consultaRepository.findByDataHoraBetween(inicio, fim);
    }

    @Transactional
    public Consulta cancelar(Long id, String motivo) {
        Consulta consulta = buscarPorId(id);

        if (consulta.getStatus() == Consulta.StatusConsulta.CANCELADA) {
            throw new IllegalStateException("Consulta já está cancelada");
        }

        if (consulta.getDataHora().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new IllegalStateException("Cancelamento deve ser feito com pelo menos 24h de antecedência");
        }

        consulta.setStatus(Consulta.StatusConsulta.CANCELADA);
        consulta.setObservacoes(motivo);

        notificacaoService.enviarCancelamentoConsulta(consulta);

        return consultaRepository.save(consulta);
    }

    private void validarHorarioConsulta(LocalDateTime dataHora) {
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data/hora da consulta deve ser futura");
        }

        if (dataHora.getMinute() != 0 && dataHora.getMinute() != 30) {
            throw new IllegalArgumentException("Consultas só podem ser agendadas em horários redondos ou meia-hora");
        }
    }

    private void validarDisponibilidadeMedico(Medico medico, LocalDateTime dataHora) {
        if (consultaRepository.existsByMedicoAndDataHora(medico, dataHora)) {
            throw new IllegalArgumentException("Médico já possui consulta agendada neste horário");
        }
    }

    private void validarAgendaMedico(Medico medico, LocalDateTime dataHora) {
        if (!agendaService.medicoDisponivelNoHorario(medico, dataHora)) {
            throw new IllegalArgumentException("Médico não atende neste horário/dia da semana");
        }
    }

    public Consulta buscarPorId(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
    }
}