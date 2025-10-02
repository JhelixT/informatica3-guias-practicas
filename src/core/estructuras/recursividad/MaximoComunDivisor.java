package core.estructuras.recursividad;

/**
 * Cálculo del Máximo Común Divisor usando recursividad (Algoritmo de Euclides)
 * 
 * Implementa el algoritmo de Euclides para calcular el MCD de forma recursiva
 * Complejidad: O(log min(a,b))
 * 
 * @author Informática 3
 * @version 1.0
 */
public class MaximoComunDivisor {
    
    /**
     * Calcula el MCD de dos números de forma recursiva
     * 
     * @param a Primer número
     * @param b Segundo número
     * @return Máximo común divisor de a y b
     */
    public static int mcd(int a, int b) {
        int resto = Integer.max(a, b);
        int divisor = Integer.min(a, b);
        
        // Caso base: si el divisor es 0, el MCD es el resto
        if (divisor == 0) {
            return resto; 
        }
        
        // Calcular el resto de la división
        int diferencia;
        while (true) {
            diferencia = resto - divisor;
            if (diferencia < 0) {
                break;
            }
            resto = diferencia;
        }
        
        // Si el resto es 0, el divisor es el MCD
        if (resto == 0) {
            return divisor;
        } else {
            // Caso recursivo: aplicar el algoritmo con nuevos valores
            return mcd(resto, divisor);
        }
    }
    
    /**
     * Método de prueba
     */
    public static void main(String[] args) {
        int a = 753;
        int b = 132;

        int resultado = mcd(a, b);

        System.out.println("El maximo comun divisor de " + a + " y " + b + " es: " + resultado);
    }
}
