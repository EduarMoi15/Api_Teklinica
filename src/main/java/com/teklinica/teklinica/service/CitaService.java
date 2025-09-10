package com.teklinica.teklinica.service;

import com.teklinica.teklinica.exception.ResourceNotFoundException;
import com.teklinica.teklinica.model.Cita;
import com.teklinica.teklinica.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaService {

    private final CitaRepository repo;

    public CitaService(CitaRepository repo) {
        this.repo = repo;
    }

    public List<Cita> listar() {
        return repo.findAll();
    }

    public Cita buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita " + id + " no encontrada"));
    }

    public Cita crear(Cita cita) {
        // Aquí podrías agregar validación de horario disponible
        return repo.save(cita);
    }

    public Cita actualizar(Long id, Cita datos) {
        Cita existente = buscar(id);
        existente.setFecha(datos.getFecha());
        existente.setDoctor(datos.getDoctor());
        existente.setPaciente(datos.getPaciente());
        existente.setMotivo(datos.getMotivo());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Cita " + id + " no encontrada");
        }
        repo.deleteById(id);
    }
}
