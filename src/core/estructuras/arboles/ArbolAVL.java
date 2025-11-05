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
        return node.getHeight();
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
        return node.getBalance();
    }

    /**
     * Actualiza la altura y el balance de un nodo
     * 
     * @param node Nodo al cual actualizar altura y balance
     */
    private void updateHeight(NodoAVL<T> node) {
        if (node != null) {
            int leftHeight = height(node.getLeft());
            int rightHeight = height(node.getRight());
            
            node.setHeight(1 + Math.max(leftHeight, rightHeight));
            node.setBalance(leftHeight - rightHeight);
        }
    }

    /**
     * Rotación simple a la derecha
     * 
     * @param y Nodo desbalanceado
     * @return Nueva raíz después de la rotación
     */
    private NodoAVL<T> rotateRight(NodoAVL<T> y) {
        NodoAVL<T> x = y.getLeft();
        NodoAVL<T> B = x.getRight();

        x.setRight(y);
        y.setLeft(B);

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
        NodoAVL<T> y = x.getRight();
        NodoAVL<T> B = y.getLeft();

        y.setLeft(x);
        x.setRight(B);

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

        int comparison = data.compareTo(node.getData());
        
        if (comparison < 0) {
            node.setLeft(insertRecursive(node.getLeft(), data));
        } else if (comparison > 0) {
            node.setRight(insertRecursive(node.getRight(), data));
        } else {
            return node; // Valores duplicados no permitidos
        }

        // PASO 2: Actualizar altura
        updateHeight(node);

        // PASO 3: Obtener balance
        int balance = getBalance(node);

        // PASO 4: Aplicar rotaciones si es necesario

        // Caso Izquierda-Izquierda
        if (balance > 1 && data.compareTo(node.getLeft().getData()) < 0) {
            return rotateRight(node);
        }

        // Caso Derecha-Derecha
        if (balance < -1 && data.compareTo(node.getRight().getData()) > 0) {
            return rotateLeft(node);
        }

        // Caso Izquierda-Derecha
        if (balance > 1 && data.compareTo(node.getLeft().getData()) > 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && data.compareTo(node.getRight().getData()) < 0) {
            node.setRight(rotateRight(node.getRight()));
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

        int comparison = data.compareTo(node.getData());
        
        if (comparison == 0) {
            return true;
        }

        if (comparison < 0) {
            return searchRecursive(node.getLeft(), data);
        } else {
            return searchRecursive(node.getRight(), data);
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
        while (current.getLeft() != null) {
            current = current.getLeft();
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

        int comparison = data.compareTo(node.getData());
        
        if (comparison < 0) {
            node.setLeft(deleteRecursive(node.getLeft(), data));
        } else if (comparison > 0) {
            node.setRight(deleteRecursive(node.getRight(), data));
        } else {
            // Nodo encontrado
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }

            // Nodo con dos hijos
            NodoAVL<T> successor = minNode(node.getRight());
            node.setData(successor.getData());
            node.setRight(deleteRecursive(node.getRight(), successor.getData()));
        }

        // PASO 2: Actualizar altura
        updateHeight(node);

        // PASO 3: Obtener balance
        int balance = getBalance(node);

        // PASO 4: Aplicar rotaciones si es necesario

        // Caso Izquierda-Izquierda
        if (balance > 1 && getBalance(node.getLeft()) >= 0) {
            return rotateRight(node);
        }

        // Caso Izquierda-Derecha
        if (balance > 1 && getBalance(node.getLeft()) < 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        // Caso Derecha-Derecha
        if (balance < -1 && getBalance(node.getRight()) <= 0) {
            return rotateLeft(node);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && getBalance(node.getRight()) > 0) {
            node.setRight(rotateRight(node.getRight()));
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
            inOrderTraversalRecursive(node.getLeft());
            System.out.print(node.getData() + " ");
            inOrderTraversalRecursive(node.getRight());
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
            System.out.print(node.getData() + " ");
            preOrderTraversalRecursive(node.getLeft());
            preOrderTraversalRecursive(node.getRight());
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
            postOrderTraversalRecursive(node.getLeft());
            postOrderTraversalRecursive(node.getRight());
            System.out.print(node.getData() + " ");
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
            System.out.print(current.getData() + " ");

            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.offer(current.getRight());
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
                             node.getData() + " (h:" + node.getHeight() + ", b:" + node.getBalance() + ")");
            
            String newPrefix = prefix + (isLast ? "    " : "│   ");
            
            if (node.getLeft() != null || node.getRight() != null) {
                if (node.getLeft() != null) {
                    displayRecursive(node.getLeft(), newPrefix, node.getRight() == null);
                }
                if (node.getRight() != null) {
                    displayRecursive(node.getRight(), newPrefix, true);
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
        return 1 + countNodesRecursive(node.getLeft()) + countNodesRecursive(node.getRight());
    }
    
    /**
     * Busca el primer nodo con valor >= al dado (successor o igual)
     * Complejidad: O(log n)
     * 
     * @param value Valor a buscar
     * @return Nodo con valor >= value, o null si no existe
     */
    public NodoAVL<T> findCeilingNode(T value) {
        return findCeilingRecursive(root, value);
    }
    
    private NodoAVL<T> findCeilingRecursive(NodoAVL<T> node, T value) {
        if (node == null) {
            return null;
        }
        
        int comparison = value.compareTo(node.getData());
        
        if (comparison == 0) {
            // Encontramos el valor exacto
            return node;
        } else if (comparison > 0) {
            // El valor buscado es mayor, buscar en subárbol derecho
            return findCeilingRecursive(node.getRight(), value);
        } else {
            // El valor buscado es menor, este nodo es candidato
            // Pero puede haber uno menor en el subárbol izquierdo
            NodoAVL<T> leftResult = findCeilingRecursive(node.getLeft(), value);
            return (leftResult != null) ? leftResult : node;
        }
    }
    
    /**
     * Obtiene el sucesor inorden de un nodo (siguiente en recorrido inorden)
     * Complejidad: O(log n) en promedio
     * 
     * @param node Nodo del cual obtener el sucesor
     * @return Sucesor inorden, o null si no existe
     */
    public NodoAVL<T> getInorderSuccessor(NodoAVL<T> node) {
        if (node == null) {
            return null;
        }
        
        // Si tiene subárbol derecho, el sucesor es el mínimo de ese subárbol
        if (node.getRight() != null) {
            return findMinimumNode(node.getRight());
        }
        
        // Si no tiene subárbol derecho, buscar el primer ancestro que sea hijo izquierdo
        return findSuccessorAncestor(root, node);
    }
    
    /**
     * Encuentra el nodo con el valor mínimo en un subárbol
     */
    private NodoAVL<T> findMinimumNode(NodoAVL<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }
    
    /**
     * Encuentra el ancestro que es sucesor del nodo dado
     */
    private NodoAVL<T> findSuccessorAncestor(NodoAVL<T> root, NodoAVL<T> target) {
        NodoAVL<T> successor = null;
        NodoAVL<T> current = root;
        
        while (current != null) {
            int comparison = target.getData().compareTo(current.getData());
            
            if (comparison < 0) {
                successor = current;  // Este es un candidato a sucesor
                current = current.getLeft();
            } else if (comparison > 0) {
                current = current.getRight();
            } else {
                break;  // Encontramos el nodo target
            }
        }
        
        return successor;
    }
    
    /**
     * Obtiene la raíz del árbol (para acceso controlado)
     * 
     * @return Nodo raíz del árbol
     */
    public NodoAVL<T> getRoot() {
        return root;
    }
}
