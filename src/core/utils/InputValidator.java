package core.utils;

import java.util.Scanner;

public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Lee un entero válido del usuario con validación
     */
    public static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            }
        }
    }
    
    /**
     * Lee un entero en un rango específico
     */
    public static int leerEnteroEnRango(String mensaje, int min, int max) {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor >= min && valor <= max) {
                return valor;
            }
            System.out.printf("❌ El valor debe estar entre %d y %d.%n", min, max);
        }
    }
    
    /**
     * Lee un double válido del usuario
     */
    public static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número decimal válido.");
            }
        }
    }
    
    /**
     * Lee un double positivo
     */
    public static double leerDoublePositivo(String mensaje) {
        while (true) {
            double valor = leerDouble(mensaje);
            if (valor >= 0) {
                return valor;
            }
            System.out.println("❌ El valor no puede ser negativo.");
        }
    }
    
    /**
     * Lee una cadena no vacía
     */
    public static String leerCadenaNoVacia(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("❌ No se permiten valores vacíos.");
        }
    }
    
    /**
     * Confirma una acción con S/N
     */
    public static boolean confirmar(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (respuesta.equals("S") || respuesta.equals("SI")) {
                return true;
            } else if (respuesta.equals("N") || respuesta.equals("NO")) {
                return false;
            }
            System.out.println("❌ Por favor, responda con S (Sí) o N (No).");
        }
    }
    
    /**
     * Pausa hasta que el usuario presione Enter
     */
    public static void pausar() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Obtiene el scanner global
     */
    public static Scanner getScanner() {
        return scanner;
    }
}