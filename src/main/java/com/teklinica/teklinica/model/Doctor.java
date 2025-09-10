package com.teklinica.teklinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    private String especialidad;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Cita> citas;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Receta> recetas;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Expediente> expedientes;
}
