package core.integrador.quirofano;

import core.integrador.modelo.SolicitudCirugia;
import core.estructuras.listas.ListaEnlazada;

/** Interfaz para planificar asignación de quirófanos y obtener top-K médicos bloqueados */
public interface PlanificadorQuirofano {
    /** Registra un médico en el sistema */
    void registrarMedico(String matricula, String nombre);
    
    /** Procesa una solicitud de cirugía asignándola al primer quirófano libre */
    void procesar(SolicitudCirugia s);
    
    /** Retorna los K médicos con más minutos bloqueados */
    ListaEnlazada<String> topKMedicosBloqueados(int K);
}
