package com.teklinica.teklinica.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teklinica.teklinica.dto.CitaResponse;
import com.teklinica.teklinica.model.Cita;
import com.teklinica.teklinica.service.CitaService;
import com.teklinica.teklinica.service.MappingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService service;
    private final MappingService mappingService;

    public CitaController(CitaService service, MappingService mappingService) {
        this.service = service;
        this.mappingService = mappingService;
    }

    @GetMapping
    public List<CitaResponse> listar() {
        return service.listar().stream()
                .map(mappingService::mapToCitaResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CitaResponse buscar(@PathVariable Long id) {
        Cita cita = service.buscar(id);
        return mappingService.mapToCitaResponse(cita);
    }

    @PostMapping
    public CitaResponse crear(@Valid @RequestBody Cita cita) {
        Cita citaCreada = service.crear(cita);
        return mappingService.mapToCitaResponse(citaCreada);
    }

    @PutMapping("/{id}")
    public CitaResponse actualizar(@PathVariable Long id, @Valid @RequestBody Cita cita) {
        Cita citaActualizada = service.actualizar(id, cita);
        return mappingService.mapToCitaResponse(citaActualizada);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
