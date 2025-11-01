package core.estructuras.monticulo;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de un Montículo Binario (Binary Heap).
 * 
 * Un montículo binario es un árbol binario completo que cumple con la propiedad de heap:
 * - Min-Heap: El valor del padre es menor o igual que sus hijos (el mínimo está en la raíz)
 * - Max-Heap: El valor del padre es mayor o igual que sus hijos (el máximo está en la raíz)
 * 
 * Propiedades:
 * - Árbol binario completo (todos los niveles llenos excepto el último)
 * - Representado mediante un arreglo
 * - Hijo izquierdo de i: 2*i
 * - Hijo derecho de i: 2*i + 1
 * - Padre de i: i/2
 * 
 * Complejidad:
 * - Insertar: O(log n)
 * - Eliminar mínimo/máximo: O(log n)
 * - Consultar mínimo/máximo: O(1)
 * - Construir desde arreglo: O(n)
 * 
 * @param <T> Tipo de dato que debe ser comparable
 * @author JhelixT
 * @version 1.0
 */
public class MonticuloBinario<T extends Comparable<T>> {
    
    /**
     * Enum para especificar el tipo de montículo.
     */
    public enum TipoMonticulo {
        MIN_HEAP,  // Montículo mínimo
        MAX_HEAP   // Montículo máximo
    }
    
    private List<T> heap;
    private TipoMonticulo tipo;
    
    /**
     * Constructor que crea un montículo vacío del tipo especificado.
     * 
     * @param tipo MIN_HEAP o MAX_HEAP
     */
    public MonticuloBinario(TipoMonticulo tipo) {
        this.heap = new ArrayList<>();
        this.heap.add(null); // Índice 0 no se usa, comenzamos desde 1
        this.tipo = tipo;
    }
    
    /**
     * Constructor que crea un Min-Heap por defecto.
     */
    public MonticuloBinario() {
        this(TipoMonticulo.MIN_HEAP);
    }
    
    /**
     * Inserta un nuevo elemento en el montículo.
     * El elemento se agrega al final y se percola hacia arriba.
     * 
     * @param valor El valor a insertar
     * @throws IllegalArgumentException si el valor es null
     */
    public void add(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No se puede insertar null");
        }
        
        heap.add(valor);
        percolateUp(size());
    }
    
    /**
     * Percola un elemento hacia arriba hasta restaurar la propiedad del heap.
     * 
     * @param i Índice del elemento a percolar
     */
    private void percolateUp(int i) {
        while (i > 1) {
            int parent = i / 2;
            
            if (cumplePropiedad(heap.get(i), heap.get(parent))) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }
    
    /**
     * Elimina y retorna el elemento raíz (mínimo o máximo).
     * El último elemento se mueve a la raíz y se percola hacia abajo.
     * 
     * @return El elemento raíz eliminado
     * @throws IllegalStateException si el montículo está vacío
     */
    public T poll() {
        if (isEmpty()) {
            throw new IllegalStateException("El montículo está vacío");
        }
        
        T root = heap.get(1);
        
        if (size() == 1) {
            heap.remove(1);
        } else {
            heap.set(1, heap.remove(size()));
            percolateDown(1);
        }
        
        return root;
    }
    
    /**
     * Percola un elemento hacia abajo hasta restaurar la propiedad del heap.
     * 
     * @param i Índice del elemento a percolar
     */
    private void percolateDown(int i) {
        while (2 * i <= size()) {
            int leftChild = 2 * i;
            int rightChild = 2 * i + 1;
            int selected = leftChild;
            
            // Seleccionar el hijo más pequeño (min-heap) o más grande (max-heap)
            if (rightChild <= size() && cumplePropiedad(heap.get(rightChild), heap.get(leftChild))) {
                selected = rightChild;
            }
            
            // Si el padre ya cumple la propiedad, terminar
            if (cumplePropiedad(heap.get(i), heap.get(selected))) {
                break;
            }
            
            swap(i, selected);
            i = selected;
        }
    }
    
    /**
     * Verifica si se cumple la propiedad del heap entre dos elementos.
     * 
     * @param hijo El elemento hijo
     * @param padre El elemento padre
     * @return true si se debe intercambiar (hijo debe estar arriba del padre)
     */
    private boolean cumplePropiedad(T hijo, T padre) {
        int comparacion = hijo.compareTo(padre);
        
        if (tipo == TipoMonticulo.MIN_HEAP) {
            return comparacion < 0; // Hijo menor que padre
        } else {
            return comparacion > 0; // Hijo mayor que padre
        }
    }
    
    /**
     * Intercambia dos elementos en el heap.
     * 
     * @param i Primera posición
     * @param j Segunda posición
     */
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    /**
     * Retorna el elemento raíz sin eliminarlo.
     * 
     * @return El elemento mínimo (min-heap) o máximo (max-heap)
     * @throws IllegalStateException si el montículo está vacío
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("El montículo está vacío");
        }
        return heap.get(1);
    }
    
    /**
     * Verifica si el montículo está vacío.
     * 
     * @return true si no tiene elementos, false en caso contrario
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Retorna el número de elementos en el montículo.
     * 
     * @return La cantidad de elementos
     */
    public int size() {
        return heap.size() - 1; // Restamos 1 porque el índice 0 no se usa
    }
    
    /**
     * Limpia todos los elementos del montículo.
     */
    public void clear() {
        heap.clear();
        heap.add(null); // Mantener el índice 0 vacío
    }
    
    /**
     * Construye un montículo desde un arreglo existente (heapify).
     * Utiliza el algoritmo Floyd que tiene complejidad O(n).
     * 
     * @param elementos Arreglo de elementos
     */
    public void buildHeap(T[] elementos) {
        clear();
        
        for (T elemento : elementos) {
            if (elemento != null) {
                heap.add(elemento);
            }
        }
        
        // Heapify desde el último nodo interno hacia arriba
        for (int i = size() / 2; i >= 1; i--) {
            percolateDown(i);
        }
    }
    
    /**
     * Retorna una lista con los elementos del heap (incluyendo null en índice 0).
     * 
     * @return Lista con la representación interna del heap
     */
    public List<T> toList() {
        return new ArrayList<>(heap);
    }
    
    /**
     * Retorna una lista con los elementos ordenados (sin null).
     * 
     * @return Lista de elementos desde índice 1
     */
    public List<T> getElements() {
        return new ArrayList<>(heap.subList(1, heap.size()));
    }
    
    /**
     * Retorna el tipo de montículo (MIN_HEAP o MAX_HEAP).
     * 
     * @return El tipo de montículo
     */
    public TipoMonticulo getTipo() {
        return tipo;
    }
    
    /**
     * Verifica si el montículo cumple con la propiedad de heap.
     * Útil para testing y debugging.
     * 
     * @return true si es un heap válido, false en caso contrario
     */
    public boolean isValidHeap() {
        for (int i = 1; i <= size() / 2; i++) {
            int leftChild = 2 * i;
            int rightChild = 2 * i + 1;
            
            // Verificar hijo izquierdo
            if (leftChild <= size()) {
                if (tipo == TipoMonticulo.MIN_HEAP) {
                    if (heap.get(i).compareTo(heap.get(leftChild)) > 0) {
                        return false;
                    }
                } else {
                    if (heap.get(i).compareTo(heap.get(leftChild)) < 0) {
                        return false;
                    }
                }
            }
            
            // Verificar hijo derecho
            if (rightChild <= size()) {
                if (tipo == TipoMonticulo.MIN_HEAP) {
                    if (heap.get(i).compareTo(heap.get(rightChild)) > 0) {
                        return false;
                    }
                } else {
                    if (heap.get(i).compareTo(heap.get(rightChild)) < 0) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Muestra el montículo de forma visual en la consola.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Montículo vacío");
            return;
        }
        
        System.out.println("Tipo: " + tipo);
        System.out.println("Arreglo: " + getElements());
        System.out.println("\nEstructura de árbol:");
        displayTree(1, "", true);
    }
    
    /**
     * Método auxiliar para mostrar el árbol de forma visual.
     */
    private void displayTree(int i, String prefix, boolean isRight) {
        if (i <= size()) {
            System.out.println(prefix + (isRight ? "└── " : "┌── ") + heap.get(i));
            
            int leftChild = 2 * i;
            int rightChild = 2 * i + 1;
            
            if (leftChild <= size() || rightChild <= size()) {
                if (rightChild <= size()) {
                    displayTree(rightChild, prefix + (isRight ? "    " : "│   "), true);
                }
                if (leftChild <= size()) {
                    displayTree(leftChild, prefix + (isRight ? "    " : "│   "), false);
                }
            }
        }
    }
    
    /**
     * Retorna la altura del montículo.
     * 
     * @return La altura del árbol
     */
    public int getHeight() {
        if (isEmpty()) {
            return 0;
        }
        return (int) Math.ceil(Math.log(size() + 1) / Math.log(2));
    }
    
    @Override
    public String toString() {
        return "MonticuloBinario [tipo=" + tipo + ", tamaño=" + size() + 
               ", raíz=" + (isEmpty() ? "null" : peek()) + "]";
    }
}
