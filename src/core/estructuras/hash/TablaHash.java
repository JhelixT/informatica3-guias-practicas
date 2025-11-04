package core.estructuras.hash;

import core.estructuras.listas.ListaEnlazada;
import core.estructuras.nodos.Nodo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementación de Tabla Hash con encadenamiento usando ListaEnlazada del proyecto.
 * 
 * Una tabla hash es una estructura de datos que implementa un array asociativo,
 * permitiendo mapear claves a valores mediante una función hash.
 * 
 * Características:
 * - Usa encadenamiento para resolver colisiones (cada posición es una ListaEnlazada)
 * - Factor de carga máximo: 0.75 (redimensiona cuando se alcanza)
 * - Redimensionamiento dinámico cuando se llena
 * - Implementación 100% con estructuras del proyecto
 * - Soporte completo para claves null (null-safe)
 * 
 * Complejidad temporal (promedio):
 * - Insertar: O(1)
 * - Buscar: O(1)
 * - Eliminar: O(1)
 * 
 * Complejidad temporal (peor caso con muchas colisiones):
 * - Insertar: O(n)
 * - Buscar: O(n)
 * - Eliminar: O(n)
 * 
 * @param <K> Tipo de la clave
 * @param <V> Tipo del valor
 * @author JhelixT
 * @version 2.1 (Mejorada con null-safety y hash robusto)
 */
public class TablaHash<K, V> {
    
    /**
     * Entrada que almacena un par clave-valor.
     */
    private static class Entry<K, V> {
        private K key;
        private V value;
        
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return key;
        }
        
        public V getValue() {
            return value;
        }
        
        public void setValue(V value) {
            this.value = value;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Entry)) return false;
            @SuppressWarnings("unchecked")
            Entry<K, V> other = (Entry<K, V>) obj;
            // Usa Objects.equals para comparación null-safe
            return Objects.equals(key, other.key);
        }
        
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
    
    private static final int CAPACIDAD_INICIAL = 16;
    private static final double FACTOR_CARGA_MAXIMO = 0.75;
    
    private ListaEnlazada<Entry<K, V>>[] tabla;
    private int size;
    private int capacidad;
    
    /**
     * Constructor por defecto con capacidad inicial de 16.
     */
    @SuppressWarnings("unchecked")
    public TablaHash() {
        this.capacidad = CAPACIDAD_INICIAL;
        this.tabla = new ListaEnlazada[capacidad];
        this.size = 0;
        
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaEnlazada<>();
        }
    }
    
    /**
     * Constructor con capacidad personalizada.
     * 
     * @param capacidadInicial La capacidad inicial de la tabla
     */
    @SuppressWarnings("unchecked")
    public TablaHash(int capacidadInicial) {
        this.capacidad = capacidadInicial;
        this.tabla = new ListaEnlazada[capacidad];
        this.size = 0;
        
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaEnlazada<>();
        }
    }
    
    /**
     * Calcula el índice hash para una clave.
     * Usa bitwise AND con 0x7FFFFFFF para garantizar valores no negativos,
     * evitando el problema de Math.abs(Integer.MIN_VALUE) que retorna negativo.
     * 
     * @param key La clave (puede ser null)
     * @return El índice en la tabla (0 a capacidad-1)
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        // Bitwise AND con 0x7FFFFFFF elimina el bit de signo, garantizando valores positivos
        return (key.hashCode() & 0x7FFFFFFF) % capacidad;
    }
    
    /**
     * Inserta o actualiza un par clave-valor en la tabla.
     * Si la clave ya existe, actualiza el valor.
     * Soporta claves null.
     * 
     * Complejidad: O(1) en promedio, O(n) en el peor caso
     * 
     * @param key La clave (puede ser null)
     * @param value El valor
     */
    public void put(K key, V value) {
        int index = hash(key);
        ListaEnlazada<Entry<K, V>> bucket = tabla[index];
        
        // Buscar si la clave ya existe usando ListaEnlazada
        Nodo<Entry<K, V>> current = bucket.getHead();
        while (current != null) {
            Entry<K, V> entry = current.getData();
            if (Objects.equals(entry.getKey(), key)) {
                entry.setValue(value); // Actualizar valor existente
                return;
            }
            current = current.getNext();
        }
        
        // Clave nueva, agregar al bucket
        bucket.insertFirst(new Entry<>(key, value));
        size++;
        
        // Verificar si necesita redimensionar
        if (getLoadFactor() > FACTOR_CARGA_MAXIMO) {
            resize();
        }
    }
    
    /**
     * Obtiene el valor asociado a una clave.
     * Soporta claves null.
     * 
     * Complejidad: O(1) en promedio, O(n) en el peor caso
     * 
     * @param key La clave a buscar (puede ser null)
     * @return El valor asociado, o null si no existe
     */
    public V get(K key) {
        int index = hash(key);
        ListaEnlazada<Entry<K, V>> bucket = tabla[index];
        
        // Buscar en la lista enlazada
        Nodo<Entry<K, V>> current = bucket.getHead();
        while (current != null) {
            Entry<K, V> entry = current.getData();
            if (Objects.equals(entry.getKey(), key)) {
                return entry.getValue();
            }
            current = current.getNext();
        }
        
        return null; // Clave no encontrada
    }
    
    /**
     * Elimina un par clave-valor de la tabla.
     * Soporta claves null.
     * 
     * Complejidad: O(1) en promedio, O(n) en el peor caso
     * 
     * @param key La clave a eliminar (puede ser null)
     * @return El valor asociado a la clave eliminada, o null si no existía
     */
    public V remove(K key) {
        int index = hash(key);
        ListaEnlazada<Entry<K, V>> bucket = tabla[index];
        
        // Buscar y eliminar usando ListaEnlazada
        Nodo<Entry<K, V>> current = bucket.getHead();
        int position = 0;
        
        while (current != null) {
            Entry<K, V> entry = current.getData();
            if (Objects.equals(entry.getKey(), key)) {
                // Encontrado, eliminar usando removeAt de ListaEnlazada
                bucket.removeAt(position);
                size--;
                return entry.getValue();
            }
            current = current.getNext();
            position++;
        }
        
        return null; // Clave no encontrada
    }
    
    /**
     * Verifica si la tabla contiene una clave.
     * Implementación explícita que no depende del valor asociado,
     * permitiendo distinguir entre ausencia de clave y valor null.
     * Soporta claves null.
     * 
     * @param key La clave a verificar (puede ser null)
     * @return true si la clave existe, false en caso contrario
     */
    public boolean containsKey(K key) {
        int index = hash(key);
        ListaEnlazada<Entry<K, V>> bucket = tabla[index];
        
        Nodo<Entry<K, V>> current = bucket.getHead();
        while (current != null) {
            Entry<K, V> entry = current.getData();
            if (Objects.equals(entry.getKey(), key)) {
                return true;
            }
            current = current.getNext();
        }
        
        return false;
    }
    
    /**
     * Verifica si la tabla está vacía.
     * 
     * @return true si no hay elementos, false en caso contrario
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Obtiene el número de pares clave-valor en la tabla.
     * 
     * @return El tamaño de la tabla
     */
    public int size() {
        return size;
    }
    
    /**
     * Obtiene la capacidad actual de la tabla.
     * 
     * @return La capacidad (número de buckets)
     */
    public int getCapacity() {
        return capacidad;
    }
    
    /**
     * Calcula el factor de carga actual.
     * 
     * @return El factor de carga (size / capacidad)
     */
    public double getLoadFactor() {
        return (double) size / capacidad;
    }
    
    /**
     * Limpia todos los elementos de la tabla.
     */
    public void clear() {
        for (int i = 0; i < capacidad; i++) {
            tabla[i].clear();
        }
        size = 0;
    }
    
    /**
     * Redimensiona la tabla cuando el factor de carga es muy alto.
     * Duplica la capacidad y rehashea todos los elementos.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int nuevaCapacidad = capacidad * 2;
        ListaEnlazada<Entry<K, V>>[] nuevaTabla = new ListaEnlazada[nuevaCapacidad];
        
        // Inicializar nueva tabla
        for (int i = 0; i < nuevaCapacidad; i++) {
            nuevaTabla[i] = new ListaEnlazada<>();
        }
        
        // Rehashear todos los elementos
        int capacidadAnterior = capacidad;
        capacidad = nuevaCapacidad;
        
        for (int i = 0; i < capacidadAnterior; i++) {
            ListaEnlazada<Entry<K, V>> bucket = tabla[i];
            
            // Recorrer cada elemento en el bucket usando Nodo
            Nodo<Entry<K, V>> current = bucket.getHead();
            while (current != null) {
                Entry<K, V> entry = current.getData();
                
                // Calcular nuevo índice y agregar
                int nuevoIndex = hash(entry.getKey());
                nuevaTabla[nuevoIndex].insertFirst(entry);
                
                current = current.getNext();
            }
        }
        
        tabla = nuevaTabla;
    }
    
    /**
     * Obtiene una lista de todas las claves en la tabla.
     * 
     * @return Lista con todas las claves
     */
    public List<K> keys() {
        List<K> claves = new ArrayList<>();
        
        for (int i = 0; i < capacidad; i++) {
            ListaEnlazada<Entry<K, V>> bucket = tabla[i];
            
            Nodo<Entry<K, V>> current = bucket.getHead();
            while (current != null) {
                claves.add(current.getData().getKey());
                current = current.getNext();
            }
        }
        
        return claves;
    }
    
    /**
     * Obtiene una lista de todos los valores en la tabla.
     * 
     * @return Lista con todos los valores
     */
    public List<V> values() {
        List<V> valores = new ArrayList<>();
        
        for (int i = 0; i < capacidad; i++) {
            ListaEnlazada<Entry<K, V>> bucket = tabla[i];
            
            Nodo<Entry<K, V>> current = bucket.getHead();
            while (current != null) {
                valores.add(current.getData().getValue());
                current = current.getNext();
            }
        }
        
        return valores;
    }
    
    /**
     * Obtiene una lista con todas las entradas como strings "clave=valor".
     * 
     * @return Lista con todas las entradas
     */
    public List<String> entries() {
        List<String> entradas = new ArrayList<>();
        
        for (int i = 0; i < capacidad; i++) {
            ListaEnlazada<Entry<K, V>> bucket = tabla[i];
            
            Nodo<Entry<K, V>> current = bucket.getHead();
            while (current != null) {
                entradas.add(current.getData().toString());
                current = current.getNext();
            }
        }
        
        return entradas;
    }
    
    /**
     * Muestra la estructura interna de la tabla hash.
     * Útil para debugging y visualizar cómo se distribuyen las claves.
     */
    public void display() {
        System.out.println("╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                  ESTRUCTURA INTERNA - TABLA HASH                           ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ Capacidad: %-10d   Elementos: %-10d   Load Factor: %.2f         ║%n", 
                         capacidad, size, getLoadFactor());
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        
        int bucketsShown = 0;
        final int MAX_BUCKETS = 10;
        
        for (int i = 0; i < capacidad && bucketsShown < MAX_BUCKETS; i++) {
            ListaEnlazada<Entry<K, V>> bucket = tabla[i];
            
            if (!bucket.isEmpty()) {
                System.out.printf("║ [%3d] -> ", i);
                
                Nodo<Entry<K, V>> current = bucket.getHead();
                boolean first = true;
                StringBuilder line = new StringBuilder();
                while (current != null) {
                    if (!first) {
                        line.append(" -> ");
                    }
                    line.append(current.getData().toString());
                    current = current.getNext();
                    first = false;
                }
                
                // Imprimir la línea, truncarla si es muy larga
                String lineStr = line.toString();
                if (lineStr.length() > 65) {
                    System.out.println(lineStr.substring(0, 62) + "...║");
                } else {
                    System.out.printf("%-65s║%n", lineStr);
                }
                
                bucketsShown++;
            }
        }
        
        // Contar buckets no mostrados
        int bucketsNoMostrados = 0;
        for (int i = 0; i < capacidad; i++) {
            if (!tabla[i].isEmpty()) {
                bucketsNoMostrados++;
            }
        }
        bucketsNoMostrados -= bucketsShown;
        
        if (bucketsNoMostrados > 0) {
            System.out.printf("║ ... (%d buckets adicionales no mostrados)                                  ║%n", bucketsNoMostrados);
        }
        
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Obtiene estadísticas de colisiones en la tabla.
     * 
     * @return Array con [bucketsUsados, maxLongitudCadena, colisionesTotales]
     */
    public int[] getCollisionStats() {
        int bucketsUsados = 0;
        int maxLongitudCadena = 0;
        int colisionesTotales = 0;
        
        for (int i = 0; i < capacidad; i++) {
            int bucketSize = tabla[i].getSize();
            
            if (bucketSize > 0) {
                bucketsUsados++;
                maxLongitudCadena = Math.max(maxLongitudCadena, bucketSize);
                
                if (bucketSize > 1) {
                    colisionesTotales += (bucketSize - 1);
                }
            }
        }
        
        return new int[]{bucketsUsados, maxLongitudCadena, colisionesTotales};
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "TablaHash vacía (capacidad: " + capacidad + ")";
        }
        
        return String.format("TablaHash [size=%d, capacidad=%d, factor=%.2f]", 
                           size, capacidad, getLoadFactor());
    }
}
