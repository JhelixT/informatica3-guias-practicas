package core.estructuras.recursividad;

/**
 * Inversión de cadena usando recursividad
 * 
 * Implementa un algoritmo recursivo para invertir una cadena de texto
 * Complejidad: O(n) - donde n es la longitud de la cadena
 * 
 * @author Informática 3
 * @version 1.0
 */
public class InvertirCadena {

    /**
     * Invierte una cadena de forma recursiva
     * 
     * @param s Cadena a invertir
     * @return Cadena invertida
     */
    public static String inversion(String s) {
        // Caso base: cadena vacía o de un solo carácter
        if (s.length() <= 1) {
            return s;
        } else {
            // Caso recursivo: invertir el resto + primer carácter
            return inversion(s.substring(1)) + s.charAt(0);
        }
    }
    
    /**
     * Método de prueba
     */
    public static void main(String[] args) {
        String original = "hola";
        String invertida = inversion(original);
        System.out.println("Original: " + original);
        System.out.println("Invertida: " + invertida);
    }
}
