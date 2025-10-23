package core.estructuras.listas;

import core.estructuras.nodos.NodoDoble;

/**
 * Implementación de una Lista Doblemente Enlazada.
 * Permite navegación bidireccional y operaciones eficientes en ambos extremos.
 * 
 * @param <T> Tipo de dato que almacena la lista
 */
public class ListaDoblementeEnlazada<T> {
    private NodoDoble<T> head;
    private NodoDoble<T> tail;
    private int size;

    /**
     * Constructor que crea una lista vacía
     */
    public ListaDoblementeEnlazada() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Verifica si la lista está vacía
     * @return true si la lista está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Obtiene el tamaño de la lista
     * @return Número de elementos en la lista
     */
    public int getSize() {
        return size;
    }

    /**
     * Agrega un elemento al inicio de la lista
     * @param data El dato a agregar
     */
    public void addFirst(T data) {
        NodoDoble<T> newNode = new NodoDoble<>(data);
        
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;
    }

    /**
     * Agrega un elemento al final de la lista
     * @param data El dato a agregar
     */
    public void addLast(T data) {
        NodoDoble<T> newNode = new NodoDoble<>(data);
        
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        size++;
    }

    /**
     * Inserta un elemento en una posición específica
     * @param data El dato a insertar
     * @param position La posición donde insertar (0-based)
     * @return true si se insertó correctamente, false si la posición es inválida
     */
    public boolean insertAt(T data, int position) {
        if (position < 0 || position > size) {
            return false;
        }

        if (position == 0) {
            addFirst(data);
            return true;
        }

        if (position == size) {
            addLast(data);
            return true;
        }

        NodoDoble<T> newNode = new NodoDoble<>(data);
        NodoDoble<T> current = head;

        for (int i = 0; i < position - 1; i++) {
            current = current.getNext();
        }

        newNode.setNext(current.getNext());
        newNode.setPrevious(current);
        current.getNext().setPrevious(newNode);
        current.setNext(newNode);
        size++;
        return true;
    }

    /**
     * Elimina el primer elemento de la lista
     * @return El dato eliminado, o null si la lista está vacía
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T removedData = head.getData();

        if (head == tail) {
            head = tail = null;
        } else {
            head = head.getNext();
            head.setPrevious(null);
        }
        size--;
        return removedData;
    }

    /**
     * Elimina el último elemento de la lista
     * @return El dato eliminado, o null si la lista está vacía
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T removedData = tail.getData();

        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        size--;
        return removedData;
    }

    /**
     * Elimina un elemento en una posición específica
     * @param position La posición del elemento a eliminar (0-based)
     * @return El dato eliminado, o null si la posición es inválida
     */
    public T removeAt(int position) {
        if (position < 0 || position >= size) {
            return null;
        }

        if (position == 0) {
            return removeFirst();
        }

        if (position == size - 1) {
            return removeLast();
        }

        NodoDoble<T> current = head;
        for (int i = 0; i < position; i++) {
            current = current.getNext();
        }

        T removedData = current.getData();
        current.getPrevious().setNext(current.getNext());
        current.getNext().setPrevious(current.getPrevious());
        size--;
        return removedData;
    }

    /**
     * Busca un elemento en la lista
     * @param data El dato a buscar
     * @return La posición del elemento (0-based), o -1 si no se encuentra
     */
    public int search(T data) {
        NodoDoble<T> current = head;
        int position = 0;

        while (current != null) {
            if (current.getData().equals(data)) {
                return position;
            }
            current = current.getNext();
            position++;
        }
        return -1;
    }

    /**
     * Obtiene el elemento en una posición específica
     * @param position La posición del elemento (0-based)
     * @return El dato en la posición, o null si la posición es inválida
     */
    public T getAt(int position) {
        if (position < 0 || position >= size) {
            return null;
        }

        NodoDoble<T> current;

        // Optimización: recorrer desde el extremo más cercano
        if (position < size / 2) {
            current = head;
            for (int i = 0; i < position; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > position; i--) {
                current = current.getPrevious();
            }
        }

        return current.getData();
    }

    /**
     * Invierte el orden de los elementos en la lista
     */
    public void reverse() {
        if (isEmpty() || size == 1) {
            return;
        }

        NodoDoble<T> current = head;
        NodoDoble<T> temp = null;

        // Intercambiar los punteros siguiente y anterior de cada nodo
        while (current != null) {
            temp = current.getPrevious();
            current.setPrevious(current.getNext());
            current.setNext(temp);
            current = current.getPrevious();
        }

        // Intercambiar cabeza y cola
        temp = head;
        head = tail;
        tail = temp;
    }

    /**
     * Elimina todos los elementos de la lista
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Verifica si la lista contiene un elemento
     * @param data El dato a buscar
     * @return true si el elemento está en la lista, false en caso contrario
     */
    public boolean contains(T data) {
        return search(data) != -1;
    }

    /**
     * Muestra la lista de inicio a fin
     * @return String con la representación de la lista
     */
    public String displayForward() {
        if (isEmpty()) {
            return "Lista vacía";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("null <- ");
        NodoDoble<T> current = head;

        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(" <-> ");
            }
            current = current.getNext();
        }
        sb.append(" -> null");
        return sb.toString();
    }

    /**
     * Muestra la lista de fin a inicio
     * @return String con la representación de la lista en reversa
     */
    public String displayBackward() {
        if (isEmpty()) {
            return "Lista vacía";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("null <- ");
        NodoDoble<T> current = tail;

        while (current != null) {
            sb.append(current.getData());
            if (current.getPrevious() != null) {
                sb.append(" <-> ");
            }
            current = current.getPrevious();
        }
        sb.append(" -> null");
        return sb.toString();
    }

    @Override
    public String toString() {
        return displayForward();
    }

    /**
     * Obtiene la cabeza de la lista
     * @return El primer nodo de la lista
     */
    public NodoDoble<T> getHead() {
        return head;
    }

    /**
     * Obtiene la cola de la lista
     * @return El último nodo de la lista
     */
    public NodoDoble<T> getTail() {
        return tail;
    }
}
