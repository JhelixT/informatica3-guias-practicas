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
        // Inicializar soporte de colores ANSI
        AnsiColors.inicializar();
        
        mostrarBienvenida();
        menuPrincipal();
        MenuFormatter.mostrarDespedida();
    }
    
    private static void mostrarBienvenida() {
        MenuFormatter.limpiarPantalla();
        MenuFormatter.mostrarBanner("GUIAS PRACTICAS DE INFORMATICA 3");
        System.out.println(AnsiColors.blanco("  Bienvenido al sistema integrado de practicas"));
        System.out.println(AnsiColors.blanco("  Explora diferentes estructuras de datos y algoritmos"));
        System.out.println(AnsiColors.blanco("  Perfecto para aprender y practicar conceptos fundamentales"));
        System.out.println();
        System.out.println(AnsiColors.azulNegrita("  Modulos disponibles:"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Gestion de Pizzeria (Algoritmos de Ordenamiento)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Gestion de Tareas (Listas y Operaciones Basicas)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Estructuras Lineales (Listas, Pilas y Colas)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Arboles AVL (Estructuras Balanceadas)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Arboles BST (Binary Search Tree)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Arboles Rojinegro (Red-Black Tree)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Recursividad (Tecnicas de Programacion)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Ejercicios de Listas (10 Ejercicios Practicos)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Ejercicios de Arboles AVL (9 Ejercicios Avanzados)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Ejercicios de Arboles Rojinegro (10 Ejercicios RB-Tree)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Monticulo Binario (Binary Heap - Min/Max Heap)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Ejercicios de Monticulo Binario (10 Ejercicios Heap)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Tabla Hash (Hash Table con Encadenamiento)"));
        System.out.println(AnsiColors.naranja("     * ") + AnsiColors.blanco("Sistema Integrador (Gestion de Turnos Medicos)"));
        System.out.println();
        InputValidator.pausar();
    }
    
    private static void menuPrincipal() {
        while (true) {
            MenuFormatter.limpiarPantalla();
            MenuFormatter.mostrarTituloPrincipal("MENU PRINCIPAL");
            
            MenuFormatter.mostrarOpcion(1, "Gestion de Pizzeria");
            System.out.println(AnsiColors.gris("     |-- Algoritmos: Insercion, Shell Sort, Quick Sort"));
            
            MenuFormatter.mostrarOpcion(2, "Gestion de Tareas");
            System.out.println(AnsiColors.gris("     |-- CRUD basico con listas y filtros"));
            
            MenuFormatter.mostrarOpcion(3, "Estructuras Lineales");
            System.out.println(AnsiColors.gris("     |-- Listas, Pilas y Colas (Arreglos y Enlazadas)"));
            
            MenuFormatter.mostrarOpcion(4, "Arboles AVL");
            System.out.println(AnsiColors.gris("     |-- Arboles binarios auto-balanceados"));
            
            MenuFormatter.mostrarOpcion(5, "Arboles BST");
            System.out.println(AnsiColors.gris("     |-- Binary Search Tree con operaciones completas"));
            
            MenuFormatter.mostrarOpcion(6, "Arboles Rojinegro");
            System.out.println(AnsiColors.gris("     |-- Red-Black Tree auto-balanceado"));
            
            MenuFormatter.mostrarOpcion(7, "Recursividad");
            System.out.println(AnsiColors.gris("     |-- Ejercicios y ejemplos recursivos"));
            
            MenuFormatter.mostrarOpcion(8, "Ejercicios de Listas Enlazadas");
            System.out.println(AnsiColors.gris("     |-- 10 ejercicios practicos paso a paso"));
            
            MenuFormatter.mostrarOpcion(9, "Ejercicios de Arboles AVL");
            System.out.println(AnsiColors.gris("     |-- 9 ejercicios practicos de AVL"));
            
            MenuFormatter.mostrarOpcion(10, "Ejercicios de Arboles Rojinegro");
            System.out.println(AnsiColors.gris("     |-- 10 ejercicios practicos de RB-Tree"));
            
            MenuFormatter.mostrarOpcion(11, "Monticulo Binario (Binary Heap)");
            System.out.println(AnsiColors.gris("     |-- Min-Heap y Max-Heap con cola de prioridad"));
            
            MenuFormatter.mostrarOpcion(12, "Ejercicios de Monticulo Binario");
            System.out.println(AnsiColors.gris("     |-- 10 ejercicios practicos de heap"));
            
            MenuFormatter.mostrarOpcion(13, "Tabla Hash (Hash Table)");
            System.out.println(AnsiColors.gris("     |-- Encadenamiento con ListaEnlazada - O(1) ops"));
            
            MenuFormatter.mostrarOpcion(14, "Sistema Integrador de Gestion Medica");
            System.out.println(AnsiColors.gris("     |-- Integracion completa: Hash, AVL, Cola, Heap, Merge"));
            
            MenuFormatter.mostrarSeparador();
            MenuFormatter.mostrarOpcion(15, "Informacion del sistema");
            MenuFormatter.mostrarOpcion(0, "Salir");
            
            int opcion = InputValidator.leerEnteroEnRango("\n" + AnsiColors.azul("Seleccione un modulo: "), 0, 15);
            
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
                    ArbolBSTMenu.mostrarMenu();
                }
                case 6 -> {
                    MenuFormatter.limpiarPantalla();
                    ArbolRojinegroMenu.mostrarMenu();
                }
                case 7 -> {
                    MenuFormatter.limpiarPantalla();
                    new RecursividadMenu().mostrarMenu();
                }
                case 8 -> {
                    MenuFormatter.limpiarPantalla();
                    new EjerciciosListasMenu().mostrarMenu();
                }
                case 9 -> {
                    MenuFormatter.limpiarPantalla();
                    EjerciciosArbolesAVLMenu.mostrar();
                }
                case 10 -> {
                    MenuFormatter.limpiarPantalla();
                    EjerciciosRojinegroMenu.mostrar();
                }
                case 11 -> {
                    MenuFormatter.limpiarPantalla();
                    new MonticuloMenu().mostrarMenu();
                }
                case 12 -> {
                    MenuFormatter.limpiarPantalla();
                    EjerciciosMonticuloMenu.mostrar();
                }
                case 13 -> {
                    MenuFormatter.limpiarPantalla();
                    new TablaHashMenu().mostrarMenu();
                }
                case 14 -> {
                    MenuFormatter.limpiarPantalla();
                    new IntegradorMenu().mostrar();
                }
                case 15 -> mostrarInformacionSistema();
                case 0 -> {
                    if (InputValidator.confirmar("¿Está seguro de que desea salir?")) {
                        return;
                    }
                }
            }
        }
    }
    
    private static void mostrarInformacionSistema() {
        MenuFormatter.mostrarTituloSecundario("INFORMACION DEL SISTEMA");
        
        System.out.println(AnsiColors.naranjaNegrita("ESTADISTICAS DEL PROYECTO:"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Nombre: Informatica3 Guias Practicas"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Version: 1.0"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Lenguaje: Java"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Arquitectura: MVC (Modelo-Vista-Controlador)"));
        System.out.println();
        
        System.out.println(AnsiColors.naranjaNegrita("ESTRUCTURA DEL PROYECTO:"));
        System.out.println(AnsiColors.blanco("  src/"));
        System.out.println(AnsiColors.azul("  +-- ") + AnsiColors.blanco("core/                    # Implementaciones (Modelo)"));
        System.out.println(AnsiColors.azul("  |   +-- ") + AnsiColors.blanco("pizzeria/            # Clases del gestor de pizzeria"));
        System.out.println(AnsiColors.azul("  |   +-- ") + AnsiColors.blanco("tareas/              # Clases del gestor de tareas"));
        System.out.println(AnsiColors.azul("  |   +-- ") + AnsiColors.blanco("estructuras/         # Estructuras de datos"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("arboles/         # Arboles AVL, BST y Rojinegro"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("listas/          # Listas enlazadas"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("pilas/           # Pilas (arreglo y enlazada)"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("colas/           # Colas (circular y enlazada)"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("monticulo/       # Monticulo binario (heap)"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("hash/            # Tabla Hash con encadenamiento"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("nodos/           # Nodos para estructuras enlazadas"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("recursividad/    # Ejercicios recursivos"));
        System.out.println(AnsiColors.azul("  |   +-- ") + AnsiColors.blanco("integrador/          # Sistema integrador de turnos"));
        System.out.println(AnsiColors.azul("  |   +-- ") + AnsiColors.blanco("ejercicios/          # Ejercicios practicos"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("listas/          # Ejercicios de listas"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("arboles/         # Ejercicios de arboles AVL"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("rojinegro/       # Ejercicios de arboles RB"));
        System.out.println(AnsiColors.azul("  |   |   +-- ") + AnsiColors.blanco("monticulo/       # Ejercicios de monticulo binario"));
        System.out.println(AnsiColors.azul("  |   +-- ") + AnsiColors.blanco("utils/               # Utilidades y helpers"));
        System.out.println(AnsiColors.azul("  +-- ") + AnsiColors.blanco("views/                   # Interfaces de usuario (Vista)"));
        System.out.println(AnsiColors.azul("  +-- ") + AnsiColors.blanco("Main.java                # Controlador principal"));
        System.out.println();
        
        System.out.println(AnsiColors.naranjaNegrita("CONCEPTOS IMPLEMENTADOS:"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Algoritmos de Ordenamiento (Insercion, Shell Sort, Quick Sort)"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Estructuras Lineales (Listas, Pilas, Colas - Arreglo y Enlazadas)"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Estructuras No Lineales (Arboles AVL, BST, Rojinegro, Heap)"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Tablas Hash (Hash Table con encadenamiento y ListaEnlazada)"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Colas de Prioridad (Monticulo Binario)"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Sistema Integrador (Gestion de Turnos Medicos - Todas las estructuras)"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Programacion Orientada a Objetos"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Manejo de Excepciones"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Validacion de Entrada de Datos"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Separacion de Responsabilidades (MVC)"));
        System.out.println();
        
        System.out.println(AnsiColors.naranjaNegrita("CARACTERISTICAS TECNICAS:"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Menus interactivos con validacion"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Manejo robusto de errores"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Codigo modular y reutilizable"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Documentacion en codigo"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Analisis de rendimiento de algoritmos"));
        System.out.println(AnsiColors.azul("  * ") + AnsiColors.blanco("Soporte de colores ANSI en terminal"));
        
        InputValidator.pausar();
    }
}