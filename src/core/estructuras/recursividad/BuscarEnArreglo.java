package core.estructuras.recursividad;

/**
 * Búsqueda recursiva en un arreglo
 * 
 * Implementa un algoritmo recursivo para buscar un elemento en un arreglo
 * Complejidad: O(n) - debe revisar todos los elementos en el peor caso
 * 
 * @author Informática 3
 * @version 1.0
 */
public class BuscarEnArreglo {

    /**
     * Busca un valor en un arreglo de forma recursiva
     * 
     * @param arr Arreglo donde buscar
     * @param n Valor a buscar
     * @param index Índice actual de búsqueda
     * @return true si el valor está en el arreglo, false en caso contrario
     */
    public static boolean buscar(int[] arr, int n, int index) {
        // Caso base: llegamos al final del arreglo sin encontrar el valor
        if (index == arr.length) {
            return false;
        }

        // Caso base: encontramos el valor
        if (arr[index] == n) {
            return true;
        }
        
        // Caso recursivo: seguir buscando en el resto del arreglo
        return buscar(arr, n, index + 1);
    }

    /**
     * Método de prueba
     */
    public static void main(String[] args) {
        int[] arreglo = {3, 5, 7, 9};
        int numero = 7;

        boolean encontrado = buscar(arreglo, numero, 0);
        System.out.println("¿Está " + numero + " en el arreglo? " + encontrado);
    }
}
