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
 * - Crecimiento dinámico cuando se llena (incremento del 50%)
 * - Uso eficiente de memoria al reutilizar espacios
 * 
 * @author JhelixT
 * @version 1.0
 */
public class ColaCircular {
    private int[] data;       // Arreglo que almacena los elementos
    private int front;        // Índice del primer elemento
    private int rear;         // Índice del último elemento
    private int size;         // Cantidad actual de elementos
    private int capacity;     // Capacidad total del arreglo

    /**
     * Constructor de la Cola Circular.
     * 
     * @param capacity Capacidad inicial del arreglo (debe ser mayor a 0)
     * @throws IllegalArgumentException si la capacidad es menor o igual a 0
     */
    public ColaCircular(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        this.capacity = capacity;
        this.data = new int[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    /**
     * Agrega un elemento al final de la cola.
     * Si la cola está llena, aumenta automáticamente su capacidad en un 50%.
     * Utiliza aritmética modular para envolver el índice rear circularmente.
     * 
     * @param value El elemento a agregar a la cola
     */
    public void enqueue(int value) {
        if (isFull()) {
            // Crear nuevo arreglo con 50% más de capacidad
            int newCapacity = (int) (capacity * 1.5);
            if (newCapacity == capacity) newCapacity = capacity + 1;
            
            int[] newData = new int[newCapacity];

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
    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Cola vacía, no se puede desencolar");
        }
        int value = data[front];
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
    public int front() {
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
     * Retorna la cantidad actual de elementos en la cola.
     * 
     * @return El número de elementos en la cola
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Retorna la capacidad total del arreglo subyacente.
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