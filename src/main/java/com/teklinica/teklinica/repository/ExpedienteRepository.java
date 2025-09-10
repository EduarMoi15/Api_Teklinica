package com.teklinica.teklinica.repository;

import com.teklinica.teklinica.model.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
    List<Expediente> findByPacienteId(Long pacienteId);
}
