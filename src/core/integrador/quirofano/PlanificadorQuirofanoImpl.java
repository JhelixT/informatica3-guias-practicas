package core.integrador.quirofano;

import core.integrador.modelo.SolicitudCirugia;
import core.estructuras.monticulo.MonticuloBinario;
import core.estructuras.monticulo.MonticuloBinario.TipoMonticulo;
import core.estructuras.listas.ListaEnlazada;
import core.estructuras.nodos.Nodo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Implementación del planificador de quirófanos usando montículos binarios propios.
 * - Min-heap de quirófanos ordenados por tiempo de finalización (finOcupado)
 * - Min-heap de tamaño K para top-K médicos con más minutos bloqueados
 * 
 * Complejidad por evento: O(log Q + log K) donde Q = quirófanos, K = top-K
 */
public class PlanificadorQuirofanoImpl implements PlanificadorQuirofano {
    
    /** Representa un quirófano con su estado actual */
    private static class Quirofano implements Comparable<Quirofano> {
        String id;
        LocalDateTime finOcupado;
        
        Quirofano(String id, LocalDateTime finOcupado) {
            this.id = id;
            this.finOcupado = finOcupado;
        }
        
        @Override
        public int compareTo(Quirofano otro) {
            return this.finOcupado.compareTo(otro.finOcupado);
        }
    }
    
    /** Representa un médico con sus minutos bloqueados */
    private static class MedicoBloqueado implements Comparable<MedicoBloqueado> {
        String matricula;
        String nombre;
        int minutosBloqueados;
        
        MedicoBloqueado(String matricula, String nombre, int minutosBloqueados) {
            this.matricula = matricula;
            this.nombre = nombre;
            this.minutosBloqueados = minutosBloqueados;
        }
        
        @Override
        public int compareTo(MedicoBloqueado otro) {
            return Integer.compare(this.minutosBloqueados, otro.minutosBloqueados);
        }
    }
    
    // Min-heap de quirófanos ordenados por finOcupado
    private MonticuloBinario<Quirofano> quirofanos;
    
    // Listas enlazadas paralelas para rastrear minutos bloqueados por médico (reemplaza HashMap)
    private ListaEnlazada<String> matriculasMedicos;
    private ListaEnlazada<String> nombresMedicos;
    private ListaEnlazada<Integer> minutosMedicos;
    
    // Reloj actual del sistema
    private LocalDateTime ahora;
    
    // Formateador de fechas
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM HH:mm");
    
    public PlanificadorQuirofanoImpl(int numQuirofanos, LocalDateTime inicio) {
        this.quirofanos = new MonticuloBinario<>(TipoMonticulo.MIN_HEAP);
        this.matriculasMedicos = new ListaEnlazada<>();
        this.nombresMedicos = new ListaEnlazada<>();
        this.minutosMedicos = new ListaEnlazada<>();
        this.ahora = inicio;
        
        // Inicializar todos los quirófanos libres desde el inicio
        for (int i = 1; i <= numQuirofanos; i++) {
            quirofanos.add(new Quirofano("Q" + i, inicio));
        }
    }
    
    /**
     * Registra un médico en el sistema con su nombre
     */
    @Override
    public void registrarMedico(String matricula, String nombre) {
        // Usa search() de ListaEnlazada - O(n)
        if (!matriculasMedicos.contains(matricula)) {
            matriculasMedicos.insertLast(matricula);
            nombresMedicos.insertLast(nombre);
            minutosMedicos.insertLast(0);
        }
    }
    
    /**
     * Procesa una solicitud de cirugía.
     * Asigna al primer quirófano libre que cumpla el deadline.
     * Complejidad: O(log Q) para extraer y reinsertar en el heap
     */
    @Override
    public void procesar(SolicitudCirugia s) {
        if (s == null) return;
        
        // Obtener el quirófano que se libera más pronto (tope del min-heap)
        if (quirofanos.isEmpty()) {
            System.out.println("❌ No hay quirófanos disponibles para: " + s);
            return;
        }
        
        Quirofano quirofano = quirofanos.poll();
        
        // Calcular cuándo puede comenzar la cirugía
        LocalDateTime inicio = quirofano.finOcupado.isAfter(ahora) 
                              ? quirofano.finOcupado 
                              : ahora;
        
        LocalDateTime fin = inicio.plusMinutes(s.getDurMin());
        
        // Verificar si cumple el deadline
        if (fin.isAfter(s.getDeadline())) {
            System.out.println("Cirugia " + s.getId() + " NO cumple deadline (" + 
                             FORMATO_FECHA.format(fin) + " > " + FORMATO_FECHA.format(s.getDeadline()) + ")");
        }
        
        // Actualizar minutos bloqueados del médico (usando listas paralelas)
        actualizarMinutosMedico(s.getMatricula(), s.getDurMin());
        
        // Actualizar el quirófano y devolverlo al heap
        quirofano.finOcupado = fin;
        quirofanos.add(quirofano);
        
        System.out.printf("Asignado: %s -> %s [%s - %s] (Medico %s: %d min acumulados)%n",
                s.getId(), quirofano.id, FORMATO_FECHA.format(inicio), FORMATO_FECHA.format(fin), 
                s.getMatricula(), obtenerMinutosMedico(s.getMatricula()));
    }
    
    /** Actualiza los minutos bloqueados de un médico usando listas paralelas */
    private void actualizarMinutosMedico(String matricula, int minutos) {
        // Usa search() de ListaEnlazada - O(n)
        int index = matriculasMedicos.search(matricula);
        
        if (index == -1) {
            // Médico nuevo (sin nombre por ahora)
            matriculasMedicos.insertLast(matricula);
            nombresMedicos.insertLast("Desconocido");
            minutosMedicos.insertLast(minutos);
        } else {
            // Médico existente, acumular minutos
            // Usa getAt() de ListaEnlazada - O(n)
            Integer minutosActuales = minutosMedicos.getAt(index);
            // Actualiza usando Nodo.setData() navegando
            Nodo<Integer> nodo = minutosMedicos.getHead();
            for (int i = 0; i < index; i++) {
                nodo = nodo.getNext();
            }
            nodo.setData(minutosActuales + minutos);
        }
    }
    
    /** Obtiene los minutos bloqueados de un médico */
    private int obtenerMinutosMedico(String matricula) {
        // Usa search() de ListaEnlazada - O(n)
        int index = matriculasMedicos.search(matricula);
        if (index == -1) return 0;
        
        // Usa getAt() de ListaEnlazada - O(n)
        Integer minutos = minutosMedicos.getAt(index);
        return minutos != null ? minutos : 0;
    }
    
    /**
     * Retorna los K médicos con más minutos bloqueados.
     * Usa un min-heap de tamaño K para mantener solo los K máximos.
     * Complejidad: O(M log K) donde M = total de médicos
     */
    @Override
    public ListaEnlazada<String> topKMedicosBloqueados(int K) {
        ListaEnlazada<String> resultado = new ListaEnlazada<>();
        
        if (K <= 0 || matriculasMedicos.isEmpty()) {
            return resultado;
        }
        
        // Min-heap de tamaño K para mantener los K médicos con MÁS minutos
        MonticuloBinario<MedicoBloqueado> topK = new MonticuloBinario<>(TipoMonticulo.MIN_HEAP);
        
        // Iterar sobre las listas paralelas usando nodos
        Nodo<String> nodoMatricula = matriculasMedicos.getHead();
        Nodo<String> nodoNombre = nombresMedicos.getHead();
        Nodo<Integer> nodoMinutos = minutosMedicos.getHead();
        
        while (nodoMatricula != null && nodoNombre != null && nodoMinutos != null) {
            MedicoBloqueado medico = new MedicoBloqueado(
                nodoMatricula.getData(),
                nodoNombre.getData(),
                nodoMinutos.getData()
            );
            
            if (topK.size() < K) {
                // Si aún no tenemos K elementos, agregar directamente
                topK.add(medico);
            } else if (medico.minutosBloqueados > topK.peek().minutosBloqueados) {
                // Si este médico tiene más minutos que el mínimo del heap, reemplazar
                topK.poll();
                topK.add(medico);
            }
            
            nodoMatricula = nodoMatricula.getNext();
            nodoNombre = nodoNombre.getNext();
            nodoMinutos = nodoMinutos.getNext();
        }
        
        // Convertir a ListaEnlazada y ordenar de mayor a menor
        ListaEnlazada<MedicoBloqueado> lista = new ListaEnlazada<>();
        while (!topK.isEmpty()) {
            lista.insertLast(topK.poll());
        }
        
        // Ordenar de mayor a menor usando nodos
        ordenarListaPorMinutos(lista);
        
        // Convertir a formato de string
        Nodo<MedicoBloqueado> nodo = lista.getHead();
        while (nodo != null) {
            MedicoBloqueado mb = nodo.getData();
            resultado.insertLast(mb.nombre + " [" + mb.matricula + "] - " + mb.minutosBloqueados + " min");
            nodo = nodo.getNext();
        }
        
        return resultado;
    }
    
    /**
     * Ordena una lista enlazada de MedicoBloqueado de mayor a menor por minutos.
     * Usa bubble sort adaptado para listas enlazadas.
     */
    private void ordenarListaPorMinutos(ListaEnlazada<MedicoBloqueado> lista) {
        if (lista.isEmpty()) return;
        
        boolean cambio = true;
        while (cambio) {
            cambio = false;
            Nodo<MedicoBloqueado> actual = lista.getHead();
            
            while (actual != null && actual.getNext() != null) {
                MedicoBloqueado datoActual = actual.getData();
                MedicoBloqueado datoSiguiente = actual.getNext().getData();
                
                if (datoActual.minutosBloqueados < datoSiguiente.minutosBloqueados) {
                    // Intercambiar datos
                    actual.setData(datoSiguiente);
                    actual.getNext().setData(datoActual);
                    cambio = true;
                }
                
                actual = actual.getNext();
            }
        }
    }
    
    /** Avanza el reloj del sistema */
    public void avanzarTiempo(LocalDateTime nuevoTiempo) {
        this.ahora = nuevoTiempo;
    }
    
    /** Muestra el estado actual de los quirófanos */
    public void mostrarEstadoQuirofanos() {
        System.out.println("\nEstado de quirofanos:");
        
        // Extraer todos los quirófanos para mostrarlos
        ListaEnlazada<Quirofano> temp = new ListaEnlazada<>();
        while (!quirofanos.isEmpty()) {
            temp.insertLast(quirofanos.poll());
        }
        
        // Ordenar por ID usando bubble sort
        ordenarQuirofanosPorId(temp);
        
        // Mostrar y volver a insertar en el heap
        Nodo<Quirofano> nodo = temp.getHead();
        while (nodo != null) {
            Quirofano q = nodo.getData();
            System.out.printf("   %s: libre desde %s%n", q.id, FORMATO_FECHA.format(q.finOcupado));
            quirofanos.add(q);
            nodo = nodo.getNext();
        }
    }
    
    /**
     * Ordena quirófanos por ID alfabéticamente usando bubble sort.
     */
    private void ordenarQuirofanosPorId(ListaEnlazada<Quirofano> lista) {
        if (lista.isEmpty()) return;
        
        boolean cambio = true;
        while (cambio) {
            cambio = false;
            Nodo<Quirofano> actual = lista.getHead();
            
            while (actual != null && actual.getNext() != null) {
                Quirofano datoActual = actual.getData();
                Quirofano datoSiguiente = actual.getNext().getData();
                
                if (datoActual.id.compareTo(datoSiguiente.id) > 0) {
                    // Intercambiar datos
                    actual.setData(datoSiguiente);
                    actual.getNext().setData(datoActual);
                    cambio = true;
                }
                
                actual = actual.getNext();
            }
        }
    }
}
