package com.gerenciadordeconsultas.controller;

import com.gerenciadordeconsultas.entity.Medico;
import com.gerenciadordeconsultas.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public ResponseEntity<Medico> cadastrar(@Valid @RequestBody Medico medico) {
        Medico novoMedico = medicoService.cadastrar(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedico);
    }

    @GetMapping
    public ResponseEntity<List<Medico>> listarTodos() {
        List<Medico> medicos = medicoService.listarTodos();
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscarPorId(@PathVariable Long id) {
        Medico medico = medicoService.buscarPorId(id);
        return ResponseEntity.ok(medico);
    }

    @GetMapping("/especialidade/{especialidadeId}")
    public ResponseEntity<List<Medico>> buscarPorEspecialidade(
            @PathVariable Long especialidadeId) {
        List<Medico> medicos = medicoService.buscarPorEspecialidade(especialidadeId);
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Medico>> buscarDisponiveis(
            @RequestParam String dataHora) {
        List<Medico> medicos = medicoService.buscarDisponiveisPorDataHora(
                java.time.LocalDateTime.parse(dataHora));
        return ResponseEntity.ok(medicos);
    }
}