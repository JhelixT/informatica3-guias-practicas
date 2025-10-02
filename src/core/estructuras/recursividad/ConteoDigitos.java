package core.estructuras.recursividad;

/**
 * Conteo de dígitos de un número usando recursividad
 * 
 * Implementa un algoritmo recursivo para contar cuántos dígitos tiene un número entero
 * Complejidad: O(log n) - donde n es el número
 * 
 * @author Informática 3
 * @version 1.0
 */
public class ConteoDigitos {
    
    /**
     * Cuenta la cantidad de dígitos de un número de forma recursiva
     * 
     * @param n Número al cual contar dígitos
     * @return Cantidad de dígitos
     */
    public static int contarDigitos(int n) {
        // Caso base: si el número tiene un solo dígito
        if (n < 10) {
            return 1;
        }
        // Caso recursivo: 1 dígito más los del resto
        return 1 + contarDigitos(n / 10);
    }

    /**
     * Método de prueba
     */
    public static void main(String[] args) {
        int numero = 921381233;
        int digitos = contarDigitos(numero);

        System.out.println("El numero " + numero + " tiene " + digitos + " digitos");
    }
}
