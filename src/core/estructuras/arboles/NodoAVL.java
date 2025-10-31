package core.estructuras.arboles;

/**
 * Clase NodoAVL: Representa un nodo en un árbol AVL
 * 
 * Un nodo AVL contiene:
 * - Un valor (dato)
 * - Referencias a sus hijos izquierdo y derecho
 * - La altura del subárbol que tiene como raíz
 * 
 * @param <T> Tipo de dato que almacena el nodo (debe ser Comparable)
 * @author Sistema
 * @version 1.0
 */
public class NodoAVL<T extends Comparable<T>> {
    // Atributos del nodo (privados para encapsulación)
    private T data;                      // Valor almacenado en el nodo
    private int height;                  // Altura del subárbol con raíz en este nodo
    private int balance;                 // Factor de balance del nodo (altura izq - altura der)
    private NodoAVL<T> left;             // Referencia al hijo izquierdo
    private NodoAVL<T> right;            // Referencia al hijo derecho

    /**
     * Constructor: Crea un nuevo nodo con un valor dado
     * 
     * @param data Valor a almacenar en el nodo
     * 
     * Inicializa:
     * - El dato con el valor proporcionado
     * - La altura en 1 (nodo hoja)
     * - Los hijos en null (sin hijos inicialmente)
     */
    public NodoAVL(T data) {
        this.data = data;
        this.height = 1;           // Un nodo nuevo tiene altura 1
        this.balance = 0;          // Un nodo hoja tiene balance 0
        this.left = null;          // Sin hijo izquierdo inicialmente
        this.right = null;         // Sin hijo derecho inicialmente
    }

    /**
     * Obtiene el valor almacenado en el nodo
     * @return El dato del nodo
     */
    public T getData() {
        return data;
    }

    /**
     * Establece el valor del nodo
     * @param data El nuevo valor del nodo
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Obtiene la altura del subárbol
     * @return La altura del nodo
     */
    public int getHeight() {
        return height;
    }

    /**
     * Establece la altura del nodo
     * @param height La nueva altura del nodo
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Obtiene el factor de balance del nodo
     * @return El balance del nodo (altura izq - altura der)
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Establece el factor de balance del nodo
     * @param balance El nuevo balance del nodo
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Obtiene el hijo izquierdo
     * @return Referencia al nodo hijo izquierdo
     */
    public NodoAVL<T> getLeft() {
        return left;
    }

    /**
     * Establece el hijo izquierdo
     * @param left El nuevo hijo izquierdo
     */
    public void setLeft(NodoAVL<T> left) {
        this.left = left;
    }

    /**
     * Obtiene el hijo derecho
     * @return Referencia al nodo hijo derecho
     */
    public NodoAVL<T> getRight() {
        return right;
    }

    /**
     * Establece el hijo derecho
     * @param right El nuevo hijo derecho
     */
    public void setRight(NodoAVL<T> right) {
        this.right = right;
    }

    /**
     * Verifica si el nodo es una hoja (sin hijos)
     * @return true si es hoja, false en caso contrario
     */
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    /**
     * Representación en cadena del nodo
     * @return Cadena con el valor del nodo
     */
    @Override
    public String toString() {
        return this.data != null ? this.data.toString() : "null";
    }
}