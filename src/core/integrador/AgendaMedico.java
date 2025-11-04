package core.integrador;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendaMedico {
    boolean agendar(Turno t);
    boolean cancelar(String idTurno);
    Optional<Turno> siguiente(LocalDateTime t);
}