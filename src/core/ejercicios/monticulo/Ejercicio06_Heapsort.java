package core.ejercicios.monticulo;

import core.estructuras.monticulo.MonticuloBinario;

/**
 * Ejercicio 6: Implementar Heapsort
 * 
 * Ordenar arreglo usando MonticuloBinario.
 * Complejidad: O(n log n)
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio06_Heapsort {
    
    public static void heapsort(int[] arr) {
        MonticuloBinario<Integer> heap = new MonticuloBinario<>();
        
        // Insertar todos los elementos - O(n log n)
        for (int v : arr) {
            heap.add(v);
        }
        
        // Extraer en orden ascendente - O(n log n)
        for (int i = 0; i < arr.length; i++) {
            arr[i] = heap.poll();
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 6: HEAPSORT ═══\n");
        
        int[] arr = {9, 4, 7, 1, 6, 2};
        
        System.out.print("Arreglo original: ");
        for (int v : arr) System.out.print(v + " ");
        
        heapsort(arr);
        
        System.out.print("\nArreglo ordenado: ");
        for (int v : arr) System.out.print(v + " ");
        
        System.out.println("\n\n✓ Complejidad: O(n log n)");
    }
}
