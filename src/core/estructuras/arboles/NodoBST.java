package core.estructuras.arboles;

/**
 * Nodo para Árbol de Búsqueda Binaria (Binary Search Tree - BST).
 * 
 * Cada nodo contiene:
 * - Un dato de tipo T (debe ser comparable)
 * - Referencia al hijo izquierdo (valores menores)
 * - Referencia al hijo derecho (valores mayores)
 * 
 * @param <T> Tipo de dato que debe ser comparable
 * @author JhelixT
 * @version 1.0
 */
public class NodoBST<T extends Comparable<T>> {
    private T data;
    private NodoBST<T> left;
    private NodoBST<T> right;
    
    /**
     * Constructor que crea un nodo con un dato.
     * Los hijos se inicializan en null.
     * 
     * @param data El valor a almacenar en el nodo
     */
    public NodoBST(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
    
    /**
     * Constructor completo que crea un nodo con dato e hijos.
     * 
     * @param data El valor a almacenar
     * @param left El hijo izquierdo
     * @param right El hijo derecho
     */
    public NodoBST(T data, NodoBST<T> left, NodoBST<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
    
    // Getters y Setters
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public NodoBST<T> getLeft() {
        return left;
    }
    
    public void setLeft(NodoBST<T> left) {
        this.left = left;
    }
    
    public NodoBST<T> getRight() {
        return right;
    }
    
    public void setRight(NodoBST<T> right) {
        this.right = right;
    }
    
    /**
     * Verifica si el nodo es una hoja (no tiene hijos).
     * 
     * @return true si el nodo no tiene hijos, false en caso contrario
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }
    
    /**
     * Verifica si el nodo tiene ambos hijos.
     * 
     * @return true si tiene hijo izquierdo y derecho, false en caso contrario
     */
    public boolean hasBothChildren() {
        return left != null && right != null;
    }
    
    /**
     * Cuenta la cantidad de hijos que tiene el nodo.
     * 
     * @return 0, 1 o 2 según la cantidad de hijos
     */
    public int getChildrenCount() {
        int count = 0;
        if (left != null) count++;
        if (right != null) count++;
        return count;
    }
    
    @Override
    public String toString() {
        return String.valueOf(data);
    }
}
