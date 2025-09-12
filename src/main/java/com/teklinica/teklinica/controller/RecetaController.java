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

import com.teklinica.teklinica.dto.RecetaResponse;
import com.teklinica.teklinica.model.Receta;
import com.teklinica.teklinica.service.MappingService;
import com.teklinica.teklinica.service.RecetaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaService service;
    private final MappingService mappingService;

    public RecetaController(RecetaService service, MappingService mappingService) {
        this.service = service;
        this.mappingService = mappingService;
    }

    @GetMapping
    public List<RecetaResponse> listar() {
        return service.listar().stream()
                .map(mappingService::mapToRecetaResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RecetaResponse buscar(@PathVariable Long id) {
        Receta receta = service.buscar(id);
        return mappingService.mapToRecetaResponse(receta);
    }

    @PostMapping
    public RecetaResponse crear(@Valid @RequestBody Receta receta) {
        Receta recetaCreada = service.crear(receta);
        return mappingService.mapToRecetaResponse(recetaCreada);
    }

    @PutMapping("/{id}")
    public RecetaResponse actualizar(@PathVariable Long id, @Valid @RequestBody Receta receta) {
        Receta recetaActualizada = service.actualizar(id, receta);
        return mappingService.mapToRecetaResponse(recetaActualizada);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
