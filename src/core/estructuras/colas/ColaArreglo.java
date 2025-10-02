package core.estructuras.colas;

public class ColaArreglo {
    private int[] datos;
    private int front;
    private int back;
    private int size;
    private int capacity;

    public ColaArreglo(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        this.capacity = capacity;
        this.datos = new int[capacity];
        this.front = 0;
        this.back = -1;
        this.size = 0;
    }

    public void enqueue(int dato) {
        if (isFull()) {
            // Crear nuevo arreglo con 50% más de capacidad
            int nuevaCapacidad = (int) (capacity * 1.5);
            if (nuevaCapacidad == capacity) nuevaCapacidad = capacity + 1;
            
            int[] nuevo = new int[nuevaCapacidad];

            // Copiar elementos de la cola al nuevo arreglo en orden
            for (int i = 0; i < size; i++) {
                nuevo[i] = datos[(front + i) % capacity];
            }

            // Ajustar referencias
            datos = nuevo;
            capacity = nuevaCapacidad;
            front = 0;
            back = size - 1;
        }

        back = (back + 1) % capacity;
        datos[back] = dato;
        size++;
    }

    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Cola vacía, no se puede desencolar");
        }
        int valor = datos[front];
        front = (front + 1) % capacity;
        size--;
        return valor;
    }

    public int front() {
        if (isEmpty()) {
            throw new RuntimeException("Cola vacía");
        }
        return datos[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
    
    public int getSize() {
        return size;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void clear() {
        front = 0;
        back = -1;
        size = 0;
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Cola vacía";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Cola [");
        for (int i = 0; i < size; i++) {
            sb.append(datos[(front + i) % capacity]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("] (front: ").append(datos[front]).append(")");
        return sb.toString();
    }
}