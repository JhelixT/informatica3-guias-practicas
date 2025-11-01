package views;

import core.estructuras.monticulo.MonticuloBinario;
import core.estructuras.monticulo.MonticuloBinario.TipoMonticulo;
import core.utils.InputValidator;
import core.utils.MenuFormatter;

/**
 * Menú interactivo para Montículo Binario (Binary Heap).
 * 
 * @author JhelixT
 * @version 1.0
 */
public class MonticuloMenu {
    
    private MonticuloBinario<Integer> monticulo;
    
    public MonticuloMenu() {
        this.monticulo = new MonticuloBinario<>(TipoMonticulo.MIN_HEAP);
    }
    
    public void mostrarMenu() {
        boolean continuar = true;
        
        while (continuar) {
            MenuFormatter.limpiarPantalla();
            
            System.out.println("╔════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                    MONTÍCULO BINARIO (BINARY HEAP)                ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════╣");
            System.out.println("║                                                                    ║");
            System.out.println("║  ESTADO ACTUAL:                                                    ║");
            System.out.println("║  Tipo: " + String.format("%-60s", monticulo.getTipo()) + " ║");
            System.out.println("║  Tamaño: " + String.format("%-58d", monticulo.size()) + " ║");
            System.out.println("║  Raíz: " + String.format("%-60s", 
                monticulo.isEmpty() ? "vacío" : monticulo.peek()) + " ║");
            System.out.println("║                                                                    ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════╣");
            System.out.println("║                                                                    ║");
            System.out.println("║  📚 OPERACIONES BÁSICAS                                            ║");
            System.out.println("║   1. Insertar elemento (add)                                       ║");
            System.out.println("║   2. Eliminar raíz (poll)                                          ║");
            System.out.println("║   3. Consultar raíz (peek)                                         ║");
            System.out.println("║   4. Verificar si está vacío                                       ║");
            System.out.println("║   5. Limpiar montículo                                             ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🔧 OPERACIONES AVANZADAS                                          ║");
            System.out.println("║   6. Construir heap desde arreglo (heapify)                        ║");
            System.out.println("║   7. Cambiar tipo (Min-Heap ↔ Max-Heap)                           ║");
            System.out.println("║   8. Verificar validez del heap                                    ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  📊 VISUALIZACIÓN                                                  ║");
            System.out.println("║   9. Mostrar estructura completa                                   ║");
            System.out.println("║  10. Mostrar elementos como arreglo                                ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🎯 DEMOS                                                          ║");
            System.out.println("║  11. Demo: Inserción paso a paso                                   ║");
            System.out.println("║  12. Demo: Cola de prioridad                                       ║");
            System.out.println("║                                                                    ║");
            System.out.println("║   0. Volver al menú principal                                      ║");
            System.out.println("║                                                                    ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════╝");
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 12);
            
            MenuFormatter.limpiarPantalla();
            
            switch (opcion) {
                case 1 -> insertarElemento();
                case 2 -> eliminarRaiz();
                case 3 -> consultarRaiz();
                case 4 -> verificarVacio();
                case 5 -> limpiarMonticulo();
                case 6 -> construirDesdeArreglo();
                case 7 -> cambiarTipo();
                case 8 -> verificarValidez();
                case 9 -> mostrarEstructura();
                case 10 -> mostrarArreglo();
                case 11 -> demoInsercionPasoAPaso();
                case 12 -> demoColaPrioridad();
                case 0 -> {
                    continuar = false;
                    System.out.println("\n👋 Volviendo al menú principal...\n");
                }
                default -> System.out.println("\n❌ Opción no válida\n");
            }
            
            if (continuar && opcion != 0) {
                InputValidator.pausar();
            }
        }
    }
    
    private void insertarElemento() {
        System.out.println("═══ INSERTAR ELEMENTO ═══\n");
        
        int valor = InputValidator.leerEntero("Ingrese el valor a insertar: ");
        
        System.out.println("\nAntes de insertar:");
        System.out.println(monticulo);
        
        monticulo.add(valor);
        
        System.out.println("\nDespués de insertar " + valor + ":");
        System.out.println(monticulo);
        System.out.println("\n✅ Elemento insertado correctamente");
    }
    
    private void eliminarRaiz() {
        System.out.println("═══ ELIMINAR RAÍZ ═══\n");
        
        if (monticulo.isEmpty()) {
            System.out.println("❌ El montículo está vacío");
            return;
        }
        
        System.out.println("Antes de eliminar:");
        System.out.println(monticulo);
        
        Integer eliminado = monticulo.poll();
        
        System.out.println("\nElemento eliminado: " + eliminado);
        System.out.println("\nDespués de eliminar:");
        System.out.println(monticulo);
    }
    
    private void consultarRaiz() {
        System.out.println("═══ CONSULTAR RAÍZ ═══\n");
        
        if (monticulo.isEmpty()) {
            System.out.println("❌ El montículo está vacío");
        } else {
            System.out.println("Raíz actual: " + monticulo.peek());
            System.out.println("(" + (monticulo.getTipo() == TipoMonticulo.MIN_HEAP ? "mínimo" : "máximo") + ")");
        }
    }
    
    private void verificarVacio() {
        System.out.println("═══ VERIFICAR VACÍO ═══\n");
        
        if (monticulo.isEmpty()) {
            System.out.println("✅ El montículo está vacío");
        } else {
            System.out.println("❌ El montículo NO está vacío");
            System.out.println("Tamaño actual: " + monticulo.size());
        }
    }
    
    private void limpiarMonticulo() {
        System.out.println("═══ LIMPIAR MONTÍCULO ═══\n");
        
        if (monticulo.isEmpty()) {
            System.out.println("El montículo ya está vacío");
        } else {
            System.out.println("Tamaño antes: " + monticulo.size());
            monticulo.clear();
            System.out.println("Tamaño después: " + monticulo.size());
            System.out.println("\n✅ Montículo limpiado");
        }
    }
    
    private void construirDesdeArreglo() {
        System.out.println("═══ CONSTRUIR HEAP DESDE ARREGLO ═══\n");
        
        System.out.println("Ingrese números separados por espacios:");
        System.out.println("Ejemplo: 20 5 15 3 11");
        String input = InputValidator.leerCadenaNoVacia("> ");
        
        String[] partes = input.trim().split("\\s+");
        Integer[] elementos = new Integer[partes.length];
        
        try {
            for (int i = 0; i < partes.length; i++) {
                elementos[i] = Integer.parseInt(partes[i]);
            }
            
            System.out.println("\nArreglo original: ");
            for (Integer e : elementos) {
                System.out.print(e + " ");
            }
            
            monticulo.buildHeap(elementos);
            
            System.out.println("\n\nHeap construido:");
            System.out.println(monticulo);
            System.out.println("Elementos: " + monticulo.getElements());
            System.out.println("\n✅ Heap construido en O(n)");
            
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Error: Ingrese solo números válidos");
        }
    }
    
    private void cambiarTipo() {
        System.out.println("═══ CAMBIAR TIPO DE MONTÍCULO ═══\n");
        
        TipoMonticulo tipoActual = monticulo.getTipo();
        System.out.println("Tipo actual: " + tipoActual);
        
        if (monticulo.isEmpty()) {
            TipoMonticulo nuevoTipo = (tipoActual == TipoMonticulo.MIN_HEAP) 
                ? TipoMonticulo.MAX_HEAP : TipoMonticulo.MIN_HEAP;
            monticulo = new MonticuloBinario<>(nuevoTipo);
            System.out.println("Nuevo tipo: " + nuevoTipo);
            System.out.println("\n✅ Tipo cambiado");
        } else {
            System.out.println("\n⚠️  Para cambiar el tipo, primero debe limpiar el montículo");
            if (InputValidator.confirmar("¿Desea limpiar y cambiar el tipo?")) {
                TipoMonticulo nuevoTipo = (tipoActual == TipoMonticulo.MIN_HEAP) 
                    ? TipoMonticulo.MAX_HEAP : TipoMonticulo.MIN_HEAP;
                monticulo = new MonticuloBinario<>(nuevoTipo);
                System.out.println("\n✅ Tipo cambiado a: " + nuevoTipo);
            }
        }
    }
    
    private void verificarValidez() {
        System.out.println("═══ VERIFICAR VALIDEZ ═══\n");
        
        boolean valido = monticulo.isValidHeap();
        
        System.out.println("Tipo: " + monticulo.getTipo());
        System.out.println("Tamaño: " + monticulo.size());
        
        if (valido) {
            System.out.println("\n✅ El heap es válido");
            System.out.println("Cumple con la propiedad de " + 
                (monticulo.getTipo() == TipoMonticulo.MIN_HEAP ? "mínimo" : "máximo"));
        } else {
            System.out.println("\n❌ El heap NO es válido");
        }
    }
    
    private void mostrarEstructura() {
        System.out.println("═══ ESTRUCTURA COMPLETA ═══\n");
        
        if (monticulo.isEmpty()) {
            System.out.println("El montículo está vacío");
        } else {
            monticulo.display();
            System.out.println("\nAltura: " + monticulo.getHeight());
        }
    }
    
    private void mostrarArreglo() {
        System.out.println("═══ REPRESENTACIÓN COMO ARREGLO ═══\n");
        
        if (monticulo.isEmpty()) {
            System.out.println("El montículo está vacío");
        } else {
            System.out.println("Elementos (índices desde 1):");
            var elementos = monticulo.getElements();
            for (int i = 0; i < elementos.size(); i++) {
                System.out.println("  [" + (i + 1) + "] = " + elementos.get(i));
            }
            
            System.out.println("\nRelaciones padre-hijo:");
            for (int i = 1; i <= elementos.size() / 2; i++) {
                System.out.print("  Nodo [" + i + "] = " + elementos.get(i - 1));
                
                int left = 2 * i;
                int right = 2 * i + 1;
                
                if (left <= elementos.size()) {
                    System.out.print(" → hijo izq [" + left + "] = " + elementos.get(left - 1));
                }
                if (right <= elementos.size()) {
                    System.out.print(", hijo der [" + right + "] = " + elementos.get(right - 1));
                }
                System.out.println();
            }
        }
    }
    
    private void demoInsercionPasoAPaso() {
        System.out.println("═══ DEMO: INSERCIÓN PASO A PASO ═══\n");
        System.out.println("Insertaremos los valores: 20, 5, 15, 3, 11\n");
        
        monticulo = new MonticuloBinario<>(TipoMonticulo.MIN_HEAP);
        int[] valores = {20, 5, 15, 3, 11};
        
        for (int valor : valores) {
            System.out.println("\n─── Insertando " + valor + " ───");
            monticulo.add(valor);
            System.out.println("Arreglo: " + monticulo.getElements());
            monticulo.display();
            InputValidator.pausar();
        }
        
        System.out.println("\n✅ Demo completada");
        System.out.println("El mínimo siempre está en la raíz: " + monticulo.peek());
    }
    
    private void demoColaPrioridad() {
        System.out.println("═══ DEMO: COLA DE PRIORIDAD ═══\n");
        System.out.println("Simulando sistema de atención por prioridad");
        System.out.println("(números menores = mayor prioridad)\n");
        
        monticulo = new MonticuloBinario<>(TipoMonticulo.MIN_HEAP);
        
        System.out.println("Llegada de pacientes:");
        String[] pacientes = {"Urgencia (prioridad 1)", "Consulta (prioridad 5)", 
                              "Emergencia (prioridad 0)", "Revisión (prioridad 3)"};
        int[] prioridades = {1, 5, 0, 3};
        
        for (int i = 0; i < pacientes.length; i++) {
            System.out.println("  → " + pacientes[i]);
            monticulo.add(prioridades[i]);
        }
        
        System.out.println("\nOrden de atención:");
        int orden = 1;
        while (!monticulo.isEmpty()) {
            int prioridad = monticulo.poll();
            String tipo = switch (prioridad) {
                case 0 -> "Emergencia";
                case 1 -> "Urgencia";
                case 3 -> "Revisión";
                case 5 -> "Consulta";
                default -> "Desconocido";
            };
            System.out.println("  " + orden++ + ". " + tipo + " (prioridad " + prioridad + ")");
        }
        
        System.out.println("\n✅ Cola de prioridad procesada correctamente");
    }
}
