package core.estructuras.colas;

import core.estructuras.nodos.Nodo;

/**
 * Implementación de una Cola usando Lista Enlazada.
 * 
 * Una cola (Queue) es una estructura de datos FIFO (First In, First Out)
 * donde el primer elemento insertado es el primero en ser removido.
 * Esta implementación usa una lista enlazada con referencias tanto al
 * frente como al final, permitiendo operaciones O(1) en ambos extremos.
 * 
 * Características:
 * - Enqueue (encolar): O(1)
 * - Dequeue (desencolar): O(1)
 * - Front (ver frente): O(1)
 * - Tamaño dinámico ilimitado
 * 
 * @author JhelixT
 * @version 1.0
 */
public class ColaEnlazada {
    private Nodo front;       // Primer nodo de la cola
    private Nodo rear;        // Último nodo de la cola
    private int size;         // Cantidad de elementos en la cola
    
    /**
     * Constructor que crea una cola enlazada vacía.
     */
    public ColaEnlazada() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }
    
    /**
     * Encola un elemento al final de la cola.
     * 
     * @param data El valor a encolar
     */
    public void enqueue(int data) {
        Nodo newNode = new Nodo(data);
        
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }
        
        size++;
    }
    
    /**
     * Desencola y retorna el elemento al frente de la cola.
     * 
     * @return El elemento que estaba al frente
     * @throws RuntimeException si la cola está vacía
     */
    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Cola vacía, no se puede desencolar");
        }
        
        int value = front.getData();
        front = front.getNext();
        
        if (front == null) {
            rear = null;
        }
        
        size--;
        return value;
    }
    
    /**
     * Retorna el elemento al frente sin desencolarlo.
     * 
     * @return El elemento al frente de la cola
     * @throws RuntimeException si la cola está vacía
     */
    public int front() {
        if (isEmpty()) {
            throw new RuntimeException("Cola vacía");
        }
        return front.getData();
    }
    
    /**
     * Retorna el elemento al final sin desencolarlo.
     * 
     * @return El elemento al final de la cola
     * @throws RuntimeException si la cola está vacía
     */
    public int back() {
        if (isEmpty()) {
            throw new RuntimeException("Cola vacía");
        }
        return rear.getData();
    }
    
    /**
     * Verifica si la cola está vacía.
     * 
     * @return true si la cola no tiene elementos, false en caso contrario
     */
    public boolean isEmpty() {
        return front == null;
    }
    
    /**
     * Obtiene el tamaño actual de la cola.
     * 
     * @return La cantidad de elementos en la cola
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Limpia todos los elementos de la cola.
     */
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
    
    /**
     * Busca un elemento en la cola.
     * Retorna la posición desde el frente (0 = frente, 1 = siguiente, etc.)
     * 
     * @param data El valor a buscar
     * @return La posición desde el frente, o -1 si no se encuentra
     */
    public int buscar(int data) {
        Nodo current = front;
        int position = 0;
        
        while (current != null) {
            if (current.getData() == data) {
                return position;
            }
            current = current.getNext();
            position++;
        }
        
        return -1;
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Cola vacía";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Cola [frente: ");
        Nodo current = front;
        
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(" <- ");
            }
            current = current.getNext();
        }
        
        sb.append(" :fin] (").append(size).append(" elementos)");
        return sb.toString();
    }
}
