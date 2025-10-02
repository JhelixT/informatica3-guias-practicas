package core.estructuras.pilas;

public class PilaArreglo {
    private int[] data;
    private int top;
    private int size;

    public PilaArreglo(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor a 0");
        }
        this.size = size;
        this.data = new int[size];
        this.top = -1;
    }

    public void push(int dato) {
        if (isFull()) {
            // Crear nuevo arreglo con 50% más de capacidad
            int nuevoTamaño = (int) (size * 1.5);
            if (nuevoTamaño == size) nuevoTamaño = size + 1;
            
            int[] nuevo = new int[nuevoTamaño];
            System.arraycopy(data, 0, nuevo, 0, size);
            
            data = nuevo;
            size = nuevoTamaño;
        }
        data[++top] = dato;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Pila vacía, no se puede desapilar");
        }
        return data[top--];
    }

    public int top() {
        if (isEmpty()) {
            throw new RuntimeException("Pila vacía");
        }
        return data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == size - 1;
    }
    
    public int getSize() {
        return top + 1;
    }
    
    public int getCapacity() {
        return size;
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