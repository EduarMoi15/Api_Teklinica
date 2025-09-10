package com.teklinica.teklinica.service;

import com.teklinica.teklinica.exception.ResourceNotFoundException;
import com.teklinica.teklinica.model.Receta;
import com.teklinica.teklinica.repository.RecetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaService {

    private final RecetaRepository repo;

    public RecetaService(RecetaRepository repo) {
        this.repo = repo;
    }

    public List<Receta> listar() {
        return repo.findAll();
    }

    public Receta buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receta " + id + " no encontrada"));
    }

    public Receta crear(Receta receta) {
        return repo.save(receta);
    }

    public Receta actualizar(Long id, Receta datos) {
        Receta existente = buscar(id);
        existente.setMedicamento(datos.getMedicamento());
        existente.setIndicaciones(datos.getIndicaciones());
        existente.setFecha(datos.getFecha());
        existente.setDoctor(datos.getDoctor());
        existente.setPaciente(datos.getPaciente());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Receta " + id + " no encontrada");
        }
        repo.deleteById(id);
    }
}
