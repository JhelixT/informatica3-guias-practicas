package core.integrador;

import core.estructuras.hash.TablaHash;
import java.util.List;

/**
 * Índice rápido de pacientes usando TablaHash del proyecto.
 * 
 * Wrapper sobre TablaHash<String, Paciente> que implementa MapaPacientes.
 * 
 * TablaHash internamente usa:
 * - Hash con chaining (listas enlazadas para colisiones)
 * - Rehash automático cuando loadFactor > 0.75
 * - Operaciones O(1) promedio: put, get, remove, containsKey
 * 
 * Función hash:
 * Usa el hashCode() de String que implementa algoritmo polinomial:
 * hash = s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
 * 
 * Esta función es efectiva porque:
 * - 31 es primo → mejor distribución, menos colisiones
 * - Potencias de 31 → optimizado por compilador (31*i = (i<<5)-i)
 * - Considera todos los caracteres → DNIs similares dan hashes diferentes
 * - Usado por Java estándar → probado y confiable
 * 
 * Ejemplo: "12345678" vs "12345679" tienen hashes muy diferentes
 * evitando colisiones entre DNIs consecutivos.
 */
public class IndicePacientes implements MapaPacientes {
    
    private TablaHash<String, Paciente> tabla;
    
    public IndicePacientes() {
        this.tabla = new TablaHash<>();
    }
    
    public IndicePacientes(int capacidadInicial) {
        this.tabla = new TablaHash<>(capacidadInicial);
    }
    
    /**
     * Inserta o actualiza un paciente.
     * 
     * Complejidad: O(1) promedio
     */
    @Override
    public void put(String dni, Paciente p) {
        if (dni == null || p == null) {
            throw new IllegalArgumentException("DNI y Paciente no pueden ser null");
        }
        tabla.put(dni, p);
    }
    
    /**
     * Obtiene un paciente por DNI.
     * 
     * Complejidad: O(1) promedio
     */
    @Override
    public Paciente get(String dni) {
        if (dni == null) {
            return null;
        }
        return tabla.get(dni);
    }
    
    /**
     * Elimina un paciente por DNI.
     * 
     * Complejidad: O(1) promedio
     */
    @Override
    public boolean remove(String dni) {
        if (dni == null) {
            return false;
        }
        return tabla.remove(dni) != null;
    }
    
    /**
     * Verifica si existe un DNI.
     * 
     * Complejidad: O(1) promedio
     */
    @Override
    public boolean containsKey(String dni) {
        return tabla.containsKey(dni);
    }
    
    /**
     * Retorna cantidad de pacientes.
     * 
     * Complejidad: O(1)
     */
    @Override
    public int size() {
        return tabla.size();
    }
    
    /**
     * Retorna todos los DNIs almacenados.
     * 
     * Complejidad: O(n)
     */
    @Override
    public Iterable<String> keys() {
        return tabla.keys();
    }
    
    /**
     * Retorna capacidad actual de la tabla.
     */
    public int getCapacidad() {
        return tabla.getCapacity();
    }
    
    /**
     * Retorna load factor actual.
     */
    public double getLoadFactor() {
        return tabla.getLoadFactor();
    }
    
    /**
     * Verifica si está vacío.
     */
    public boolean isEmpty() {
        return tabla.isEmpty();
    }
    
    /**
     * Limpia todos los pacientes.
     */
    public void clear() {
        tabla.clear();
    }
    
    /**
     * Retorna todos los pacientes.
     */
    public List<Paciente> values() {
        return tabla.values();
    }
    
    /**
     * Muestra estructura interna de la tabla (debug).
     */
    public void display() {
        tabla.display();
    }
    
    /**
     * Retorna estadísticas de colisiones.
     * [bucketsUsados, maxLongitudCadena, colisionesTotales]
     */
    public int[] getCollisionStats() {
        return tabla.getCollisionStats();
    }
    
    /**
     * Retorna estadísticas formateadas para análisis.
     */
    public String getStats() {
        int[] stats = tabla.getCollisionStats();
        return String.format("Capacidad: %d, Size: %d, Load Factor: %.2f, " +
                           "Buckets usados: %d (%.1f%%), Max colisiones: %d, Colisiones totales: %d",
                           tabla.getCapacity(), tabla.size(), tabla.getLoadFactor(),
                           stats[0], (stats[0] * 100.0 / tabla.getCapacity()),
                           stats[1] - 1, stats[2]);
    }
    
    @Override
    public String toString() {
        return "IndicePacientes [" + tabla.toString() + "]";
    }
}
