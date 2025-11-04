package core.integrador;

import core.estructuras.monticulo.MonticuloIndexado;
import core.estructuras.hash.TablaHash;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Planificador de recordatorios por prioridad temporal usando MonticuloIndexado + TablaHash.
 * 
 * Utiliza dos estructuras complementarias:
 * 1. MonticuloIndexado (Min-Heap con índice inverso) - mantiene recordatorios ordenados por fecha
 * 2. TablaHash<String, Recordatorio> - índice para búsqueda O(1) por ID
 * 
 * Complejidades alcanzadas:
 * - programar(): O(log n) - inserción en heap + O(1) en hash
 * - proximo(): O(log n) - extracción de raíz + O(1) eliminación en hash
 * - reprogramar(): O(log n) ✅ - O(1) búsqueda + O(log n) actualización en heap
 * - size(): O(1) - delegado al heap
 * 
 * MonticuloIndexado es una extensión de MonticuloBinario que:
 * - Mantiene un índice inverso interno para búsquedas O(1)
 * - Permite actualizar elementos en O(log n) con update()
 * - No modifica la clase base MonticuloBinario
 * 
 * @author Integrante 2
 * @version 3.0
 */
public class PlanificadorRecordatorios implements Planner {
    
    private MonticuloIndexado<Recordatorio> heap;
    private TablaHash<String, Recordatorio> indice; // ID -> Recordatorio
    
    /**
     * Constructor que inicializa el planificador con un MonticuloIndexado (Min-Heap)
     * y una tabla hash para índice de IDs.
     */
    public PlanificadorRecordatorios() {
        this.heap = new MonticuloIndexado<>(); // Min-Heap por defecto
        this.indice = new TablaHash<>();
    }
    
    /**
     * Programa un nuevo recordatorio.
     * 
     * El recordatorio se inserta en el heap y se registra en el índice.
     * El heap se reordena automáticamente para mantener la propiedad de min-heap.
     * 
     * Complejidad: O(log n) + O(1) = O(log n)
     * 
     * @param r Recordatorio a programar
     * @throws IllegalArgumentException si el recordatorio es null o su ID ya existe
     */
    @Override
    public void programar(Recordatorio r) {
        if (r == null) {
            throw new IllegalArgumentException("Recordatorio no puede ser null");
        }
        
        // Verificar que el ID no exista ya (O(1) con TablaHash)
        if (indice.containsKey(r.getId())) {
            throw new IllegalArgumentException("Ya existe un recordatorio con ID: " + r.getId());
        }
        
        heap.add(r);           // O(log n)
        indice.put(r.getId(), r); // O(1)
    }
    
    /**
     * Obtiene y elimina el próximo recordatorio (fecha más cercana).
     * 
     * La raíz del min-heap siempre contiene el recordatorio con la fecha
     * más próxima. Al extraerla, se elimina también del índice.
     * 
     * Complejidad: O(log n) + O(1) = O(log n)
     * 
     * @return El recordatorio más próximo, o null si no hay ninguno
     */
    @Override
    public Recordatorio proximo() {
        if (heap.isEmpty()) {
            return null;
        }
        
        Recordatorio r = heap.poll();        // O(log n)
        indice.remove(r.getId());            // O(1)
        return r;
    }
    
    /**
     * Reprograma un recordatorio existente con una nueva fecha.
     * 
     * Proceso optimizado con MonticuloIndexado + TablaHash:
     * 1. Buscar el recordatorio por ID en el índice externo: O(1)
     * 2. Actualizar la fecha del recordatorio
     * 3. Usar heap.update() para actualizar en el heap: O(log n)
     *    - Encuentra posición con índice interno del heap: O(1)
     *    - Realiza percolate-up o percolate-down según corresponda: O(log n)
     * 
     * Complejidad total: O(log n) ✅
     * 
     * Nota: Se logra O(log n) gracias a MonticuloIndexado que mantiene un
     * TablaHash interno (elemento -> posición) permitiendo búsqueda O(1)
     * dentro del heap y actualización in-place con reordenamiento O(log n).
     * 
     * @param id ID del recordatorio a reprogramar
     * @param nuevaFecha Nueva fecha/hora programada
     * @throws IllegalArgumentException si el ID no existe o la fecha es null
     */
    @Override
    public void reprogramar(String id, LocalDateTime nuevaFecha) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID no puede ser null o vacío");
        }
        if (nuevaFecha == null) {
            throw new IllegalArgumentException("Nueva fecha no puede ser null");
        }
        
        // Buscar en el índice externo (ID -> Recordatorio) O(1)
        Recordatorio encontrado = indice.get(id);
        
        if (encontrado == null) {
            throw new IllegalArgumentException("No existe recordatorio con ID: " + id);
        }
        
        // Guardar estado anterior para búsqueda en heap
        // (necesario porque equals/hashCode usan id, no fecha)
        Recordatorio valorAntiguo = new Recordatorio(
            encontrado.getId(), 
            encontrado.getFecha(), 
            encontrado.getDniPaciente(), 
            encontrado.getMensaje()
        );
        
        // Actualizar fecha del recordatorio original
        encontrado.setFecha(nuevaFecha);
        
        // Usar heap.update() para reordenar: O(log n)
        // - Busca valorAntiguo en índice interno: O(1)
        // - Actualiza con encontrado (misma identidad, nueva fecha)
        // - Reordena con percolate: O(log n)
        boolean actualizado = heap.update(valorAntiguo, encontrado);
        
        if (!actualizado) {
            throw new IllegalStateException("Error interno: no se pudo actualizar el heap");
        }
        
        // El índice externo ya tiene la referencia actualizada (mismo objeto)
        // No es necesario actualizar indice porque encontrado es el mismo objeto
    }
    
    /**
     * Retorna la cantidad de recordatorios programados.
     * 
     * Complejidad: O(1)
     * 
     * @return Número de recordatorios
     */
    @Override
    public int size() {
        return heap.size();
    }
    
    /**
     * Verifica si el planificador está vacío.
     * 
     * @return true si no hay recordatorios, false en caso contrario
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    /**
     * Consulta el próximo recordatorio sin eliminarlo.
     * 
     * @return El recordatorio más próximo, o null si no hay ninguno
     */
    public Recordatorio peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.peek();
    }
    
    /**
     * Verifica si existe un recordatorio con el ID dado.
     * 
     * Complejidad: O(1) - búsqueda en TablaHash
     * 
     * @param id ID a buscar
     * @return true si existe, false en caso contrario
     */
    public boolean existeId(String id) {
        return indice.containsKey(id);
    }
    
    /**
     * Obtiene un recordatorio por su ID sin eliminarlo.
     * 
     * Complejidad: O(1)
     * 
     * @param id ID del recordatorio
     * @return El recordatorio encontrado, o null si no existe
     */
    public Recordatorio getRecordatorio(String id) {
        return indice.get(id);
    }
    
    /**
     * Limpia todos los recordatorios del planificador.
     */
    public void clear() {
        heap.clear();
        indice.clear();
    }
    
    /**
     * Retorna una lista con todos los recordatorios (sin orden garantizado).
     * 
     * @return Lista de recordatorios
     */
    public List<Recordatorio> getRecordatorios() {
        return heap.getElements();
    }
    
    /**
     * Verifica si el heap interno es válido (útil para testing).
     * 
     * @return true si la estructura es un heap válido
     */
    public boolean isValidHeap() {
        return heap.isValidHeap();
    }
    
    @Override
    public String toString() {
        return "PlanificadorRecordatorios [recordatorios=" + size() + 
               ", próximo=" + (isEmpty() ? "ninguno" : peek().getFecha()) + 
               ", índice=" + indice.size() + "]";
    }
}
