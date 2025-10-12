package core.estructuras.listas;

import core.estructuras.nodos.Nodo;

/**
 * Implementación de una Lista Enlazada Simple.
 * 
 * Una lista enlazada es una estructura de datos dinámica donde cada elemento
 * (nodo) contiene un dato y una referencia al siguiente elemento.
 * 
 * Características:
 * - Inserción al inicio: O(1)
 * - Inserción al final: O(n)
 * - Búsqueda: O(n)
 * - Eliminación: O(n)
 * - Tamaño dinámico, crece según necesidad
 * 
 * @author JhelixT
 * @version 1.0
 */
public class ListaEnlazada {
    private Nodo head;        // Primer nodo de la lista
    private int size;         // Cantidad de elementos en la lista
    
    /**
     * Constructor que crea una lista enlazada vacía.
     */
    public ListaEnlazada() {
        this.head = null;
        this.size = 0;
    }
    
    /**
     * Inserta un elemento al inicio de la lista.
     * 
     * @param data El valor a insertar
     */
    public void insertarAlInicio(int data) {
        Nodo newNode = new Nodo(data, head);
        head = newNode;
        size++;
    }
    
    /**
     * Inserta un elemento al final de la lista.
     * 
     * @param data El valor a insertar
     */
    public void insertarAlFinal(int data) {
        Nodo newNode = new Nodo(data);
        
        if (estaVacia()) {
            head = newNode;
        } else {
            Nodo current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }
    
    /**
     * Inserta un elemento en una posición específica.
     * 
     * @param data El valor a insertar
     * @param posicion La posición donde insertar (0 = inicio)
     * @throws IndexOutOfBoundsException si la posición es inválida
     */
    public void insertarEnPosicion(int data, int posicion) {
        if (posicion < 0 || posicion > size) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
        }
        
        if (posicion == 0) {
            insertarAlInicio(data);
            return;
        }
        
        Nodo newNode = new Nodo(data);
        Nodo current = head;
        
        for (int i = 0; i < posicion - 1; i++) {
            current = current.getNext();
        }
        
        newNode.setNext(current.getNext());
        current.setNext(newNode);
        size++;
    }
    
    /**
     * Elimina el primer elemento de la lista.
     * 
     * @return El valor del elemento eliminado
     * @throws RuntimeException si la lista está vacía
     */
    public int eliminarAlInicio() {
        if (estaVacia()) {
            throw new RuntimeException("Lista vacía, no se puede eliminar");
        }
        
        int value = head.getData();
        head = head.getNext();
        size--;
        return value;
    }
    
    /**
     * Elimina el último elemento de la lista.
     * 
     * @return El valor del elemento eliminado
     * @throws RuntimeException si la lista está vacía
     */
    public int eliminarAlFinal() {
        if (estaVacia()) {
            throw new RuntimeException("Lista vacía, no se puede eliminar");
        }
        
        if (size == 1) {
            return eliminarAlInicio();
        }
        
        Nodo current = head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        
        int value = current.getNext().getData();
        current.setNext(null);
        size--;
        return value;
    }
    
    /**
     * Elimina un elemento en una posición específica.
     * 
     * @param posicion La posición del elemento a eliminar (0 = inicio)
     * @return El valor del elemento eliminado
     * @throws IndexOutOfBoundsException si la posición es inválida
     */
    public int eliminarEnPosicion(int posicion) {
        if (posicion < 0 || posicion >= size) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
        }
        
        if (posicion == 0) {
            return eliminarAlInicio();
        }
        
        Nodo current = head;
        for (int i = 0; i < posicion - 1; i++) {
            current = current.getNext();
        }
        
        int value = current.getNext().getData();
        current.setNext(current.getNext().getNext());
        size--;
        return value;
    }
    
    /**
     * Busca la primera ocurrencia de un valor en la lista.
     * 
     * @param data El valor a buscar
     * @return La posición del elemento (0 = inicio), o -1 si no se encuentra
     */
    public int buscar(int data) {
        Nodo current = head;
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
    
    /**
     * Obtiene el valor en una posición específica.
     * 
     * @param posicion La posición del elemento a obtener (0 = inicio)
     * @return El valor en esa posición
     * @throws IndexOutOfBoundsException si la posición es inválida
     */
    public int obtenerEnPosicion(int posicion) {
        if (posicion < 0 || posicion >= size) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
        }
        
        Nodo current = head;
        for (int i = 0; i < posicion; i++) {
            current = current.getNext();
        }
        
        return current.getData();
    }
    
    /**
     * Verifica si la lista contiene un valor específico.
     * 
     * @param data El valor a verificar
     * @return true si el valor está en la lista, false en caso contrario
     */
    public boolean contiene(int data) {
        return buscar(data) != -1;
    }
    
    /**
     * Verifica si la lista está vacía.
     * 
     * @return true si la lista no tiene elementos, false en caso contrario
     */
    public boolean estaVacia() {
        return head == null;
    }
    
    /**
     * Obtiene el tamaño de la lista.
     * 
     * @return La cantidad de elementos en la lista
     */
    public int getTamanio() {
        return size;
    }
    
    /**
     * Limpia todos los elementos de la lista.
     */
    public void limpiar() {
        head = null;
        size = 0;
    }
    
    /**
     * Invierte el orden de los elementos en la lista.
     */
    public void invertir() {
        if (size <= 1) return;
        
        Nodo previous = null;
        Nodo current = head;
        Nodo next;
        
        while (current != null) {
            next = current.getNext();
            current.setNext(previous);
            previous = current;
            current = next;
        }
        
        head = previous;
    }
    
    @Override
    public String toString() {
        if (estaVacia()) {
            return "Lista vacía";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Lista [");
        Nodo current = head;
        
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(" -> ");
            }
            current = current.getNext();
        }
        
        sb.append("] (").append(size).append(" elementos)");
        return sb.toString();
    }
}
