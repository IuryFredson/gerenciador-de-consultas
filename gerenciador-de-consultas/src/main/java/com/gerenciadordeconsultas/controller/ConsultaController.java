package com.gerenciadordeconsultas.controller;

import com.gerenciadordeconsultas.entity.Consulta;
import com.gerenciadordeconsultas.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<Consulta> agendar(@Valid @RequestBody Consulta consulta) {
        Consulta novaConsulta = consultaService.agendar(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConsulta);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Consulta>> listarPorPaciente(
            @PathVariable Long pacienteId) {
        List<Consulta> consultas = consultaService.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Consulta>> listarPorMedico(
            @PathVariable Long medicoId) {
        List<Consulta> consultas = consultaService.listarPorMedico(medicoId);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> listarPorPeriodo(
            @RequestParam String inicio,
            @RequestParam String fim) {
        List<Consulta> consultas = consultaService.listarPorPeriodo(
                java.time.LocalDateTime.parse(inicio),
                java.time.LocalDateTime.parse(fim));
        return ResponseEntity.ok(consultas);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Consulta> cancelar(
            @PathVariable Long id,
            @RequestParam String motivo) {
        Consulta consulta = consultaService.cancelar(id, motivo);
        return ResponseEntity.ok(consulta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id) {
        Consulta consulta = consultaService.buscarPorId(id);
        return ResponseEntity.ok(consulta);
    }
}