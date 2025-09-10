package com.teklinica.teklinica.controller;

import com.teklinica.teklinica.model.Receta;
import com.teklinica.teklinica.service.RecetaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaService service;

    public RecetaController(RecetaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Receta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Receta buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public Receta crear(@Valid @RequestBody Receta receta) {
        return service.crear(receta);
    }

    @PutMapping("/{id}")
    public Receta actualizar(@PathVariable Long id, @Valid @RequestBody Receta receta) {
        return service.actualizar(id, receta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
