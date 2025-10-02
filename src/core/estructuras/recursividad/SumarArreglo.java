package core.estructuras.recursividad;

import java.util.Arrays;

/**
 * Suma de elementos de un arreglo usando recursividad
 * 
 * Implementa un algoritmo recursivo para sumar todos los elementos de un arreglo
 * Complejidad: O(n) - donde n es el número de elementos
 * 
 * @author Informática 3
 * @version 1.0
 */
public class SumarArreglo {

    /**
     * Suma todos los elementos de un arreglo de forma recursiva
     * 
     * @param arreglo Arreglo a sumar
     * @return Suma de todos los elementos
     */
    public static int suma(int[] arreglo) {
        // Caso base: arreglo de un solo elemento
        if (arreglo.length <= 1) {
            return arreglo[0];
        } else {
            // Caso recursivo: primer elemento + suma del resto
            return arreglo[0] + suma(Arrays.copyOfRange(arreglo, 1, arreglo.length));
        }
    }
    
    /**
     * Método de prueba - calcula suma y promedio
     */
    public static void main(String[] args) {
        int[] arreglo = {1, 2, 3, 5, 10};
        int sumaTotal = suma(arreglo);
        double promedio = (double) sumaTotal / arreglo.length;
        
        System.out.println("Arreglo: " + Arrays.toString(arreglo));
        System.out.println("La suma del arreglo es: " + sumaTotal);
        System.out.println("El promedio es: " + promedio);
    }
}
