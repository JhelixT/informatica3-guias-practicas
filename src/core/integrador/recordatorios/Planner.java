package core.integrador.recordatorios;

import core.integrador.modelo.Recordatorio;
import java.time.LocalDateTime;

/**
 * Planificador de recordatorios por prioridad temporal.
 * Todas las operaciones en O(log n).
 */
public interface Planner {
    
    void programar(Recordatorio r);
    
    Recordatorio proximo();
    
    void reprogramar(String id, LocalDateTime nuevaFecha);
    
    int size();
}
