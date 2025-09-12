package com.teklinica.teklinica;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.teklinica.teklinica.model.Cita;
import com.teklinica.teklinica.model.Doctor;
import com.teklinica.teklinica.model.Expediente;
import com.teklinica.teklinica.model.Paciente;
import com.teklinica.teklinica.model.Receta;
import com.teklinica.teklinica.model.Rol;
import com.teklinica.teklinica.model.Usuario;
import com.teklinica.teklinica.repository.CitaRepository;
import com.teklinica.teklinica.repository.DoctorRepository;
import com.teklinica.teklinica.repository.ExpedienteRepository;
import com.teklinica.teklinica.repository.PacienteRepository;
import com.teklinica.teklinica.repository.RecetaRepository;
import com.teklinica.teklinica.repository.UsuarioRepository;

@SpringBootApplication
public class ApiTeklinicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTeklinicaApplication.class, args);
	}

	// Bean para cargar datos de prueba al iniciar la aplicación
	@Bean
	CommandLineRunner initDatabase(
			DoctorRepository doctorRepo,
			PacienteRepository pacienteRepo,
			CitaRepository citaRepo,
			RecetaRepository recetaRepo,
			ExpedienteRepository expedienteRepo,
			UsuarioRepository usuarioRepo
	) {
		return args -> {

			// Crear doctores
			Doctor doctor1 = new Doctor();
			doctor1.setNombre("Dr. Juan Pérez");
			doctor1.setEspecialidad("Cardiología");
			doctorRepo.save(doctor1);

			Doctor doctor2 = new Doctor();
			doctor2.setNombre("Dra. María López");
			doctor2.setEspecialidad("Neurología");
			doctorRepo.save(doctor2);

			// Crear pacientes
			Paciente paciente1 = new Paciente();
			paciente1.setNombre("Ana López");
			paciente1.setTelefono("12345678");
			pacienteRepo.save(paciente1);

			Paciente paciente2 = new Paciente();
			paciente2.setNombre("Carlos Martínez");
			paciente2.setTelefono("87654321");
			pacienteRepo.save(paciente2);

			// Crear citas
			Cita cita1 = new Cita();
			cita1.setFecha(LocalDateTime.now().plusDays(1));
			cita1.setMotivo("Consulta general");
			cita1.setDoctor(doctor1);
			cita1.setPaciente(paciente1);
			citaRepo.save(cita1);

			Cita cita2 = new Cita();
			cita2.setFecha(LocalDateTime.now().plusDays(2));
			cita2.setMotivo("Control seguimiento");
			cita2.setDoctor(doctor2);
			cita2.setPaciente(paciente2);
			citaRepo.save(cita2);

			// Crear receta
			Receta receta1 = new Receta();
			receta1.setMedicamento("Ibuprofeno");
			receta1.setIndicaciones("Tomar cada 8h");
			receta1.setFecha(LocalDate.now());
			receta1.setDoctor(doctor1);
			receta1.setPaciente(paciente1);
			recetaRepo.save(receta1);

			// Crear expediente (sin archivo real)
			Expediente expediente1 = new Expediente();
			expediente1.setNombreArchivo("documento1.pdf");
			expediente1.setNombreOriginal("documento_original.pdf");
			expediente1.setTipoArchivo("application/pdf");
			expediente1.setDoctor(doctor2);
			expediente1.setPaciente(paciente2);
			expedienteRepo.save(expediente1);

			// Crear usuarios de prueba
			Usuario admin = new Usuario();
			admin.setUsername("admin");
			admin.setPassword("admin123");
			admin.setRol(Rol.ADMIN);
			admin.setNombre("Administrador");
			admin.setEmail("admin@teklinica.com");
			admin.setActivo(true);
			usuarioRepo.save(admin);

			Usuario doctor = new Usuario();
			doctor.setUsername("doctor");
			doctor.setPassword("doctor123");
			doctor.setRol(Rol.DOCTOR);
			doctor.setNombre("Dr. Juan Pérez");
			doctor.setEmail("doctor@teklinica.com");
			doctor.setActivo(true);
			usuarioRepo.save(doctor);

			Usuario paciente = new Usuario();
			paciente.setUsername("paciente");
			paciente.setPassword("paciente123");
			paciente.setRol(Rol.PACIENTE);
			paciente.setNombre("Ana López");
			paciente.setEmail("paciente@teklinica.com");
			paciente.setActivo(true);
			usuarioRepo.save(paciente);

			System.out.println("Datos de prueba cargados correctamente!");
		};
	}
}
