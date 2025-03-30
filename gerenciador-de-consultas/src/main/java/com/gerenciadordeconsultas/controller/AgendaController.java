package com.gerenciadordeconsultas.controller;

import com.gerenciadordeconsultas.entity.Agenda;
import com.gerenciadordeconsultas.entity.Medico;
import com.gerenciadordeconsultas.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    public ResponseEntity<Agenda> cadastrarHorario(
            @Valid @RequestBody Agenda agenda) {
        Agenda novaAgenda = agendaService.cadastrarHorario(agenda);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAgenda);
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Agenda>> listarHorariosPorMedico(
            @PathVariable Long medicoId) {
        List<Agenda> agendas = agendaService.listarHorariosPorMedico(medicoId);
        return ResponseEntity.ok(agendas);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Medico>> listarMedicosDisponiveisNoDia(
            @RequestParam String diaSemana) {
        List<Medico> medicos = agendaService.listarMedicosDisponiveisNoDia(
                java.time.DayOfWeek.valueOf(diaSemana.toUpperCase()));
        return ResponseEntity.ok(medicos);
    }
}