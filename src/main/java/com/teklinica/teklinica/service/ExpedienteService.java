package com.teklinica.teklinica.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.teklinica.teklinica.exception.ResourceNotFoundException;
import com.teklinica.teklinica.model.Doctor;
import com.teklinica.teklinica.model.Expediente;
import com.teklinica.teklinica.model.Paciente;
import com.teklinica.teklinica.repository.DoctorRepository;
import com.teklinica.teklinica.repository.ExpedienteRepository;
import com.teklinica.teklinica.repository.PacienteRepository;

@Service
public class ExpedienteService {

    private final ExpedienteRepository repo;
    private final DoctorRepository doctorRepo;
    private final PacienteRepository pacienteRepo;
    
    @Value("${expedientes.upload-dir:uploads/}")
    private String uploadDir;

    public ExpedienteService(ExpedienteRepository repo, DoctorRepository doctorRepo, PacienteRepository pacienteRepo) {
        this.repo = repo;
        this.doctorRepo = doctorRepo;
        this.pacienteRepo = pacienteRepo;
    }

    public List<Expediente> listar() {
        return repo.findAll();
    }

    public Expediente buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expediente " + id + " no encontrado"));
    }

    public Expediente crear(Expediente expediente) {
        return repo.save(expediente);
    }

    public Expediente actualizar(Long id, Expediente datos) {
        Expediente existente = buscar(id);
        existente.setNombreArchivo(datos.getNombreArchivo());
        existente.setNombreOriginal(datos.getNombreOriginal());
        existente.setTipoArchivo(datos.getTipoArchivo());
        existente.setRuta(datos.getRuta());
        existente.setDoctor(datos.getDoctor());
        existente.setPaciente(datos.getPaciente());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Expediente " + id + " no encontrado");
        }
        repo.deleteById(id);
    }

    public Expediente subirArchivo(MultipartFile archivo, Long doctorId, Long pacienteId) {
        try {
            // Validar que el doctor y paciente existen
            Doctor doctor = doctorRepo.findById(doctorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor " + doctorId + " no encontrado"));
            
            Paciente paciente = pacienteRepo.findById(pacienteId)
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente " + pacienteId + " no encontrado"));

            // Crear directorio si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar nombre único para el archivo
            String nombreOriginal = archivo.getOriginalFilename();
            String extension = nombreOriginal != null ? 
                nombreOriginal.substring(nombreOriginal.lastIndexOf(".")) : "";
            String nombreArchivo = UUID.randomUUID().toString() + extension;
            
            // Guardar archivo
            Path filePath = uploadPath.resolve(nombreArchivo);
            Files.copy(archivo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Crear expediente usando constructor normal
            Expediente expediente = new Expediente();
            expediente.setNombreArchivo(nombreArchivo);
            expediente.setNombreOriginal(nombreOriginal);
            expediente.setTipoArchivo(archivo.getContentType());
            expediente.setRuta(filePath.toString());
            expediente.setDoctor(doctor);
            expediente.setPaciente(paciente);

            return repo.save(expediente);
            
        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo: " + e.getMessage());
        }
    }

    public byte[] descargarArchivo(Long id) {
        Expediente expediente = buscar(id);
        try {
            Path filePath = Paths.get(expediente.getRuta());
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error al descargar archivo: " + e.getMessage());
        }
    }

    public void eliminarArchivo(Long id) {
        Expediente expediente = buscar(id);
        try {
            Path filePath = Paths.get(expediente.getRuta());
            Files.deleteIfExists(filePath);
            eliminar(id);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar archivo: " + e.getMessage());
        }
    }
}
