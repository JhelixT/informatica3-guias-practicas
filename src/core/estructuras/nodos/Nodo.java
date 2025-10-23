package core.estructuras.nodos;

/**
 * Clase Nodo para estructuras de datos enlazadas.
 * 
 * Representa un nodo individual que contiene un dato y una referencia
 * al siguiente nodo en la estructura. Es la unidad básica de construcción
 * para listas enlazadas, pilas enlazadas y colas enlazadas.
 * 
 * @param <T> Tipo de dato que almacena el nodo
 * @author JhelixT
 * @version 1.0
 */
public class Nodo<T> {
    private T data;           // Valor almacenado en el nodo
    private Nodo<T> next;     // Referencia al siguiente nodo
    
    /**
     * Constructor que crea un nodo con un dato específico.
     * El siguiente nodo se inicializa como null.
     * 
     * @param data El valor a almacenar en el nodo
     */
    public Nodo(T data) {
        this.data = data;
        this.next = null;
    }
    
    /**
     * Constructor que crea un nodo con dato y referencia al siguiente nodo.
     * 
     * @param data El valor a almacenar en el nodo
     * @param next Referencia al siguiente nodo
     */
    public Nodo(T data, Nodo<T> next) {
        this.data = data;
        this.next = next;
    }
    
    /**
     * Obtiene el dato almacenado en el nodo.
     * 
     * @return El valor del nodo
     */
    public T getData() {
        return data;
    }
    
    /**
     * Establece el dato del nodo.
     * 
     * @param data El nuevo valor a almacenar
     */
    public void setData(T data) {
        this.data = data;
    }
    
    /**
     * Obtiene la referencia al siguiente nodo.
     * 
     * @return El siguiente nodo, o null si es el último
     */
    public Nodo<T> getNext() {
        return next;
    }
    
    /**
     * Establece la referencia al siguiente nodo.
     * 
     * @param next El nodo que seguirá a este
     */
    public void setNext(Nodo<T> next) {
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
        return data != null ? data.toString() : "null";
    }
}
