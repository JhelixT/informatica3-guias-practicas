package core.estructuras.arboles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import core.estructuras.arboles.NodoRojinegro.Color;

/**
 * Implementación de un Árbol Rojinegro (Red-Black Tree).
 * 
 * Un árbol rojinegro es un árbol binario de búsqueda balanceado que garantiza:
 * 1. Cada nodo es ROJO o NEGRO
 * 2. La raíz es siempre NEGRA
 * 3. Todas las hojas (NIL) son NEGRAS
 * 4. Si un nodo es ROJO, ambos hijos son NEGROS (no puede haber dos nodos rojos consecutivos)
 * 5. Todos los caminos desde un nodo hasta sus hojas descendientes contienen el mismo número de nodos NEGROS
 * 
 * Estas propiedades garantizan que el árbol permanece aproximadamente balanceado,
 * con altura máxima de 2*log₂(n+1).
 * 
 * Complejidad garantizada:
 * - Búsqueda: O(log n)
 * - Inserción: O(log n)
 * - Eliminación: O(log n)
 * 
 * @param <T> Tipo de dato que debe ser comparable
 * @author JhelixT
 * @version 1.0
 */
public class ArbolRojinegro<T extends Comparable<T>> {
    private NodoRojinegro<T> root;
    private int size;
    
    /**
     * Constructor que crea un árbol vacío.
     */
    public ArbolRojinegro() {
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
        
        // Verificar si el valor ya existe
        if (search(data)) {
            return false;
        }
        
        NodoRojinegro<T> newNode = new NodoRojinegro<>(data);
        
        if (root == null) {
            // Primer nodo: debe ser negro
            newNode.setColor(Color.NEGRO);
            root = newNode;
            size++;
            return true;
        }
        
        // Insertar como en BST normal
        NodoRojinegro<T> current = root;
        NodoRojinegro<T> parent = null;
        
        while (current != null) {
            parent = current;
            int comparison = data.compareTo(current.getData());
            
            if (comparison < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        
        // Establecer el padre del nuevo nodo
        newNode.setParent(parent);
        
        // Establecer el hijo correspondiente del padre
        int comparison = data.compareTo(parent.getData());
        if (comparison < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
        
        size++;
        
        // Restaurar las propiedades del árbol rojinegro
        fixInsert(newNode);
        
        return true;
    }
    
    /**
     * Restaura las propiedades del árbol rojinegro después de una inserción.
     */
    private void fixInsert(NodoRojinegro<T> node) {
        // Mientras el padre sea rojo (violación de la regla 4)
        while (node != root && node.getParent().isRed()) {
            NodoRojinegro<T> parent = node.getParent();
            NodoRojinegro<T> grandparent = node.getGrandparent();
            
            if (parent == grandparent.getLeft()) {
                // El padre es hijo izquierdo
                NodoRojinegro<T> uncle = grandparent.getRight();
                
                if (uncle != null && uncle.isRed()) {
                    // Caso 1: El tío es rojo
                    parent.setColor(Color.NEGRO);
                    uncle.setColor(Color.NEGRO);
                    grandparent.setColor(Color.ROJO);
                    node = grandparent;
                } else {
                    // Caso 2 o 3: El tío es negro o null
                    if (node == parent.getRight()) {
                        // Caso 2: El nodo es hijo derecho
                        node = parent;
                        rotateLeft(node);
                        parent = node.getParent();
                        grandparent = node.getGrandparent();
                    }
                    
                    // Caso 3: El nodo es hijo izquierdo
                    parent.setColor(Color.NEGRO);
                    grandparent.setColor(Color.ROJO);
                    rotateRight(grandparent);
                }
            } else {
                // El padre es hijo derecho (simétrico)
                NodoRojinegro<T> uncle = grandparent.getLeft();
                
                if (uncle != null && uncle.isRed()) {
                    // Caso 1: El tío es rojo
                    parent.setColor(Color.NEGRO);
                    uncle.setColor(Color.NEGRO);
                    grandparent.setColor(Color.ROJO);
                    node = grandparent;
                } else {
                    // Caso 2 o 3: El tío es negro o null
                    if (node == parent.getLeft()) {
                        // Caso 2: El nodo es hijo izquierdo
                        node = parent;
                        rotateRight(node);
                        parent = node.getParent();
                        grandparent = node.getGrandparent();
                    }
                    
                    // Caso 3: El nodo es hijo derecho
                    parent.setColor(Color.NEGRO);
                    grandparent.setColor(Color.ROJO);
                    rotateLeft(grandparent);
                }
            }
        }
        
        // Asegurar que la raíz siempre sea negra
        root.setColor(Color.NEGRO);
    }
    
    /**
     * Rotación a la izquierda.
     */
    private void rotateLeft(NodoRojinegro<T> node) {
        NodoRojinegro<T> rightChild = node.getRight();
        
        // El hijo izquierdo del hijo derecho se convierte en hijo derecho del nodo
        node.setRight(rightChild.getLeft());
        if (rightChild.getLeft() != null) {
            rightChild.getLeft().setParent(node);
        }
        
        // El padre del nodo se convierte en padre del hijo derecho
        rightChild.setParent(node.getParent());
        
        if (node.getParent() == null) {
            root = rightChild;
        } else if (node == node.getParent().getLeft()) {
            node.getParent().setLeft(rightChild);
        } else {
            node.getParent().setRight(rightChild);
        }
        
        // El nodo se convierte en hijo izquierdo de su antiguo hijo derecho
        rightChild.setLeft(node);
        node.setParent(rightChild);
    }
    
    /**
     * Rotación a la derecha.
     */
    private void rotateRight(NodoRojinegro<T> node) {
        NodoRojinegro<T> leftChild = node.getLeft();
        
        // El hijo derecho del hijo izquierdo se convierte en hijo izquierdo del nodo
        node.setLeft(leftChild.getRight());
        if (leftChild.getRight() != null) {
            leftChild.getRight().setParent(node);
        }
        
        // El padre del nodo se convierte en padre del hijo izquierdo
        leftChild.setParent(node.getParent());
        
        if (node.getParent() == null) {
            root = leftChild;
        } else if (node == node.getParent().getRight()) {
            node.getParent().setRight(leftChild);
        } else {
            node.getParent().setLeft(leftChild);
        }
        
        // El nodo se convierte en hijo derecho de su antiguo hijo izquierdo
        leftChild.setRight(node);
        node.setParent(leftChild);
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
        return searchNode(data) != null;
    }
    
    /**
     * Busca un nodo con un valor específico.
     */
    private NodoRojinegro<T> searchNode(T data) {
        NodoRojinegro<T> current = root;
        
        while (current != null) {
            int comparison = data.compareTo(current.getData());
            
            if (comparison == 0) {
                return current;
            } else if (comparison < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        
        return null;
    }
    
    /**
     * Elimina un valor del árbol.
     * 
     * @param data El valor a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    public boolean delete(T data) {
        if (data == null) {
            return false;
        }
        
        NodoRojinegro<T> nodeToDelete = searchNode(data);
        if (nodeToDelete == null) {
            return false;
        }
        
        deleteNode(nodeToDelete);
        size--;
        return true;
    }
    
    /**
     * Elimina un nodo del árbol.
     */
    private void deleteNode(NodoRojinegro<T> node) {
        NodoRojinegro<T> replacement;
        NodoRojinegro<T> child;
        
        // Caso 1: Nodo con dos hijos
        if (node.hasBothChildren()) {
            // Encontrar el sucesor (mínimo del subárbol derecho)
            replacement = findMinNode(node.getRight());
            node.setData(replacement.getData());
            node = replacement;
        }
        
        // Ahora el nodo tiene a lo sumo un hijo
        child = (node.getLeft() != null) ? node.getLeft() : node.getRight();
        
        if (node.isBlack()) {
            if (child != null && child.isRed()) {
                child.setColor(Color.NEGRO);
            } else {
                fixDelete(node);
            }
        }
        
        // Reemplazar el nodo con su hijo
        replaceNode(node, child);
    }
    
    /**
     * Reemplaza un nodo con otro en el árbol.
     */
    private void replaceNode(NodoRojinegro<T> oldNode, NodoRojinegro<T> newNode) {
        if (oldNode.getParent() == null) {
            root = newNode;
        } else if (oldNode == oldNode.getParent().getLeft()) {
            oldNode.getParent().setLeft(newNode);
        } else {
            oldNode.getParent().setRight(newNode);
        }
        
        if (newNode != null) {
            newNode.setParent(oldNode.getParent());
        }
    }
    
    /**
     * Restaura las propiedades del árbol rojinegro después de una eliminación.
     */
    private void fixDelete(NodoRojinegro<T> node) {
        while (node != root && (node == null || node.isBlack())) {
            if (node == node.getParent().getLeft()) {
                NodoRojinegro<T> sibling = node.getParent().getRight();
                
                // Caso 1: El hermano es rojo
                if (sibling != null && sibling.isRed()) {
                    sibling.setColor(Color.NEGRO);
                    node.getParent().setColor(Color.ROJO);
                    rotateLeft(node.getParent());
                    sibling = node.getParent().getRight();
                }
                
                // Caso 2: El hermano es negro y ambos hijos del hermano son negros
                if (sibling != null && 
                    (sibling.getLeft() == null || sibling.getLeft().isBlack()) &&
                    (sibling.getRight() == null || sibling.getRight().isBlack())) {
                    sibling.setColor(Color.ROJO);
                    node = node.getParent();
                } else if (sibling != null) {
                    // Caso 3: El hermano es negro, hijo izquierdo es rojo, derecho es negro
                    if (sibling.getRight() == null || sibling.getRight().isBlack()) {
                        if (sibling.getLeft() != null) {
                            sibling.getLeft().setColor(Color.NEGRO);
                        }
                        sibling.setColor(Color.ROJO);
                        rotateRight(sibling);
                        sibling = node.getParent().getRight();
                    }
                    
                    // Caso 4: El hermano es negro y el hijo derecho es rojo
                    if (sibling != null) {
                        sibling.setColor(node.getParent().getColor());
                        node.getParent().setColor(Color.NEGRO);
                        if (sibling.getRight() != null) {
                            sibling.getRight().setColor(Color.NEGRO);
                        }
                        rotateLeft(node.getParent());
                    }
                    node = root;
                } else {
                    node = root;
                }
            } else {
                // Simétrico para el hijo derecho
                NodoRojinegro<T> sibling = node.getParent().getLeft();
                
                if (sibling != null && sibling.isRed()) {
                    sibling.setColor(Color.NEGRO);
                    node.getParent().setColor(Color.ROJO);
                    rotateRight(node.getParent());
                    sibling = node.getParent().getLeft();
                }
                
                if (sibling != null &&
                    (sibling.getRight() == null || sibling.getRight().isBlack()) &&
                    (sibling.getLeft() == null || sibling.getLeft().isBlack())) {
                    sibling.setColor(Color.ROJO);
                    node = node.getParent();
                } else if (sibling != null) {
                    if (sibling.getLeft() == null || sibling.getLeft().isBlack()) {
                        if (sibling.getRight() != null) {
                            sibling.getRight().setColor(Color.NEGRO);
                        }
                        sibling.setColor(Color.ROJO);
                        rotateLeft(sibling);
                        sibling = node.getParent().getLeft();
                    }
                    
                    if (sibling != null) {
                        sibling.setColor(node.getParent().getColor());
                        node.getParent().setColor(Color.NEGRO);
                        if (sibling.getLeft() != null) {
                            sibling.getLeft().setColor(Color.NEGRO);
                        }
                        rotateRight(node.getParent());
                    }
                    node = root;
                } else {
                    node = root;
                }
            }
        }
        
        if (node != null) {
            node.setColor(Color.NEGRO);
        }
    }
    
    /**
     * Encuentra el nodo con el valor mínimo en un subárbol.
     */
    private NodoRojinegro<T> findMinNode(NodoRojinegro<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }
    
    /**
     * Encuentra el valor mínimo en el árbol.
     * 
     * @return El valor mínimo, o null si el árbol está vacío
     */
    public T findMin() {
        if (isEmpty()) {
            return null;
        }
        return findMinNode(root).getData();
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
        
        NodoRojinegro<T> current = root;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current.getData();
    }
    
    /**
     * Recorrido inorden (izquierda-raíz-derecha).
     * 
     * @return Lista con los valores en orden ascendente
     */
    public List<T> inOrderTraversal() {
        List<T> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }
    
    private void inOrderRecursive(NodoRojinegro<T> node, List<T> result) {
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
    
    private void preOrderRecursive(NodoRojinegro<T> node, List<T> result) {
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
    
    private void postOrderRecursive(NodoRojinegro<T> node, List<T> result) {
        if (node != null) {
            postOrderRecursive(node.getLeft(), result);
            postOrderRecursive(node.getRight(), result);
            result.add(node.getData());
        }
    }
    
    /**
     * Recorrido por niveles (BFS).
     * 
     * @return Lista con los valores por niveles
     */
    public List<T> levelOrderTraversal() {
        List<T> result = new ArrayList<>();
        
        if (root == null) {
            return result;
        }
        
        Queue<NodoRojinegro<T>> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            NodoRojinegro<T> current = queue.poll();
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
     * 
     * @return La altura del árbol
     */
    public int getHeight() {
        return getHeightRecursive(root);
    }
    
    private int getHeightRecursive(NodoRojinegro<T> node) {
        if (node == null) {
            return 0;
        }
        
        int leftHeight = getHeightRecursive(node.getLeft());
        int rightHeight = getHeightRecursive(node.getRight());
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    /**
     * Calcula la altura negra del árbol (número de nodos negros en cualquier camino).
     * 
     * @return La altura negra del árbol
     */
    public int getBlackHeight() {
        return getBlackHeightRecursive(root);
    }
    
    private int getBlackHeightRecursive(NodoRojinegro<T> node) {
        if (node == null) {
            return 1; // Las hojas NIL son negras
        }
        
        int leftBlackHeight = getBlackHeightRecursive(node.getLeft());
        
        if (node.isBlack()) {
            return leftBlackHeight + 1;
        } else {
            return leftBlackHeight;
        }
    }
    
    /**
     * Cuenta los nodos rojos en el árbol.
     * 
     * @return Cantidad de nodos rojos
     */
    public int countRedNodes() {
        return countRedNodesRecursive(root);
    }
    
    private int countRedNodesRecursive(NodoRojinegro<T> node) {
        if (node == null) {
            return 0;
        }
        
        int count = node.isRed() ? 1 : 0;
        return count + countRedNodesRecursive(node.getLeft()) + 
               countRedNodesRecursive(node.getRight());
    }
    
    /**
     * Cuenta los nodos negros en el árbol.
     * 
     * @return Cantidad de nodos negros
     */
    public int countBlackNodes() {
        return size - countRedNodes();
    }
    
    /**
     * Verifica si el árbol está vacío.
     * 
     * @return true si no tiene nodos, false en caso contrario
     */
    public boolean isEmpty() {
        return root == null;
    }
    
    /**
     * Obtiene el tamaño del árbol.
     * 
     * @return Cantidad de nodos en el árbol
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
     * Verifica si el árbol cumple con las propiedades del Árbol Rojinegro.
     * 
     * @return true si es válido, false en caso contrario
     */
    public boolean isValidRedBlackTree() {
        if (root == null) {
            return true;
        }
        
        // Propiedad 2: La raíz debe ser negra
        if (root.isRed()) {
            return false;
        }
        
        // Verificar las demás propiedades
        return checkRedProperty(root) && checkBlackHeight(root) != -1;
    }
    
    /**
     * Verifica que no haya dos nodos rojos consecutivos.
     */
    private boolean checkRedProperty(NodoRojinegro<T> node) {
        if (node == null) {
            return true;
        }
        
        if (node.isRed()) {
            if ((node.getLeft() != null && node.getLeft().isRed()) ||
                (node.getRight() != null && node.getRight().isRed())) {
                return false;
            }
        }
        
        return checkRedProperty(node.getLeft()) && checkRedProperty(node.getRight());
    }
    
    /**
     * Verifica que todos los caminos tengan la misma altura negra.
     * 
     * @return La altura negra si es consistente, -1 si no es válida
     */
    private int checkBlackHeight(NodoRojinegro<T> node) {
        if (node == null) {
            return 1; // Las hojas NIL son negras
        }
        
        int leftBlackHeight = checkBlackHeight(node.getLeft());
        int rightBlackHeight = checkBlackHeight(node.getRight());
        
        if (leftBlackHeight == -1 || rightBlackHeight == -1 || 
            leftBlackHeight != rightBlackHeight) {
            return -1;
        }
        
        return leftBlackHeight + (node.isBlack() ? 1 : 0);
    }
    
    /**
     * Muestra el árbol de forma visual en la consola con colores.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Árbol vacío");
            return;
        }
        
        displayRecursive(root, "", true);
    }
    
    private void displayRecursive(NodoRojinegro<T> node, String prefix, boolean isRight) {
        if (node != null) {
            System.out.println(prefix + (isRight ? "└── " : "┌── ") + node);
            
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
            return "Árbol Rojinegro vacío";
        }
        
        return "Árbol Rojinegro [tamaño=" + size + 
               ", altura=" + getHeight() + 
               ", altura negra=" + getBlackHeight() +
               ", nodos rojos=" + countRedNodes() +
               ", nodos negros=" + countBlackNodes() + "]";
    }
}
