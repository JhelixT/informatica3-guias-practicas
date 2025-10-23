package core.estructuras.pilas;

/**
 * Implementación de una Pila usando un arreglo dinámico.
 * 
 * Una pila (Stack) es una estructura de datos LIFO (Last In, First Out).
 * Esta implementación crece automáticamente cuando se llena.
 * 
 * @param <T> Tipo de dato que almacena la pila
 * @author JhelixT
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class PilaArreglo<T> {
    private T[] data;
    private int top;
    private int capacity;

    public PilaArreglo(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor a 0");
        }
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.top = -1;
    }

    public void push(T value) {
        if (isFull()) {
            // Crear nuevo arreglo con 50% más de capacidad
            int newCapacity = (int) (capacity * 1.5);
            if (newCapacity == capacity) newCapacity = capacity + 1;
            
            T[] newData = (T[]) new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, capacity);
            
            data = newData;
            capacity = newCapacity;
        }
        data[++top] = value;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Pila vacía, no se puede desapilar");
        }
        return data[top--];
    }

    public T top() {
        if (isEmpty()) {
            throw new RuntimeException("Pila vacía");
        }
        return data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == capacity - 1;
    }
    
    public int getSize() {
        return top + 1;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void clear() {
        top = -1;
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Pila vacía";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Pila [");
        for (int i = 0; i <= top; i++) {
            sb.append(data[i]);
            if (i < top) sb.append(", ");
        }
        sb.append("] (top: ").append(data[top]).append(")");
        return sb.toString();
    }
}