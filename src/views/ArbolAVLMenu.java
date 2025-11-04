package views;

import core.estructuras.arboles.ArbolAVL;
import core.utils.*;

/**
 * Menu interactivo para el Arbol AVL
 * 
 * Proporciona una interfaz user-friendly que permite al usuario:
 * - Insertar y eliminar valores
 * - Buscar valores
 * - Visualizar el arbol
 * - Ver recorridos y estadisticas
 * 
 * @author Informatica 3
 * @version 1.0
 */
public class ArbolAVLMenu {
    private ArbolAVL<Integer> arbol;
    
    public ArbolAVLMenu() {
        this.arbol = new ArbolAVL<>();
    }
    
    public void mostrarMenu() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("GESTION DE ARBOL AVL");
            MenuFormatter.mostrarOpcion(1, "Insertar un valor");
            MenuFormatter.mostrarOpcion(2, "Eliminar un valor");
            MenuFormatter.mostrarOpcion(3, "Buscar un valor");
            MenuFormatter.mostrarOpcion(4, "Mostrar estructura del arbol");
            MenuFormatter.mostrarOpcion(5, "Mostrar recorridos");
            MenuFormatter.mostrarOpcion(6, "Ver estadisticas");
            MenuFormatter.mostrarOpcion(7, "Llenar con valores de ejemplo");
            MenuFormatter.mostrarOpcion(8, "Vaciar arbol");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opcion: ", 0, 8);
            
            switch (opcion) {
                case 1 -> insertarValor();
                case 2 -> eliminarValor();
                case 3 -> buscarValor();
                case 4 -> mostrarEstructura();
                case 5 -> mostrarRecorridos();
                case 6 -> mostrarEstadisticas();
                case 7 -> llenarConEjemplos();
                case 8 -> vaciarArbol();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void insertarValor() {
        MenuFormatter.mostrarTituloSecundario("INSERTAR VALOR");
        
        int valor = InputValidator.leerEntero("Ingrese el valor a insertar: ");
        
        if (arbol.search(valor)) {
            MenuFormatter.mostrarMensajeAdvertencia("El valor " + valor + " ya existe en el arbol");
            MenuFormatter.mostrarMensajeInfo("Los arboles AVL no permiten valores duplicados");
            return;
        }
        
        arbol.insert(valor);
        MenuFormatter.mostrarMensajeExito("Valor " + valor + " insertado exitosamente");
        MenuFormatter.mostrarMensajeInfo("El arbol se ha rebalanceado automaticamente");
        
        System.out.println("\nResumen:");
        System.out.println("  - Altura del arbol: " + arbol.getHeight());
        System.out.println("  - Total de nodos: " + arbol.countNodes());
    }
    
    private void eliminarValor() {
        MenuFormatter.mostrarTituloSecundario("ELIMINAR VALOR");
        
        if (arbol.isEmpty()) {
            MenuFormatter.mostrarMensajeAdvertencia("El arbol esta vacio");
            return;
        }
        
        int valor = InputValidator.leerEntero("Ingrese el valor a eliminar: ");
        
        if (!arbol.search(valor)) {
            MenuFormatter.mostrarMensajeError("El valor " + valor + " no se encuentra en el arbol");
            return;
        }
        
        arbol.delete(valor);
        MenuFormatter.mostrarMensajeExito("Valor " + valor + " eliminado exitosamente");
        MenuFormatter.mostrarMensajeInfo("El arbol se ha rebalanceado automaticamente");
        
        if (!arbol.isEmpty()) {
            System.out.println("\nResumen:");
            System.out.println("  - Altura del arbol: " + arbol.getHeight());
            System.out.println("  - Total de nodos: " + arbol.countNodes());
        } else {
            System.out.println("  El arbol ahora esta vacio");
        }
    }
    
    private void buscarValor() {
        MenuFormatter.mostrarTituloSecundario("BUSCAR VALOR");
        
        if (arbol.isEmpty()) {
            MenuFormatter.mostrarMensajeAdvertencia("El arbol esta vacio");
            return;
        }
        
        int valor = InputValidator.leerEntero("Ingrese el valor a buscar: ");
        
        boolean encontrado = arbol.search(valor);
        
        if (encontrado) {
            MenuFormatter.mostrarMensajeExito("El valor " + valor + " SI se encuentra en el arbol");
        } else {
            MenuFormatter.mostrarMensajeError("El valor " + valor + " NO se encuentra en el arbol");
        }
    }
    
    private void mostrarEstructura() {
        MenuFormatter.mostrarTituloSecundario("ESTRUCTURA DEL ARBOL");
        
        if (arbol.isEmpty()) {
            MenuFormatter.mostrarMensajeAdvertencia("El arbol esta vacio");
            return;
        }
        
        arbol.display();
        
        System.out.println("\nLeyenda:");
        System.out.println("  h: altura del nodo");
        System.out.println("  b: factor de balance (debe estar entre -1 y 1)");
    }
    
    private void mostrarRecorridos() {
        MenuFormatter.mostrarTituloSecundario("RECORRIDOS DEL ARBOL");
        
        if (arbol.isEmpty()) {
            MenuFormatter.mostrarMensajeAdvertencia("El arbol esta vacio");
            return;
        }
        
        System.out.println("\nTipos de recorrido:\n");
        
        System.out.println("1. InOrden (Izq-Raiz-Der) - Orden ascendente:");
        System.out.print("   ");
        arbol.inOrderTraversal();
        
        System.out.println("\n2. PreOrden (Raiz-Izq-Der) - Para copiar estructura:");
        System.out.print("   ");
        arbol.preOrderTraversal();
        
        System.out.println("\n3. PostOrden (Izq-Der-Raiz) - Para eliminar arbol:");
        System.out.print("   ");
        arbol.postOrderTraversal();
        
        System.out.println("\n4. Por Niveles (BFS) - Nivel por nivel:");
        System.out.print("   ");
        arbol.levelOrderTraversal();
    }
    
    private void mostrarEstadisticas() {
        MenuFormatter.mostrarTituloSecundario("ESTADISTICAS DEL ARBOL");
        
        if (arbol.isEmpty()) {
            System.out.println("\nEstadisticas:");
            System.out.println("  - Estado: Vacio");
            System.out.println("  - Total de nodos: 0");
            System.out.println("  - Altura: 0");
            return;
        }
        
        int totalNodos = arbol.countNodes();
        int altura = arbol.getHeight();
        
        System.out.println("\nEstadisticas generales:");
        System.out.println("  - Estado: Arbol con datos");
        System.out.println("  - Total de nodos: " + totalNodos);
        System.out.println("  - Altura del arbol: " + altura);
        
        double alturaOptima = Math.log(totalNodos + 1) / Math.log(2);
        System.out.println("  - Altura optima teorica: " + String.format("%.2f", alturaOptima));
        
        double eficiencia = (alturaOptima / altura) * 100;
        System.out.println("  - Eficiencia de balance: " + String.format("%.2f%%", eficiencia));
        
        System.out.println("\nInformacion de balanceo:");
        System.out.println("  - El arbol AVL mantiene automaticamente un balance optimo");
        System.out.println("  - Factor de balance de cada nodo esta entre -1 y 1");
        System.out.println("  - Complejidad de operaciones: O(log n)");
    }
    
    private void llenarConEjemplos() {
        MenuFormatter.mostrarTituloSecundario("LLENAR CON EJEMPLOS");
        
        int[] valores = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 65, 85, 3, 7};
        
        System.out.println("Se insertaran los siguientes valores:");
        System.out.print("  ");
        for (int i = 0; i < valores.length; i++) {
            System.out.print(valores[i]);
            if (i < valores.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        
        if (!InputValidator.confirmar("\n¿Desea continuar?")) {
            MenuFormatter.mostrarMensajeInfo("Operacion cancelada");
            return;
        }
        
        int insertados = 0;
        int duplicados = 0;
        
        System.out.println("\nInsertando valores...");
        for (int valor : valores) {
            if (!arbol.search(valor)) {
                arbol.insert(valor);
                insertados++;
            } else {
                duplicados++;
            }
        }
        
        MenuFormatter.mostrarMensajeExito("Proceso completado:");
        System.out.println("  - Valores insertados: " + insertados);
        if (duplicados > 0) {
            System.out.println("  - Valores duplicados omitidos: " + duplicados);
        }
        System.out.println("  - Total de nodos en el arbol: " + arbol.countNodes());
        System.out.println("  - Altura del arbol: " + arbol.getHeight());
        
        MenuFormatter.mostrarMensajeInfo("Sugerencia: Use la opcion 4 para ver la estructura del arbol");
    }
    
    private void vaciarArbol() {
        MenuFormatter.mostrarTituloSecundario("VACIAR ARBOL");
        
        if (arbol.isEmpty()) {
            MenuFormatter.mostrarMensajeAdvertencia("El arbol ya esta vacio");
            return;
        }
        
        MenuFormatter.mostrarMensajeAdvertencia("Esta accion eliminara todos los valores del arbol");
        
        if (!InputValidator.confirmar("Esta seguro de que desea continuar?")) {
            MenuFormatter.mostrarMensajeInfo("Operacion cancelada. El arbol se mantiene sin cambios");
            return;
        }
        
        arbol = new ArbolAVL<>();
        MenuFormatter.mostrarMensajeExito("El arbol ha sido vaciado exitosamente");
    }
}
