package core.estructuras.recursividad;

/**
 * Secuencia de Fibonacci usando recursividad simple
 * 
 * Implementa el cálculo de números de Fibonacci de forma recursiva
 * NOTA: Esta implementación es simple pero ineficiente para números grandes
 * Complejidad: O(2^n) - exponencial
 * 
 * @author Informática 3
 * @version 1.0
 */
public class Fibonacci {

    /**
     * Calcula el n-ésimo número de Fibonacci de forma recursiva
     * 
     * @param n Posición en la secuencia de Fibonacci
     * @return El n-ésimo número de Fibonacci
     */
    public static int numeroFibonacci(int n) {
        // Casos base: primeros dos números de Fibonacci
        if (n == 1 || n == 2) {
            return 1;
        }
        // Caso recursivo: suma de los dos anteriores
        return numeroFibonacci(n - 1) + numeroFibonacci(n - 2);
    }
    
    /**
     * Método de prueba - genera los primeros n números de Fibonacci
     */
    public static void main(String[] args) {
        int n = 10;
        System.out.println("Primeros " + n + " numeros de Fibonacci:");
        for (int i = 1; i <= n; i++) {
            System.out.println("F(" + i + ") = " + numeroFibonacci(i));
        }
    }
}
