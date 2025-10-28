package core.estructuras.arboles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Implementación de un Árbol de Búsqueda Binaria (Binary Search Tree - BST).
 * 
 * Propiedades del BST:
 * - Para cada nodo, todos los valores en el subárbol izquierdo son menores
 * - Para cada nodo, todos los valores en el subárbol derecho son mayores
 * - No permite valores duplicados
 * 
 * Complejidad promedio:
 * - Búsqueda: O(log n)
 * - Inserción: O(log n)
 * - Eliminación: O(log n)
 * 
 * Complejidad peor caso (árbol degenerado):
 * - Búsqueda: O(n)
 * - Inserción: O(n)
 * - Eliminación: O(n)
 * 
 * @param <T> Tipo de dato que debe ser comparable
 * @author JhelixT
 * @version 1.0
 */
public class ArbolBST<T extends Comparable<T>> {
    private NodoBST<T> root;
    private int size;
    
    /**
     * Constructor que crea un árbol vacío.
     */
    public ArbolBST() {
        this.root = null;
        this.size = 0;
    }
    
    /**
     * Inserta un nuevo valor en el árbol.
     * No permite valores duplicados.
     * 
     * @param data El valor a insertar
     * @return true si se insertó correctamente, false si el valor ya existe
     */
    public boolean insert(T data) {
        if (data == null) {
            throw new IllegalArgumentException("No se puede insertar un valor null");
        }
        
        int sizeBefore = size;
        root = insertRecursive(root, data);
        return size > sizeBefore;
    }
    
    /**
     * Método recursivo auxiliar para insertar un valor.
     */
    private NodoBST<T> insertRecursive(NodoBST<T> node, T data) {
        // Caso base: encontramos la posición para insertar
        if (node == null) {
            size++;
            return new NodoBST<>(data);
        }
        
        int comparison = data.compareTo(node.getData());
        
        if (comparison < 0) {
            // El valor es menor, va al subárbol izquierdo
            node.setLeft(insertRecursive(node.getLeft(), data));
        } else if (comparison > 0) {
            // El valor es mayor, va al subárbol derecho
            node.setRight(insertRecursive(node.getRight(), data));
        }
        // Si comparison == 0, el valor ya existe, no insertamos
        
        return node;
    }
    
    /**
     * Busca un valor en el árbol.
     * 
     * @param data El valor a buscar
     * @return true si el valor existe, false en caso contrario
     */
    public boolean search(T data) {
        if (data == null) {
            return false;
        }
        return searchRecursive(root, data) != null;
    }
    
    /**
     * Método recursivo auxiliar para buscar un valor.
     */
    private NodoBST<T> searchRecursive(NodoBST<T> node, T data) {
        // Caso base: árbol vacío o valor no encontrado
        if (node == null) {
            return null;
        }
        
        int comparison = data.compareTo(node.getData());
        
        if (comparison == 0) {
            // Valor encontrado
            return node;
        } else if (comparison < 0) {
            // Buscar en el subárbol izquierdo
            return searchRecursive(node.getLeft(), data);
        } else {
            // Buscar en el subárbol derecho
            return searchRecursive(node.getRight(), data);
        }
    }
    
    /**
     * Elimina un valor del árbol.
     * 
     * @param data El valor a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    public boolean delete(T data) {
        if (data == null || !search(data)) {
            return false;
        }
        
        root = deleteRecursive(root, data);
        size--;
        return true;
    }
    
    /**
     * Método recursivo auxiliar para eliminar un valor.
     */
    private NodoBST<T> deleteRecursive(NodoBST<T> node, T data) {
        if (node == null) {
            return null;
        }
        
        int comparison = data.compareTo(node.getData());
        
        if (comparison < 0) {
            // El valor está en el subárbol izquierdo
            node.setLeft(deleteRecursive(node.getLeft(), data));
        } else if (comparison > 0) {
            // El valor está en el subárbol derecho
            node.setRight(deleteRecursive(node.getRight(), data));
        } else {
            // Encontramos el nodo a eliminar
            
            // Caso 1: Nodo sin hijos (hoja)
            if (node.isLeaf()) {
                return null;
            }
            
            // Caso 2: Nodo con un solo hijo
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }
            
            // Caso 3: Nodo con dos hijos
            // Encontrar el sucesor inorden (menor valor del subárbol derecho)
            T successorValue = findMin(node.getRight());
            node.setData(successorValue);
            // Eliminar el sucesor del subárbol derecho
            node.setRight(deleteRecursive(node.getRight(), successorValue));
        }
        
        return node;
    }
    
    /**
     * Encuentra el valor mínimo en un subárbol.
     * 
     * @param node La raíz del subárbol
     * @return El valor mínimo
     */
    public T findMin(NodoBST<T> node) {
        if (node == null) {
            return null;
        }
        
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        
        return node.getData();
    }
    
    /**
     * Encuentra el valor máximo en el árbol.
     * 
     * @return El valor máximo, o null si el árbol está vacío
     */
    public T findMax() {
        if (isEmpty()) {
            return null;
        }
        
        NodoBST<T> current = root;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        
        return current.getData();
    }
    
    /**
     * Encuentra el valor mínimo en el árbol.
     * 
     * @return El valor mínimo, o null si el árbol está vacío
     */
    public T findMinValue() {
        return findMin(root);
    }
    
    /**
     * Recorrido inorden (izquierda-raíz-derecha).
     * Visita los nodos en orden ascendente.
     * 
     * @return Lista con los valores en orden
     */
    public List<T> inOrderTraversal() {
        List<T> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }
    
    private void inOrderRecursive(NodoBST<T> node, List<T> result) {
        if (node != null) {
            inOrderRecursive(node.getLeft(), result);
            result.add(node.getData());
            inOrderRecursive(node.getRight(), result);
        }
    }
    
    /**
     * Recorrido preorden (raíz-izquierda-derecha).
     * 
     * @return Lista con los valores en preorden
     */
    public List<T> preOrderTraversal() {
        List<T> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }
    
    private void preOrderRecursive(NodoBST<T> node, List<T> result) {
        if (node != null) {
            result.add(node.getData());
            preOrderRecursive(node.getLeft(), result);
            preOrderRecursive(node.getRight(), result);
        }
    }
    
    /**
     * Recorrido postorden (izquierda-derecha-raíz).
     * 
     * @return Lista con los valores en postorden
     */
    public List<T> postOrderTraversal() {
        List<T> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }
    
    private void postOrderRecursive(NodoBST<T> node, List<T> result) {
        if (node != null) {
            postOrderRecursive(node.getLeft(), result);
            postOrderRecursive(node.getRight(), result);
            result.add(node.getData());
        }
    }
    
    /**
     * Recorrido por niveles (BFS - Breadth First Search).
     * 
     * @return Lista con los valores por niveles
     */
    public List<T> levelOrderTraversal() {
        List<T> result = new ArrayList<>();
        
        if (root == null) {
            return result;
        }
        
        Queue<NodoBST<T>> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            NodoBST<T> current = queue.poll();
            result.add(current.getData());
            
            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }
        }
        
        return result;
    }
    
    /**
     * Calcula la altura del árbol.
     * La altura es la longitud del camino más largo desde la raíz hasta una hoja.
     * 
     * @return La altura del árbol, 0 si está vacío
     */
    public int getHeight() {
        return getHeightRecursive(root);
    }
    
    private int getHeightRecursive(NodoBST<T> node) {
        if (node == null) {
            return 0;
        }
        
        int leftHeight = getHeightRecursive(node.getLeft());
        int rightHeight = getHeightRecursive(node.getRight());
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    /**
     * Cuenta la cantidad de nodos en el árbol.
     * 
     * @return La cantidad de nodos
     */
    public int countNodes() {
        return size;
    }
    
    /**
     * Cuenta la cantidad de hojas en el árbol.
     * 
     * @return La cantidad de nodos hoja
     */
    public int countLeaves() {
        return countLeavesRecursive(root);
    }
    
    private int countLeavesRecursive(NodoBST<T> node) {
        if (node == null) {
            return 0;
        }
        
        if (node.isLeaf()) {
            return 1;
        }
        
        return countLeavesRecursive(node.getLeft()) + countLeavesRecursive(node.getRight());
    }
    
    /**
     * Verifica si el árbol está vacío.
     * 
     * @return true si el árbol no tiene nodos, false en caso contrario
     */
    public boolean isEmpty() {
        return root == null;
    }
    
    /**
     * Obtiene el tamaño del árbol.
     * 
     * @return La cantidad de nodos en el árbol
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Limpia el árbol eliminando todos los nodos.
     */
    public void clear() {
        root = null;
        size = 0;
    }
    
    /**
     * Verifica si el árbol es un BST válido.
     * 
     * @return true si cumple las propiedades de BST, false en caso contrario
     */
    public boolean isValidBST() {
        return isValidBSTRecursive(root, null, null);
    }
    
    private boolean isValidBSTRecursive(NodoBST<T> node, T min, T max) {
        if (node == null) {
            return true;
        }
        
        // Verificar que el valor actual esté en el rango válido
        if (min != null && node.getData().compareTo(min) <= 0) {
            return false;
        }
        if (max != null && node.getData().compareTo(max) >= 0) {
            return false;
        }
        
        // Verificar recursivamente los subárboles
        return isValidBSTRecursive(node.getLeft(), min, node.getData()) &&
               isValidBSTRecursive(node.getRight(), node.getData(), max);
    }
    
    /**
     * Muestra el árbol de forma visual en la consola.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Árbol vacío");
            return;
        }
        
        displayRecursive(root, "", true);
    }
    
    private void displayRecursive(NodoBST<T> node, String prefix, boolean isRight) {
        if (node != null) {
            System.out.println(prefix + (isRight ? "└── " : "┌── ") + node.getData());
            
            if (node.getLeft() != null || node.getRight() != null) {
                if (node.getRight() != null) {
                    displayRecursive(node.getRight(), prefix + (isRight ? "    " : "│   "), true);
                } else {
                    System.out.println(prefix + (isRight ? "    " : "│   ") + "└── null");
                }
                
                if (node.getLeft() != null) {
                    displayRecursive(node.getLeft(), prefix + (isRight ? "    " : "│   "), false);
                } else {
                    System.out.println(prefix + (isRight ? "    " : "│   ") + "┌── null");
                }
            }
        }
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "BST vacío";
        }
        
        return "BST [tamaño=" + size + ", altura=" + getHeight() + ", inorden=" + inOrderTraversal() + "]";
    }
}
