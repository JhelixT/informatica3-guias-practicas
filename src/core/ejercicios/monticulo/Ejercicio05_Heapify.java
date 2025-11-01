package core.ejercicios.monticulo;

import core.estructuras.monticulo.MonticuloBinario;

/**
 * Ejercicio 5: Construcción desde un arreglo (heapify)
 * 
 * Utiliza buildHeap() de MonticuloBinario para construcción eficiente O(n).
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio05_Heapify {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 5: HEAPIFY ═══\n");
        
        Integer[] datos = {20, 5, 15, 3, 11, 8, 30};
        
        System.out.print("Arreglo original: ");
        for (int v : datos) System.out.print(v + " ");
        System.out.println();
        
        MonticuloBinario<Integer> heap = new MonticuloBinario<>();
        System.out.println("\nConstruyendo heap con buildHeap() - Complejidad O(n)");
        heap.buildHeap(datos);
        
        System.out.println("\nHeap resultante:");
        heap.display();
        
        System.out.println("\nVerificación: ¿Es un heap válido? " + heap.isValidHeap());
    }
}
