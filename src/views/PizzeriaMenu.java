package views;

import core.pizzeria.*;
import core.utils.*;
import java.util.List;

public class PizzeriaMenu {
    private Pizzeria pizzeria;
    
    public PizzeriaMenu() {
        this.pizzeria = new Pizzeria();
    }
    
    public void mostrarMenu() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("GESTIÓN DE PIZZERÍA");
            MenuFormatter.mostrarOpcion(1, "📝 Agregar pedido");
            MenuFormatter.mostrarOpcion(2, "📋 Listar pedidos");
            MenuFormatter.mostrarOpcion(3, "✏️  Editar pedido");
            MenuFormatter.mostrarOpcion(4, "🗑️  Eliminar pedido");
            MenuFormatter.mostrarOpcion(5, "🔄 Ordenar pedidos");
            MenuFormatter.mostrarOpcion(6, "⏱️  Analizar rendimiento algoritmos");
            MenuFormatter.mostrarOpcion(7, "🧪 Generar datos de prueba");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\n👉 Seleccione una opción: ", 0, 7);
            
            switch (opcion) {
                case 1 -> agregarPedido();
                case 2 -> listarPedidos();
                case 3 -> editarPedido();
                case 4 -> eliminarPedido();
                case 5 -> menuOrdenamiento();
                case 6 -> analizarRendimiento();
                case 7 -> generarDatosPrueba();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void agregarPedido() {
        MenuFormatter.mostrarTituloSecundario("AGREGAR PEDIDO");
        
        try {
            String cliente = InputValidator.leerCadenaNoVacia("👤 Nombre del cliente: ");
            double tiempo = InputValidator.leerDoublePositivo("⏱️  Tiempo de preparación (minutos): ");
            double precio = InputValidator.leerDoublePositivo("💰 Precio total: $");
            
            pizzeria.agregarPedido(tiempo, precio, cliente);
            MenuFormatter.mostrarMensajeExito("Pedido agregado correctamente");
            
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error al agregar pedido: " + e.getMessage());
        }
    }
    
    private void listarPedidos() {
        MenuFormatter.mostrarTituloSecundario("LISTA DE PEDIDOS");
        
        if (!pizzeria.tienePedidos()) {
            MenuFormatter.mostrarMensajeInfo("No hay pedidos registrados");
            return;
        }
        
        List<Pedido> pedidos = pizzeria.getPedidos();
        System.out.println();
        for (int i = 0; i < pedidos.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, pedidos.get(i));
        }
        System.out.printf("%nTotal de pedidos: %d%n", pizzeria.getCantidadPedidos());
    }
    
    private void editarPedido() {
        if (!pizzeria.tienePedidos()) {
            MenuFormatter.mostrarMensajeInfo("No hay pedidos para editar");
            return;
        }
        
        listarPedidos();
        
        int indice = InputValidator.leerEnteroEnRango("👉 Seleccione el pedido a editar: ", 1, pizzeria.getCantidadPedidos()) - 1;
        
        MenuFormatter.mostrarTituloSecundario("¿QUÉ DESEA EDITAR?");
        MenuFormatter.mostrarOpcion(1, "⏱️  Tiempo de preparación");
        MenuFormatter.mostrarOpcion(2, "💰 Precio total");
        
        int opcion = InputValidator.leerEnteroEnRango("👉 Seleccione: ", 1, 2);
        
        try {
            if (opcion == 1) {
                double nuevoTiempo = InputValidator.leerDoublePositivo("⏱️  Nuevo tiempo (minutos): ");
                pizzeria.editarTiempoPedido(indice, nuevoTiempo);
            } else {
                double nuevoPrecio = InputValidator.leerDoublePositivo("💰 Nuevo precio: $");
                pizzeria.editarPrecioPedido(indice, nuevoPrecio);
            }
            MenuFormatter.mostrarMensajeExito("Pedido editado correctamente");
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error al editar: " + e.getMessage());
        }
    }
    
    private void eliminarPedido() {
        if (!pizzeria.tienePedidos()) {
            MenuFormatter.mostrarMensajeInfo("No hay pedidos para eliminar");
            return;
        }
        
        listarPedidos();
        
        int indice = InputValidator.leerEnteroEnRango("👉 Seleccione el pedido a eliminar: ", 1, pizzeria.getCantidadPedidos()) - 1;
        
        Pedido pedido = pizzeria.getPedido(indice);
        if (InputValidator.confirmar("¿Está seguro de eliminar el pedido de " + pedido.getNombreCliente() + "?")) {
            try {
                pizzeria.eliminarPedido(indice);
                MenuFormatter.mostrarMensajeExito("Pedido eliminado correctamente");
            } catch (Exception e) {
                MenuFormatter.mostrarMensajeError("Error al eliminar: " + e.getMessage());
            }
        }
    }
    
    private void menuOrdenamiento() {
        if (!pizzeria.tienePedidos()) {
            MenuFormatter.mostrarMensajeInfo("No hay pedidos para ordenar");
            return;
        }
        
        MenuFormatter.mostrarTituloSecundario("ORDENAMIENTO DE PEDIDOS");
        MenuFormatter.mostrarOpcion(1, "⏱️  Por tiempo de preparación (Inserción)");
        MenuFormatter.mostrarOpcion(2, "💰 Por precio (Shell Sort)");
        MenuFormatter.mostrarOpcion(3, "👤 Por nombre cliente (Quick Sort)");
        
        int opcion = InputValidator.leerEnteroEnRango("👉 Seleccione algoritmo: ", 1, 3);
        
        List<Pedido> pedidos = pizzeria.getPedidos();
        
        switch (opcion) {
            case 1 -> {
                Ordenador.ordenarPorTiempoPreparacion(pedidos);
                MenuFormatter.mostrarMensajeExito("Pedidos ordenados por tiempo (Inserción)");
            }
            case 2 -> {
                Ordenador.ordenarPorPrecio(pedidos);
                MenuFormatter.mostrarMensajeExito("Pedidos ordenados por precio (Shell Sort)");
            }
            case 3 -> {
                Ordenador.ordenarPorNombreCliente(pedidos);
                MenuFormatter.mostrarMensajeExito("Pedidos ordenados por cliente (Quick Sort)");
            }
        }
        
        // Mostrar resultado
        System.out.println("\n📋 PEDIDOS ORDENADOS:");
        for (int i = 0; i < pedidos.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, pedidos.get(i));
        }
    }
    
    private void analizarRendimiento() {
        MenuFormatter.mostrarTituloSecundario("ANÁLISIS DE RENDIMIENTO");
        MenuFormatter.mostrarMensajeInfo("Ejecutando pruebas de rendimiento...");
        TiempoOrdenamiento.probarAlgoritmos();
        
        if (pizzeria.tienePedidos()) {
            System.out.println();
            MenuFormatter.mostrarMensajeInfo("Analizando pedidos actuales...");
            TiempoOrdenamiento.compararAlgoritmos(pizzeria.getPedidos());
        }
    }
    
    private void generarDatosPrueba() {
        int cantidad = InputValidator.leerEnteroEnRango("📊 ¿Cuántos pedidos generar? ", 1, 1000);
        
        for (int i = 0; i < cantidad; i++) {
            pizzeria.agregarPedido(
                Math.random() * 60,           // 0-60 minutos
                Math.random() * 2000 + 100,   // $100-$2100
                "Cliente" + (int)(Math.random() * 1000)
            );
        }
        
        MenuFormatter.mostrarMensajeExito("Se generaron " + cantidad + " pedidos de prueba");
    }
}