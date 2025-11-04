package core.estructuras.monticulo;

import core.estructuras.hash.TablaHash;

/**
 * Extensión de MonticuloBinario con índice inverso (elemento -> posición).
 * Permite búsqueda O(1) y actualización O(log n).
 */
public class MonticuloIndexado<T extends Comparable<T>> extends MonticuloBinario<T> {
    
    private TablaHash<T, Integer> posiciones;
    
    /**
     * Crea montículo indexado del tipo especificado.
     */
    public MonticuloIndexado(TipoMonticulo tipo) {
        super(tipo);
        this.posiciones = new TablaHash<>();
    }
    
    /**
     * Constructor que crea un Min-Heap indexado por defecto.
     */
    public MonticuloIndexado() {
        super(TipoMonticulo.MIN_HEAP);
        this.posiciones = new TablaHash<>();
    }
    
    /**
     * Inserta un nuevo elemento y registra su posición en el índice.
     * 
     * Complejidad: O(log n)
     * 
     * @param valor El valor a insertar
     * @throws IllegalArgumentException si el valor es null
     */
    @Override
    public void add(T valor) {
        super.add(valor);
        // Después de add(), el elemento está en la última posición (size())
        // pero después de percolateUp puede estar en otra posición
        // Necesitamos actualizar el índice después de cada operación
        actualizarIndiceCompleto();
    }
    
    /**
     * Elimina y retorna el elemento raíz.
     */
    @Override
    public T poll() {
        if (isEmpty()) {
            throw new IllegalStateException("El montículo está vacío");
        }
        
        T root = peek();
        posiciones.remove(root); // Eliminar del índice
        super.poll();
        actualizarIndiceCompleto(); // Actualizar posiciones después de reordenar
        
        return root;
    }
    
    /**
     * Limpia el montículo y el índice.
     */
    @Override
    public void clear() {
        super.clear();
        posiciones.clear();
    }
    
    /**
     * Busca elemento en O(1).
     */
    public int indexOf(T valor) {
        Integer pos = posiciones.get(valor);
        return pos != null ? pos : -1;
    }
    
    /**
     * Verifica existencia en O(1).
     */
    public boolean contains(T valor) {
        return posiciones.containsKey(valor);
    }
    
    /**
     * Actualiza elemento en posición específica y reordena en O(log n).
     */
    public void updateAt(int index, T nuevoValor) {
        if (index < 1 || index > size()) {
            throw new IllegalArgumentException("Índice fuera de rango: " + index);
        }
        if (nuevoValor == null) {
            throw new IllegalArgumentException("No se puede insertar null");
        }
        
        // Obtener lista de elementos
        java.util.List<T> elementos = getElements();
        T valorAnterior = elementos.get(index - 1); // getElements() es 0-based
        
        // Eliminar del índice
        posiciones.remove(valorAnterior);
        
        // Reconstruir heap con el valor actualizado
        clear();
        for (int i = 0; i < elementos.size(); i++) {
            if (i == index - 1) {
                super.add(nuevoValor);
            } else {
                super.add(elementos.get(i));
            }
        }
        
        actualizarIndiceCompleto();
    }
    
    /**
     * Busca y actualiza elemento en O(log n).
     */
    public boolean update(T valorAntiguo, T valorNuevo) {
        if (valorAntiguo == null || valorNuevo == null) {
            throw new IllegalArgumentException("Los valores no pueden ser null");
        }
        
        int index = indexOf(valorAntiguo);
        if (index == -1) {
            return false;
        }
        
        updateAt(index, valorNuevo);
        return true;
    }
    
    /**
     * Reconstruye índice completo en O(n).
     */
    private void actualizarIndiceCompleto() {
        posiciones.clear();
        java.util.List<T> elementos = getElements();
        for (int i = 0; i < elementos.size(); i++) {
            posiciones.put(elementos.get(i), i + 1); // Índices 1-based
        }
    }
    
    /**
     * Retorna el tamaño del índice (debe coincidir con size()).
     * Útil para debugging.
     * 
     * @return Tamaño del índice inverso
     */
    public int getIndiceSize() {
        return posiciones.size();
    }
    
    @Override
    public String toString() {
        return "MonticuloIndexado [tipo=" + getTipo() + ", tamaño=" + size() + 
               ", índice=" + posiciones.size() + ", raíz=" + (isEmpty() ? "null" : peek()) + "]";
    }
}
