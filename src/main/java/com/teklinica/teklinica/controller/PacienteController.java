package com.teklinica.teklinica.controller;

import com.teklinica.teklinica.dto.PacienteResponse;
import com.teklinica.teklinica.model.Paciente;
import com.teklinica.teklinica.service.PacienteService;
import com.teklinica.teklinica.service.MappingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService service;
    private final MappingService mappingService;

    public PacienteController(PacienteService service, MappingService mappingService) {
        this.service = service;
        this.mappingService = mappingService;
    }

    @GetMapping
    public List<PacienteResponse> listar() {
        return service.listar().stream()
                .map(mappingService::mapToPacienteResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PacienteResponse buscar(@PathVariable Long id) {
        Paciente paciente = service.buscar(id);
        return mappingService.mapToPacienteResponse(paciente);
    }

    @PostMapping
    public PacienteResponse crear(@Valid @RequestBody Paciente paciente) {
        Paciente pacienteCreado = service.crear(paciente);
        return mappingService.mapToPacienteResponse(pacienteCreado);
    }

    @PutMapping("/{id}")
    public PacienteResponse actualizar(@PathVariable Long id, @Valid @RequestBody Paciente paciente) {
        Paciente pacienteActualizado = service.actualizar(id, paciente);
        return mappingService.mapToPacienteResponse(pacienteActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
