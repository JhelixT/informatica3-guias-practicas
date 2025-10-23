package core.estructuras.nodos;

/**
 * Clase que representa un nodo doble para estructuras de datos doblemente enlazadas.
 * Cada nodo contiene un dato y referencias al nodo anterior y siguiente.
 * 
 * @param <T> Tipo de dato que almacena el nodo
 */
public class NodoDoble<T> {
    private T data;
    private NodoDoble<T> next;
    private NodoDoble<T> previous;

    /**
     * Constructor que crea un nodo con un dato específico
     * @param data El dato a almacenar en el nodo
     */
    public NodoDoble(T data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }

    /**
     * Constructor que crea un nodo con dato y referencias
     * @param data El dato a almacenar
     * @param previous Referencia al nodo anterior
     * @param next Referencia al nodo siguiente
     */
    public NodoDoble(T data, NodoDoble<T> previous, NodoDoble<T> next) {
        this.data = data;
        this.previous = previous;
        this.next = next;
    }

    // Getters y Setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NodoDoble<T> getNext() {
        return next;
    }

    public void setNext(NodoDoble<T> next) {
        this.next = next;
    }

    public NodoDoble<T> getPrevious() {
        return previous;
    }

    public void setPrevious(NodoDoble<T> previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "null";
    }
}
