package core.integrador.quirofano;

import core.integrador.modelo.SolicitudCirugia;
import java.util.List;

/** Interfaz para planificar asignación de quirófanos y obtener top-K médicos bloqueados */
public interface PlanificadorQuirofano {
    /** Procesa una solicitud de cirugía asignándola al primer quirófano libre */
    void procesar(SolicitudCirugia s);
    
    /** Retorna los K médicos con más minutos bloqueados */
    List<String> topKMedicosBloqueados(int K);
}
