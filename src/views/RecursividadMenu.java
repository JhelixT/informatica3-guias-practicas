package views;

import core.estructuras.recursividad.*;
import core.utils.*;
import java.util.Arrays;

/**
 * Menú interactivo para ejercicios de Recursividad
 * 
 * Proporciona ejemplos demostrativos de diversos algoritmos recursivos
 * 
 * @author Informática 3
 * @version 1.0
 */
public class RecursividadMenu {
    
    public RecursividadMenu() {
    }
    
    public void mostrarMenu() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("EJERCICIOS DE RECURSIVIDAD");
            MenuFormatter.mostrarOpcion(1, "Buscar en arreglo");
            MenuFormatter.mostrarOpcion(2, "Contar dígitos");
            MenuFormatter.mostrarOpcion(3, "Conversión a binario");
            MenuFormatter.mostrarOpcion(4, "Fibonacci simple");
            MenuFormatter.mostrarOpcion(5, "Fibonacci optimizado");
            MenuFormatter.mostrarOpcion(6, "Invertir cadena");
            MenuFormatter.mostrarOpcion(7, "Máximo Común Divisor");
            MenuFormatter.mostrarOpcion(8, "Verificar palíndromo");
            MenuFormatter.mostrarOpcion(9, "Sumar arreglo");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 9);
            
            switch (opcion) {
                case 1 -> buscarEnArreglo();
                case 2 -> contarDigitos();
                case 3 -> conversionBinaria();
                case 4 -> fibonacci();
                case 5 -> fibonacciOptimizado();
                case 6 -> invertirCadena();
                case 7 -> maximoComunDivisor();
                case 8 -> verificarPalindromo();
                case 9 -> sumarArreglo();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void buscarEnArreglo() {
        MenuFormatter.mostrarTituloSecundario("BUSCAR EN ARREGLO");
        
        int[] arreglo = {3, 5, 7, 9, 11, 13, 15};
        System.out.println("Arreglo: " + Arrays.toString(arreglo));
        
        int numero = InputValidator.leerEntero("\nIngrese el número a buscar: ");
        
        boolean encontrado = BuscarEnArreglo.buscar(arreglo, numero, 0);
        
        if (encontrado) {
            MenuFormatter.mostrarMensajeExito("El valor " + numero + " SÍ está en el arreglo");
        } else {
            MenuFormatter.mostrarMensajeError("El valor " + numero + " NO está en el arreglo");
        }
    }
    
    private void contarDigitos() {
        MenuFormatter.mostrarTituloSecundario("CONTAR DÍGITOS");
        
        int numero = InputValidator.leerEntero("Ingrese un número: ");
        int digitos = ConteoDigitos.contarDigitos(Math.abs(numero));
        
        System.out.println("\nResultado:");
        System.out.println("  El número " + numero + " tiene " + digitos + " dígitos");
    }
    
    private void conversionBinaria() {
        MenuFormatter.mostrarTituloSecundario("CONVERSIÓN A BINARIO");
        
        int numero = InputValidator.leerEnteroPositivo("Ingrese un número decimal: ");
        String binario = ConversionBinaria.conversion(numero);
        
        System.out.println("\nResultado:");
        System.out.println("  Decimal: " + numero);
        System.out.println("  Binario: " + binario);
    }
    
    private void fibonacci() {
        MenuFormatter.mostrarTituloSecundario("FIBONACCI SIMPLE");
        
        int n = InputValidator.leerEnteroEnRango("¿Cuántos números de Fibonacci desea generar? (1-15): ", 1, 15);
        
        System.out.println("\nPrimeros " + n + " números de Fibonacci:");
        for (int i = 1; i <= n; i++) {
            System.out.println("  F(" + i + ") = " + Fibonacci.numeroFibonacci(i));
        }
        
        MenuFormatter.mostrarMensajeInfo("Nota: Este método es ineficiente para números grandes");
    }
    
    private void fibonacciOptimizado() {
        MenuFormatter.mostrarTituloSecundario("FIBONACCI OPTIMIZADO");
        
        int n = InputValidator.leerEnteroEnRango("¿Cuántos números de Fibonacci desea generar? (1-40): ", 1, 40);
        
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        
        System.out.println("\nPrimeros " + n + " números de Fibonacci (con memoización):");
        for (int i = 1; i <= n; i++) {
            memo = new int[n + 1];
            Arrays.fill(memo, -1);
            System.out.println("  F(" + i + ") = " + FibonacciOptimizado.numeroFibonacci(i));
        }
        
        MenuFormatter.mostrarMensajeInfo("Este método usa memoización para mayor eficiencia");
    }
    
    private void invertirCadena() {
        MenuFormatter.mostrarTituloSecundario("INVERTIR CADENA");
        
        String original = InputValidator.leerCadenaNoVacia("Ingrese una cadena de texto: ");
        String invertida = InvertirCadena.inversion(original);
        
        System.out.println("\nResultado:");
        System.out.println("  Original:  " + original);
        System.out.println("  Invertida: " + invertida);
    }
    
    private void maximoComunDivisor() {
        MenuFormatter.mostrarTituloSecundario("MÁXIMO COMÚN DIVISOR");
        
        int a = InputValidator.leerEnteroPositivo("Ingrese el primer número: ");
        int b = InputValidator.leerEnteroPositivo("Ingrese el segundo número: ");
        
        int mcd = MaximoComunDivisor.mcd(a, b);
        
        System.out.println("\nResultado:");
        System.out.println("  El MCD de " + a + " y " + b + " es: " + mcd);
    }
    
    private void verificarPalindromo() {
        MenuFormatter.mostrarTituloSecundario("VERIFICAR PALÍNDROMO");
        
        String cadena = InputValidator.leerCadenaNoVacia("Ingrese una cadena: ");
        cadena = cadena.toLowerCase().replaceAll("\\s+", "");
        
        boolean esPalindromo = Palindromo.esPalindromo(cadena);
        
        System.out.println("\nResultado:");
        if (esPalindromo) {
            MenuFormatter.mostrarMensajeExito("'" + cadena + "' ES un palíndromo");
        } else {
            MenuFormatter.mostrarMensajeError("'" + cadena + "' NO es un palíndromo");
        }
    }
    
    private void sumarArreglo() {
        MenuFormatter.mostrarTituloSecundario("SUMAR ARREGLO");
        
        int[] arreglo = {1, 2, 3, 5, 10};
        System.out.println("Arreglo: " + Arrays.toString(arreglo));
        
        int suma = SumarArreglo.suma(arreglo);
        double promedio = (double) suma / arreglo.length;
        
        System.out.println("\nResultado:");
        System.out.println("  Suma total: " + suma);
        System.out.println("  Promedio: " + String.format("%.2f", promedio));
    }
}
