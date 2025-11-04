package core.integrador.salaespera;

import core.estructuras.colas.ColaCircular;

/**
 * Sala de espera implementada usando ColaCircular con capacidad fija y overflow control.
 * 
 * Caracter�sticas:
 * - Capacidad fija (no crece)
 * - Overflow control: cuando est� llena, nuevas llegadas reemplazan al m�s antiguo
 * - Todas las operaciones son O(1)
 * - Usa ColaCircular<String> en modo overflow (allowOverflow=true)
 * 
 * Esta clase es un wrapper sobre ColaCircular que:
 * - Configura el modo overflow en el constructor
 * - Adapta los nombres de m�todos al dominio (llega/atiende/peek/size)
 * - Maneja excepciones retornando null en lugar de lanzar errores
 * 
 * @author Integrante 2
 * @version 3.0
 */
public class SalaEspera {
    
    private final ColaCircular<String> cola;  // Cola circular con overflow control
    
    /**
     * Constructor que inicializa la sala de espera con capacidad dada.
     * Usa ColaCircular en modo overflow (allowOverflow=true).
     * 
     * @param capacidad N�mero m�ximo de pacientes en espera
     * @throws IllegalArgumentException si capacidad <= 0
     */
    public SalaEspera(int capacidad) {
        // ColaCircular con overflow control (capacidad fija)
        this.cola = new ColaCircular<>(capacidad, true);
    }
    
    /**
     * Un paciente llega a la sala de espera.
     * 
     * - Si hay lugar: se agrega normalmente
     * - Si est� llena: reemplaza al paciente m�s antiguo (overflow autom�tico)
     * 
     * Delega en ColaCircular.enqueue() que maneja el overflow internamente.
     * 
     * Complejidad: O(1)
     * 
     * @param dni DNI del paciente que llega
     */
    public void llega(String dni) {
        cola.enqueue(dni);
    }
    
    /**
     * Atiende al siguiente paciente (FIFO - First In First Out).
     * 
     * Delega en ColaCircular.dequeue() pero captura excepciones
     * y retorna null en lugar de lanzarlas.
     * 
     * Complejidad: O(1)
     * 
     * @return DNI del paciente atendido, o null si no hay pacientes
     */
    public String atiende() {
        if (cola.isEmpty()) {
            return null;
        }
        try {
            return cola.dequeue();
        } catch (RuntimeException e) {
            return null; // Manejo seguro de cola vac�a
        }
    }
    
    /**
     * Consulta el DNI del siguiente paciente sin atenderlo.
     * 
     * Delega en ColaCircular.front() pero captura excepciones
     * y retorna null en lugar de lanzarlas.
     * 
     * Complejidad: O(1)
     * 
     * @return DNI del proximo paciente, o null si no hay pacientes
     */
    public String peek() {
        if (cola.isEmpty()) {
            return null;
        }
        try {
            return cola.front();
        } catch (RuntimeException e) {
            return null; // Manejo seguro de cola vac�a
        }
    }
    
    /**
     * Devuelve la cantidad actual de pacientes en espera.
     * 
     * Complejidad: O(1)
     * 
     * @return N�mero de pacientes en la sala
     */
    public int size() {
        return cola.getSize();
    }
    
    /**
     * Verifica si la sala est� vac�a.
     * 
     * @return true si no hay pacientes, false en caso contrario
     */
    public boolean isEmpty() {
        return cola.isEmpty();
    }
    
    /**
     * Verifica si la sala est� llena.
     * 
     * @return true si alcanz� la capacidad m�xima
     */
    public boolean isFull() {
        return cola.isFull();
    }
    
    /**
     * Devuelve la capacidad maxima de la sala.
     * 
     * @return Capacidad configurada
     */
    public int getCapacidad() {
        return cola.getCapacity();
    }
    
    /**
     * Representacion visual del estado de la sala.
     * Delega en ColaCircular.toString() y agrega contexto de dominio.
     * 
     * @return String con el estado de la sala
     */
    @Override
    public String toString() {
        if (cola.isEmpty()) {
            return "SalaEspera vacia [capacidad=" + cola.getCapacity() + ", overflow=true]";
        }
        
        return "SalaEspera [size=" + cola.getSize() + "/" + cola.getCapacity() + 
               ", overflow=true] - " + cola.toString();
    }
}
