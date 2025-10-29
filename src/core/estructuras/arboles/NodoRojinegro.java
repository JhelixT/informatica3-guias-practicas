package core.estructuras.arboles;

/**
 * Nodo para Árbol Rojinegro (Red-Black Tree).
 * 
 * Cada nodo contiene:
 * - Un dato de tipo T (debe ser comparable)
 * - Color del nodo (ROJO o NEGRO)
 * - Referencias a hijo izquierdo, derecho y padre
 * 
 * Propiedades del color:
 * - La raíz siempre es NEGRA
 * - Los nodos ROJOS no pueden tener hijos ROJOS
 * - Todos los caminos desde la raíz hasta las hojas tienen el mismo número de nodos NEGROS
 * 
 * @param <T> Tipo de dato que debe ser comparable
 * @author JhelixT
 * @version 1.0
 */
public class NodoRojinegro<T extends Comparable<T>> {
    /**
     * Enum para representar los colores del nodo.
     */
    public enum Color {
        ROJO, NEGRO
    }
    
    private T data;
    private Color color;
    private NodoRojinegro<T> left;
    private NodoRojinegro<T> right;
    private NodoRojinegro<T> parent;
    
    /**
     * Constructor que crea un nodo ROJO con un dato.
     * Por defecto, los nodos nuevos se crean ROJOS.
     * 
     * @param data El valor a almacenar en el nodo
     */
    public NodoRojinegro(T data) {
        this.data = data;
        this.color = Color.ROJO;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
    
    /**
     * Constructor completo que permite especificar el color.
     * 
     * @param data El valor a almacenar
     * @param color El color del nodo (ROJO o NEGRO)
     */
    public NodoRojinegro(T data, Color color) {
        this.data = data;
        this.color = color;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
    
    // Getters y Setters
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public NodoRojinegro<T> getLeft() {
        return left;
    }
    
    public void setLeft(NodoRojinegro<T> left) {
        this.left = left;
    }
    
    public NodoRojinegro<T> getRight() {
        return right;
    }
    
    public void setRight(NodoRojinegro<T> right) {
        this.right = right;
    }
    
    public NodoRojinegro<T> getParent() {
        return parent;
    }
    
    public void setParent(NodoRojinegro<T> parent) {
        this.parent = parent;
    }
    
    /**
     * Verifica si el nodo es rojo.
     * 
     * @return true si el nodo es ROJO, false en caso contrario
     */
    public boolean isRed() {
        return color == Color.ROJO;
    }
    
    /**
     * Verifica si el nodo es negro.
     * 
     * @return true si el nodo es NEGRO, false en caso contrario
     */
    public boolean isBlack() {
        return color == Color.NEGRO;
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
     * Obtiene el hermano del nodo (el otro hijo del padre).
     * 
     * @return El nodo hermano, o null si no tiene padre o hermano
     */
    public NodoRojinegro<T> getSibling() {
        if (parent == null) {
            return null;
        }
        
        if (this == parent.left) {
            return parent.right;
        } else {
            return parent.left;
        }
    }
    
    /**
     * Obtiene el tío del nodo (el hermano del padre).
     * 
     * @return El nodo tío, o null si no existe
     */
    public NodoRojinegro<T> getUncle() {
        if (parent == null) {
            return null;
        }
        return parent.getSibling();
    }
    
    /**
     * Obtiene el abuelo del nodo (el padre del padre).
     * 
     * @return El nodo abuelo, o null si no existe
     */
    public NodoRojinegro<T> getGrandparent() {
        if (parent == null) {
            return null;
        }
        return parent.parent;
    }
    
    @Override
    public String toString() {
        String colorStr = isRed() ? "R" : "N";
        return data + "(" + colorStr + ")";
    }
}
