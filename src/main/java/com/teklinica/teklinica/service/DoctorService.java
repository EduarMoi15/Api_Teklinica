package com.teklinica.teklinica.service;

import com.teklinica.teklinica.exception.ResourceNotFoundException;
import com.teklinica.teklinica.model.Doctor;
import com.teklinica.teklinica.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository repo;

    public DoctorService(DoctorRepository repo) {
        this.repo = repo;
    }

    public List<Doctor> listar() {
        return repo.findAll();
    }

    public Doctor buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor " + id + " no encontrado"));
    }

    public Doctor crear(Doctor doctor) {
        return repo.save(doctor);
    }

    public Doctor actualizar(Long id, Doctor datos) {
        Doctor existente = buscar(id);
        existente.setNombre(datos.getNombre());
        existente.setEspecialidad(datos.getEspecialidad());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Doctor " + id + " no encontrado");
        }
        repo.deleteById(id);
    }
}
