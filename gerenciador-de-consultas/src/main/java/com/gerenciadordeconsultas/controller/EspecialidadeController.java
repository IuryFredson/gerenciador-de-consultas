package com.gerenciadordeconsultas.controller;

import com.gerenciadordeconsultas.entity.Especialidade;
import com.gerenciadordeconsultas.service.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    @PostMapping
    public ResponseEntity<Especialidade> cadastrar(
            @Valid @RequestBody Especialidade especialidade) {
        Especialidade novaEspecialidade = especialidadeService.cadastrar(especialidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEspecialidade);
    }

    @GetMapping
    public ResponseEntity<List<Especialidade>> listarTodas() {
        List<Especialidade> especialidades = especialidadeService.listarTodas();
        return ResponseEntity.ok(especialidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidade> buscarPorId(@PathVariable Long id) {
        Especialidade especialidade = especialidadeService.buscarPorId(id);
        return ResponseEntity.ok(especialidade);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Especialidade>> buscarPorNome(
            @RequestParam String nome) {
        List<Especialidade> especialidades = especialidadeService.buscarPorNome(nome);
        return ResponseEntity.ok(especialidades);
    }
}