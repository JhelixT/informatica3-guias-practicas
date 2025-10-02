package core.tareas;

public class Tarea {
    private String descripcion;
    private String estado;
   
    public Tarea(String descripcion, String estado) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser vacía");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede ser vacío");
        }
        
        this.descripcion = descripcion.trim();
        this.estado = estado.trim();
    }

    // Getters
    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser vacía");
        }
        this.descripcion = descripcion.trim();
    }

    public void setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede ser vacío");
        }
        this.estado = estado.trim();
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s", estado.toUpperCase(), descripcion);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tarea tarea = (Tarea) obj;
        return descripcion.equals(tarea.descripcion);
    }
    
    @Override
    public int hashCode() {
        return descripcion.hashCode();
    }
}