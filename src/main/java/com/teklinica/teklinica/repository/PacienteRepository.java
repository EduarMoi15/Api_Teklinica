package com.teklinica.teklinica.repository;

import com.teklinica.teklinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> { }
