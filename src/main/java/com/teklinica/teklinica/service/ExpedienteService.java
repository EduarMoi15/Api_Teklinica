package com.teklinica.teklinica.service;

import com.teklinica.teklinica.model.Expediente;
import com.teklinica.teklinica.model.Doctor;
import com.teklinica.teklinica.model.Paciente;
import com.teklinica.teklinica.repository.ExpedienteRepository;
import com.teklinica.teklinica.repository.DoctorRepository;
import com.teklinica.teklinica.repository.PacienteRepository;
import com.teklinica.teklinica.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExpedienteService {

    private final ExpedienteRepository expedienteRepo;
    private final DoctorRepository doctorRepo;
    private final PacienteRepository pacienteRepo;

    private final Path uploadDir = Paths.get("uploads");

    public ExpedienteService(ExpedienteRepository expedienteRepo,
                             DoctorRepository doctorRepo,
                             PacienteRepository pacienteRepo) throws IOException {
        this.expedienteRepo = expedienteRepo;
        this.doctorRepo = doctorRepo;
        this.pacienteRepo = pacienteRepo;

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public Expediente subirExpediente(MultipartFile file, Long doctorId, Long pacienteId) throws IOException {

        Doctor doctor = null;
        if (doctorId != null) {
            doctor = doctorRepo.findById(doctorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con id: " + doctorId));
        }

        Paciente paciente = pacienteRepo.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + pacienteId));

        String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path rutaArchivo = uploadDir.resolve(nombreArchivo);
        Files.write(rutaArchivo, file.getBytes());

        Expediente expediente = Expediente.builder()
                .nombreArchivo(nombreArchivo)
                .nombreOriginal(file.getOriginalFilename())
                .tipoArchivo(file.getContentType())
                .doctor(doctor)
                .paciente(paciente)
                .ruta(rutaArchivo.toString())
                .build();

        return expedienteRepo.save(expediente);
    }

    public List<Expediente> listarExpedientes() {
        return expedienteRepo.findAll();
    }

    public Expediente obtenerExpediente(Long id) {
        return expedienteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expediente no encontrado con id: " + id));
    }

    public Path descargarExpediente(Long id) {
        Expediente expediente = obtenerExpediente(id);
        return Paths.get(expediente.getRuta());
    }

    public void eliminarExpediente(Long id) throws IOException {
        Expediente expediente = obtenerExpediente(id);
        Path path = Paths.get(expediente.getRuta());

        if (Files.exists(path)) {
            Files.delete(path);
        }

        expedienteRepo.delete(expediente);
    }
}
