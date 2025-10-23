package core.estructuras.arboles;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase ArbolAVL: Implementación de un árbol AVL balanceado
 * 
 * Un árbol AVL es un árbol binario de búsqueda autobalanceado donde:
 * - La diferencia de alturas entre subárboles izquierdo y derecho es máximo 1
 * - Se mantiene el balance mediante rotaciones después de inserciones/eliminaciones
 * 
 * Propiedades:
 * - Búsqueda: O(log n)
 * - Inserción: O(log n)
 * - Eliminación: O(log n)
 * 
 * @param <T> Tipo de dato que almacena el árbol (debe ser Comparable)
 * @author Informática 3
 * @version 1.0
 */
public class ArbolAVL<T extends Comparable<T>> {
    // Raíz del árbol AVL
    private NodoAVL<T> root;

    /**
     * Constructor: Crea un árbol AVL vacío
     */
    public ArbolAVL() {
        this.root = null;  // Árbol vacío inicialmente
    }

    /**
     * Calcula la altura de un nodo
     * La altura de un nodo null es 0
     * 
     * @param node Nodo del cual obtener la altura
     * @return Altura del nodo (0 si es null)
     */
    private int height(NodoAVL<T> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * Obtiene el factor de balance de un nodo
     * Factor de balance = altura(subárbol izquierdo) - altura(subárbol derecho)
     * 
     * @param node Nodo del cual obtener el factor de balance
     * @return Factor de balance del nodo
     */
    private int getBalance(NodoAVL<T> node) {
        if (node == null) {
            return 0;
        }
        return node.balance;
    }

    /**
     * Actualiza la altura y el balance de un nodo
     * 
     * @param node Nodo al cual actualizar altura y balance
     */
    private void updateHeight(NodoAVL<T> node) {
        if (node != null) {
            int leftHeight = height(node.left);
            int rightHeight = height(node.right);
            
            node.height = 1 + Math.max(leftHeight, rightHeight);
            node.balance = leftHeight - rightHeight;
        }
    }

    /**
     * Rotación simple a la derecha
     * 
     * @param y Nodo desbalanceado
     * @return Nueva raíz después de la rotación
     */
    private NodoAVL<T> rotateRight(NodoAVL<T> y) {
        NodoAVL<T> x = y.left;
        NodoAVL<T> B = x.right;

        x.right = y;
        y.left = B;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    /**
     * Rotación simple a la izquierda
     * 
     * @param x Nodo desbalanceado
     * @return Nueva raíz después de la rotación
     */
    private NodoAVL<T> rotateLeft(NodoAVL<T> x) {
        NodoAVL<T> y = x.right;
        NodoAVL<T> B = y.left;

        y.left = x;
        x.right = B;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    /**
     * Inserta un nuevo valor en el árbol AVL
     * 
     * @param data Valor a insertar
     */
    public void insert(T data) {
        root = insertRecursive(root, data);
    }

    /**
     * Inserta un valor de forma recursiva manteniendo el balance AVL
     * 
     * @param node Nodo actual en la recursión
     * @param data Valor a insertar
     * @return Nodo después de la inserción
     */
    private NodoAVL<T> insertRecursive(NodoAVL<T> node, T data) {
        // PASO 1: Inserción BST estándar
        if (node == null) {
            return new NodoAVL<>(data);
        }

        int comparison = data.compareTo(node.data);
        
        if (comparison < 0) {
            node.left = insertRecursive(node.left, data);
        } else if (comparison > 0) {
            node.right = insertRecursive(node.right, data);
        } else {
            return node; // Valores duplicados no permitidos
        }

        // PASO 2: Actualizar altura
        updateHeight(node);

        // PASO 3: Obtener balance
        int balance = getBalance(node);

        // PASO 4: Aplicar rotaciones si es necesario

        // Caso Izquierda-Izquierda
        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rotateRight(node);
        }

        // Caso Derecha-Derecha
        if (balance < -1 && data.compareTo(node.right.data) > 0) {
            return rotateLeft(node);
        }

        // Caso Izquierda-Derecha
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    /**
     * Busca un valor en el árbol AVL
     * 
     * @param data Valor a buscar
     * @return true si el valor existe, false en caso contrario
     */
    public boolean search(T data) {
        return searchRecursive(root, data);
    }

    /**
     * Busca un valor de forma recursiva
     * 
     * @param node Nodo actual
     * @param data Valor a buscar
     * @return true si se encuentra, false en caso contrario
     */
    private boolean searchRecursive(NodoAVL<T> node, T data) {
        if (node == null) {
            return false;
        }

        int comparison = data.compareTo(node.data);
        
        if (comparison == 0) {
            return true;
        }

        if (comparison < 0) {
            return searchRecursive(node.left, data);
        } else {
            return searchRecursive(node.right, data);
        }
    }

    /**
     * Encuentra el nodo con el valor mínimo
     * 
     * @param node Raíz del subárbol
     * @return Nodo con el valor mínimo
     */
    private NodoAVL<T> minNode(NodoAVL<T> node) {
        NodoAVL<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * Elimina un valor del árbol AVL
     * 
     * @param data Valor a eliminar
     */
    public void delete(T data) {
        root = deleteRecursive(root, data);
    }

    /**
     * Elimina un valor de forma recursiva manteniendo el balance AVL
     * 
     * @param node Nodo actual
     * @param data Valor a eliminar
     * @return Nodo después de la eliminación
     */
    private NodoAVL<T> deleteRecursive(NodoAVL<T> node, T data) {
        // PASO 1: Eliminación BST estándar
        if (node == null) {
            return node;
        }

        int comparison = data.compareTo(node.data);
        
        if (comparison < 0) {
            node.left = deleteRecursive(node.left, data);
        } else if (comparison > 0) {
            node.right = deleteRecursive(node.right, data);
        } else {
            // Nodo encontrado
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Nodo con dos hijos
            NodoAVL<T> successor = minNode(node.right);
            node.data = successor.data;
            node.right = deleteRecursive(node.right, successor.data);
        }

        // PASO 2: Actualizar altura
        updateHeight(node);

        // PASO 3: Obtener balance
        int balance = getBalance(node);

        // PASO 4: Aplicar rotaciones si es necesario

        // Caso Izquierda-Izquierda
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }

        // Caso Izquierda-Derecha
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Caso Derecha-Derecha
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    /**
     * Recorrido InOrden (Izq-Raíz-Der)
     */
    public void inOrderTraversal() {
        System.out.print("InOrden: ");
        inOrderTraversalRecursive(root);
        System.out.println();
    }

    private void inOrderTraversalRecursive(NodoAVL<T> node) {
        if (node != null) {
            inOrderTraversalRecursive(node.left);
            System.out.print(node.data + " ");
            inOrderTraversalRecursive(node.right);
        }
    }

    /**
     * Recorrido PreOrden (Raíz-Izq-Der)
     */
    public void preOrderTraversal() {
        System.out.print("PreOrden: ");
        preOrderTraversalRecursive(root);
        System.out.println();
    }

    private void preOrderTraversalRecursive(NodoAVL<T> node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrderTraversalRecursive(node.left);
            preOrderTraversalRecursive(node.right);
        }
    }

    /**
     * Recorrido PostOrden (Izq-Der-Raíz)
     */
    public void postOrderTraversal() {
        System.out.print("PostOrden: ");
        postOrderTraversalRecursive(root);
        System.out.println();
    }

    private void postOrderTraversalRecursive(NodoAVL<T> node) {
        if (node != null) {
            postOrderTraversalRecursive(node.left);
            postOrderTraversalRecursive(node.right);
            System.out.print(node.data + " ");
        }
    }

    /**
     * Recorrido por niveles (BFS)
     */
    public void levelOrderTraversal() {
        if (root == null) {
            System.out.println("Por Niveles: (árbol vacío)");
            return;
        }

        System.out.print("Por Niveles: ");
        Queue<NodoAVL<T>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            NodoAVL<T> current = queue.poll();
            System.out.print(current.data + " ");

            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        System.out.println();
    }

    /**
     * Muestra el árbol de forma visual
     */
    public void display() {
        if (root == null) {
            System.out.println("El árbol está vacío");
            return;
        }
        System.out.println("\n=== Estructura del Árbol AVL ===");
        displayRecursive(root, "", true);
    }

    private void displayRecursive(NodoAVL<T> node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + 
                             node.data + " (h:" + node.height + ", b:" + node.balance + ")");
            
            String newPrefix = prefix + (isLast ? "    " : "│   ");
            
            if (node.left != null || node.right != null) {
                if (node.left != null) {
                    displayRecursive(node.left, newPrefix, node.right == null);
                }
                if (node.right != null) {
                    displayRecursive(node.right, newPrefix, true);
                }
            }
        }
    }

    /**
     * Verifica si el árbol está vacío
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Obtiene la altura del árbol
     */
    public int getHeight() {
        return height(root);
    }

    /**
     * Cuenta el número total de nodos
     */
    public int countNodes() {
        return countNodesRecursive(root);
    }

    private int countNodesRecursive(NodoAVL<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodesRecursive(node.left) + countNodesRecursive(node.right);
    }
}
