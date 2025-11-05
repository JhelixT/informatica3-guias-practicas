package core.integrador.reportes;

import core.integrador.modelo.Turno;
import core.estructuras.listas.ListaEnlazada;
import core.estructuras.nodos.Nodo;
import java.util.Comparator;

/**
 * Implementa tres algoritmos de ordenamiento para generar reportes de turnos:
 * - Insertion Sort (estable) - mejor para listas pequeñas/casi ordenadas
 * - Shell Sort (gap sequence estándar) - mejor rendimiento general
 * - Quick Sort (Lomuto) - rápido en promedio pero inestable
 */
public class OrdenadorTurnos {
    
    /**
     * Ordenamiento por inserción - ESTABLE
     * Complejidad: O(n²) peor caso, O(n) mejor caso (casi ordenado)
     */
    public static ListaEnlazada<Turno> insertionSort(ListaEnlazada<Turno> lista, Comparator<Turno> comp) {
        if (lista == null || lista.isEmpty()) return lista;
        
        ListaEnlazada<Turno> resultado = new ListaEnlazada<>();
        Nodo<Turno> actual = lista.getHead();
        
        while (actual != null) {
            Turno turno = actual.getData();
            insertarOrdenado(resultado, turno, comp);
            actual = actual.getNext();
        }
        
        return resultado;
    }
    
    /**
     * Inserta un elemento en su posición ordenada manteniendo estabilidad
     */
    private static void insertarOrdenado(ListaEnlazada<Turno> lista, Turno turno, Comparator<Turno> comp) {
        if (lista.isEmpty()) {
            lista.insertFirst(turno);
            return;
        }
        
        Nodo<Turno> actual = lista.getHead();
        Nodo<Turno> anterior = null;
        
        while (actual != null && comp.compare(turno, actual.getData()) > 0) {
            anterior = actual;
            actual = actual.getNext();
        }
        
        if (anterior == null) {
            lista.insertFirst(turno);
        } else {
            // Insertar después de 'anterior'
            Nodo<Turno> nuevoNodo = new Nodo<>(turno);
            nuevoNodo.setNext(anterior.getNext());
            anterior.setNext(nuevoNodo);
        }
    }
    
    /**
     * Shell Sort con secuencia de gaps: n/2, n/4, ..., 1
     * Complejidad: O(n²) peor caso, O(n log n) caso promedio
     */
    public static ListaEnlazada<Turno> shellSort(ListaEnlazada<Turno> lista, Comparator<Turno> comp) {
        if (lista == null || lista.isEmpty()) return lista;
        
        // Convertir a array para indexación eficiente
        int n = lista.getSize();
        Turno[] array = listaToArray(lista, n);
        
        // Secuencia de gaps: n/2, n/4, ..., 1
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // Insertion sort con gap
            for (int i = gap; i < n; i++) {
                Turno temp = array[i];
                int j = i;
                
                while (j >= gap && comp.compare(array[j - gap], temp) > 0) {
                    array[j] = array[j - gap];
                    j -= gap;
                }
                
                array[j] = temp;
            }
        }
        
        return arrayToLista(array);
    }
    
    /**
     * Quick Sort con partición Lomuto (pivote final)
     * Complejidad: O(n log n) promedio, O(n²) peor caso
     */
    public static ListaEnlazada<Turno> quickSort(ListaEnlazada<Turno> lista, Comparator<Turno> comp) {
        if (lista == null || lista.isEmpty()) return lista;
        
        int n = lista.getSize();
        Turno[] array = listaToArray(lista, n);
        
        quickSortRecursivo(array, 0, n - 1, comp);
        
        return arrayToLista(array);
    }
    
    /**
     * Implementación recursiva de Quick Sort
     */
    private static void quickSortRecursivo(Turno[] array, int low, int high, Comparator<Turno> comp) {
        if (low < high) {
            int pi = particionLomuto(array, low, high, comp);
            quickSortRecursivo(array, low, pi - 1, comp);
            quickSortRecursivo(array, pi + 1, high, comp);
        }
    }
    
    /**
     * Partición Lomuto - pivote es el último elemento
     */
    private static int particionLomuto(Turno[] array, int low, int high, Comparator<Turno> comp) {
        Turno pivote = array[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (comp.compare(array[j], pivote) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        
        swap(array, i + 1, high);
        return i + 1;
    }
    
    /**
     * Intercambia dos elementos en el array
     */
    private static void swap(Turno[] array, int i, int j) {
        Turno temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /**
     * Convierte ListaEnlazada a array
     */
    private static Turno[] listaToArray(ListaEnlazada<Turno> lista, int n) {
        Turno[] array = new Turno[n];
        Nodo<Turno> actual = lista.getHead();
        int i = 0;
        
        while (actual != null) {
            array[i++] = actual.getData();
            actual = actual.getNext();
        }
        
        return array;
    }
    
    /**
     * Convierte array a ListaEnlazada
     */
    private static ListaEnlazada<Turno> arrayToLista(Turno[] array) {
        ListaEnlazada<Turno> lista = new ListaEnlazada<>();
        
        for (Turno turno : array) {
            lista.insertLast(turno);
        }
        
        return lista;
    }
}
