# Datos de Prueba

## 📁 Archivos CSV

### `pacientes.csv`
- 10 pacientes de prueba
- Campos: `dni,nombre`
- DNIs de 8 dígitos
- Sin tildes ni caracteres especiales

### `medicos.csv`
- 10 médicos con diferentes especialidades
- Campos: `matricula,nombre,especialidad`
- Matrículas formato: MP##### (5 dígitos)
- Sin títulos (Dr./Dra.) ni tildes

### `turnos.csv`
- 10 turnos de ejemplo
- Campos: `id,dniPaciente,matriculaMedico,fechaHora,duracionMin,motivo`
- IDs: T001, T002, ..., T010
- Fechas: Del 5 al 9 de noviembre de 2025
- Formato fechaHora: `YYYY-MM-DDTHH:MM` (ISO 8601 sin segundos)
- Ejemplo: `2025-11-05T09:00`
- Duraciones en minutos: 30, 45 o 60
- Motivo de consulta sin tildes

## 💡 Uso

```java
// Ejercicio 1: Cargar datos
CargadorCSV cargador = new CargadorCSV();
List<Paciente> pacientes = cargador.cargarPacientes("datos/pacientes.csv");
List<Medico> medicos = cargador.cargarMedicos("datos/medicos.csv");
List<Turno> turnos = cargador.cargarTurnos("datos/turnos.csv", pacientes, medicos);
```

## 📝 Parsing Simple

```java
// Leer línea por línea
String[] campos = linea.split(",");

// Paciente: dni,nombre
String dni = campos[0];
String nombre = campos[1];

// Médico: matricula,nombre,especialidad
String matricula = campos[0];
String nombre = campos[1];
String especialidad = campos[2];

// Turno: id,dniPaciente,matriculaMedico,fechaHora,duracionMin,motivo
String id = campos[0];
String dniPaciente = campos[1];
String matriculaMedico = campos[2];
LocalDateTime fechaHora = LocalDateTime.parse(campos[3]); // 2025-11-05T09:00
int duracionMin = Integer.parseInt(campos[4]);
String motivo = campos[5];
```
