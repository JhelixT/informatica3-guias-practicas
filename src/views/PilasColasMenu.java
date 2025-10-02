package views;

import core.estructuras.pilas.PilaArreglo;
import core.estructuras.colas.ColaArreglo;
import core.utils.*;

public class PilasColasMenu {
    private PilaArreglo pila;
    private ColaArreglo cola;
    
    public PilasColasMenu() {
        // Inicializar con capacidades por defecto
        this.pila = new PilaArreglo(5);
        this.cola = new ColaArreglo(5);
    }
    
    public void mostrarMenu() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("PILAS Y COLAS");
            MenuFormatter.mostrarOpcion(1, "📚 Gestión de Pilas");
            MenuFormatter.mostrarOpcion(2, "🎯 Gestión de Colas");
            MenuFormatter.mostrarOpcion(3, "🔄 Reiniciar estructuras");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\n👉 Seleccione una opción: ", 0, 3);
            
            switch (opcion) {
                case 1 -> menuPilas();
                case 2 -> menuColas();
                case 3 -> reiniciarEstructuras();
                case 0 -> {
                    return;
                }
            }
        }
    }
    
    private void menuPilas() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("GESTIÓN DE PILAS");
            System.out.printf("Estado actual: %s%n", pila);
            MenuFormatter.mostrarSeparador();
            MenuFormatter.mostrarOpcion(1, "⬆️  Push (Apilar elemento)");
            MenuFormatter.mostrarOpcion(2, "⬇️  Pop (Desapilar elemento)");
            MenuFormatter.mostrarOpcion(3, "👁️  Top (Ver elemento superior)");
            MenuFormatter.mostrarOpcion(4, "📊 Ver información");
            MenuFormatter.mostrarOpcion(5, "🧪 Llenar con datos de prueba");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\n👉 Seleccione una opción: ", 0, 5);
            
            switch (opcion) {
                case 1 -> pushPila();
                case 2 -> popPila();
                case 3 -> topPila();
                case 4 -> infoPila();
                case 5 -> llenarPilaPrueba();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void menuColas() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("GESTIÓN DE COLAS");
            System.out.printf("Estado actual: %s%n", cola);
            MenuFormatter.mostrarSeparador();
            MenuFormatter.mostrarOpcion(1, "➡️  Enqueue (Encolar elemento)");
            MenuFormatter.mostrarOpcion(2, "⬅️  Dequeue (Desencolar elemento)");
            MenuFormatter.mostrarOpcion(3, "👁️  Front (Ver primer elemento)");
            MenuFormatter.mostrarOpcion(4, "📊 Ver información");
            MenuFormatter.mostrarOpcion(5, "🧪 Llenar con datos de prueba");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\n👉 Seleccione una opción: ", 0, 5);
            
            switch (opcion) {
                case 1 -> enqueueCola();
                case 2 -> dequeueCola();
                case 3 -> frontCola();
                case 4 -> infoCola();
                case 5 -> llenarColaPrueba();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void pushPila() {
        try {
            int valor = InputValidator.leerEntero("📥 Ingrese el valor a apilar: ");
            pila.push(valor);
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " apilado correctamente");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void popPila() {
        try {
            if (pila.isEmpty()) {
                MenuFormatter.mostrarMensajeAdvertencia("La pila está vacía");
                return;
            }
            int valor = pila.pop();
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " desapilado correctamente");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void topPila() {
        try {
            if (pila.isEmpty()) {
                MenuFormatter.mostrarMensajeAdvertencia("La pila está vacía");
                return;
            }
            int valor = pila.top();
            MenuFormatter.mostrarMensajeInfo("Elemento superior: " + valor);
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void infoPila() {
        System.out.println("\n📊 INFORMACIÓN DE LA PILA:");
        System.out.printf("  Tamaño actual: %d elementos%n", pila.getSize());
        System.out.printf("  Capacidad: %d elementos%n", pila.getCapacity());
        System.out.printf("  ¿Está vacía?: %s%n", pila.isEmpty() ? "Sí" : "No");
        System.out.printf("  ¿Está llena?: %s%n", pila.isFull() ? "Sí" : "No");
        System.out.printf("  Estado: %s%n", pila);
    }
    
    private void llenarPilaPrueba() {
        int cantidad = InputValidator.leerEnteroEnRango("¿Cuántos elementos generar? ", 1, 20);
        
        for (int i = 1; i <= cantidad; i++) {
            pila.push((int)(Math.random() * 100) + 1);
        }
        MenuFormatter.mostrarMensajeExito("Se agregaron " + cantidad + " elementos a la pila");
    }
    
    private void enqueueCola() {
        try {
            int valor = InputValidator.leerEntero("📥 Ingrese el valor a encolar: ");
            cola.enqueue(valor);
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " encolado correctamente");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void dequeueCola() {
        try {
            if (cola.isEmpty()) {
                MenuFormatter.mostrarMensajeAdvertencia("La cola está vacía");
                return;
            }
            int valor = cola.dequeue();
            MenuFormatter.mostrarMensajeExito("Elemento " + valor + " desencolado correctamente");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void frontCola() {
        try {
            if (cola.isEmpty()) {
                MenuFormatter.mostrarMensajeAdvertencia("La cola está vacía");
                return;
            }
            int valor = cola.front();
            MenuFormatter.mostrarMensajeInfo("Primer elemento: " + valor);
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error: " + e.getMessage());
        }
    }
    
    private void infoCola() {
        System.out.println("\n📊 INFORMACIÓN DE LA COLA:");
        System.out.printf("  Tamaño actual: %d elementos%n", cola.getSize());
        System.out.printf("  Capacidad: %d elementos%n", cola.getCapacity());
        System.out.printf("  ¿Está vacía?: %s%n", cola.isEmpty() ? "Sí" : "No");
        System.out.printf("  ¿Está llena?: %s%n", cola.isFull() ? "Sí" : "No");
        System.out.printf("  Estado: %s%n", cola);
    }
    
    private void llenarColaPrueba() {
        int cantidad = InputValidator.leerEnteroEnRango("¿Cuántos elementos generar? ", 1, 20);
        
        for (int i = 1; i <= cantidad; i++) {
            cola.enqueue((int)(Math.random() * 100) + 1);
        }
        MenuFormatter.mostrarMensajeExito("Se agregaron " + cantidad + " elementos a la cola");
    }
    
    private void reiniciarEstructuras() {
        if (InputValidator.confirmar("¿Desea reiniciar todas las estructuras?")) {
            int capacidadPila = InputValidator.leerEnteroEnRango("Nueva capacidad inicial de la pila: ", 1, 50);
            int capacidadCola = InputValidator.leerEnteroEnRango("Nueva capacidad inicial de la cola: ", 1, 50);
            
            pila = new PilaArreglo(capacidadPila);
            cola = new ColaArreglo(capacidadCola);
            
            MenuFormatter.mostrarMensajeExito("Estructuras reiniciadas correctamente");
        }
    }
}