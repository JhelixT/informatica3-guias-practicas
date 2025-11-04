package core.integrador.pacientes;

import core.integrador.modelo.Paciente;

/**
 * Interfaz para índice rápido de pacientes por DNI.
 * Operaciones en O(1) promedio.
 */
public interface MapaPacientes {
    
    void put(String dni, Paciente p);
    
    Paciente get(String dni);
    
    boolean remove(String dni);
    
    boolean containsKey(String dni);
    
    int size();
    
    Iterable<String> keys();
}
