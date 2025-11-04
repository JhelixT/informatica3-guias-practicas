package core.integrador;

import core.estructuras.monticulo.MonticuloBinario;
import core.estructuras.monticulo.MonticuloBinario.TipoMonticulo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        int minutosBloqueados;
        
        MedicoBloqueado(String matricula, int minutosBloqueados) {
            this.matricula = matricula;
            this.minutosBloqueados = minutosBloqueados;
        }
        
        @Override
        public int compareTo(MedicoBloqueado otro) {
            return Integer.compare(this.minutosBloqueados, otro.minutosBloqueados);
        }
    }
    
    // Min-heap de quirófanos ordenados por finOcupado
    private MonticuloBinario<Quirofano> quirofanos;
    
    // Listas paralelas para rastrear minutos bloqueados por médico (reemplaza HashMap)
    private List<String> matriculasMedicos;
    private List<Integer> minutosMedicos;
    
    // Reloj actual del sistema
    private LocalDateTime ahora;
    
    public PlanificadorQuirofanoImpl(int numQuirofanos, LocalDateTime inicio) {
        this.quirofanos = new MonticuloBinario<>(TipoMonticulo.MIN_HEAP);
        this.matriculasMedicos = new ArrayList<>();
        this.minutosMedicos = new ArrayList<>();
        this.ahora = inicio;
        
        // Inicializar todos los quirófanos libres desde el inicio
        for (int i = 1; i <= numQuirofanos; i++) {
            quirofanos.add(new Quirofano("Q" + i, inicio));
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
            System.out.println("⚠️  Cirugía " + s.getId() + " NO cumple deadline (" + 
                             fin + " > " + s.getDeadline() + ")");
        }
        
        // Actualizar minutos bloqueados del médico (usando listas paralelas)
        actualizarMinutosMedico(s.getMatricula(), s.getDurMin());
        
        // Actualizar el quirófano y devolverlo al heap
        quirofano.finOcupado = fin;
        quirofanos.add(quirofano);
        
        System.out.printf("✅ Asignado: %s → %s [%s - %s] (Médico %s: %d min acumulados)%n",
                s.getId(), quirofano.id, inicio, fin, s.getMatricula(), 
                obtenerMinutosMedico(s.getMatricula()));
    }
    
    /** Actualiza los minutos bloqueados de un médico usando listas paralelas */
    private void actualizarMinutosMedico(String matricula, int minutos) {
        int index = matriculasMedicos.indexOf(matricula);
        
        if (index == -1) {
            // Médico nuevo
            matriculasMedicos.add(matricula);
            minutosMedicos.add(minutos);
        } else {
            // Médico existente, acumular minutos
            minutosMedicos.set(index, minutosMedicos.get(index) + minutos);
        }
    }
    
    /** Obtiene los minutos bloqueados de un médico */
    private int obtenerMinutosMedico(String matricula) {
        int index = matriculasMedicos.indexOf(matricula);
        return index == -1 ? 0 : minutosMedicos.get(index);
    }
    
    /**
     * Retorna los K médicos con más minutos bloqueados.
     * Usa un min-heap de tamaño K para mantener solo los K máximos.
     * Complejidad: O(M log K) donde M = total de médicos
     */
    @Override
    public List<String> topKMedicosBloqueados(int K) {
        if (K <= 0 || matriculasMedicos.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Min-heap de tamaño K para mantener los K médicos con MÁS minutos
        MonticuloBinario<MedicoBloqueado> topK = new MonticuloBinario<>(TipoMonticulo.MIN_HEAP);
        
        for (int i = 0; i < matriculasMedicos.size(); i++) {
            MedicoBloqueado medico = new MedicoBloqueado(
                matriculasMedicos.get(i), 
                minutosMedicos.get(i)
            );
            
            if (topK.size() < K) {
                // Si aún no tenemos K elementos, agregar directamente
                topK.add(medico);
            } else if (medico.minutosBloqueados > topK.peek().minutosBloqueados) {
                // Si este médico tiene más minutos que el mínimo del heap, reemplazar
                topK.poll();
                topK.add(medico);
            }
        }
        
        // Convertir a lista y ordenar de mayor a menor
        List<MedicoBloqueado> lista = new ArrayList<>();
        while (!topK.isEmpty()) {
            lista.add(topK.poll());
        }
        
        // Ordenar de mayor a menor (insertion sort simple ya que K es pequeño)
        for (int i = 0; i < lista.size(); i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                if (lista.get(j).minutosBloqueados > lista.get(i).minutosBloqueados) {
                    MedicoBloqueado temp = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, temp);
                }
            }
        }
        
        List<String> resultado = new ArrayList<>();
        for (MedicoBloqueado mb : lista) {
            resultado.add(mb.matricula + " (" + mb.minutosBloqueados + " min)");
        }
        
        return resultado;
    }
    
    /** Avanza el reloj del sistema */
    public void avanzarTiempo(LocalDateTime nuevoTiempo) {
        this.ahora = nuevoTiempo;
    }
    
    /** Muestra el estado actual de los quirófanos */
    public void mostrarEstadoQuirofanos() {
        System.out.println("\n📊 Estado de quirófanos:");
        
        // Extraer todos los quirófanos para mostrarlos
        List<Quirofano> temp = new ArrayList<>();
        while (!quirofanos.isEmpty()) {
            temp.add(quirofanos.poll());
        }
        
        // Ordenar por ID para mostrar en orden
        for (int i = 0; i < temp.size(); i++) {
            for (int j = i + 1; j < temp.size(); j++) {
                if (temp.get(j).id.compareTo(temp.get(i).id) < 0) {
                    Quirofano aux = temp.get(i);
                    temp.set(i, temp.get(j));
                    temp.set(j, aux);
                }
            }
        }
        
        // Mostrar y volver a insertar en el heap
        for (Quirofano q : temp) {
            System.out.printf("   %s: libre desde %s%n", q.id, q.finOcupado);
            quirofanos.add(q);
        }
    }
}
