package core.integrador.agenda;

import core.integrador.modelo.Turno;
import core.estructuras.pilas.PilaEnlazada;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Implementación de AgendaMedico con soporte para Undo/Redo.
 * Mantiene dos pilas para gestionar el historial de cambios.
 */
public class AgendaMedicoConHistorial implements AgendaMedico {
    
    /** Agenda base que gestiona los turnos */
    private final AgendaMedicoTree agenda;
    
    /** Pila de acciones ejecutadas (para undo) */
    private final PilaEnlazada<Accion> pilaUndo;
    
    /** Pila de acciones deshechas (para redo) */
    private final PilaEnlazada<Accion> pilaRedo;
    
    /**
     * Tipos de acción soportadas
     */
    private enum TipoAccion {
        AGENDAR,
        CANCELAR,
        REPROGRAMAR
    }
    
    /**
     * Representa una acción reversible en la agenda
     */
    private static class Accion {
        TipoAccion tipo;
        Turno turno;
        Turno turnoAntiguo; // Para reprogramar
        
        Accion(TipoAccion tipo, Turno turno) {
            this.tipo = tipo;
            this.turno = turno;
        }
        
        Accion(TipoAccion tipo, Turno turno, Turno turnoAntiguo) {
            this.tipo = tipo;
            this.turno = turno;
            this.turnoAntiguo = turnoAntiguo;
        }
    }
    
    public AgendaMedicoConHistorial() {
        this.agenda = new AgendaMedicoTree();
        this.pilaUndo = new PilaEnlazada<>();
        this.pilaRedo = new PilaEnlazada<>();
    }
    
    /**
     * Agenda un turno y registra la acción
     * Complejidad: O(log n) + O(1) para pilas
     */
    @Override
    public boolean agendar(Turno t) {
        if (agenda.agendar(t)) {
            pilaUndo.push(new Accion(TipoAccion.AGENDAR, t));
            pilaRedo.clear(); // Limpiar redo después de nueva acción
            return true;
        }
        return false;
    }
    
    /**
     * Cancela un turno y registra la acción
     * Complejidad: O(log n) + O(1) para pilas
     */
    @Override
    public boolean cancelar(String idTurno) {
        // Primero necesitamos obtener el turno antes de cancelarlo
        Optional<Turno> turnoOpt = buscarPorId(idTurno);
        
        if (turnoOpt.isEmpty()) {
            return false;
        }
        
        Turno turno = turnoOpt.get();
        
        if (agenda.cancelar(idTurno)) {
            pilaUndo.push(new Accion(TipoAccion.CANCELAR, turno));
            pilaRedo.clear(); // Limpiar redo después de nueva acción
            return true;
        }
        return false;
    }
    
    /**
     * Retorna el siguiente turno a partir de la fecha especificada
     * Complejidad: O(log n) delegado a la agenda base
     */
    @Override
    public Optional<Turno> siguiente(LocalDateTime t) {
        return agenda.siguiente(t);
    }
    
    /**
     * Busca un turno por ID (delegado a la agenda base)
     */
    private Optional<Turno> buscarPorId(String id) {
        return agenda.buscarPorIdOpt(id);
    }
    
    /**
     * Reprograma un turno existente a una nueva fecha
     * Complejidad: O(log n) para cancelar + O(log n) para agendar = O(log n)
     */
    public boolean reprogramar(String idTurno, LocalDateTime nuevaFecha) {
        Optional<Turno> turnoOpt = buscarPorId(idTurno);
        
        if (turnoOpt.isEmpty()) {
            return false;
        }
        
        Turno turnoAntiguo = turnoOpt.get();
        
        // Crear nuevo turno con la fecha actualizada
        Turno turnoNuevo = new Turno(
            turnoAntiguo.getId(),
            turnoAntiguo.getDniPaciente(),
            turnoAntiguo.getMatriculaMedico(),
            nuevaFecha,
            turnoAntiguo.getDuracionMin(),
            turnoAntiguo.getMotivo()
        );
        
        // Intentar cancelar y reagendar
        if (agenda.cancelar(idTurno) && agenda.agendar(turnoNuevo)) {
            pilaUndo.push(new Accion(TipoAccion.REPROGRAMAR, turnoNuevo, turnoAntiguo));
            pilaRedo.clear(); // Limpiar redo después de nueva acción
            return true;
        }
        
        return false;
    }
    
    /**
     * Deshace la última acción
     * Complejidad: O(log n) para operaciones en agenda + O(1) para pilas
     */
    public boolean undo() {
        if (pilaUndo.isEmpty()) {
            return false;
        }
        
        Accion accion = pilaUndo.pop();
        
        switch (accion.tipo) {
            case AGENDAR:
                // Deshacer agendar = cancelar
                if (agenda.cancelar(accion.turno.getId())) {
                    pilaRedo.push(accion);
                    return true;
                }
                break;
                
            case CANCELAR:
                // Deshacer cancelar = reagendar
                if (agenda.agendar(accion.turno)) {
                    pilaRedo.push(accion);
                    return true;
                }
                break;
                
            case REPROGRAMAR:
                // Deshacer reprogramar = volver a fecha antigua
                if (agenda.cancelar(accion.turno.getId()) && 
                    agenda.agendar(accion.turnoAntiguo)) {
                    pilaRedo.push(accion);
                    return true;
                }
                break;
        }
        
        return false;
    }
    
    /**
     * Rehace la última acción deshecha
     * Complejidad: O(log n) para operaciones en agenda + O(1) para pilas
     */
    public boolean redo() {
        if (pilaRedo.isEmpty()) {
            return false;
        }
        
        Accion accion = pilaRedo.pop();
        
        switch (accion.tipo) {
            case AGENDAR:
                // Rehacer agendar
                if (agenda.agendar(accion.turno)) {
                    pilaUndo.push(accion);
                    return true;
                }
                break;
                
            case CANCELAR:
                // Rehacer cancelar
                if (agenda.cancelar(accion.turno.getId())) {
                    pilaUndo.push(accion);
                    return true;
                }
                break;
                
            case REPROGRAMAR:
                // Rehacer reprogramar
                if (agenda.cancelar(accion.turnoAntiguo.getId()) && 
                    agenda.agendar(accion.turno)) {
                    pilaUndo.push(accion);
                    return true;
                }
                break;
        }
        
        return false;
    }
    
    /**
     * Retorna la cantidad de acciones disponibles para undo
     */
    public int undoCount() {
        return pilaUndo.getSize();
    }
    
    /**
     * Retorna la cantidad de acciones disponibles para redo
     */
    public int redoCount() {
        return pilaRedo.getSize();
    }
    
    /**
     * Limpia todo el historial
     */
    public void limpiarHistorial() {
        pilaUndo.clear();
        pilaRedo.clear();
    }
    
    /**
     * Obtiene la agenda base (para acceso a métodos adicionales)
     */
    public AgendaMedicoTree getAgendaBase() {
        return agenda;
    }
}
