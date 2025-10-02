package core.pizzeria;

import java.util.ArrayList;
import java.util.List;

public class TiempoOrdenamiento {

    public static void probarAlgoritmos() {
        int[] tamanios = {100, 1000, 10000};

        System.out.println("=== ANÁLISIS DE RENDIMIENTO DE ALGORITMOS DE ORDENAMIENTO ===");
        
        for (int n : tamanios) {
            List<Pedido> listaPrueba = generarPedidosPrueba(n);

            System.out.println("\n--- Midiendo tiempos con " + n + " pedidos ---");

            // Ordenamiento por Inserción (tiempo de preparación)
            List<Pedido> copia1 = new ArrayList<>(listaPrueba);
            long inicio = System.nanoTime();
            Ordenador.ordenarPorTiempoPreparacion(copia1);
            long fin = System.nanoTime();
            System.out.printf("Inserción (tiempo prep): %.3f ms%n", (fin - inicio) / 1_000_000.0);

            // Shell Sort (precio)
            List<Pedido> copia2 = new ArrayList<>(listaPrueba);
            inicio = System.nanoTime();
            Ordenador.ordenarPorPrecio(copia2);
            fin = System.nanoTime();
            System.out.printf("Shell Sort (precio): %.3f ms%n", (fin - inicio) / 1_000_000.0);

            // QuickSort (nombre cliente)
            List<Pedido> copia3 = new ArrayList<>(listaPrueba);
            inicio = System.nanoTime();
            Ordenador.ordenarPorNombreCliente(copia3);
            fin = System.nanoTime();
            System.out.printf("QuickSort (nombre cliente): %.3f ms%n", (fin - inicio) / 1_000_000.0);
        }
        
        System.out.println("\n=== FIN DEL ANÁLISIS ===");
    }

    private static List<Pedido> generarPedidosPrueba(int n) {
        List<Pedido> pedidos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            pedidos.add(new Pedido(
                    Math.random() * 60,          // tiempo entre 0 y 60 min
                    Math.random() * 2000,        // precio entre 0 y 2000
                    "Cliente" + (int)(Math.random() * 1000) // nombres variados
            ));
        }
        return pedidos;
    }
    
    public static void compararAlgoritmos(List<Pedido> pedidos) {
        if (pedidos == null || pedidos.isEmpty()) {
            System.out.println("No hay pedidos para analizar");
            return;
        }
        
        int n = pedidos.size();
        System.out.println("\n=== COMPARACIÓN CON " + n + " PEDIDOS REALES ===");
        
        // Inserción
        List<Pedido> copia1 = new ArrayList<>(pedidos);
        long inicio = System.nanoTime();
        Ordenador.ordenarPorTiempoPreparacion(copia1);
        long fin = System.nanoTime();
        System.out.printf("Inserción: %.3f ms%n", (fin - inicio) / 1_000_000.0);

        // Shell Sort
        List<Pedido> copia2 = new ArrayList<>(pedidos);
        inicio = System.nanoTime();
        Ordenador.ordenarPorPrecio(copia2);
        fin = System.nanoTime();
        System.out.printf("Shell Sort: %.3f ms%n", (fin - inicio) / 1_000_000.0);

        // QuickSort
        List<Pedido> copia3 = new ArrayList<>(pedidos);
        inicio = System.nanoTime();
        Ordenador.ordenarPorNombreCliente(copia3);
        fin = System.nanoTime();
        System.out.printf("QuickSort: %.3f ms%n", (fin - inicio) / 1_000_000.0);
    }
}