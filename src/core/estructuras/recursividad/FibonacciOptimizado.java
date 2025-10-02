package core.estructuras.recursividad;

import java.util.Arrays;

/**
 * Secuencia de Fibonacci usando recursividad con memoización
 * 
 * Implementa el cálculo de números de Fibonacci optimizado con memoización
 * La memoización almacena resultados previos para evitar cálculos repetidos
 * Complejidad: O(n) - lineal
 * 
 * @author Informática 3
 * @version 1.0
 */
public class FibonacciOptimizado {

    // Array para memoizar los resultados
    private static int[] memo;

    /**
     * Calcula el n-ésimo número de Fibonacci con memoización
     * 
     * @param n Posición en la secuencia de Fibonacci
     * @return El n-ésimo número de Fibonacci
     */
    public static int numeroFibonacci(int n) {
        // Si ya lo calculamos antes, lo devolvemos
        if (memo[n] != -1) {
            return memo[n];
        }

        // Casos base
        if (n == 1 || n == 2) {
            memo[n] = 1;
        } else {
            // Caso recursivo: calcular y almacenar en memo
            memo[n] = numeroFibonacci(n - 1) + numeroFibonacci(n - 2);
        }
        return memo[n];
    }

    /**
     * Método de prueba - genera los primeros n números de Fibonacci
     */
    public static void main(String[] args) {
        int n = 20;
        memo = new int[n + 1];
        Arrays.fill(memo, -1); // Inicializamos con -1 para saber qué no está calculado

        System.out.println("Primeros " + n + " numeros de Fibonacci (optimizado):");
        for (int i = 1; i <= n; i++) {
            System.out.println("F(" + i + ") = " + numeroFibonacci(i));
        }
    }
}
