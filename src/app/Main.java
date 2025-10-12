package app;

import views.*;
import core.utils.*;

/**
 * Clase principal que unifica todas las guías prácticas de Informática 3
 * 
 * Esta aplicación integra:
 * - Gestión de Pizzería (algoritmos de ordenamiento)
 * - Gestión de Tareas (estructuras de datos básicas)
 * - Estructuras Lineales (Listas, Pilas y Colas)
 * - Árboles AVL (estructuras de datos avanzadas)
 * - Recursividad (técnicas de programación)
 * 
 * @author Equipo de Informática 3
 * @version 1.0
 */
public class Main {
    
    public static void main(String[] args) {
        mostrarBienvenida();
        menuPrincipal();
        MenuFormatter.mostrarDespedida();
    }
    
    private static void mostrarBienvenida() {
        MenuFormatter.limpiarPantalla();
        MenuFormatter.mostrarTituloPrincipal("GUÍAS PRÁCTICAS DE INFORMÁTICA 3");
        System.out.println("  Bienvenido al sistema integrado de prácticas");
        System.out.println("  Explora diferentes estructuras de datos y algoritmos");
        System.out.println("  Perfecto para aprender y practicar conceptos fundamentales");
        System.out.println();
        System.out.println("  Módulos disponibles:");
        System.out.println("     • Gestión de Pizzería (Algoritmos de Ordenamiento)");
        System.out.println("     • Gestión de Tareas (Listas y Operaciones Básicas)");
        System.out.println("     • Estructuras Lineales (Listas, Pilas y Colas)");
        System.out.println("     • Árboles AVL (Estructuras Balanceadas)");
        System.out.println("     • Recursividad (Técnicas de Programación)");
        System.out.println();
        InputValidator.pausar();
    }
    
    private static void menuPrincipal() {
        while (true) {
            MenuFormatter.limpiarPantalla();
            MenuFormatter.mostrarTituloPrincipal("MENÚ PRINCIPAL");
            
            MenuFormatter.mostrarOpcion(1, "Gestión de Pizzería");
            System.out.println("     └─ Algoritmos: Inserción, Shell Sort, Quick Sort");
            
            MenuFormatter.mostrarOpcion(2, "Gestión de Tareas");
            System.out.println("     └─ CRUD básico con listas y filtros");
            
            MenuFormatter.mostrarOpcion(3, "Estructuras Lineales");
            System.out.println("     └─ Listas, Pilas y Colas (Arreglos y Enlazadas)");
            
            MenuFormatter.mostrarOpcion(4, "Árboles AVL");
            System.out.println("     └─ Árboles binarios auto-balanceados");
            
            MenuFormatter.mostrarOpcion(5, "Recursividad");
            System.out.println("     └─ Ejercicios y ejemplos recursivos");
            
            MenuFormatter.mostrarSeparador();
            MenuFormatter.mostrarOpcion(6, "Información del sistema");
            MenuFormatter.mostrarOpcion(0, "Salir");
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione un módulo: ", 0, 6);
            
            switch (opcion) {
                case 1 -> {
                    MenuFormatter.limpiarPantalla();
                    new PizzeriaMenu().mostrarMenu();
                }
                case 2 -> {
                    MenuFormatter.limpiarPantalla();
                    new TareasMenu().mostrarMenu();
                }
                case 3 -> {
                    MenuFormatter.limpiarPantalla();
                    new EstructurasLinealesMenu().mostrarMenu();
                }
                case 4 -> {
                    MenuFormatter.limpiarPantalla();
                    new ArbolAVLMenu().mostrarMenu();
                }
                case 5 -> {
                    MenuFormatter.limpiarPantalla();
                    new RecursividadMenu().mostrarMenu();
                }
                case 6 -> mostrarInformacionSistema();
                case 0 -> {
                    if (InputValidator.confirmar("¿Está seguro de que desea salir?")) {
                        return;
                    }
                }
            }
        }
    }
    
    private static void mostrarInformacionSistema() {
        MenuFormatter.mostrarTituloSecundario("INFORMACIÓN DEL SISTEMA");
        
        System.out.println("ESTADÍSTICAS DEL PROYECTO:");
        System.out.println("  • Nombre: Informatica3 Guías Prácticas");
        System.out.println("  • Versión: 1.0");
        System.out.println("  • Lenguaje: Java");
        System.out.println("  • Arquitectura: MVC (Modelo-Vista-Controlador)");
        System.out.println();
        
        System.out.println("ESTRUCTURA DEL PROYECTO:");
        System.out.println("  src/");
        System.out.println("  ├── core/                    # Implementaciones (Modelo)");
        System.out.println("  │   ├── pizzeria/            # Clases del gestor de pizzería");
        System.out.println("  │   ├── tareas/              # Clases del gestor de tareas");
        System.out.println("  │   ├── estructuras/         # Estructuras de datos");
        System.out.println("  │   │   ├── arboles/         # Árboles AVL");
        System.out.println("  │   │   ├── listas/          # Listas enlazadas");
        System.out.println("  │   │   ├── pilas/           # Pilas (arreglo y enlazada)");
        System.out.println("  │   │   ├── colas/           # Colas (circular y enlazada)");
        System.out.println("  │   │   ├── nodos/           # Nodos para estructuras enlazadas");
        System.out.println("  │   │   └── recursividad/    # Ejercicios recursivos");
        System.out.println("  │   └── utils/               # Utilidades y helpers");
        System.out.println("  ├── views/                   # Interfaces de usuario (Vista)");
        System.out.println("  └── Main.java               # Controlador principal");
        System.out.println();
        
        System.out.println("CONCEPTOS IMPLEMENTADOS:");
        System.out.println("  • Algoritmos de Ordenamiento (Inserción, Shell Sort, Quick Sort)");
        System.out.println("  • Estructuras Lineales (Listas, Pilas, Colas - Arreglo y Enlazadas)");
        System.out.println("  • Estructuras No Lineales (Árboles AVL)");
        System.out.println("  • Programación Orientada a Objetos");
        System.out.println("  • Manejo de Excepciones");
        System.out.println("  • Validación de Entrada de Datos");
        System.out.println("  • Separación de Responsabilidades (MVC)");
        System.out.println();
        
        System.out.println("CARACTERÍSTICAS TÉCNICAS:");
        System.out.println("  • Menús interactivos con validación");
        System.out.println("  • Manejo robusto de errores");
        System.out.println("  • Código modular y reutilizable");
        System.out.println("  • Documentación en código");
        System.out.println("  • Análisis de rendimiento de algoritmos");
        
        InputValidator.pausar();
    }
}