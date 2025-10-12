package core.estructuras.pilas;

import core.estructuras.nodos.Nodo;

/**
 * Implementación de una Pila usando Lista Enlazada.
 * 
 * Una pila (Stack) es una estructura de datos LIFO (Last In, First Out)
 * donde el último elemento insertado es el primero en ser removido.
 * Esta implementación usa una lista enlazada, por lo que no tiene límite
 * de capacidad y todas las operaciones son O(1).
 * 
 * Características:
 * - Push (apilar): O(1)
 * - Pop (desapilar): O(1)
 * - Top (ver tope): O(1)
 * - Tamaño dinámico ilimitado
 * 
 * @author JhelixT
 * @version 1.0
 */
public class PilaEnlazada {
    private Nodo top;         // Nodo en el tope de la pila
    private int size;         // Cantidad de elementos en la pila
    
    /**
     * Constructor que crea una pila enlazada vacía.
     */
    public PilaEnlazada() {
        this.top = null;
        this.size = 0;
    }
    
    /**
     * Apila un elemento en el tope de la pila.
     * 
     * @param data El valor a apilar
     */
    public void push(int data) {
        Nodo newNode = new Nodo(data, top);
        top = newNode;
        size++;
    }
    
    /**
     * Desapila y retorna el elemento en el tope de la pila.
     * 
     * @return El elemento que estaba en el tope
     * @throws RuntimeException si la pila está vacía
     */
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Pila vacía, no se puede desapilar");
        }
        
        int value = top.getData();
        top = top.getNext();
        size--;
        return value;
    }
    
    /**
     * Retorna el elemento en el tope sin desapilarlo.
     * 
     * @return El elemento en el tope de la pila
     * @throws RuntimeException si la pila está vacía
     */
    public int top() {
        if (isEmpty()) {
            throw new RuntimeException("Pila vacía");
        }
        return top.getData();
    }
    
    /**
     * Verifica si la pila está vacía.
     * 
     * @return true si la pila no tiene elementos, false en caso contrario
     */
    public boolean isEmpty() {
        return top == null;
    }
    
    /**
     * Obtiene el tamaño actual de la pila.
     * 
     * @return La cantidad de elementos en la pila
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Limpia todos los elementos de la pila.
     */
    public void clear() {
        top = null;
        size = 0;
    }
    
    /**
     * Busca un elemento en la pila.
     * Retorna la posición desde el tope (0 = tope, 1 = siguiente, etc.)
     * 
     * @param data El valor a buscar
     * @return La posición desde el tope, o -1 si no se encuentra
     */
    public int buscar(int data) {
        Nodo current = top;
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
            return "Pila vacía";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Pila [tope: ");
        Nodo current = top;
        
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(" <- ");
            }
            current = current.getNext();
        }
        
        sb.append("] (").append(size).append(" elementos)");
        return sb.toString();
    }
}
