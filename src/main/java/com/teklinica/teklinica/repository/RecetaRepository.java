package com.teklinica.teklinica.repository;

import com.teklinica.teklinica.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByPacienteId(Long pacienteId);
    List<Receta> findByDoctorId(Long doctorId);
}
