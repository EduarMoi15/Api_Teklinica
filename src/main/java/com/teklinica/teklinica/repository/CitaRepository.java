package com.teklinica.teklinica.repository;

import com.teklinica.teklinica.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByDoctorId(Long doctorId);
    List<Cita> findByPacienteId(Long pacienteId);
    boolean existsByDoctorIdAndFecha(Long doctorId, LocalDateTime fecha);
}
