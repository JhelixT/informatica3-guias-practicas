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
 * @author Informática 3
 * @version 1.0
 */
public class ArbolAVL {
    // Raíz del árbol AVL
    private NodoAVL raiz;

    /**
     * Constructor: Crea un árbol AVL vacío
     */
    public ArbolAVL() {
        this.raiz = null;  // Árbol vacío inicialmente
    }

    /**
     * Calcula la altura de un nodo
     * La altura de un nodo null es 0
     * 
     * @param nodo Nodo del cual obtener la altura
     * @return Altura del nodo (0 si es null)
     */
    private int altura(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    /**
     * Obtiene el factor de balance de un nodo
     * Factor de balance = altura(subárbol izquierdo) - altura(subárbol derecho)
     * 
     * @param nodo Nodo del cual obtener el factor de balance
     * @return Factor de balance del nodo
     */
    private int obtenerBalance(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.balance;
    }

    /**
     * Actualiza la altura y el balance de un nodo
     * 
     * @param nodo Nodo al cual actualizar altura y balance
     */
    private void actualizarAltura(NodoAVL nodo) {
        if (nodo != null) {
            int alturaIzq = altura(nodo.izquierdo);
            int alturaDer = altura(nodo.derecho);
            
            nodo.altura = 1 + Math.max(alturaIzq, alturaDer);
            nodo.balance = alturaIzq - alturaDer;
        }
    }

    /**
     * Rotación simple a la derecha
     * 
     * @param y Nodo desbalanceado
     * @return Nueva raíz después de la rotación
     */
    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL B = x.derecho;

        x.derecho = y;
        y.izquierdo = B;

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    /**
     * Rotación simple a la izquierda
     * 
     * @param x Nodo desbalanceado
     * @return Nueva raíz después de la rotación
     */
    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL B = y.izquierdo;

        y.izquierdo = x;
        x.derecho = B;

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    /**
     * Inserta un nuevo valor en el árbol AVL
     * 
     * @param dato Valor a insertar
     */
    public void insertar(int dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    /**
     * Inserta un valor de forma recursiva manteniendo el balance AVL
     * 
     * @param nodo Nodo actual en la recursión
     * @param dato Valor a insertar
     * @return Nodo después de la inserción
     */
    private NodoAVL insertarRecursivo(NodoAVL nodo, int dato) {
        // PASO 1: Inserción BST estándar
        if (nodo == null) {
            return new NodoAVL(dato);
        }

        if (dato < nodo.dato) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, dato);
        } else if (dato > nodo.dato) {
            nodo.derecho = insertarRecursivo(nodo.derecho, dato);
        } else {
            return nodo; // Valores duplicados no permitidos
        }

        // PASO 2: Actualizar altura
        actualizarAltura(nodo);

        // PASO 3: Obtener balance
        int balance = obtenerBalance(nodo);

        // PASO 4: Aplicar rotaciones si es necesario

        // Caso Izquierda-Izquierda
        if (balance > 1 && dato < nodo.izquierdo.dato) {
            return rotacionDerecha(nodo);
        }

        // Caso Derecha-Derecha
        if (balance < -1 && dato > nodo.derecho.dato) {
            return rotacionIzquierda(nodo);
        }

        // Caso Izquierda-Derecha
        if (balance > 1 && dato > nodo.izquierdo.dato) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && dato < nodo.derecho.dato) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    /**
     * Busca un valor en el árbol AVL
     * 
     * @param dato Valor a buscar
     * @return true si el valor existe, false en caso contrario
     */
    public boolean buscar(int dato) {
        return buscarRecursivo(raiz, dato);
    }

    /**
     * Busca un valor de forma recursiva
     * 
     * @param nodo Nodo actual
     * @param dato Valor a buscar
     * @return true si se encuentra, false en caso contrario
     */
    private boolean buscarRecursivo(NodoAVL nodo, int dato) {
        if (nodo == null) {
            return false;
        }

        if (dato == nodo.dato) {
            return true;
        }

        if (dato < nodo.dato) {
            return buscarRecursivo(nodo.izquierdo, dato);
        } else {
            return buscarRecursivo(nodo.derecho, dato);
        }
    }

    /**
     * Encuentra el nodo con el valor mínimo
     * 
     * @param nodo Raíz del subárbol
     * @return Nodo con el valor mínimo
     */
    private NodoAVL nodoMinimo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

    /**
     * Elimina un valor del árbol AVL
     * 
     * @param dato Valor a eliminar
     */
    public void eliminar(int dato) {
        raiz = eliminarRecursivo(raiz, dato);
    }

    /**
     * Elimina un valor de forma recursiva manteniendo el balance AVL
     * 
     * @param nodo Nodo actual
     * @param dato Valor a eliminar
     * @return Nodo después de la eliminación
     */
    private NodoAVL eliminarRecursivo(NodoAVL nodo, int dato) {
        // PASO 1: Eliminación BST estándar
        if (nodo == null) {
            return nodo;
        }

        if (dato < nodo.dato) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, dato);
        } else if (dato > nodo.dato) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, dato);
        } else {
            // Nodo encontrado
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            } else if (nodo.derecho == null) {
                return nodo.izquierdo;
            }

            // Nodo con dos hijos
            NodoAVL sucesor = nodoMinimo(nodo.derecho);
            nodo.dato = sucesor.dato;
            nodo.derecho = eliminarRecursivo(nodo.derecho, sucesor.dato);
        }

        // PASO 2: Actualizar altura
        actualizarAltura(nodo);

        // PASO 3: Obtener balance
        int balance = obtenerBalance(nodo);

        // PASO 4: Aplicar rotaciones si es necesario

        // Caso Izquierda-Izquierda
        if (balance > 1 && obtenerBalance(nodo.izquierdo) >= 0) {
            return rotacionDerecha(nodo);
        }

        // Caso Izquierda-Derecha
        if (balance > 1 && obtenerBalance(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        // Caso Derecha-Derecha
        if (balance < -1 && obtenerBalance(nodo.derecho) <= 0) {
            return rotacionIzquierda(nodo);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && obtenerBalance(nodo.derecho) > 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    /**
     * Recorrido InOrden (Izq-Raíz-Der)
     */
    public void recorridoInOrden() {
        System.out.print("InOrden: ");
        recorridoInOrdenRecursivo(raiz);
        System.out.println();
    }

    private void recorridoInOrdenRecursivo(NodoAVL nodo) {
        if (nodo != null) {
            recorridoInOrdenRecursivo(nodo.izquierdo);
            System.out.print(nodo.dato + " ");
            recorridoInOrdenRecursivo(nodo.derecho);
        }
    }

    /**
     * Recorrido PreOrden (Raíz-Izq-Der)
     */
    public void recorridoPreOrden() {
        System.out.print("PreOrden: ");
        recorridoPreOrdenRecursivo(raiz);
        System.out.println();
    }

    private void recorridoPreOrdenRecursivo(NodoAVL nodo) {
        if (nodo != null) {
            System.out.print(nodo.dato + " ");
            recorridoPreOrdenRecursivo(nodo.izquierdo);
            recorridoPreOrdenRecursivo(nodo.derecho);
        }
    }

    /**
     * Recorrido PostOrden (Izq-Der-Raíz)
     */
    public void recorridoPostOrden() {
        System.out.print("PostOrden: ");
        recorridoPostOrdenRecursivo(raiz);
        System.out.println();
    }

    private void recorridoPostOrdenRecursivo(NodoAVL nodo) {
        if (nodo != null) {
            recorridoPostOrdenRecursivo(nodo.izquierdo);
            recorridoPostOrdenRecursivo(nodo.derecho);
            System.out.print(nodo.dato + " ");
        }
    }

    /**
     * Recorrido por niveles (BFS)
     */
    public void recorridoPorNiveles() {
        if (raiz == null) {
            System.out.println("Por Niveles: (árbol vacío)");
            return;
        }

        System.out.print("Por Niveles: ");
        Queue<NodoAVL> cola = new LinkedList<>();
        cola.offer(raiz);

        while (!cola.isEmpty()) {
            NodoAVL actual = cola.poll();
            System.out.print(actual.dato + " ");

            if (actual.izquierdo != null) {
                cola.offer(actual.izquierdo);
            }
            if (actual.derecho != null) {
                cola.offer(actual.derecho);
            }
        }
        System.out.println();
    }

    /**
     * Muestra el árbol de forma visual
     */
    public void mostrarArbol() {
        if (raiz == null) {
            System.out.println("El árbol está vacío");
            return;
        }
        System.out.println("\n=== Estructura del Árbol AVL ===");
        mostrarArbolRecursivo(raiz, "", true);
    }

    private void mostrarArbolRecursivo(NodoAVL nodo, String prefijo, boolean esUltimo) {
        if (nodo != null) {
            System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + 
                             nodo.dato + " (h:" + nodo.altura + ", b:" + nodo.balance + ")");
            
            String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
            
            if (nodo.izquierdo != null || nodo.derecho != null) {
                if (nodo.izquierdo != null) {
                    mostrarArbolRecursivo(nodo.izquierdo, nuevoPrefijo, nodo.derecho == null);
                }
                if (nodo.derecho != null) {
                    mostrarArbolRecursivo(nodo.derecho, nuevoPrefijo, true);
                }
            }
        }
    }

    /**
     * Verifica si el árbol está vacío
     */
    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Obtiene la altura del árbol
     */
    public int obtenerAltura() {
        return altura(raiz);
    }

    /**
     * Cuenta el número total de nodos
     */
    public int contarNodos() {
        return contarNodosRecursivo(raiz);
    }

    private int contarNodosRecursivo(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodosRecursivo(nodo.izquierdo) + contarNodosRecursivo(nodo.derecho);
    }
}
