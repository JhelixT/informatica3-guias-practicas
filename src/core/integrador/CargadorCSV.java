package core.integrador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Sistema que carga y mantiene pacientes, médicos y turnos en listas. */
public class CargadorCSV {
	private final List<Paciente> pacientes = new ArrayList<>();
	private final List<Medico> medicos = new ArrayList<>();
	private final List<Turno> turnos = new ArrayList<>();

	public static final String DEFAULT_PACIENTES = "src/core/integrador/datos/pacientes.csv";
	public static final String DEFAULT_MEDICOS = "src/core/integrador/datos/medicos.csv";
	public static final String DEFAULT_TURNOS = "src/core/integrador/datos/turnos.csv";

	/** Carga los tres CSV por las rutas por defecto. */
	public void loadAll() throws IOException {
		loadPacientes(DEFAULT_PACIENTES);
		loadMedicos(DEFAULT_MEDICOS);
		loadTurnos(DEFAULT_TURNOS);
	}

	/** Carga pacientes desde un CSV (formato: nombre,dni). */
	public void loadPacientes(String csvPath) throws IOException {
		Path p = Paths.get(csvPath);
		if (!Files.exists(p)) p = Paths.get(System.getProperty("user.dir"), csvPath);
		if (!Files.exists(p)) throw new IOException("Archivo no encontrado: " + csvPath + " | Dir: " + System.getProperty("user.dir"));

		List<String> lines = Files.readAllLines(p);
		if (lines.size() <= 1) return; // sin datos
		Map<String,Integer> header = parseHeaderLine(lines.get(0));
		int idxNombre = header.getOrDefault("nombre", 0);
		int idxDni = header.getOrDefault("dni", 1);

		pacientes.clear();
		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] cols = line.split(",", -1);
			int need = Math.max(idxNombre, idxDni);
			if (cols.length - 1 >= need) {
				String nombre = cols[idxNombre].trim();
				String dni = cols[idxDni].trim();
				pacientes.add(new Paciente(dni, nombre));
			} else {
				System.err.println("[pacientes] Fila inválida: " + line);
			}
		}
	}

	/** Carga médicos desde un CSV (formato: matricula,nombre,especialidad). */
	public void loadMedicos(String csvPath) throws IOException {
		Path p = Paths.get(csvPath);
		if (!Files.exists(p)) p = Paths.get(System.getProperty("user.dir"), csvPath);
		if (!Files.exists(p)) throw new IOException("Archivo no encontrado: " + csvPath + " | Dir: " + System.getProperty("user.dir"));

		List<String> lines = Files.readAllLines(p);
		if (lines.size() <= 1) return;
		Map<String,Integer> header = parseHeaderLine(lines.get(0));
		int idxMat = header.getOrDefault("matricula", 0);
		int idxNombre = header.getOrDefault("nombre", 1);
		int idxEsp = header.getOrDefault("especialidad", 2);

		medicos.clear();
		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] cols = line.split(",", -1);
			int need = Math.max(idxMat, Math.max(idxNombre, idxEsp));
			if (cols.length - 1 >= need) {
				String matricula = cols[idxMat].trim();
				String nombre = cols[idxNombre].trim();
				String especialidad = cols[idxEsp].trim();
				medicos.add(new Medico(matricula, nombre, especialidad));
			} else {
				System.err.println("[medicos] Fila inválida: " + line);
			}
		}
	}


	/** Carga turnos desde CSV. Valida existencia de paciente y médico y parsea fecha/duración. */
	public void loadTurnos(String csvPath) throws IOException {
		Path p = Paths.get(csvPath);
		if (!Files.exists(p)) p = Paths.get(System.getProperty("user.dir"), csvPath);
		if (!Files.exists(p)) throw new IOException("Archivo no encontrado: " + csvPath + " | Dir: " + System.getProperty("user.dir"));

		List<String> lines = Files.readAllLines(p);
		if (lines.size() <= 1) return;
		Map<String,Integer> header = parseHeaderLine(lines.get(0));
		int idxId = header.getOrDefault("id", 0);
		int idxDni = header.getOrDefault("dni", header.getOrDefault("dniPaciente", 1));
		int idxMat = header.getOrDefault("matricula", header.getOrDefault("matriculaMedico", 2));
		int idxFecha = header.getOrDefault("fecha", 3);
		int idxDur = header.getOrDefault("duracion", header.getOrDefault("duracionMin", 4));
		int idxMot = header.getOrDefault("motivo", 5);

		turnos.clear();
		for (int i = 1; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] cols = line.split(",", -1);
			int need = Math.max(Math.max(idxId, idxDni), Math.max(Math.max(idxMat, idxFecha), Math.max(idxDur, idxMot)));
			if (cols.length - 1 >= need) {
				String id = cols[idxId].trim();
				String dniPaciente = cols[idxDni].trim();
				String matriculaMedico = cols[idxMat].trim();
				String fechaStr = cols[idxFecha].trim();
				String durStr = cols[idxDur].trim();
				String motivo = cols[idxMot].trim();

				Paciente paciente = findPacienteByDni(dniPaciente);
				if (paciente == null) {
					System.err.println("[turnos] Turno " + id + " ignorado: paciente no encontrado -> " + dniPaciente);
					continue;
				}

				Medico medico = findMedicoByMatricula(matriculaMedico);
				if (medico == null) {
					System.err.println("[turnos] Turno " + id + " ignorado: medico no encontrado -> " + matriculaMedico);
					continue;
				}

				try {
					LocalDateTime fecha = parseFecha(fechaStr);
					int duracion = Integer.parseInt(durStr);
					turnos.add(new Turno(id, dniPaciente, matriculaMedico, fecha, duracion, motivo));
				} catch (NumberFormatException ex) {
					System.err.println("[turnos] Turno " + id + " ignorado por duración inválida ('" + durStr + "') línea:" + line);
				} catch (DateTimeParseException ex) {
					System.err.println("[turnos] Turno " + id + " ignorado por fecha inválida ('" + fechaStr + "') línea:" + line + " -> " + ex.getMessage());
				}
			} else {
				System.err.println("[turnos] Fila inválida: " + line);
			}
		}
	}

	/** Busca un paciente por DNI (búsqueda lineal). */
	public Paciente findPacienteByDni(String dni) {
		for (Paciente p : pacientes) {
			if (p.getDni() != null && p.getDni().equals(dni)) return p;
		}
		return null;
	}

	/** Busca un médico por matrícula (búsqueda lineal). */
	public Medico findMedicoByMatricula(String matricula) {
		for (Medico m : medicos) if (m.getMatricula() != null && m.getMatricula().equals(matricula)) return m;
		return null;
	}

	/** Devuelve copia de la lista de pacientes. */
	public List<Paciente> getPacientes() { return new ArrayList<>(pacientes); }

	/** Devuelve copia de la lista de médicos. */
	public List<Medico> getMedicos() { return new ArrayList<>(medicos); }

	/** Devuelve copia de la lista de turnos. */
	public List<Turno> getTurnos() { return new ArrayList<>(turnos); }
	
	/** Intenta parsear una fecha con varios formatos comunes. */
	private LocalDateTime parseFecha(String s) {
		// Prueba ISO primero
		try {
			return LocalDateTime.parse(s);
		} catch (DateTimeParseException e) {
			// continuará con otros formatos
		}
		String[] patrones = new String[] {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "dd/MM/yyyy HH:mm", "dd-MM-yyyy HH:mm"};
		for (String p : patrones) {
			try {
				DateTimeFormatter fmt = DateTimeFormatter.ofPattern(p);
				return LocalDateTime.parse(s, fmt);
			} catch (DateTimeParseException ex) {
				// seguir intentando
			}
		}
		// Si no coincide ninguno, lanza excepción para que el llamador la capture
		throw new DateTimeParseException("Formato no reconocido: " + s, s, 0);
	}

	/** Parsea la línea de cabecera y devuelve un mapa nombre->índice (minúsculas). */
	private Map<String,Integer> parseHeaderLine(String headerLine) {
		Map<String,Integer> map = new HashMap<>();
		if (headerLine == null) return map;
		String[] cols = headerLine.split(",", -1);
		for (int i = 0; i < cols.length; i++) {
			String key = cols[i].trim().toLowerCase();
			map.put(key, i);
			// normalizar nombres comunes
			if (key.contains("dni") && !map.containsKey("dni")) map.put("dni", i);
			if (key.contains("nombre") && !map.containsKey("nombre")) map.put("nombre", i);
			if (key.contains("matricula") && !map.containsKey("matricula")) map.put("matricula", i);
			if (key.contains("especialidad") && !map.containsKey("especialidad")) map.put("especialidad", i);
			if ((key.contains("fecha") || key.contains("fecha_hora") || key.contains("fechahora")) && !map.containsKey("fecha")) map.put("fecha", i);
			if (key.contains("dur") && !map.containsKey("duracion")) map.put("duracion", i);
			if (key.contains("motivo") && !map.containsKey("motivo")) map.put("motivo", i);
			if (key.contains("id") && !map.containsKey("id")) map.put("id", i);
			if (key.contains("medico") && !map.containsKey("matriculaMedico")) map.put("matriculaMedico", i);
			if (key.contains("paciente") && !map.containsKey("dniPaciente")) map.put("dniPaciente", i);
		}
		return map;
	}

}
