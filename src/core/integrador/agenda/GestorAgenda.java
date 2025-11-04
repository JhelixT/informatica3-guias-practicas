package core.integrador.agenda;

import core.integrador.modelo.Turno;
import core.integrador.pacientes.IndicePacientes;
import core.estructuras.listas.ListaEnlazada;
import core.estructuras.nodos.Nodo;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Gestor de operaciones sobre la agenda médica
 */
public class GestorAgenda {
    
    private AgendaMedicoTree agenda;
    private Historial historial;
    private IndicePacientes indicePacientes;
    private ListaEnlazada<Turno> listaTurnos;
    
    public GestorAgenda(AgendaMedicoTree agenda, IndicePacientes indicePacientes, 
                       ListaEnlazada<Turno> listaTurnos) {
        this.agenda = agenda;
        this.historial = new Historial(agenda);
        this.indicePacientes = indicePacientes;
        this.listaTurnos = listaTurnos;
    }
    
    /**
     * Genera un nuevo ID de turno automáticamente
     */
    public String generarNuevoId() {
        int maxId = 0;
        Nodo<Turno> nodo = listaTurnos.getHead();
        
        while (nodo != null) {
            String id = nodo.getData().getId();
            if (id.startsWith("T-")) {
                try {
                    int num = Integer.parseInt(id.substring(2));
                    if (num > maxId) maxId = num;
                } catch (NumberFormatException e) {}
            }
            nodo = nodo.getNext();
        }
        
        return String.format("T-%03d", maxId + 1);
    }
    
    /**
     * Agenda un nuevo turno con validaciones
     */
    public ResultadoAgenda agendar(String dniPaciente, String matriculaMedico,
                                   LocalDateTime fechaHora, int duracionMin, String motivo) {
        // Validar que el paciente existe
        if (indicePacientes.get(dniPaciente) == null) {
            return new ResultadoAgenda(false, "Paciente no encontrado", null);
        }
        
        String nuevoId = generarNuevoId();
        Turno nuevoTurno = new Turno(nuevoId, dniPaciente, matriculaMedico,
                                     fechaHora, duracionMin, motivo);
        
        if (agenda.agendar(nuevoTurno)) {
            listaTurnos.insertLast(nuevoTurno);
            return new ResultadoAgenda(true, "Turno agendado exitosamente", nuevoId);
        } else {
            return new ResultadoAgenda(false, "Conflicto de horario", null);
        }
    }
    
    /**
     * Cancela un turno existente
     */
    public ResultadoCancelacion cancelar(String idTurno, String matriculaMedico) {
        ListaEnlazada<Turno> turnosActuales = agenda.turnosPorMedico(matriculaMedico);
        
        // Buscar el turno
        Turno turnoSeleccionado = null;
        Nodo<Turno> nodo = turnosActuales.getHead();
        while (nodo != null) {
            if (nodo.getData().getId().equals(idTurno)) {
                turnoSeleccionado = nodo.getData();
                break;
            }
            nodo = nodo.getNext();
        }
        
        if (turnoSeleccionado == null) {
            return new ResultadoCancelacion(false, "Turno no encontrado", null);
        }
        
        if (agenda.cancelar(idTurno)) {
            return new ResultadoCancelacion(true, "Turno cancelado exitosamente", turnoSeleccionado);
        } else {
            return new ResultadoCancelacion(false, "Error al cancelar el turno", null);
        }
    }
    
    /**
     * Busca el próximo turno disponible
     */
    public Optional<LocalDateTime> buscarProximoDisponible(String matriculaMedico,
                                                           LocalDateTime desde, int duracionMin) {
        return agenda.primerHueco(matriculaMedico, desde, duracionMin);
    }
    
    /**
     * Reprograma un turno con historial
     */
    public boolean reprogramar(String idTurno, LocalDateTime nuevaFecha) {
        return historial.reprogramar(idTurno, nuevaFecha);
    }
    
    /**
     * Deshace la última reprogramación
     */
    public boolean undo() {
        return historial.undo();
    }
    
    /**
     * Rehace una reprogramación deshecha
     */
    public boolean redo() {
        return historial.redo();
    }
    
    /**
     * Obtiene los turnos actuales de un médico
     */
    public ListaEnlazada<Turno> obtenerTurnosMedico(String matriculaMedico) {
        return agenda.turnosPorMedico(matriculaMedico);
    }
    
    /**
     * Obtiene la cantidad total de turnos
     */
    public int cantidadTurnos() {
        return agenda.cantidadTurnos();
    }
    
    // ===== CLASES INTERNAS =====
    
    public static class ResultadoAgenda {
        private boolean exito;
        private String mensaje;
        private String idTurno;
        
        public ResultadoAgenda(boolean exito, String mensaje, String idTurno) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.idTurno = idTurno;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public String getIdTurno() { return idTurno; }
    }
    
    public static class ResultadoCancelacion {
        private boolean exito;
        private String mensaje;
        private Turno turnoCancelado;
        
        public ResultadoCancelacion(boolean exito, String mensaje, Turno turnoCancelado) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.turnoCancelado = turnoCancelado;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public Turno getTurnoCancelado() { return turnoCancelado; }
    }
}
