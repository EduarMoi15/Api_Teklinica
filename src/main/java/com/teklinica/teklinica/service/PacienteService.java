package com.teklinica.teklinica.service;

import com.teklinica.teklinica.exception.ResourceNotFoundException;
import com.teklinica.teklinica.model.Paciente;
import com.teklinica.teklinica.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository repo;

    public PacienteService(PacienteRepository repo) {
        this.repo = repo;
    }

    public List<Paciente> listar() {
        return repo.findAll();
    }

    public Paciente buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente " + id + " no encontrado"));
    }

    public Paciente crear(Paciente paciente) {
        return repo.save(paciente);
    }

    public Paciente actualizar(Long id, Paciente datos) {
        Paciente existente = buscar(id);
        existente.setNombre(datos.getNombre());
        existente.setTelefono(datos.getTelefono());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Paciente " + id + " no encontrado");
        }
        repo.deleteById(id);
    }
}
