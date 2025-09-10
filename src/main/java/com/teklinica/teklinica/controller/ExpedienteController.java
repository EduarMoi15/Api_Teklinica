package com.teklinica.teklinica.controller;

import com.teklinica.teklinica.model.Expediente;
import com.teklinica.teklinica.service.ExpedienteService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/expedientes")
public class ExpedienteController {

    private final ExpedienteService expedienteService;

    public ExpedienteController(ExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    @GetMapping
    public List<Expediente> listarExpedientes() {
        return expedienteService.listarExpedientes();
    }

    @GetMapping("/{id}")
    public Expediente obtenerExpediente(@PathVariable Long id) {
        return expedienteService.obtenerExpediente(id);
    }

    @PostMapping("/upload")
    public Expediente subirExpediente(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "doctorId", required = false) Long doctorId,
            @RequestParam("pacienteId") Long pacienteId
    ) throws Exception {
        return expedienteService.subirExpediente(file, doctorId, pacienteId);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> descargarExpediente(@PathVariable Long id) throws Exception {
        Path path = expedienteService.descargarExpediente(id);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("Archivo no encontrado");
        }

        String nombreArchivo = path.getFileName().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarExpediente(@PathVariable Long id) throws Exception {
        expedienteService.eliminarExpediente(id);
        return ResponseEntity.ok("Expediente eliminado correctamente");
    }
}
