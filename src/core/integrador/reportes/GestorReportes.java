package core.integrador.reportes;

import core.integrador.modelo.Turno;
import core.integrador.modelo.Paciente;
import core.estructuras.listas.ListaEnlazada;
import core.estructuras.nodos.Nodo;
import core.estructuras.hash.TablaHash;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Genera reportes operativos del día con múltiples ordenamientos.
 * Permite comparar tiempos de ejecución de diferentes algoritmos.
 */
public class GestorReportes {
    
    private TablaHash<String, Paciente> pacientesPorDni;
    
    public GestorReportes(TablaHash<String, Paciente> pacientesPorDni) {
        this.pacientesPorDni = pacientesPorDni;
    }
    
    // ========== COMPARADORES ==========
    
    /**
     * Comparador por hora (estable) - ordena por fechaHora
     */
    public static class ComparadorPorHora implements Comparator<Turno> {
        @Override
        public int compare(Turno t1, Turno t2) {
            return t1.getFechaHora().compareTo(t2.getFechaHora());
        }
    }
    
    /**
     * Comparador por duración - ordena de mayor a menor
     */
    public static class ComparadorPorDuracion implements Comparator<Turno> {
        @Override
        public int compare(Turno t1, Turno t2) {
            return Integer.compare(t2.getDuracionMin(), t1.getDuracionMin()); // Descendente
        }
    }
    
    /**
     * Comparador por apellido de paciente
     */
    public class ComparadorPorApellidoPaciente implements Comparator<Turno> {
        @Override
        public int compare(Turno t1, Turno t2) {
            Paciente p1 = pacientesPorDni.get(t1.getDniPaciente());
            Paciente p2 = pacientesPorDni.get(t2.getDniPaciente());
            
            String nombre1 = (p1 != null) ? p1.getNombre() : "";
            String nombre2 = (p2 != null) ? p2.getNombre() : "";
            
            return nombre1.compareToIgnoreCase(nombre2);
        }
    }
    
    // ========== GENERADORES DE REPORTES ==========
    
    /**
     * Reporte ordenado por hora usando Insertion Sort (estable)
     */
    public ResultadoReporte reportePorHora(ListaEnlazada<Turno> turnos) {
        long inicio = System.nanoTime();
        ListaEnlazada<Turno> resultado = OrdenadorTurnos.insertionSort(turnos, new ComparadorPorHora());
        long fin = System.nanoTime();
        
        return new ResultadoReporte("Por Hora (Insertion Sort)", resultado, fin - inicio);
    }
    
    /**
     * Reporte ordenado por duración usando Shell Sort
     */
    public ResultadoReporte reportePorDuracion(ListaEnlazada<Turno> turnos) {
        long inicio = System.nanoTime();
        ListaEnlazada<Turno> resultado = OrdenadorTurnos.shellSort(turnos, new ComparadorPorDuracion());
        long fin = System.nanoTime();
        
        return new ResultadoReporte("Por Duración (Shell Sort)", resultado, fin - inicio);
    }
    
    /**
     * Reporte ordenado por apellido usando Quick Sort
     */
    public ResultadoReporte reportePorApellido(ListaEnlazada<Turno> turnos) {
        long inicio = System.nanoTime();
        ListaEnlazada<Turno> resultado = OrdenadorTurnos.quickSort(turnos, new ComparadorPorApellidoPaciente());
        long fin = System.nanoTime();
        
        return new ResultadoReporte("Por Apellido (Quick Sort)", resultado, fin - inicio);
    }
    
    /**
     * Filtra turnos de un día específico
     */
    public ListaEnlazada<Turno> filtrarPorDia(ListaEnlazada<Turno> turnos, LocalDateTime fecha) {
        ListaEnlazada<Turno> resultado = new ListaEnlazada<>();
        Nodo<Turno> nodo = turnos.getHead();
        
        while (nodo != null) {
            Turno turno = nodo.getData();
            if (turno.getFechaHora().toLocalDate().equals(fecha.toLocalDate())) {
                resultado.insertLast(turno);
            }
            nodo = nodo.getNext();
        }
        
        return resultado;
    }
    
    /**
     * Genera datos sintéticos para pruebas de rendimiento
     */
    public static ListaEnlazada<Turno> generarDatosSinteticos(int cantidad) {
        ListaEnlazada<Turno> turnos = new ListaEnlazada<>();
        LocalDateTime base = LocalDateTime.of(2025, 11, 5, 8, 0);
        
        for (int i = 0; i < cantidad; i++) {
            String id = "SINT" + String.format("%06d", i);
            String dni = String.format("%08d", i % 1000);
            String matricula = "MP" + String.format("%05d", i % 20);
            LocalDateTime fecha = base.plusMinutes(i * 15);
            int duracion = 30 + (i % 4) * 15; // 30, 45, 60, 75
            String motivo = "Consulta " + (i % 10);
            
            turnos.insertLast(new Turno(id, dni, matricula, fecha, duracion, motivo));
        }
        
        return turnos;
    }
    
    /**
     * Busca un paciente por DNI en la tabla hash
     */
    public Paciente buscarPaciente(String dni) {
        return pacientesPorDni.get(dni);
    }
    
    /**
     * Clase contenedora para resultados de reportes
     */
    public static class ResultadoReporte {
        private String nombre;
        private ListaEnlazada<Turno> turnos;
        private long tiempoNanos;
        
        public ResultadoReporte(String nombre, ListaEnlazada<Turno> turnos, long tiempoNanos) {
            this.nombre = nombre;
            this.turnos = turnos;
            this.tiempoNanos = tiempoNanos;
        }
        
        public String getNombre() {
            return nombre;
        }
        
        public ListaEnlazada<Turno> getTurnos() {
            return turnos;
        }
        
        public long getTiempoNanos() {
            return tiempoNanos;
        }
        
        public double getTiempoMs() {
            return tiempoNanos / 1_000_000.0;
        }
        
        @Override
        public String toString() {
            return String.format("%s: %d turnos en %.3f ms", 
                nombre, turnos.getSize(), getTiempoMs());
        }
    }
}
