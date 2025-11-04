package core.integrador.pacientes;

import core.integrador.modelo.Paciente;
import core.estructuras.listas.ListaEnlazada;

/**
 * Gestor de operaciones sobre pacientes usando TablaHash
 */
public class GestorPacientes {
    
    private IndicePacientes indice;
    private ListaEnlazada<Paciente> listaPacientes;
    
    public GestorPacientes(IndicePacientes indice, ListaEnlazada<Paciente> listaPacientes) {
        this.indice = indice;
        this.listaPacientes = listaPacientes;
    }
    
    /**
     * Busca un paciente por DNI y devuelve el tiempo de búsqueda en microsegundos
     */
    public ResultadoBusqueda buscar(String dni) {
        long inicio = System.nanoTime();
        Paciente p = indice.get(dni);
        long fin = System.nanoTime();
        double microseg = (fin - inicio) / 1000.0;
        
        return new ResultadoBusqueda(p, microseg);
    }
    
    /**
     * Agrega un nuevo paciente validando que no exista
     */
    public ResultadoOperacion agregar(String dni, String nombre) {
        if (dni == null || dni.trim().isEmpty()) {
            return new ResultadoOperacion(false, "El DNI no puede estar vacío");
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ResultadoOperacion(false, "El nombre no puede estar vacío");
        }
        
        if (indice.get(dni) != null) {
            return new ResultadoOperacion(false, "Ya existe un paciente con ese DNI");
        }
        
        Paciente nuevo = new Paciente(dni, nombre);
        indice.put(dni, nuevo);
        listaPacientes.insertLast(nuevo);
        
        return new ResultadoOperacion(true, "Paciente agregado exitosamente");
    }
    
    /**
     * Elimina un paciente por DNI
     */
    public ResultadoOperacion eliminar(String dni) {
        Paciente p = indice.get(dni);
        
        if (p == null) {
            return new ResultadoOperacion(false, "Paciente no encontrado");
        }
        
        indice.remove(dni);
        return new ResultadoOperacion(true, "Paciente eliminado exitosamente", p);
    }
    
    /**
     * Obtiene estadísticas del índice
     */
    public EstadisticasIndice obtenerEstadisticas() {
        int[] stats = indice.getCollisionStats();
        return new EstadisticasIndice(
            indice.getCapacidad(),
            indice.size(),
            indice.getLoadFactor(),
            stats[0],  // buckets usados
            stats[2]   // colisiones
        );
    }
    
    // ===== CLASES INTERNAS =====
    
    public static class ResultadoBusqueda {
        private Paciente paciente;
        private double tiempoMicrosegundos;
        
        public ResultadoBusqueda(Paciente paciente, double tiempoMicrosegundos) {
            this.paciente = paciente;
            this.tiempoMicrosegundos = tiempoMicrosegundos;
        }
        
        public Paciente getPaciente() { return paciente; }
        public double getTiempoMicrosegundos() { return tiempoMicrosegundos; }
        public boolean encontrado() { return paciente != null; }
    }
    
    public static class ResultadoOperacion {
        private boolean exito;
        private String mensaje;
        private Paciente paciente;
        
        public ResultadoOperacion(boolean exito, String mensaje) {
            this(exito, mensaje, null);
        }
        
        public ResultadoOperacion(boolean exito, String mensaje, Paciente paciente) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.paciente = paciente;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public Paciente getPaciente() { return paciente; }
    }
    
    public static class EstadisticasIndice {
        private int capacidad;
        private int elementos;
        private double loadFactor;
        private int bucketsUsados;
        private int colisiones;
        
        public EstadisticasIndice(int capacidad, int elementos, double loadFactor,
                                 int bucketsUsados, int colisiones) {
            this.capacidad = capacidad;
            this.elementos = elementos;
            this.loadFactor = loadFactor;
            this.bucketsUsados = bucketsUsados;
            this.colisiones = colisiones;
        }
        
        public int getCapacidad() { return capacidad; }
        public int getElementos() { return elementos; }
        public double getLoadFactor() { return loadFactor; }
        public int getBucketsUsados() { return bucketsUsados; }
        public int getColisiones() { return colisiones; }
    }
}
