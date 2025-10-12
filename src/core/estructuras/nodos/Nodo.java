package core.estructuras.nodos;

/**
 * Clase Nodo para estructuras de datos enlazadas.
 * 
 * Representa un nodo individual que contiene un dato entero y una referencia
 * al siguiente nodo en la estructura. Es la unidad básica de construcción
 * para listas enlazadas, pilas enlazadas y colas enlazadas.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Nodo {
    private int data;         // Valor almacenado en el nodo
    private Nodo next;        // Referencia al siguiente nodo
    
    /**
     * Constructor que crea un nodo con un dato específico.
     * El siguiente nodo se inicializa como null.
     * 
     * @param data El valor a almacenar en el nodo
     */
    public Nodo(int data) {
        this.data = data;
        this.next = null;
    }
    
    /**
     * Constructor que crea un nodo con dato y referencia al siguiente nodo.
     * 
     * @param data El valor a almacenar en el nodo
     * @param next Referencia al siguiente nodo
     */
    public Nodo(int data, Nodo next) {
        this.data = data;
        this.next = next;
    }
    
    /**
     * Obtiene el dato almacenado en el nodo.
     * 
     * @return El valor del nodo
     */
    public int getData() {
        return data;
    }
    
    /**
     * Establece el dato del nodo.
     * 
     * @param data El nuevo valor a almacenar
     */
    public void setData(int data) {
        this.data = data;
    }
    
    /**
     * Obtiene la referencia al siguiente nodo.
     * 
     * @return El siguiente nodo, o null si es el último
     */
    public Nodo getNext() {
        return next;
    }
    
    /**
     * Establece la referencia al siguiente nodo.
     * 
     * @param next El nodo que seguirá a este
     */
    public void setNext(Nodo next) {
        this.next = next;
    }
    
    /**
     * Verifica si este nodo tiene un siguiente nodo.
     * 
     * @return true si hay un siguiente nodo, false si es el último
     */
    public boolean hasNext() {
        return next != null;
    }
    
    @Override
    public String toString() {
        return String.valueOf(data);
    }
}
