package core.tareas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorTareas {
    private List<Tarea> tareas;

    public GestorTareas() {
        this.tareas = new ArrayList<>();
    }

    public void agregarTarea(String descripcion, String estado) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser vacía");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede ser vacío");
        }
        
        String estadoNormalizado = estado.trim().toLowerCase();
        if (!estadoNormalizado.equals("pendiente") && !estadoNormalizado.equals("completada")) {
            throw new IllegalArgumentException("Estado no válido. Use 'pendiente' o 'completada'");
        }

        Tarea nuevaTarea = new Tarea(descripcion, estadoNormalizado);
        tareas.add(nuevaTarea);
    }
    
    public void agregarTarea(Tarea tarea) {
        if (tarea == null) {
            throw new IllegalArgumentException("La tarea no puede ser nula");
        }
        tareas.add(tarea);
    }

    public List<Tarea> getTareas() {
        return new ArrayList<>(tareas); // Retorna copia para encapsulación
    }
    
    public List<Tarea> getTareasPendientes() {
        return tareas.stream()
                    .filter(tarea -> tarea.getEstado().equalsIgnoreCase("pendiente"))
                    .collect(Collectors.toList());
    }
    
    public List<Tarea> getTareasCompletadas() {
        return tareas.stream()
                    .filter(tarea -> tarea.getEstado().equalsIgnoreCase("completada"))
                    .collect(Collectors.toList());
    }

    public boolean marcarComoCompletada(int index) {
        if (index < 0 || index >= tareas.size()) {
            return false;
        }

        Tarea tarea = tareas.get(index);
        if (tarea.getEstado().equalsIgnoreCase("completada")) {
            return false; // Ya está completada
        }

        tarea.setEstado("completada");
        return true;
    }
    
    public boolean marcarComoPendiente(int index) {
        if (index < 0 || index >= tareas.size()) {
            return false;
        }

        Tarea tarea = tareas.get(index);
        if (tarea.getEstado().equalsIgnoreCase("pendiente")) {
            return false; // Ya está pendiente
        }

        tarea.setEstado("pendiente");
        return true;
    }

    public int eliminarCompletadas() {
        int sizePrevio = tareas.size();
        tareas.removeIf(tarea -> tarea.getEstado().equalsIgnoreCase("completada"));
        return sizePrevio - tareas.size();
    }
    
    public boolean eliminarTarea(int index) {
        if (index < 0 || index >= tareas.size()) {
            return false;
        }
        tareas.remove(index);
        return true;
    }
    
    public int getCantidadTareas() {
        return tareas.size();
    }
    
    public int getCantidadTareasPendientes() {
        return (int) tareas.stream()
                         .filter(tarea -> tarea.getEstado().equalsIgnoreCase("pendiente"))
                         .count();
    }
    
    public int getCantidadTareasCompletadas() {
        return (int) tareas.stream()
                         .filter(tarea -> tarea.getEstado().equalsIgnoreCase("completada"))
                         .count();
    }
    
    public boolean tieneTareas() {
        return !tareas.isEmpty();
    }
    
    public Tarea getTarea(int index) {
        if (index < 0 || index >= tareas.size()) {
            throw new IndexOutOfBoundsException("Índice de tarea no válido");
        }
        return tareas.get(index);
    }
}