package views;

import core.estructuras.pilas.PilaArreglo;
import core.estructuras.pilas.PilaEnlazada;
import core.estructuras.colas.ColaCircular;
import core.estructuras.colas.ColaEnlazada;
import core.estructuras.listas.ListaEnlazada;
import core.utils.*;

/**
 * Menú completo para Estructuras Lineales de Datos.
 * 
 * Agrupa todas las estructuras de datos lineales:
 * - Listas Enlazadas
 * - Pilas (con arreglo y enlazada)
 * - Colas (circular y enlazada)
 * 
 * @author JhelixT
 * @version 2.0
 */
public class EstructurasLinealesMenu {
    private ListaEnlazada lista;
    private PilaArreglo pilaArreglo;
    private PilaEnlazada pilaEnlazada;
    private ColaCircular colaCircular;
    private ColaEnlazada colaEnlazada;
    
    public EstructurasLinealesMenu() {
        this.lista = new ListaEnlazada();
        this.pilaArreglo = new PilaArreglo(5);
        this.pilaEnlazada = new PilaEnlazada();
        this.colaCircular = new ColaCircular(5);
        this.colaEnlazada = new ColaEnlazada();
    }
    
    public void mostrarMenu() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("ESTRUCTURAS LINEALES DE DATOS");
            MenuFormatter.mostrarOpcion(1, "Listas Enlazadas");
            MenuFormatter.mostrarOpcion(2, "Pilas");
            MenuFormatter.mostrarOpcion(3, "Colas");
            MenuFormatter.mostrarOpcion(4, "Reiniciar todas las estructuras");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 4);
            
            switch (opcion) {
                case 1 -> menuListas();
                case 2 -> menuPilas();
                case 3 -> menuColas();
                case 4 -> reiniciarTodasLasEstructuras();
                case 0 -> {
                    return;
                }
            }
        }
    }
    
    // ==================== MENÚ DE LISTAS ENLAZADAS ====================
    
    private void menuListas() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("LISTAS ENLAZADAS");
            System.out.printf("Estado actual: %s%n", lista);
            MenuFormatter.mostrarSeparador();
            MenuFormatter.mostrarOpcion(1, "Insertar al inicio");
            MenuFormatter.mostrarOpcion(2, "Insertar al final");
            MenuFormatter.mostrarOpcion(3, "Insertar en posición");
            MenuFormatter.mostrarOpcion(4, "Eliminar del inicio");
            MenuFormatter.mostrarOpcion(5, "Eliminar del final");
            MenuFormatter.mostrarOpcion(6, "Eliminar en posición");
            MenuFormatter.mostrarOpcion(7, "Buscar elemento");
            MenuFormatter.mostrarOpcion(8, "Obtener elemento en posición");
            MenuFormatter.mostrarOpcion(9, "Invertir lista");
            MenuFormatter.mostrarOpcion(10, "Limpiar lista");
            MenuFormatter.mostrarOpcion(11, "Llenar con datos de prueba");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 11);
            
            switch (opcion) {
                case 1 -> insertarListaInicio();
                case 2 -> insertarListaFinal();
                case 3 -> insertarListaPosicion();
                case 4 -> eliminarListaInicio();
                case 5 -> eliminarListaFinal();
                case 6 -> eliminarListaPosicion();
                case 7 -> buscarEnLista();
                case 8 -> obtenerEnPosicion();
                case 9 -> invertirLista();
                case 10 -> limpiarLista();
                case 11 -> llenarListaPrueba();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void insertarListaInicio() {
        int valor = InputValidator.leerEntero("Ingrese el valor a insertar: ");
        lista.insertarAlInicio(valor);
        MenuFormatter.mostrarMensajeExito("Elemento " + valor + " insertado al inicio");
    }
    
    private void insertarListaFinal() {
        int valor = InputValidator.leerEntero("Ingrese el valor a insertar: ");
        lista.insertarAlFinal(valor);
        MenuFormatter.mostrarMensajeExito("Elemento " + valor + " insertado al final");
    }
    
    private void insertarListaPosicion() {
        if (lista.estaVacia()) {
            MenuFormatter.mostrarMensajeAdvertencia("La lista está vacía. Use insertar al inicio o al final.");
            return;
        }
        int valor = InputValidator.leerEntero("Ingrese el valor a insertar: ");
        int posicion = InputValidator.leerEnteroEnRango("Ingrese la posición (0-" + lista.getTamanio() + "): ", 
                                                         0, lista.getTamanio());
        try {
            lista.insertarEnPosicion(valor, posicion);
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " insertado en posición " + posicion);
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void eliminarListaInicio() {
        try {
            int valor = lista.eliminarAlInicio();
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " eliminado del inicio");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void eliminarListaFinal() {
        try {
            int valor = lista.eliminarAlFinal();
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " eliminado del final");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void eliminarListaPosicion() {
        if (lista.estaVacia()) {
            MenuFormatter.mostrarMensajeAdvertencia("La lista está vacía");
            return;
        }
        int posicion = InputValidator.leerEnteroEnRango("Ingrese la posición a eliminar (0-" + 
                                                         (lista.getTamanio()-1) + "): ", 
                                                         0, lista.getTamanio()-1);
        try {
            int valor = lista.eliminarEnPosicion(posicion);
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " eliminado de posición " + posicion);
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void buscarEnLista() {
        int valor = InputValidator.leerEntero("Ingrese el valor a buscar: ");
        int posicion = lista.buscar(valor);
        if (posicion != -1) {
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " encontrado en posición " + posicion);
        } else {
            MenuFormatter.mostrarMensajeAdvertencia("Elemento " + valor + " no encontrado");
        }
    }
    
    private void obtenerEnPosicion() {
        if (lista.estaVacia()) {
            MenuFormatter.mostrarMensajeAdvertencia("La lista está vacía");
            return;
        }
        int posicion = InputValidator.leerEnteroEnRango("Ingrese la posición (0-" + 
                                                         (lista.getTamanio()-1) + "): ", 
                                                         0, lista.getTamanio()-1);
        try {
            int valor = lista.obtenerEnPosicion(posicion);
            MenuFormatter.mostrarMensajeInfo("Elemento en posición " + posicion + ": " + valor);
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void invertirLista() {
        lista.invertir();
        MenuFormatter.mostrarMensajeExito("Lista invertida correctamente");
    }
    
    private void limpiarLista() {
        if (InputValidator.confirmar("¿Está seguro de limpiar la lista?")) {
            lista.limpiar();
            MenuFormatter.mostrarMensajeExito("Lista limpiada");
        }
    }
    
    private void llenarListaPrueba() {
        int cantidad = InputValidator.leerEnteroEnRango("¿Cuántos elementos generar? ", 1, 20);
        for (int i = 1; i <= cantidad; i++) {
            lista.insertarAlFinal((int)(Math.random() * 100) + 1);
        }
        MenuFormatter.mostrarMensajeExito("Se agregaron " + cantidad + " elementos a la lista");
    }
    
    // ==================== MENÚ DE PILAS ====================
    
    private void menuPilas() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("GESTIÓN DE PILAS");
            MenuFormatter.mostrarOpcion(1, "Pila con Arreglo");
            MenuFormatter.mostrarOpcion(2, "Pila Enlazada");
            MenuFormatter.mostrarOpcion(3, "Comparar implementaciones");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 3);
            
            switch (opcion) {
                case 1 -> menuPilaArreglo();
                case 2 -> menuPilaEnlazada();
                case 3 -> compararPilas();
                case 0 -> {
                    return;
                }
            }
        }
    }
    
    private void menuPilaArreglo() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("PILA CON ARREGLO");
            System.out.printf("Estado actual: %s%n", pilaArreglo);
            MenuFormatter.mostrarSeparador();
            MenuFormatter.mostrarOpcion(1, "Push (Apilar)");
            MenuFormatter.mostrarOpcion(2, "Pop (Desapilar)");
            MenuFormatter.mostrarOpcion(3, "Top (Ver tope)");
            MenuFormatter.mostrarOpcion(4, "Ver información");
            MenuFormatter.mostrarOpcion(5, "Llenar con datos de prueba");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 5);
            
            switch (opcion) {
                case 1 -> pushPilaArreglo();
                case 2 -> popPilaArreglo();
                case 3 -> topPilaArreglo();
                case 4 -> infoPilaArreglo();
                case 5 -> llenarPilaArregloPrueba();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void menuPilaEnlazada() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("PILA ENLAZADA");
            System.out.printf("Estado actual: %s%n", pilaEnlazada);
            MenuFormatter.mostrarSeparador();
            MenuFormatter.mostrarOpcion(1, "Push (Apilar)");
            MenuFormatter.mostrarOpcion(2, "Pop (Desapilar)");
            MenuFormatter.mostrarOpcion(3, "Top (Ver tope)");
            MenuFormatter.mostrarOpcion(4, "Buscar elemento");
            MenuFormatter.mostrarOpcion(5, "Llenar con datos de prueba");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 5);
            
            switch (opcion) {
                case 1 -> pushPilaEnlazada();
                case 2 -> popPilaEnlazada();
                case 3 -> topPilaEnlazada();
                case 4 -> buscarPilaEnlazada();
                case 5 -> llenarPilaEnlazadaPrueba();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void pushPilaArreglo() {
        try {
            int valor = InputValidator.leerEntero("Ingrese el valor a apilar: ");
            pilaArreglo.push(valor);
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " apilado");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void popPilaArreglo() {
        try {
            int valor = pilaArreglo.pop();
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " desapilado");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void topPilaArreglo() {
        try {
            int valor = pilaArreglo.top();
            MenuFormatter.mostrarMensajeInfo("Elemento en el tope: " + valor);
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void infoPilaArreglo() {
        System.out.println("\nINFORMACIÓN DE LA PILA CON ARREGLO:");
        System.out.printf("  Tamaño actual: %d elementos%n", pilaArreglo.getSize());
        System.out.printf("  Capacidad: %d elementos%n", pilaArreglo.getCapacity());
        System.out.printf("  ¿Está vacía?: %s%n", pilaArreglo.isEmpty() ? "Sí" : "No");
        System.out.printf("  ¿Está llena?: %s%n", pilaArreglo.isFull() ? "Sí" : "No");
        System.out.printf("  Estado: %s%n", pilaArreglo);
    }
    
    private void llenarPilaArregloPrueba() {
        int cantidad = InputValidator.leerEnteroEnRango("¿Cuántos elementos generar? ", 1, 20);
        for (int i = 1; i <= cantidad; i++) {
            pilaArreglo.push((int)(Math.random() * 100) + 1);
        }
        MenuFormatter.mostrarMensajeExito("Se agregaron " + cantidad + " elementos a la pila");
    }
    
    private void pushPilaEnlazada() {
        int valor = InputValidator.leerEntero("Ingrese el valor a apilar: ");
        pilaEnlazada.push(valor);
        MenuFormatter.mostrarMensajeExito("Elemento " + valor + " apilado");
    }
    
    private void popPilaEnlazada() {
        try {
            int valor = pilaEnlazada.pop();
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " desapilado");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void topPilaEnlazada() {
        try {
            int valor = pilaEnlazada.top();
            MenuFormatter.mostrarMensajeInfo("Elemento en el tope: " + valor);
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void buscarPilaEnlazada() {
        int valor = InputValidator.leerEntero("Ingrese el valor a buscar: ");
        int posicion = pilaEnlazada.buscar(valor);
        if (posicion != -1) {
            MenuFormatter.mostrarMensajeExito("Elemento encontrado a " + posicion + " posiciones desde el tope");
        } else {
            MenuFormatter.mostrarMensajeAdvertencia("Elemento no encontrado");
        }
    }
    
    private void llenarPilaEnlazadaPrueba() {
        int cantidad = InputValidator.leerEnteroEnRango("¿Cuántos elementos generar? ", 1, 20);
        for (int i = 1; i <= cantidad; i++) {
            pilaEnlazada.push((int)(Math.random() * 100) + 1);
        }
        MenuFormatter.mostrarMensajeExito("Se agregaron " + cantidad + " elementos a la pila");
    }
    
    private void compararPilas() {
        MenuFormatter.mostrarTituloSecundario("COMPARACIÓN DE IMPLEMENTACIONES DE PILAS");
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║            PILA CON ARREGLO vs PILA ENLAZADA                 ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Característica    │  Pila Arreglo    │  Pila Enlazada       ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Capacidad         │  Fija/Dinámica   │  Ilimitada           ║");
        System.out.println("║ Push              │  O(1)            │  O(1)                ║");
        System.out.println("║ Pop               │  O(1)            │  O(1)                ║");
        System.out.println("║ Memoria           │  Más eficiente   │  Overhead por nodos  ║");
        System.out.println("║ Redimensión       │  Costosa O(n)    │  No necesaria        ║");
        System.out.println("║ Implementación    │  Más simple      │  Requiere Nodo       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\nESTADO ACTUAL:");
        System.out.printf("  Pila Arreglo:  %s%n", pilaArreglo);
        System.out.printf("  Pila Enlazada: %s%n", pilaEnlazada);
        
        InputValidator.pausar();
    }
    
    // ==================== MENÚ DE COLAS ====================
    
    private void menuColas() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("GESTIÓN DE COLAS");
            MenuFormatter.mostrarOpcion(1, "Cola Circular (Arreglo)");
            MenuFormatter.mostrarOpcion(2, "Cola Enlazada");
            MenuFormatter.mostrarOpcion(3, "Comparar implementaciones");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 3);
            
            switch (opcion) {
                case 1 -> menuColaCircular();
                case 2 -> menuColaEnlazada();
                case 3 -> compararColas();
                case 0 -> {
                    return;
                }
            }
        }
    }
    
    private void menuColaCircular() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("COLA CIRCULAR (ARREGLO)");
            System.out.printf("Estado actual: %s%n", colaCircular);
            MenuFormatter.mostrarSeparador();
            MenuFormatter.mostrarOpcion(1, "Enqueue (Encolar)");
            MenuFormatter.mostrarOpcion(2, "Dequeue (Desencolar)");
            MenuFormatter.mostrarOpcion(3, "Front (Ver frente)");
            MenuFormatter.mostrarOpcion(4, "Ver información");
            MenuFormatter.mostrarOpcion(5, "Llenar con datos de prueba");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 5);
            
            switch (opcion) {
                case 1 -> enqueueColaCircular();
                case 2 -> dequeueColaCircular();
                case 3 -> frontColaCircular();
                case 4 -> infoColaCircular();
                case 5 -> llenarColaCircularPrueba();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void menuColaEnlazada() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("COLA ENLAZADA");
            System.out.printf("Estado actual: %s%n", colaEnlazada);
            MenuFormatter.mostrarSeparador();
            MenuFormatter.mostrarOpcion(1, "Enqueue (Encolar)");
            MenuFormatter.mostrarOpcion(2, "Dequeue (Desencolar)");
            MenuFormatter.mostrarOpcion(3, "Front (Ver frente)");
            MenuFormatter.mostrarOpcion(4, "Back (Ver final)");
            MenuFormatter.mostrarOpcion(5, "Buscar elemento");
            MenuFormatter.mostrarOpcion(6, "Llenar con datos de prueba");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione una opción: ", 0, 6);
            
            switch (opcion) {
                case 1 -> enqueueColaEnlazada();
                case 2 -> dequeueColaEnlazada();
                case 3 -> frontColaEnlazada();
                case 4 -> backColaEnlazada();
                case 5 -> buscarColaEnlazada();
                case 6 -> llenarColaEnlazadaPrueba();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void enqueueColaCircular() {
        int valor = InputValidator.leerEntero("Ingrese el valor a encolar: ");
        colaCircular.enqueue(valor);
        MenuFormatter.mostrarMensajeExito("Elemento " + valor + " encolado");
    }
    
    private void dequeueColaCircular() {
        try {
            int valor = colaCircular.dequeue();
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " desencolado");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void frontColaCircular() {
        try {
            int valor = colaCircular.front();
            MenuFormatter.mostrarMensajeInfo("Elemento al frente: " + valor);
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void infoColaCircular() {
        System.out.println("\nINFORMACIÓN DE LA COLA CIRCULAR:");
        System.out.printf("  Tamaño actual: %d elementos%n", colaCircular.getSize());
        System.out.printf("  Capacidad: %d elementos%n", colaCircular.getCapacity());
        System.out.printf("  ¿Está vacía?: %s%n", colaCircular.isEmpty() ? "Sí" : "No");
        System.out.printf("  ¿Está llena?: %s%n", colaCircular.isFull() ? "Sí" : "No");
        System.out.printf("  Estado: %s%n", colaCircular);
    }
    
    private void llenarColaCircularPrueba() {
        int cantidad = InputValidator.leerEnteroEnRango("¿Cuántos elementos generar? ", 1, 20);
        for (int i = 1; i <= cantidad; i++) {
            colaCircular.enqueue((int)(Math.random() * 100) + 1);
        }
        MenuFormatter.mostrarMensajeExito("Se agregaron " + cantidad + " elementos a la cola");
    }
    
    private void enqueueColaEnlazada() {
        int valor = InputValidator.leerEntero("Ingrese el valor a encolar: ");
        colaEnlazada.enqueue(valor);
        MenuFormatter.mostrarMensajeExito("Elemento " + valor + " encolado");
    }
    
    private void dequeueColaEnlazada() {
        try {
            int valor = colaEnlazada.dequeue();
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " desencolado");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void frontColaEnlazada() {
        try {
            int valor = colaEnlazada.front();
            MenuFormatter.mostrarMensajeInfo("Elemento al frente: " + valor);
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void backColaEnlazada() {
        try {
            int valor = colaEnlazada.back();
            MenuFormatter.mostrarMensajeInfo("Elemento al final: " + valor);
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void buscarColaEnlazada() {
        int valor = InputValidator.leerEntero("Ingrese el valor a buscar: ");
        int posicion = colaEnlazada.buscar(valor);
        if (posicion != -1) {
            MenuFormatter.mostrarMensajeExito("Elemento encontrado a " + posicion + " posiciones desde el frente");
        } else {
            MenuFormatter.mostrarMensajeAdvertencia("Elemento no encontrado");
        }
    }
    
    private void llenarColaEnlazadaPrueba() {
        int cantidad = InputValidator.leerEnteroEnRango("¿Cuántos elementos generar? ", 1, 20);
        for (int i = 1; i <= cantidad; i++) {
            colaEnlazada.enqueue((int)(Math.random() * 100) + 1);
        }
        MenuFormatter.mostrarMensajeExito("Se agregaron " + cantidad + " elementos a la cola");
    }
    
    private void compararColas() {
        MenuFormatter.mostrarTituloSecundario("COMPARACIÓN DE IMPLEMENTACIONES DE COLAS");
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║           COLA CIRCULAR vs COLA ENLAZADA                      ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Característica    │  Cola Circular   │  Cola Enlazada       ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Capacidad         │  Fija/Dinámica   │  Ilimitada           ║");
        System.out.println("║ Enqueue           │  O(1)            │  O(1)                ║");
        System.out.println("║ Dequeue           │  O(1)            │  O(1)                ║");
        System.out.println("║ Memoria           │  Más eficiente   │  Overhead por nodos  ║");
        System.out.println("║ Circularidad      │  Sí (módulo)     │  No necesaria        ║");
        System.out.println("║ Redimensión       │  Costosa O(n)    │  No necesaria        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\nESTADO ACTUAL:");
        System.out.printf("  Cola Circular: %s%n", colaCircular);
        System.out.printf("  Cola Enlazada: %s%n", colaEnlazada);
        
        InputValidator.pausar();
    }
    
    // ==================== REINICIAR ESTRUCTURAS ====================
    
    private void reiniciarTodasLasEstructuras() {
        if (InputValidator.confirmar("¿Desea reiniciar TODAS las estructuras de datos?")) {
            lista = new ListaEnlazada();
            
            int capacidadPila = InputValidator.leerEnteroEnRango(
                "Nueva capacidad inicial de la pila con arreglo: ", 1, 50);
            pilaArreglo = new PilaArreglo(capacidadPila);
            pilaEnlazada = new PilaEnlazada();
            
            int capacidadCola = InputValidator.leerEnteroEnRango(
                "Nueva capacidad inicial de la cola circular: ", 1, 50);
            colaCircular = new ColaCircular(capacidadCola);
            colaEnlazada = new ColaEnlazada();
            
            MenuFormatter.mostrarMensajeExito("Todas las estructuras han sido reiniciadas");
        }
    }
}
