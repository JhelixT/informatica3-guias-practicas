package core.estructuras.recursividad;

/**
 * Verificación de palíndromo usando recursividad
 * 
 * Implementa un algoritmo recursivo para verificar si una cadena es palíndromo
 * Un palíndromo es una palabra que se lee igual de izquierda a derecha que de derecha a izquierda
 * Complejidad: O(n) - donde n es la longitud de la cadena
 * 
 * @author Informática 3
 * @version 1.0
 */
public class Palindromo {

    /**
     * Verifica si una cadena es palíndromo de forma recursiva
     * 
     * @param s Cadena a verificar
     * @return true si es palíndromo, false en caso contrario
     */
    public static boolean esPalindromo(String s) {
        // Caso base: cadena vacía o de un solo carácter es palíndromo
        if (s.length() <= 1) {
            return true;
        }
        
        // Caso recursivo: verificar primer y último carácter, y el resto
        return (s.charAt(0) == s.charAt(s.length() - 1)) && 
               esPalindromo(s.substring(1, s.length() - 1));
    }
    
    /**
     * Método de prueba
     */
    public static void main(String[] args) {
        String s = "neuquen";
        if (esPalindromo(s)) {
            System.out.printf("El string '%s' es palindromo%n", s);
        } else {
            System.out.printf("El string '%s' no es palindromo%n", s);
        }
    }
}
