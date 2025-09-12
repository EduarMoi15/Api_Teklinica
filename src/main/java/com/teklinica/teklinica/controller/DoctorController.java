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

import com.teklinica.teklinica.dto.DoctorResponse;
import com.teklinica.teklinica.model.Doctor;
import com.teklinica.teklinica.service.DoctorService;
import com.teklinica.teklinica.service.MappingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctores")
public class DoctorController {

    private final DoctorService service;
    private final MappingService mappingService;

    public DoctorController(DoctorService service, MappingService mappingService) {
        this.service = service;
        this.mappingService = mappingService;
    }

    @GetMapping
    public List<DoctorResponse> listar() {
        return service.listar().stream()
                .map(mappingService::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DoctorResponse buscar(@PathVariable Long id) {
        Doctor doctor = service.buscar(id);
        return mappingService.mapToDoctorResponse(doctor);
    }

    @PostMapping
    public DoctorResponse crear(@Valid @RequestBody Doctor doctor) {
        Doctor doctorCreado = service.crear(doctor);
        return mappingService.mapToDoctorResponse(doctorCreado);
    }

    @PutMapping("/{id}")
    public DoctorResponse actualizar(@PathVariable Long id, @Valid @RequestBody Doctor doctor) {
        Doctor doctorActualizado = service.actualizar(id, doctor);
        return mappingService.mapToDoctorResponse(doctorActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}


