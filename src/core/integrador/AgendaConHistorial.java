package core.integrador;

import java.time.LocalDateTime;

public interface AgendaConHistorial extends AgendaMedico {
    boolean reprogramar(String idTurno, LocalDateTime nuevaFecha);
    boolean undo();
    boolean redo();
}
