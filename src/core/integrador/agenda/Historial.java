package core.integrador.agenda;

import core.integrador.modelo.Turno;
import core.estructuras.pilas.PilaEnlazada;
import core.estructuras.listas.ListaEnlazada;
import core.estructuras.nodos.Nodo;
import java.time.LocalDateTime;
import java.util.Optional;

/** Implementa AgendaConHistorial con funcionalidad de undo/redo usando pilas */
public class Historial implements AgendaConHistorial {
    private PilaEnlazada<Comando> acciones;
    private PilaEnlazada<Comando> deshechas;
    private AgendaMedico agenda;

    public Historial(AgendaMedico agenda) {
        this.agenda = agenda;
        this.acciones = new PilaEnlazada<>();
        this.deshechas = new PilaEnlazada<>();
    }

    /** Delega la operación de agendar a la agenda interna */
    @Override
    public boolean agendar(Turno t) {
        return agenda.agendar(t);
    }

    /** Delega la operación de cancelar a la agenda interna */
    @Override
    public boolean cancelar(String idTurno) {
        return agenda.cancelar(idTurno);
    }

    /** Delega la operación de siguiente a la agenda interna */
    @Override
    public Optional<Turno> siguiente(LocalDateTime t) {
        return agenda.siguiente(t);
    }

    /** Reprograma un turno guardando el comando para undo/redo */
    @Override
    public boolean reprogramar(String idTurno, LocalDateTime nuevaFecha) {
        try {
            // Buscar el turno actual en la agenda
            Turno turnoActual = buscarTurnoPorId(idTurno);
            if (turnoActual == null) {
                return false;
            }

            LocalDateTime fechaAnterior = turnoActual.getFechaHora();
            
            // Cancelar el turno viejo
            if (!agenda.cancelar(idTurno)) {
                return false;
            }
            
            // Crear el turno con la nueva fecha
            Turno turnoNuevo = new Turno(
                turnoActual.getId(),
                turnoActual.getDniPaciente(),
                turnoActual.getMatriculaMedico(),
                nuevaFecha,
                turnoActual.getDuracionMin(),
                turnoActual.getMotivo()
            );
            
            // Agendar el turno con la nueva fecha
            if (agenda.agendar(turnoNuevo)) {
                // Guardar el comando para undo/redo
                Comando cmd = new Comando(idTurno, fechaAnterior, nuevaFecha, turnoActual);
                acciones.push(cmd);
                deshechas.clear();
                return true;
            } else {
                // Si falla, restaurar el turno original
                agenda.agendar(turnoActual);
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /** Deshace la última reprogramación */
    @Override
    public boolean undo() {
        if (acciones.isEmpty()) {
            return false;
        }
        try {
            Comando ultimo = acciones.pop();
            
            // Cancelar el turno con fecha nueva
            if (!agenda.cancelar(ultimo.idTurno)) {
                acciones.push(ultimo);
                return false;
            }
            
            // Restaurar turno con fecha anterior
            Turno turnoRestaurado = new Turno(
                ultimo.turnoOriginal.getId(),
                ultimo.turnoOriginal.getDniPaciente(),
                ultimo.turnoOriginal.getMatriculaMedico(),
                ultimo.fechaAnterior,
                ultimo.turnoOriginal.getDuracionMin(),
                ultimo.turnoOriginal.getMotivo()
            );
            
            if (agenda.agendar(turnoRestaurado)) {
                deshechas.push(ultimo);
                return true;
            } else {
                // Si falla, restaurar con fecha nueva
                Turno turnoNuevo = new Turno(
                    ultimo.turnoOriginal.getId(),
                    ultimo.turnoOriginal.getDniPaciente(),
                    ultimo.turnoOriginal.getMatriculaMedico(),
                    ultimo.fechaNueva,
                    ultimo.turnoOriginal.getDuracionMin(),
                    ultimo.turnoOriginal.getMotivo()
                );
                agenda.agendar(turnoNuevo);
                acciones.push(ultimo);
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /** Rehace la última reprogramación deshecha */
    @Override
    public boolean redo() {
        if (deshechas.isEmpty()) {
            return false;
        }
        try {
            Comando ultimo = deshechas.pop();
            
            // Cancelar el turno con fecha anterior
            if (!agenda.cancelar(ultimo.idTurno)) {
                deshechas.push(ultimo);
                return false;
            }
            
            // Restaurar turno con fecha nueva
            Turno turnoNuevo = new Turno(
                ultimo.turnoOriginal.getId(),
                ultimo.turnoOriginal.getDniPaciente(),
                ultimo.turnoOriginal.getMatriculaMedico(),
                ultimo.fechaNueva,
                ultimo.turnoOriginal.getDuracionMin(),
                ultimo.turnoOriginal.getMotivo()
            );
            
            if (agenda.agendar(turnoNuevo)) {
                acciones.push(ultimo);
                return true;
            } else {
                // Si falla, restaurar con fecha anterior
                Turno turnoAnterior = new Turno(
                    ultimo.turnoOriginal.getId(),
                    ultimo.turnoOriginal.getDniPaciente(),
                    ultimo.turnoOriginal.getMatriculaMedico(),
                    ultimo.fechaAnterior,
                    ultimo.turnoOriginal.getDuracionMin(),
                    ultimo.turnoOriginal.getMotivo()
                );
                agenda.agendar(turnoAnterior);
                deshechas.push(ultimo);
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /** Busca un turno por ID en la agenda */
    private Turno buscarTurnoPorId(String idTurno) {
        if (agenda instanceof AgendaMedicoTree) {
            AgendaMedicoTree tree = (AgendaMedicoTree) agenda;
            ListaEnlazada<Turno> turnos = tree.todosTurnos();
            Nodo<Turno> nodo = turnos.getHead();
            
            while (nodo != null) {
                Turno t = nodo.getData();
                if (t.getId().equals(idTurno)) {
                    return t;
                }
                nodo = nodo.getNext();
            }
        }
        return null;
    }

    /** Comando que almacena información de una reprogramación */
    private class Comando {
        private String idTurno;
        private LocalDateTime fechaAnterior;
        private LocalDateTime fechaNueva;
        private Turno turnoOriginal;

        public Comando(String idTurno, LocalDateTime fechaAnterior, LocalDateTime fechaNueva, Turno turnoOriginal) {
            this.idTurno = idTurno;
            this.fechaAnterior = fechaAnterior;
            this.fechaNueva = fechaNueva;
            this.turnoOriginal = turnoOriginal;
        }
    }
}