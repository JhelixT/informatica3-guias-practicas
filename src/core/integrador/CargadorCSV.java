package core.integrador;

import core.estructuras.listas.ListaEnlazada;
import core.estructuras.hash.TablaHash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Sistema que carga y mantiene pacientes, médicos y turnos usando estructuras del repositorio.
 * 
 * Estructuras utilizadas:
 * - ListaEnlazada<T> para almacenar colecciones
 * - TablaHash<String, T> para búsquedas O(1) por clave (DNI, matrícula)
 * 
 * @author Integrador
 * @version 2.0
 */
public class CargadorCSV {
	// Listas enlazadas para almacenamiento ordenado
	private final ListaEnlazada<Paciente> pacientes = new ListaEnlazada<>();
	private final ListaEnlazada<Medico> medicos = new ListaEnlazada<>();
	private final ListaEnlazada<Turno> turnos = new ListaEnlazada<>();
	
	// Tablas hash para búsquedas rápidas O(1)
	private final TablaHash<String, Paciente> pacientesPorDni = new TablaHash<>();
	private final TablaHash<String, Medico> medicosPorMatricula = new TablaHash<>();

	public static final String DEFAULT_PACIENTES = "src/core/integrador/datos/pacientes.csv";
	public static final String DEFAULT_MEDICOS = "src/core/integrador/datos/medicos.csv";
	public static final String DEFAULT_TURNOS = "src/core/integrador/datos/turnos.csv";

	/** Carga los tres CSV por las rutas por defecto. */
	public void loadAll() throws IOException {
		loadPacientes(DEFAULT_PACIENTES);
		loadMedicos(DEFAULT_MEDICOS);
		loadTurnos(DEFAULT_TURNOS);
	}

	/** Carga pacientes desde un CSV (formato: dni,nombre). */
	public void loadPacientes(String csvPath) throws IOException {
		Path p = resolverRuta(csvPath);
		
		java.util.List<String> lines = Files.readAllLines(p);
		if (lines.size() <= 1) return; // sin datos
		
		Map<String,Integer> header = parseHeaderLine(lines.get(0));
		int idxDni = header.getOrDefault("dni", 0);
		int idxNombre = header.getOrDefault("nombre", 1);

		pacientes.clear();
		pacientesPorDni.clear();
		
		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i).trim();
			if (line.isEmpty()) continue;
			
			String[] cols = line.split(",", -1);
			int need = Math.max(idxDni, idxNombre);
			
			if (cols.length > need) {
				String dni = cols[idxDni].trim();
				String nombre = cols[idxNombre].trim();
				
				if (!dni.isEmpty() && !nombre.isEmpty()) {
					Paciente paciente = new Paciente(dni, nombre);
					pacientes.insertLast(paciente);
					pacientesPorDni.put(dni, paciente);
				}
			} else {
				System.err.println("[pacientes] Fila inválida: " + line);
			}
		}
	}

	/** Carga médicos desde un CSV (formato: matricula,nombre,especialidad). */
	public void loadMedicos(String csvPath) throws IOException {
		Path p = resolverRuta(csvPath);
		
		java.util.List<String> lines = Files.readAllLines(p);
		if (lines.size() <= 1) return;
		
		Map<String,Integer> header = parseHeaderLine(lines.get(0));
		int idxMat = header.getOrDefault("matricula", 0);
		int idxNombre = header.getOrDefault("nombre", 1);
		int idxEsp = header.getOrDefault("especialidad", 2);

		medicos.clear();
		medicosPorMatricula.clear();
		
		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i).trim();
			if (line.isEmpty()) continue;
			
			String[] cols = line.split(",", -1);
			int need = Math.max(idxMat, Math.max(idxNombre, idxEsp));
			
			if (cols.length > need) {
				String matricula = cols[idxMat].trim();
				String nombre = cols[idxNombre].trim();
				String especialidad = cols[idxEsp].trim();
				
				if (!matricula.isEmpty() && !nombre.isEmpty()) {
					Medico medico = new Medico(matricula, nombre, especialidad);
					medicos.insertLast(medico);
					medicosPorMatricula.put(matricula, medico);
				}
			} else {
				System.err.println("[medicos] Fila inválida: " + line);
			}
		}
	}


	/** Carga turnos desde CSV. Valida existencia de paciente y médico y parsea fecha/duración. */
	public void loadTurnos(String csvPath) throws IOException {
		Path p = resolverRuta(csvPath);
		
		java.util.List<String> lines = Files.readAllLines(p);
		if (lines.size() <= 1) return;
		
		Map<String,Integer> header = parseHeaderLine(lines.get(0));
		int idxId = header.getOrDefault("id", 0);
		int idxDni = header.getOrDefault("dnipaciente", 1);
		int idxMat = header.getOrDefault("matriculamedico", 2);
		int idxFecha = header.getOrDefault("fechahora", 3);
		int idxDur = header.getOrDefault("duracionmin", 4);
		int idxMot = header.getOrDefault("motivo", 5);

		turnos.clear();
		
		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i).trim();
			if (line.isEmpty()) continue;
			
			String[] cols = line.split(",", -1);
			int need = Math.max(Math.max(idxId, idxDni), 
							   Math.max(Math.max(idxMat, idxFecha), 
							   Math.max(idxDur, idxMot)));
			
			if (cols.length > need) {
				String id = cols[idxId].trim();
				String dniPaciente = cols[idxDni].trim();
				String matriculaMedico = cols[idxMat].trim();
				String fechaStr = cols[idxFecha].trim();
				String durStr = cols[idxDur].trim();
				String motivo = cols[idxMot].trim();

				// Búsqueda O(1) usando TablaHash
				Paciente paciente = findPacienteByDni(dniPaciente);
				if (paciente == null) {
					System.err.println("[turnos] Turno " + id + " ignorado: paciente no encontrado -> " + dniPaciente);
					continue;
				}

				// Búsqueda O(1) usando TablaHash
				Medico medico = findMedicoByMatricula(matriculaMedico);
				if (medico == null) {
					System.err.println("[turnos] Turno " + id + " ignorado: medico no encontrado -> " + matriculaMedico);
					continue;
				}

				try {
					LocalDateTime fecha = parseFecha(fechaStr);
					int duracion = Integer.parseInt(durStr);
					turnos.insertLast(new Turno(id, dniPaciente, matriculaMedico, fecha, duracion, motivo));
				} catch (NumberFormatException ex) {
					System.err.println("[turnos] Turno " + id + " ignorado por duración inválida ('" + durStr + "')");
				} catch (DateTimeParseException ex) {
					System.err.println("[turnos] Turno " + id + " ignorado por fecha inválida ('" + fechaStr + "'): " + ex.getMessage());
				}
			} else {
				System.err.println("[turnos] Fila inválida: " + line);
			}
		}
	}

	/**
	 * Busca un paciente por DNI.
	 * Complejidad: O(1) promedio usando TablaHash.
	 */
	public Paciente findPacienteByDni(String dni) {
		return pacientesPorDni.get(dni);
	}

	/**
	 * Busca un médico por matrícula.
	 * Complejidad: O(1) promedio usando TablaHash.
	 */
	public Medico findMedicoByMatricula(String matricula) {
		return medicosPorMatricula.get(matricula);
	}

	/** Devuelve la lista enlazada de pacientes (referencia directa). */
	public ListaEnlazada<Paciente> getPacientes() {
		return pacientes;
	}

	/** Devuelve la lista enlazada de médicos (referencia directa). */
	public ListaEnlazada<Medico> getMedicos() {
		return medicos;
	}

	/** Devuelve la lista enlazada de turnos (referencia directa). */
	public ListaEnlazada<Turno> getTurnos() {
		return turnos;
	}
	
	/** Devuelve la tabla hash de pacientes por DNI. */
	public TablaHash<String, Paciente> getPacientesPorDni() {
		return pacientesPorDni;
	}
	
	/** Devuelve la tabla hash de médicos por matrícula. */
	public TablaHash<String, Medico> getMedicosPorMatricula() {
		return medicosPorMatricula;
	}

	/**
	 * Resuelve la ruta del archivo CSV, intentando tanto ruta relativa como absoluta.
	 */
	private Path resolverRuta(String csvPath) throws IOException {
		Path p = Paths.get(csvPath);
		if (!Files.exists(p)) {
			p = Paths.get(System.getProperty("user.dir"), csvPath);
		}
		if (!Files.exists(p)) {
			throw new IOException("Archivo no encontrado: " + csvPath + 
								" | Dir actual: " + System.getProperty("user.dir"));
		}
		return p;
	}
	
	/**
	 * Intenta parsear una fecha con varios formatos comunes.
	 */
	private LocalDateTime parseFecha(String s) {
		// Prueba ISO primero (formato estándar)
		try {
			return LocalDateTime.parse(s);
		} catch (DateTimeParseException e) {
			// continuará con otros formatos
		}
		
		String[] patrones = {
			"yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd HH:mm",
			"dd/MM/yyyy HH:mm",
			"dd-MM-yyyy HH:mm"
		};
		
		for (String patron : patrones) {
			try {
				DateTimeFormatter fmt = DateTimeFormatter.ofPattern(patron);
				return LocalDateTime.parse(s, fmt);
			} catch (DateTimeParseException ex) {
				// seguir intentando
			}
		}
		
		throw new DateTimeParseException("Formato no reconocido: " + s, s, 0);
	}

	/**
	 * Parsea la línea de cabecera y devuelve un mapa nombre->índice (minúsculas, sin espacios).
	 */
	private Map<String,Integer> parseHeaderLine(String headerLine) {
		Map<String,Integer> map = new HashMap<>();
		if (headerLine == null) return map;
		
		String[] cols = headerLine.split(",", -1);
		for (int i = 0; i < cols.length; i++) {
			// Normalizar: minúsculas, sin espacios, sin guiones bajos
			String key = cols[i].trim().toLowerCase().replaceAll("[_ ]", "");
			map.put(key, i);
		}
		
		return map;
	}

}
