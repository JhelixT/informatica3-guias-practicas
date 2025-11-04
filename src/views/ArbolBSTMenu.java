package views;

import core.estructuras.arboles.ArbolBST;
import core.utils.InputValidator;
import core.utils.MenuFormatter;

import java.util.List;
import java.util.Scanner;

/**
 * Menu interactivo para demostrar las operaciones del Arbol de Busqueda Binaria (BST).
 * 
 * @author JhelixT
 * @version 1.0
 */
public class ArbolBSTMenu {
    private static ArbolBST<Integer> arbol;
    private static Scanner scanner;
    
    /**
     * Muestra el Menu principal del BST.
     */
    public static void mostrarMenu() {
        scanner = new Scanner(System.in);
        arbol = new ArbolBST<>();
        boolean continuar = true;
        
        while (continuar) {
            mostrarOpciones();
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opcion: ", 0, 15);
            System.out.println();
            
            switch (opcion) {
                case 1 -> insertarValor();
                case 2 -> buscarValor();
                case 3 -> eliminarValor();
                case 4 -> mostrarRecorridoInorden();
                case 5 -> mostrarRecorridoPreorden();
                case 6 -> mostrarRecorridoPostorden();
                case 7 -> mostrarRecorridoPorNiveles();
                case 8 -> mostrarTodosLosRecorridos();
                case 9 -> encontrarMinMax();
                case 10 -> mostrarEstadisticas();
                case 11 -> visualizarArbol();
                case 12 -> verificarValidez();
                case 13 -> insertarMultiplesValores();
                case 14 -> limpiarArbol();
                case 15 -> demostrarOperaciones();
                case 0 -> {
                    System.out.println("Volviendo al Menu principal...");
                    continuar = false;
                }
            }
            
            if (continuar && opcion != 0) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }
    }
    
    private static void mostrarOpciones() {
        MenuFormatter.limpiarPantalla();
        MenuFormatter.mostrarTituloPrincipal("Arbol DE Busqueda BINARIA (BST)");
        
        System.out.println("OPERACIONES BASICAS:");
        System.out.println("  1. Insertar valor");
        System.out.println("  2. Buscar valor");
        System.out.println("  3. Eliminar valor");
        System.out.println();
        
        System.out.println("RECORRIDOS:");
        System.out.println("  4. Recorrido Inorden (ascendente)");
        System.out.println("  5. Recorrido Preorden");
        System.out.println("  6. Recorrido Postorden");
        System.out.println("  7. Recorrido por Niveles (BFS)");
        System.out.println("  8. Mostrar todos los recorridos");
        System.out.println();
        
        System.out.println("CONSULTAS:");
        System.out.println("  9. Encontrar minimo y maximo");
        System.out.println(" 10. Mostrar estadisticas del Arbol");
        System.out.println(" 11. Visualizar estructura del Arbol");
        System.out.println(" 12. Verificar validez del BST");
        System.out.println();
        
        System.out.println("UTILIDADES:");
        System.out.println(" 13. Insertar multiples valores");
        System.out.println(" 14. Limpiar Arbol");
        System.out.println(" 15. Demostracion completa");
        System.out.println();
        
        System.out.println("  0. Volver al Menu principal");
        System.out.println();
        MenuFormatter.mostrarSeparador();
        
        // Mostrar estado actual
        if (!arbol.isEmpty()) {
            System.out.println("Estado: " + arbol.getSize() + " nodos, altura: " + arbol.getHeight());
        } else {
            System.out.println("Estado: Arbol vacio");
        }
        System.out.println();
    }
    
    private static void insertarValor() {
        System.out.println("=== INSERTAR VALOR ===\n");
        
        int valor = InputValidator.leerEntero("Ingrese el valor a insertar: ");
        
        boolean insertado = arbol.insert(valor);
        
        if (insertado) {
            System.out.println("✅ Valor " + valor + " insertado correctamente");
            System.out.println("\nEstado actual del Arbol:");
            arbol.display();
        } else {
            System.out.println("❌ El valor " + valor + " ya existe en el Arbol");
            System.out.println("(El BST no permite valores duplicados)");
        }
    }
    
    private static void buscarValor() {
        System.out.println("=== BUSCAR VALOR ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        int valor = InputValidator.leerEntero("Ingrese el valor a buscar: ");
        
        boolean encontrado = arbol.search(valor);
        
        if (encontrado) {
            System.out.println("✅ El valor " + valor + " Si existe en el Arbol");
        } else {
            System.out.println("❌ El valor " + valor + " NO existe en el Arbol");
        }
        
        System.out.println("\nArbol actual:");
        arbol.display();
    }
    
    private static void eliminarValor() {
        System.out.println("=== ELIMINAR VALOR ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        System.out.println("Arbol actual:");
        arbol.display();
        System.out.println();
        
        int valor = InputValidator.leerEntero("Ingrese el valor a eliminar: ");
        
        boolean eliminado = arbol.delete(valor);
        
        if (eliminado) {
            System.out.println("\n✅ Valor " + valor + " eliminado correctamente");
            System.out.println("\nArbol despues de eliminar:");
            arbol.display();
        } else {
            System.out.println("\n❌ El valor " + valor + " no existe en el Arbol");
        }
    }
    
    private static void mostrarRecorridoInorden() {
        System.out.println("=== RECORRIDO INORDEN (Izquierda-Raiz-Derecha) ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        System.out.println("Este recorrido visita los nodos en orden ASCENDENTE");
        System.out.println();
        
        List<Integer> resultado = arbol.inOrderTraversal();
        System.out.println("Resultado: " + resultado);
        
        System.out.println("\nEstructura del Arbol:");
        arbol.display();
    }
    
    private static void mostrarRecorridoPreorden() {
        System.out.println("=== RECORRIDO PREORDEN (Raiz-Izquierda-Derecha) ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        System.out.println("Este recorrido visita primero la Raiz, luego subArbol izquierdo, luego derecho");
        System.out.println();
        
        List<Integer> resultado = arbol.preOrderTraversal();
        System.out.println("Resultado: " + resultado);
        
        System.out.println("\nEstructura del Arbol:");
        arbol.display();
    }
    
    private static void mostrarRecorridoPostorden() {
        System.out.println("=== RECORRIDO POSTORDEN (Izquierda-Derecha-Raiz) ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        System.out.println("Este recorrido visita primero los hijos, luego la Raiz");
        System.out.println();
        
        List<Integer> resultado = arbol.postOrderTraversal();
        System.out.println("Resultado: " + resultado);
        
        System.out.println("\nEstructura del Arbol:");
        arbol.display();
    }
    
    private static void mostrarRecorridoPorNiveles() {
        System.out.println("=== RECORRIDO POR NIVELES (BFS) ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        System.out.println("Este recorrido visita los nodos nivel por nivel (de arriba a abajo)");
        System.out.println();
        
        List<Integer> resultado = arbol.levelOrderTraversal();
        System.out.println("Resultado: " + resultado);
        
        System.out.println("\nEstructura del Arbol:");
        arbol.display();
    }
    
    private static void mostrarTodosLosRecorridos() {
        System.out.println("=== TODOS LOS RECORRIDOS ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        System.out.println("Estructura del Arbol:");
        arbol.display();
        System.out.println();
        
        System.out.println("📋 RECORRIDOS:");
        System.out.println();
        
        System.out.println("1. Inorden (ascendente):     " + arbol.inOrderTraversal());
        System.out.println("2. Preorden:                 " + arbol.preOrderTraversal());
        System.out.println("3. Postorden:                " + arbol.postOrderTraversal());
        System.out.println("4. Por niveles (BFS):        " + arbol.levelOrderTraversal());
    }
    
    private static void encontrarMinMax() {
        System.out.println("=== ENCONTRAR minimo Y maximo ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        Integer min = arbol.findMinValue();
        Integer max = arbol.findMax();
        
        System.out.println("📊 Valores extremos:");
        System.out.println("   • minimo: " + min + " (nodo mas a la izquierda)");
        System.out.println("   • maximo: " + max + " (nodo mas a la derecha)");
        
        System.out.println("\nEstructura del Arbol:");
        arbol.display();
    }
    
    private static void mostrarEstadisticas() {
        System.out.println("=== estadisticas DEL Arbol ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        System.out.println("📊 informacion GENERAL:");
        System.out.println("   • Total de nodos:    " + arbol.countNodes());
        System.out.println("   • Nodos hoja:        " + arbol.countLeaves());
        System.out.println("   • Nodos internos:    " + (arbol.countNodes() - arbol.countLeaves()));
        System.out.println("   • Altura del Arbol:  " + arbol.getHeight());
        System.out.println("   • Valor minimo:      " + arbol.findMinValue());
        System.out.println("   • Valor maximo:      " + arbol.findMax());
        System.out.println();
        
        System.out.println("📋 RECORRIDO INORDEN (ordenado):");
        System.out.println("   " + arbol.inOrderTraversal());
        System.out.println();
        
        System.out.println("🔍 PROPIEDADES:");
        System.out.println("   • ¿Es un BST valido? " + (arbol.isValidBST() ? "✅ Si" : "❌ No"));
        System.out.println("   • Esta vacio?       " + (arbol.isEmpty() ? "Si" : "No"));
        
        // Calcular factor de balance aproximado
        double alturaIdeal = Math.log(arbol.countNodes() + 1) / Math.log(2);
        double eficiencia = (alturaIdeal / arbol.getHeight()) * 100;
        System.out.println("   • Eficiencia:        " + String.format("%.1f%%", eficiencia));
        
        if (eficiencia < 70) {
            System.out.println("   ⚠️ El Arbol esta algo desbalanceado");
        }
    }
    
    private static void visualizarArbol() {
        System.out.println("=== Visualizacion DEL Arbol ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        System.out.println("Estructura completa del BST:");
        System.out.println();
        arbol.display();
        
        System.out.println("\n📊 informacion rapida:");
        System.out.println("   Nodos: " + arbol.countNodes() + " | Altura: " + arbol.getHeight() + 
                         " | Hojas: " + arbol.countLeaves());
    }
    
    private static void verificarValidez() {
        System.out.println("=== VERIFICAR VALIDEZ DEL BST ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol esta vacio");
            return;
        }
        
        System.out.println("Verificando propiedades del Binary Search Tree...");
        System.out.println();
        
        boolean esValido = arbol.isValidBST();
        
        if (esValido) {
            System.out.println("✅ El Arbol ES un BST valido");
            System.out.println();
            System.out.println("Cumple con las propiedades:");
            System.out.println("   • Todos los valores del subArbol izquierdo son menores que la Raiz");
            System.out.println("   • Todos los valores del subArbol derecho son mayores que la Raiz");
            System.out.println("   • Esta propiedad se cumple recursivamente para cada subArbol");
        } else {
            System.out.println("❌ El Arbol NO es un BST valido");
            System.out.println("   (No cumple con alguna propiedad del BST)");
        }
        
        System.out.println("\nRecorrido inorden (debe estar ordenado): " + arbol.inOrderTraversal());
    }
    
    private static void insertarMultiplesValores() {
        System.out.println("=== INSERTAR multiples VALORES ===\n");
        
        System.out.println("Opciones:");
        System.out.println("1. Ingresar valores manualmente");
        System.out.println("2. Generar valores aleatorios");
        System.out.println();
        
        int opcion = InputValidator.leerEnteroEnRango("Seleccione una opcion (1-2): ", 1, 2);
        
        if (opcion == 1) {
            System.out.println("\nIngrese los valores separados por espacios (ej: 50 30 70 20 40):");
            scanner.nextLine(); // Limpiar buffer
            String linea = scanner.nextLine();
            
            String[] partes = linea.trim().split("\\s+");
            int insertados = 0;
            int duplicados = 0;
            
            for (String parte : partes) {
                try {
                    int valor = Integer.parseInt(parte);
                    if (arbol.insert(valor)) {
                        insertados++;
                    } else {
                        duplicados++;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Ignorando valor invalido: " + parte);
                }
            }
            
            System.out.println("\n✅ Valores insertados: " + insertados);
            if (duplicados > 0) {
                System.out.println("⚠️ Valores duplicados ignorados: " + duplicados);
            }
        } else {
            int cantidad = InputValidator.leerEnteroEnRango("\nCuantos valores aleatorios desea generar? ", 1, 50);
            int min = InputValidator.leerEntero("Valor minimo: ");
            int max = InputValidator.leerEnteroEnRango("Valor maximo: ", min + 1, Integer.MAX_VALUE);
            
            int insertados = 0;
            for (int i = 0; i < cantidad; i++) {
                int valor = min + (int)(Math.random() * (max - min + 1));
                if (arbol.insert(valor)) {
                    insertados++;
                }
            }
            
            System.out.println("\n✅ Valores unicos insertados: " + insertados + " de " + cantidad + " intentos");
        }
        
        System.out.println("\nArbol resultante:");
        arbol.display();
    }
    
    private static void limpiarArbol() {
        System.out.println("=== LIMPIAR Arbol ===\n");
        
        if (arbol.isEmpty()) {
            System.out.println("⚠️ El Arbol ya esta vacio");
            return;
        }
        
        System.out.println("Estado actual: " + arbol.getSize() + " nodos");
        System.out.print("\nEsta seguro que desea eliminar todos los nodos? (S/N): ");
        scanner.nextLine(); // Limpiar buffer
        String respuesta = scanner.nextLine().trim().toUpperCase();
        
        if (respuesta.equals("S") || respuesta.equals("SI") || respuesta.equals("Si")) {
            arbol.clear();
            System.out.println("\n✅ Arbol limpiado correctamente");
        } else {
            System.out.println("\n❌ Operacion cancelada");
        }
    }
    
    private static void demostrarOperaciones() {
        System.out.println("=== Demostracion COMPLETA DEL BST ===\n");
        
        // Limpiar Arbol anterior
        arbol.clear();
        
        System.out.println("Se creara un Arbol con los valores: 50, 30, 70, 20, 40, 60, 80");
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine();
        
        // Insertar valores
        System.out.println("\n--- PASO 1: INSERCION ---");
        int[] valores = {50, 30, 70, 20, 40, 60, 80};
        for (int valor : valores) {
            arbol.insert(valor);
            System.out.println("Insertando " + valor + "...");
        }
        System.out.println("\n✅ Arbol creado:");
        arbol.display();
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
        
        // Mostrar recorridos
        System.out.println("\n--- PASO 2: RECORRIDOS ---");
        System.out.println("Inorden (ascendente): " + arbol.inOrderTraversal());
        System.out.println("Preorden:             " + arbol.preOrderTraversal());
        System.out.println("Postorden:            " + arbol.postOrderTraversal());
        System.out.println("Por niveles:          " + arbol.levelOrderTraversal());
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
        
        // Busquedas
        System.out.println("\n--- PASO 3: BusquedaS ---");
        System.out.println("Buscar 40: " + (arbol.search(40) ? "✅ Encontrado" : "❌ No encontrado"));
        System.out.println("Buscar 90: " + (arbol.search(90) ? "✅ Encontrado" : "❌ No encontrado"));
        System.out.println("minimo: " + arbol.findMinValue());
        System.out.println("maximo: " + arbol.findMax());
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
        
        // Eliminar
        System.out.println("\n--- PASO 4: ELIMINACION ---");
        System.out.println("Eliminando el valor 30 (nodo con dos hijos)...");
        arbol.delete(30);
        System.out.println("\n✅ Arbol despues de eliminar 30:");
        arbol.display();
        
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
        
        // estadisticas finales
        System.out.println("\n--- PASO 5: estadisticas FINALES ---");
        System.out.println("Total de nodos:  " + arbol.countNodes());
        System.out.println("Nodos hoja:      " + arbol.countLeaves());
        System.out.println("Altura:          " + arbol.getHeight());
        System.out.println("¿Es BST valido?  " + (arbol.isValidBST() ? "✅ Si" : "❌ No"));
        
        System.out.println("\n🎉 Demostracion completada!");
    }
}
