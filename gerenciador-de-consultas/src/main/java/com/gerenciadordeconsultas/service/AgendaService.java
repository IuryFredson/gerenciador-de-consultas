package com.gerenciadordeconsultas.service;

import com.gerenciadordeconsultas.entity.Agenda;
import com.gerenciadordeconsultas.entity.Medico;
import com.gerenciadordeconsultas.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public Agenda cadastrarHorario(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    public List<Agenda> listarHorariosPorMedico(Long medicoId) {
        Medico medico = new Medico();
        medico.setId(medicoId);
        return agendaRepository.findByMedico(medico);
    }

    public boolean medicoDisponivelNoHorario(Medico medico, LocalDateTime dataHora) {
        DayOfWeek diaSemana = dataHora.getDayOfWeek();
        LocalTime hora = dataHora.toLocalTime();

        List<Agenda> agendas = agendaRepository.findByMedicoAndDiaSemana(medico, diaSemana);

        return agendas.stream()
                .anyMatch(agenda ->
                        !hora.isBefore(agenda.getHoraInicio()) &&
                                !hora.isAfter(agenda.getHoraFim().minusMinutes(30)));
    }

    public List<Medico> listarMedicosDisponiveisNoDia(DayOfWeek diaSemana) {
        return agendaRepository.findMedicosPorDiaSemana(diaSemana);
    }
}