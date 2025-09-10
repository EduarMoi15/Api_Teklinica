package com.teklinica.teklinica.repository;

import com.teklinica.teklinica.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> { }
