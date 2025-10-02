package core.estructuras.recursividad;

/**
 * Conversión de decimal a binario usando recursividad
 * 
 * Implementa un algoritmo recursivo para convertir un número decimal a binario
 * Complejidad: O(log n) - donde n es el número
 * 
 * @author Informática 3
 * @version 1.0
 */
public class ConversionBinaria {
    
    /**
     * Convierte un número decimal a binario de forma recursiva
     * 
     * @param n Número decimal a convertir
     * @return Representación binaria como String
     */
    public static String conversion(int n) {
        // Casos base: 0 o 1
        if (n == 1 || n == 0) {
            return String.valueOf(n);
        } else {
            // Caso recursivo: dividir entre 2 y concatenar el resto
            return conversion(n / 2) + String.valueOf(n % 2); 
        }
    }
    
    /**
     * Método de prueba
     */
    public static void main(String[] args) {
        int n = 143;
        String binario = conversion(n);
        System.out.printf("El numero %d en binario es %s%n", n, binario);
    }
}
