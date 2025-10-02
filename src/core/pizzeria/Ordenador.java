package core.pizzeria;

import java.util.Collections;
import java.util.List;

public class Ordenador {

    /**
     * Ordenamiento por inserción basado en tiempo de preparación (ascendente)
     */
    public static void ordenarPorTiempoPreparacion(List<Pedido> pedidos) {
        if (pedidos == null || pedidos.size() <= 1) return;
        
        for (int i = 1; i < pedidos.size(); i++) {
            Pedido key = pedidos.get(i);
            int j = i - 1;
            while (j >= 0 && pedidos.get(j).getTiempoPrepMinutos() > key.getTiempoPrepMinutos()) {
                pedidos.set(j + 1, pedidos.get(j));
                j--;
            }
            pedidos.set(j + 1, key);
        }
    }

    /**
     * Ordenamiento Shell Sort basado en precio total (ascendente)
     */
    public static void ordenarPorPrecio(List<Pedido> pedidos) {
        if (pedidos == null || pedidos.size() <= 1) return;
        
        int n = pedidos.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Pedido temp = pedidos.get(i);
                int j;
                for (j = i; j >= gap && pedidos.get(j - gap).getPrecioTotal() > temp.getPrecioTotal(); j -= gap) {
                    pedidos.set(j, pedidos.get(j - gap));
                }
                pedidos.set(j, temp);
            }
        }
    }

    /**
     * Ordenamiento QuickSort basado en nombre del cliente (alfabético)
     */
    public static void ordenarPorNombreCliente(List<Pedido> pedidos) {
        if (pedidos == null || pedidos.size() <= 1) return;
        quicksort(pedidos, 0, pedidos.size() - 1);
    }

    private static void quicksort(List<Pedido> pedidos, int low, int high) {
        if (low < high) {
            int pi = partition(pedidos, low, high);
            quicksort(pedidos, low, pi - 1);
            quicksort(pedidos, pi + 1, high);
        }
    }

    private static int partition(List<Pedido> pedidos, int low, int high) {
        String pivot = pedidos.get(high).getNombreCliente();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (pedidos.get(j).getNombreCliente().compareToIgnoreCase(pivot) <= 0) {
                i++;
                Collections.swap(pedidos, i, j);
            }
        }
        Collections.swap(pedidos, i + 1, high);
        return i + 1;
    }
}