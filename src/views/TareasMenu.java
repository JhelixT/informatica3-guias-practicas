package views;

import core.tareas.*;
import core.utils.*;
import java.util.List;

public class TareasMenu {
    private GestorTareas gestor;
    
    public TareasMenu() {
        this.gestor = new GestorTareas();
    }
    
    public void mostrarMenu() {
        while (true) {
            MenuFormatter.mostrarTituloSecundario("GESTIÓN DE TAREAS");
            MenuFormatter.mostrarOpcion(1, "📝 Agregar tarea");
            MenuFormatter.mostrarOpcion(2, "📋 Listar todas las tareas");
            MenuFormatter.mostrarOpcion(3, "✅ Listar tareas pendientes");
            MenuFormatter.mostrarOpcion(4, "✔️  Listar tareas completadas");
            MenuFormatter.mostrarOpcion(5, "🔄 Cambiar estado de tarea");
            MenuFormatter.mostrarOpcion(6, "🗑️  Eliminar tarea");
            MenuFormatter.mostrarOpcion(7, "🧹 Eliminar tareas completadas");
            MenuFormatter.mostrarOpcion(8, "📊 Ver estadísticas");
            MenuFormatter.mostrarOpcionSalir(0);
            
            int opcion = InputValidator.leerEnteroEnRango("\n👉 Seleccione una opción: ", 0, 8);
            
            switch (opcion) {
                case 1 -> agregarTarea();
                case 2 -> listarTodasLasTareas();
                case 3 -> listarTareasPendientes();
                case 4 -> listarTareasCompletadas();
                case 5 -> cambiarEstadoTarea();
                case 6 -> eliminarTarea();
                case 7 -> eliminarTareasCompletadas();
                case 8 -> mostrarEstadisticas();
                case 0 -> {
                    return;
                }
            }
            InputValidator.pausar();
        }
    }
    
    private void agregarTarea() {
        MenuFormatter.mostrarTituloSecundario("AGREGAR TAREA");
        
        try {
            String descripcion = InputValidator.leerCadenaNoVacia("📝 Descripción de la tarea: ");
            
            MenuFormatter.mostrarTituloSecundario("ESTADO INICIAL");
            MenuFormatter.mostrarOpcion(1, "⏳ Pendiente");
            MenuFormatter.mostrarOpcion(2, "✅ Completada");
            
            int opcionEstado = InputValidator.leerEnteroEnRango("👉 Seleccione estado: ", 1, 2);
            String estado = (opcionEstado == 1) ? "pendiente" : "completada";
            
            gestor.agregarTarea(descripcion, estado);
            MenuFormatter.mostrarMensajeExito("Tarea agregada correctamente");
            
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("Error al agregar tarea: " + e.getMessage());
        }
    }
    
    private void listarTodasLasTareas() {
        MenuFormatter.mostrarTituloSecundario("TODAS LAS TAREAS");
        
        if (!gestor.tieneTareas()) {
            MenuFormatter.mostrarMensajeInfo("No hay tareas registradas");
            return;
        }
        
        List<Tarea> tareas = gestor.getTareas();
        System.out.println();
        for (int i = 0; i < tareas.size(); i++) {
            String icono = tareas.get(i).getEstado().equalsIgnoreCase("completada") ? "✅" : "⏳";
            System.out.printf("  %d. %s %s%n", i + 1, icono, tareas.get(i));
        }
        mostrarResumenTareas();
    }
    
    private void listarTareasPendientes() {
        MenuFormatter.mostrarTituloSecundario("TAREAS PENDIENTES");
        
        List<Tarea> pendientes = gestor.getTareasPendientes();
        if (pendientes.isEmpty()) {
            MenuFormatter.mostrarMensajeInfo("No hay tareas pendientes");
            return;
        }
        
        System.out.println();
        for (int i = 0; i < pendientes.size(); i++) {
            System.out.printf("  %d. ⏳ %s%n", i + 1, pendientes.get(i));
        }
        System.out.printf("%nTotal tareas pendientes: %d%n", pendientes.size());
    }
    
    private void listarTareasCompletadas() {
        MenuFormatter.mostrarTituloSecundario("TAREAS COMPLETADAS");
        
        List<Tarea> completadas = gestor.getTareasCompletadas();
        if (completadas.isEmpty()) {
            MenuFormatter.mostrarMensajeInfo("No hay tareas completadas");
            return;
        }
        
        System.out.println();
        for (int i = 0; i < completadas.size(); i++) {
            System.out.printf("  %d. ✅ %s%n", i + 1, completadas.get(i));
        }
        System.out.printf("%nTotal tareas completadas: %d%n", completadas.size());
    }
    
    private void cambiarEstadoTarea() {
        if (!gestor.tieneTareas()) {
            MenuFormatter.mostrarMensajeInfo("No hay tareas para modificar");
            return;
        }
        
        listarTodasLasTareas();
        
        int indice = InputValidator.leerEnteroEnRango("👉 Seleccione la tarea: ", 1, gestor.getCantidadTareas()) - 1;
        Tarea tarea = gestor.getTarea(indice);
        
        MenuFormatter.mostrarTituloSecundario("CAMBIAR ESTADO");
        System.out.printf("Tarea actual: %s%n", tarea);
        
        if (tarea.getEstado().equalsIgnoreCase("pendiente")) {
            if (InputValidator.confirmar("¿Marcar como completada?")) {
                if (gestor.marcarComoCompletada(indice)) {
                    MenuFormatter.mostrarMensajeExito("Tarea marcada como completada");
                } else {
                    MenuFormatter.mostrarMensajeError("No se pudo cambiar el estado");
                }
            }
        } else {
            if (InputValidator.confirmar("¿Marcar como pendiente?")) {
                if (gestor.marcarComoPendiente(indice)) {
                    MenuFormatter.mostrarMensajeExito("Tarea marcada como pendiente");
                } else {
                    MenuFormatter.mostrarMensajeError("No se pudo cambiar el estado");
                }
            }
        }
    }
    
    private void eliminarTarea() {
        if (!gestor.tieneTareas()) {
            MenuFormatter.mostrarMensajeInfo("No hay tareas para eliminar");
            return;
        }
        
        listarTodasLasTareas();
        
        int indice = InputValidator.leerEnteroEnRango("👉 Seleccione la tarea a eliminar: ", 1, gestor.getCantidadTareas()) - 1;
        Tarea tarea = gestor.getTarea(indice);
        
        if (InputValidator.confirmar("¿Está seguro de eliminar la tarea '" + tarea.getDescripcion() + "'?")) {
            if (gestor.eliminarTarea(indice)) {
                MenuFormatter.mostrarMensajeExito("Tarea eliminada correctamente");
            } else {
                MenuFormatter.mostrarMensajeError("No se pudo eliminar la tarea");
            }
        }
    }
    
    private void eliminarTareasCompletadas() {
        if (gestor.getCantidadTareasCompletadas() == 0) {
            MenuFormatter.mostrarMensajeInfo("No hay tareas completadas para eliminar");
            return;
        }
        
        System.out.printf("Se encontraron %d tareas completadas%n", gestor.getCantidadTareasCompletadas());
        
        if (InputValidator.confirmar("¿Desea eliminar todas las tareas completadas?")) {
            int eliminadas = gestor.eliminarCompletadas();
            MenuFormatter.mostrarMensajeExito("Se eliminaron " + eliminadas + " tareas completadas");
        }
    }
    
    private void mostrarEstadisticas() {
        MenuFormatter.mostrarTituloSecundario("ESTADÍSTICAS DE TAREAS");
        
        if (!gestor.tieneTareas()) {
            MenuFormatter.mostrarMensajeInfo("No hay tareas registradas");
            return;
        }
        
        int total = gestor.getCantidadTareas();
        int pendientes = gestor.getCantidadTareasPendientes();
        int completadas = gestor.getCantidadTareasCompletadas();
        double porcentajeCompletado = (total > 0) ? (completadas * 100.0 / total) : 0;
        
        System.out.println();
        System.out.printf("📊 Total de tareas: %d%n", total);
        System.out.printf("⏳ Tareas pendientes: %d%n", pendientes);
        System.out.printf("✅ Tareas completadas: %d%n", completadas);
        System.out.printf("📈 Progreso: %.1f%%%n", porcentajeCompletado);
        
        // Barra de progreso simple
        int barras = (int) (porcentajeCompletado / 5); // 20 posiciones máximo
        System.out.print("▓");
        for (int i = 0; i < 20; i++) {
            System.out.print(i < barras ? "█" : "░");
        }
        System.out.println("▓");
    }
    
    private void mostrarResumenTareas() {
        System.out.printf("%nResumen: %d total | %d pendientes | %d completadas%n", 
                         gestor.getCantidadTareas(),
                         gestor.getCantidadTareasPendientes(),
                         gestor.getCantidadTareasCompletadas());
    }
}