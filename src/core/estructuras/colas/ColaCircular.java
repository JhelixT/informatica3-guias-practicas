package core.estructuras.colas;

/**
 * Implementación de una Cola Circular usando un arreglo.
 * 
 * Una cola circular utiliza el operador módulo para "envolver" los índices,
 * permitiendo reutilizar el espacio liberado al inicio del arreglo cuando
 * se realizan operaciones de dequeue.
 * 
 * Características:
 * - Operaciones enqueue y dequeue en O(1)
 * - Dos modos de operación:
 *   1. Modo dinámico (por defecto): crece automáticamente cuando se llena
 *   2. Modo overflow: capacidad fija, reemplaza elementos más antiguos
 * - Uso eficiente de memoria al reutilizar espacios
 * 
 * @param <T> Tipo de dato que almacena la cola
 * @author JhelixT
 * @version 2.0
 */
@SuppressWarnings("unchecked")
public class ColaCircular<T> {
    private T[] data;         // Arreglo que almacena los elementos
    private int front;        // Índice del primer elemento
    private int rear;         // Índice del último elemento
    private int size;         // Cantidad actual de elementos
    private int capacity;     // Capacidad total del arreglo
    private final boolean allowOverflow;  // Si true, usa overflow control en lugar de resize

    /**
     * Constructor de la Cola Circular con capacidad dinámica (por defecto).
     * Cuando se llena, la cola crece automáticamente.
     * 
     * @param capacity Capacidad inicial del arreglo (debe ser mayor a 0)
     * @throws IllegalArgumentException si la capacidad es menor o igual a 0
     */
    public ColaCircular(int capacity) {
        this(capacity, false); // Por defecto: modo dinámico (sin overflow)
    }
    
    /**
     * Constructor de la Cola Circular con control de modo.
     * 
     * @param capacity Capacidad del arreglo (debe ser mayor a 0)
     * @param allowOverflow Si es true, usa capacidad fija con overflow control.
     *                      Si es false, crece dinámicamente cuando se llena.
     * @throws IllegalArgumentException si la capacidad es menor o igual a 0
     */
    public ColaCircular(int capacity, boolean allowOverflow) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
        this.allowOverflow = allowOverflow;
    }

    /**
     * Agrega un elemento al final de la cola.
     * 
     * Comportamiento según modo:
     * - Modo dinámico (allowOverflow=false): crece automáticamente si está llena
     * - Modo overflow (allowOverflow=true): reemplaza el elemento más antiguo si está llena
     * 
     * Utiliza aritmética modular para envolver el índice rear circularmente.
     * 
     * @param value El elemento a agregar a la cola
     */
    public void enqueue(T value) {
        if (isFull()) {
            if (allowOverflow) {
                // Modo overflow: reemplaza el más antiguo (capacidad fija)
                rear = (rear + 1) % capacity;
                data[rear] = value;
                front = (front + 1) % capacity; // Avanza front (elimina el más antiguo)
                // size permanece igual (sigue llena)
                return;
            } else {
                // Modo dinámico: crece 50% (comportamiento original)
                int newCapacity = (int) (capacity * 1.5);
                if (newCapacity == capacity) newCapacity = capacity + 1;
                
                T[] newData = (T[]) new Object[newCapacity];

                // Copiar elementos de la cola al nuevo arreglo en orden
                for (int i = 0; i < size; i++) {
                    newData[i] = data[(front + i) % capacity];
                }

                // Ajustar referencias
                data = newData;
                capacity = newCapacity;
                front = 0;
                rear = size - 1;
            }
        }

        rear = (rear + 1) % capacity;
        data[rear] = value;
        size++;
    }

    /**
     * Remueve y retorna el elemento al frente de la cola.
     * Utiliza aritmética modular para envolver el índice front circularmente.
     * 
     * @return El elemento que estaba al frente de la cola
     * @throws RuntimeException si la cola está vacía
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Cola vacía, no se puede desencolar");
        }
        T value = data[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    /**
     * Retorna el elemento al frente de la cola sin removerlo.
     * 
     * @return El elemento al frente de la cola
     * @throws RuntimeException si la cola está vacía
     */
    public T front() {
        if (isEmpty()) {
            throw new RuntimeException("Cola vacía");
        }
        return data[front];
    }

    /**
     * Verifica si la cola está vacía.
     * 
     * @return true si la cola no contiene elementos, false en caso contrario
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Verifica si la cola está llena.
     * 
     * @return true si la cantidad de elementos es igual a la capacidad, false en caso contrario
     */
    public boolean isFull() {
        return size == capacity;
    }
    
    /**
     * Verifica si la cola permite overflow (capacidad fija).
     * 
     * @return true si usa overflow control, false si crece dinámicamente
     */
    public boolean isOverflowMode() {
        return allowOverflow;
    }
    
    /**
     * Retorna la cantidad actual de elementos en la cola.
     * 
     * @return El número de elementos en la cola
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Retorna la capacidad total del arreglo subyacente.
     * En modo overflow, esta capacidad es fija.
     * En modo dinámico, puede crecer.
     * 
     * @return La capacidad actual de la cola
     */
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * Limpia todos los elementos de la cola, reiniciando los índices.
     * No libera memoria, solo restablece el estado inicial.
     */
    public void clear() {
        front = 0;
        rear = -1;
        size = 0;
    }
    
    /**
     * Retorna una representación en String de la cola circular.
     * Muestra los elementos en orden de frente a final y el elemento al frente.
     * 
     * @return Una cadena que representa el estado actual de la cola
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Cola vacía";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Cola [");
        for (int i = 0; i < size; i++) {
            sb.append(data[(front + i) % capacity]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("] (front: ").append(data[front]).append(")");
        return sb.toString();
    }
}