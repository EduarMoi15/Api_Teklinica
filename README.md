# 🏥 Clinica API

API REST para la **gestión de clínica**, desarrollada en **Spring Boot 3** con **Java 21**.  
El proyecto permite administrar **Pacientes, Doctores, Citas, Recetas y Expedientes**.

---

## 🚀 Tecnologías utilizadas
- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **Spring Validation**
- **Thymeleaf** (interfaz web básica)
- **Base de datos H2** (en memoria para pruebas)
- **Maven** como gestor de dependencias

---

## 📂 Estructura del proyecto
src/main/java/com/teklinica/teklinica/
├── controller/ # Controladores REST y Web (Thymeleaf)
├── model/ # Entidades JPA (Paciente, Doctor, etc.)
├── repository/ # Interfaces JPA Repository
├── service/ # Servicios con lógica de negocio
└── ApiTeklinicaApplication.java # Clase principal

---

## ⚙️ Configuración del proyecto

El archivo `application.properties` ya está configurado para usar **H2**:

```properties
spring.datasource.url=jdbc:h2:mem:clinica_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

server.port=8085

Accede a la consola de H2 en:
👉 http://localhost:8085/h2-console

(JDBC URL: jdbc:h2:mem:clinica_db)

▶️ Ejecución del proyecto
Clona el repositorio y entra al directorio del proyecto:

git clone https://github.com/usuario/clinica-api.git
cd clinica-api


Compila y ejecuta con Maven:
mvn clean install
mvn spring-boot:run


Luego abre en el navegador:
Interfaz de Pacientes: 👉 http://localhost:8085/pacientes
API REST de Pacientes: 👉 http://localhost:8085/api/pacientes

