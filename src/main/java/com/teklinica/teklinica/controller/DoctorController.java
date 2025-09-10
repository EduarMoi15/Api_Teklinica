package com.teklinica.teklinica.controller;

import com.teklinica.teklinica.model.Doctor;
import com.teklinica.teklinica.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctores")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Doctor> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Doctor buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public Doctor crear(@Valid @RequestBody Doctor doctor) {
        return service.crear(doctor);
    }

    @PutMapping("/{id}")
    public Doctor actualizar(@PathVariable Long id, @Valid @RequestBody Doctor doctor) {
        return service.actualizar(id, doctor);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}

//Holaaaaaaaaaaa
