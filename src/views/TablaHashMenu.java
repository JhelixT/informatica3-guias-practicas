package views;

import core.estructuras.hash.TablaHash;
import core.utils.InputValidator;
import core.utils.MenuFormatter;

/**
 * Menú interactivo para demostrar las operaciones de Tabla Hash.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class TablaHashMenu {
    
    private TablaHash<String, Integer> tabla;
    
    public TablaHashMenu() {
        this.tabla = new TablaHash<>();
    }
    
    public void mostrarMenu() {
        boolean continuar = true;
        
        while (continuar) {
            MenuFormatter.limpiarPantalla();
            mostrarOpciones();
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 14);
            
            MenuFormatter.limpiarPantalla();
            
            switch (opcion) {
                case 1 -> insertarElemento();
                case 2 -> buscarElemento();
                case 3 -> eliminarElemento();
                case 4 -> verificarClave();
                case 5 -> mostrarClaves();
                case 6 -> mostrarValores();
                case 7 -> mostrarEntradas();
                case 8 -> mostrarEstructura();
                case 9 -> mostrarEstadisticas();
                case 10 -> mostrarColisiones();
                case 11 -> limpiarTabla();
                case 12 -> insertarVariosDatos();
                case 13 -> cambiarCapacidad();
                case 14 -> demostrarOperaciones();
                case 0 -> {
                    if (InputValidator.confirmar("¿Está seguro de que desea salir?")) {
                        continuar = false;
                        System.out.println("\n👋 Saliendo del menú de Tabla Hash...\n");
                    }
                }
            }
            
            if (continuar && opcion != 0) {
                InputValidator.pausar();
            }
        }
    }
    
    private void mostrarOpciones() {
        MenuFormatter.mostrarTituloPrincipal("TABLA HASH CON ENCADENAMIENTO");
        
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      OPERACIONES BÁSICAS                         ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        MenuFormatter.mostrarOpcion(1, "Insertar elemento (put)");
        MenuFormatter.mostrarOpcion(2, "Buscar elemento (get)");
        MenuFormatter.mostrarOpcion(3, "Eliminar elemento (remove)");
        MenuFormatter.mostrarOpcion(4, "Verificar si existe clave (containsKey)");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        System.out.println("║                      CONSULTAS                                   ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        MenuFormatter.mostrarOpcion(5, "Mostrar todas las claves");
        MenuFormatter.mostrarOpcion(6, "Mostrar todos los valores");
        MenuFormatter.mostrarOpcion(7, "Mostrar todas las entradas");
        MenuFormatter.mostrarOpcion(8, "Ver estructura interna");
        MenuFormatter.mostrarOpcion(9, "Ver estadísticas");
        MenuFormatter.mostrarOpcion(10, "Análisis de colisiones");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        System.out.println("║                      UTILIDADES                                  ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════╣");
        MenuFormatter.mostrarOpcion(11, "Limpiar tabla");
        MenuFormatter.mostrarOpcion(12, "Insertar datos de prueba");
        MenuFormatter.mostrarOpcion(13, "Cambiar capacidad inicial");
        MenuFormatter.mostrarOpcion(14, "Demostración completa");
        MenuFormatter.mostrarSeparador();
        MenuFormatter.mostrarOpcion(0, "Volver al menú principal");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
    }
    
    private void insertarElemento() {
        System.out.println("=== INSERTAR ELEMENTO ===\n");
        
        String clave = InputValidator.leerCadenaNoVacia("Ingrese la clave (String): ");
        int valor = InputValidator.leerEntero("Ingrese el valor (Integer): ");
        
        tabla.put(clave, valor);
        System.out.println("\n✓ Elemento insertado: " + clave + " = " + valor);
        System.out.println(tabla);
    }
    
    private void buscarElemento() {
        System.out.println("=== BUSCAR ELEMENTO ===\n");
        
        if (tabla.isEmpty()) {
            System.out.println("⚠️ La tabla está vacía");
            return;
        }
        
        String clave = InputValidator.leerCadenaNoVacia("Ingrese la clave a buscar: ");
        Integer valor = tabla.get(clave);
        
        if (valor != null) {
            System.out.println("\n✓ Encontrado: " + clave + " = " + valor);
        } else {
            System.out.println("\n✗ Clave no encontrada: " + clave);
        }
    }
    
    private void eliminarElemento() {
        System.out.println("=== ELIMINAR ELEMENTO ===\n");
        
        if (tabla.isEmpty()) {
            System.out.println("⚠️ La tabla está vacía");
            return;
        }
        
        String clave = InputValidator.leerCadenaNoVacia("Ingrese la clave a eliminar: ");
        Integer valor = tabla.remove(clave);
        
        if (valor != null) {
            System.out.println("\n✓ Elemento eliminado: " + clave + " = " + valor);
            System.out.println(tabla);
        } else {
            System.out.println("\n✗ Clave no encontrada: " + clave);
        }
    }
    
    private void verificarClave() {
        System.out.println("=== VERIFICAR CLAVE ===\n");
        
        String clave = InputValidator.leerCadenaNoVacia("Ingrese la clave a verificar: ");
        boolean existe = tabla.containsKey(clave);
        
        if (existe) {
            System.out.println("\n✓ La clave '" + clave + "' existe en la tabla");
            System.out.println("Valor asociado: " + tabla.get(clave));
        } else {
            System.out.println("\n✗ La clave '" + clave + "' no existe en la tabla");
        }
    }
    
    private void mostrarClaves() {
        System.out.println("=== TODAS LAS CLAVES ===\n");
        
        if (tabla.isEmpty()) {
            System.out.println("⚠️ La tabla está vacía");
            return;
        }
        
        System.out.println("Claves en la tabla:");
        for (String clave : tabla.keys()) {
            System.out.println("  • " + clave);
        }
        System.out.println("\nTotal: " + tabla.size() + " claves");
    }
    
    private void mostrarValores() {
        System.out.println("=== TODOS LOS VALORES ===\n");
        
        if (tabla.isEmpty()) {
            System.out.println("⚠️ La tabla está vacía");
            return;
        }
        
        System.out.println("Valores en la tabla:");
        for (Integer valor : tabla.values()) {
            System.out.println("  • " + valor);
        }
        System.out.println("\nTotal: " + tabla.size() + " valores");
    }
    
    private void mostrarEntradas() {
        System.out.println("=== TODAS LAS ENTRADAS ===\n");
        
        if (tabla.isEmpty()) {
            System.out.println("⚠️ La tabla está vacía");
            return;
        }
        
        System.out.println("Pares clave-valor:");
        for (String entry : tabla.entries()) {
            System.out.println("  • " + entry);
        }
        System.out.println("\nTotal: " + tabla.size() + " entradas");
    }
    
    private void mostrarEstructura() {
        System.out.println("=== ESTRUCTURA INTERNA ===\n");
        tabla.display();
    }
    
    private void mostrarEstadisticas() {
        System.out.println("=== ESTADÍSTICAS ===\n");
        
        System.out.println("📊 Métricas generales:");
        System.out.println("   • Tamaño: " + tabla.size());
        System.out.println("   • Capacidad: " + tabla.getCapacity());
        System.out.println("   • Factor de carga: " + String.format("%.2f", tabla.getLoadFactor()));
        System.out.println("   • Vacía: " + (tabla.isEmpty() ? "Sí" : "No"));
        
        if (!tabla.isEmpty()) {
            int[] stats = tabla.getCollisionStats();
            System.out.println("\n🔍 Distribución:");
            System.out.println("   • Buckets usados: " + stats[0] + " / " + tabla.getCapacity());
            System.out.println("   • Buckets vacíos: " + (tabla.getCapacity() - stats[0]));
            System.out.println("   • Uso: " + String.format("%.1f", stats[0] * 100.0 / tabla.getCapacity()) + "%");
        }
    }
    
    private void mostrarColisiones() {
        System.out.println("=== ANÁLISIS DE COLISIONES ===\n");
        
        if (tabla.isEmpty()) {
            System.out.println("⚠️ La tabla está vacía");
            return;
        }
        
        int[] stats = tabla.getCollisionStats();
        
        System.out.println("📈 Estadísticas de colisiones:");
        System.out.println("   • Buckets usados: " + stats[0]);
        System.out.println("   • Colisiones totales: " + stats[2]);
        System.out.println("   • Máxima cadena: " + stats[1] + " elementos");
        
        double avgChain = (double) tabla.size() / stats[0];
        System.out.println("   • Promedio por bucket: " + String.format("%.2f", avgChain));
        
        System.out.println("\n💡 Interpretación:");
        if (stats[2] == 0) {
            System.out.println("   ✓ Distribución perfecta, sin colisiones");
        } else if (stats[1] <= 3) {
            System.out.println("   ✓ Buena distribución, pocas colisiones");
        } else if (stats[1] <= 5) {
            System.out.println("   ⚠️ Distribución aceptable");
        } else {
            System.out.println("   ✗ Muchas colisiones, considere redimensionar");
        }
    }
    
    private void limpiarTabla() {
        if (tabla.isEmpty()) {
            System.out.println("⚠️ La tabla ya está vacía");
            return;
        }
        
        if (InputValidator.confirmar("¿Está seguro de que desea limpiar toda la tabla?")) {
            tabla.clear();
            System.out.println("\n✓ Tabla limpiada correctamente");
        }
    }
    
    private void insertarVariosDatos() {
        System.out.println("=== INSERTAR DATOS DE PRUEBA ===\n");
        
        String[] nombres = {"Juan", "María", "Pedro", "Ana", "Luis", 
                           "Carmen", "José", "Laura", "Carlos", "Elena"};
        int[] edades = {25, 30, 22, 28, 35, 27, 32, 24, 29, 26};
        
        for (int i = 0; i < nombres.length; i++) {
            tabla.put(nombres[i], edades[i]);
            System.out.println("Insertado: " + nombres[i] + " = " + edades[i]);
        }
        
        System.out.println("\n✓ Datos de prueba insertados");
        System.out.println(tabla);
    }
    
    private void cambiarCapacidad() {
        System.out.println("=== CAMBIAR CAPACIDAD INICIAL ===\n");
        
        if (!tabla.isEmpty()) {
            System.out.println("⚠️ La tabla actual tiene datos.");
            if (!InputValidator.confirmar("¿Desea crear una nueva tabla vacía?")) {
                return;
            }
        }
        
        int capacidad = InputValidator.leerEnteroEnRango(
            "Ingrese la nueva capacidad (potencia de 2, min 4): ", 4, 1024);
        
        tabla = new TablaHash<>(capacidad);
        System.out.println("\n✓ Nueva tabla creada con capacidad " + capacidad);
    }
    
    private void demostrarOperaciones() {
        System.out.println("=== DEMOSTRACIÓN COMPLETA ===\n");
        
        System.out.println("Se creará una tabla hash y se demostrarán sus operaciones.");
        System.out.println("Presione Enter para continuar...");
        InputValidator.pausar();
        
        // Crear nueva tabla
        tabla = new TablaHash<>(8);
        System.out.println("\n1️⃣ Tabla hash creada (capacidad 8)");
        System.out.println(tabla);
        InputValidator.pausar();
        
        // Insertar elementos
        System.out.println("\n2️⃣ Insertando elementos...");
        String[] claves = {"Alice", "Bob", "Charlie", "David", "Eve"};
        int[] valores = {100, 200, 300, 400, 500};
        
        for (int i = 0; i < claves.length; i++) {
            tabla.put(claves[i], valores[i]);
            System.out.println("   put(\"" + claves[i] + "\", " + valores[i] + ")");
        }
        System.out.println(tabla);
        InputValidator.pausar();
        
        // Mostrar estructura
        System.out.println("\n3️⃣ Estructura interna:");
        tabla.display();
        InputValidator.pausar();
        
        // Buscar elementos
        System.out.println("\n4️⃣ Buscando elementos...");
        System.out.println("   get(\"Alice\") = " + tabla.get("Alice"));
        System.out.println("   get(\"Charlie\") = " + tabla.get("Charlie"));
        System.out.println("   get(\"Zoe\") = " + tabla.get("Zoe"));
        InputValidator.pausar();
        
        // Actualizar valor
        System.out.println("\n5️⃣ Actualizando valor...");
        System.out.println("   put(\"Bob\", 999) - actualiza valor existente");
        tabla.put("Bob", 999);
        System.out.println("   get(\"Bob\") = " + tabla.get("Bob"));
        InputValidator.pausar();
        
        // Eliminar elemento
        System.out.println("\n6️⃣ Eliminando elemento...");
        System.out.println("   remove(\"David\") = " + tabla.remove("David"));
        System.out.println(tabla);
        InputValidator.pausar();
        
        // Estadísticas finales
        System.out.println("\n7️⃣ Estadísticas finales:");
        int[] stats = tabla.getCollisionStats();
        System.out.println("   • Elementos: " + tabla.size());
        System.out.println("   • Factor de carga: " + String.format("%.2f", tabla.getLoadFactor()));
        System.out.println("   • Colisiones: " + stats[2]);
        
        System.out.println("\n✓ Demostración completa");
    }
}
