package com.teklinica.teklinica.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.teklinica.teklinica.dto.ExpedienteResponse;
import com.teklinica.teklinica.model.Expediente;
import com.teklinica.teklinica.service.ExpedienteService;
import com.teklinica.teklinica.service.MappingService;

@RestController
@RequestMapping("/api/expedientes")
public class ExpedienteController {

    @Autowired
    private ExpedienteService expedienteService;
    
    @Autowired
    private MappingService mappingService;

    @GetMapping
    public List<ExpedienteResponse> listar() {
        return expedienteService.listar().stream()
                .map(mappingService::mapToExpedienteResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ExpedienteResponse buscar(@PathVariable Long id) {
        Expediente expediente = expedienteService.buscar(id);
        return mappingService.mapToExpedienteResponse(expediente);
    }

    @PostMapping
    public ExpedienteResponse crear(@RequestBody Expediente expediente) {
        Expediente expedienteCreado = expedienteService.crear(expediente);
        return mappingService.mapToExpedienteResponse(expedienteCreado);
    }

    @PostMapping("/subir")
    public ExpedienteResponse subirArchivo(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("pacienteId") Long pacienteId) {
        Expediente expediente = expedienteService.subirArchivo(archivo, doctorId, pacienteId);
        return mappingService.mapToExpedienteResponse(expediente);
    }

    @GetMapping("/{id}/descargar")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable Long id) {
        byte[] archivo = expedienteService.descargarArchivo(id);
        Expediente expediente = expedienteService.buscar(id);
        
        ByteArrayResource resource = new ByteArrayResource(archivo);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + expediente.getNombreOriginal() + "\"")
                .contentType(MediaType.parseMediaType(expediente.getTipoArchivo()))
                .body(resource);
    }

    @PutMapping("/{id}")
    public ExpedienteResponse actualizar(@PathVariable Long id, @RequestBody Expediente expediente) {
        Expediente expedienteActualizado = expedienteService.actualizar(id, expediente);
        return mappingService.mapToExpedienteResponse(expedienteActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        expedienteService.eliminar(id);
    }

    @DeleteMapping("/{id}/archivo")
    public void eliminarArchivo(@PathVariable Long id) {
        expedienteService.eliminarArchivo(id);
    }
}
