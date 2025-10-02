package core.estructuras.arboles;

/**
 * Clase NodoAVL: Representa un nodo en un árbol AVL
 * 
 * Un nodo AVL contiene:
 * - Un valor (dato)
 * - Referencias a sus hijos izquierdo y derecho
 * - La altura del subárbol que tiene como raíz
 * 
 * @author Sistema
 * @version 1.0
 */
public class NodoAVL {
    // Atributos del nodo
    int dato;              // Valor almacenado en el nodo
    int altura;            // Altura del subárbol con raíz en este nodo
    int balance;           // Factor de balance del nodo (altura izq - altura der)
    NodoAVL izquierdo;     // Referencia al hijo izquierdo
    NodoAVL derecho;       // Referencia al hijo derecho

    /**
     * Constructor: Crea un nuevo nodo con un valor dado
     * 
     * @param dato Valor a almacenar en el nodo
     * 
     * Inicializa:
     * - El dato con el valor proporcionado
     * - La altura en 1 (nodo hoja)
     * - Los hijos en null (sin hijos inicialmente)
     */
    public NodoAVL(int dato) {
        this.dato = dato;
        this.altura = 1;           // Un nodo nuevo tiene altura 1
        this.balance = 0;          // Un nodo hoja tiene balance 0
        this.izquierdo = null;     // Sin hijo izquierdo inicialmente
        this.derecho = null;       // Sin hijo derecho inicialmente
    }

    /**
     * Obtiene el valor almacenado en el nodo
     * @return El dato del nodo
     */
    public int getDato() {
        return dato;
    }

    /**
     * Establece el valor del nodo
     * @param dato El nuevo valor del nodo
     */
    public void setDato(int dato) {
        this.dato = dato;
    }

    /**
     * Obtiene la altura del subárbol
     * @return La altura del nodo
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Establece la altura del nodo
     * @param altura La nueva altura del nodo
     */
    public void setAltura(int altura) {
        this.altura = altura;
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
    public NodoAVL getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el hijo izquierdo
     * @param izquierdo El nuevo hijo izquierdo
     */
    public void setIzquierdo(NodoAVL izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Obtiene el hijo derecho
     * @return Referencia al nodo hijo derecho
     */
    public NodoAVL getDerecho() {
        return derecho;
    }

    /**
     * Establece el hijo derecho
     * @param derecho El nuevo hijo derecho
     */
    public void setDerecho(NodoAVL derecho) {
        this.derecho = derecho;
    }

    /**
     * Verifica si el nodo es una hoja (sin hijos)
     * @return true si es hoja, false en caso contrario
     */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    /**
     * Representación en cadena del nodo
     * @return Cadena con el valor del nodo
     */
    @Override
    public String toString() {
        return String.valueOf(dato);
    }
}