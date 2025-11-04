package views;

import core.integrador.carga.CargadorCSV;
import core.integrador.modelo.*;
import core.integrador.pacientes.IndicePacientes;
import core.integrador.salaespera.SalaEspera;
import core.integrador.recordatorios.PlanificadorRecordatorios;
import core.integrador.agenda.AgendaMedicoTree;
import core.integrador.merge.ConsolidadorAgendas;
import core.integrador.quirofano.PlanificadorQuirofanoImpl;
import core.estructuras.listas.ListaEnlazada;
import core.estructuras.nodos.Nodo;
import core.utils.AnsiColors;
import core.utils.InputValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Menu simplificado del Sistema Integrador de Gestion de Turnos Medicos.
 * 
 * Integra todas las estructuras de datos del proyecto:
 * - TablaHash para indice de pacientes
 * - ArbolAVL para agendas medicas
 * - ColaCircular para sala de espera
 * - MonticuloIndexado para recordatorios
 * - Min-Heap para planificador de quirofanos
 * - Merge de listas para consolidacion
 */
public class IntegradorMenu {
    
    private Scanner scanner;
    private ListaEnlazada<Paciente> pacientes;
    private ListaEnlazada<Medico> medicos;
    private ListaEnlazada<Turno> turnos;
    
    private IndicePacientes indicePacientes;
    private AgendaMedicoTree agendaMedico;
    private SalaEspera salaEspera;
    private PlanificadorRecordatorios planificadorRecordatorios;
    private PlanificadorQuirofanoImpl planificadorQuirofano;
    
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM HH:mm");
    
    public IntegradorMenu() {
        this.scanner = new Scanner(System.in);
    }
    
    public void mostrar() {
        AnsiColors.inicializar();
        
        if (!cargarDatosIniciales()) {
            System.out.println(AnsiColors.rojo("Error al cargar datos. Saliendo..."));
            return;
        }
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opcion: ", 0, 9);
            
            switch (opcion) {
                case 1 -> verIndicePacientes();
                case 2 -> verAgendaMedica();
                case 3 -> verSalaEspera();
                case 4 -> verRecordatorios();
                case 5 -> verQuirofanos();
                case 6 -> consolidarAgendas();
                case 7 -> verDatosCompletos();
                case 8 -> verEstadisticas();
                case 9 -> ejecutarTests();
                case 0 -> continuar = false;
            }
            
            if (continuar && opcion != 0) {
                esperarEnter();
            }
        }
        
        System.out.println(AnsiColors.verde("\nGracias por usar el Sistema Integrador"));
    }
    
    private void mostrarMenu() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("    SISTEMA INTEGRADOR - GESTION DE TURNOS MEDICOS"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.naranja("1.") + AnsiColors.blanco(" Indice de Pacientes (TablaHash)"));
        System.out.println(AnsiColors.naranja("2.") + AnsiColors.blanco(" Agenda Medica (Arbol AVL)"));
        System.out.println(AnsiColors.naranja("3.") + AnsiColors.blanco(" Sala de Espera (Cola Circular)"));
        System.out.println(AnsiColors.naranja("4.") + AnsiColors.blanco(" Recordatorios (Monticulo Indexado)"));
        System.out.println(AnsiColors.naranja("5.") + AnsiColors.blanco(" Quirofanos (Min-Heap)"));
        System.out.println(AnsiColors.naranja("6.") + AnsiColors.blanco(" Consolidacion de Agendas (Merge)"));
        System.out.println(AnsiColors.naranja("7.") + AnsiColors.blanco(" Ver Datos Completos"));
        System.out.println(AnsiColors.naranja("8.") + AnsiColors.blanco(" Estadisticas del Sistema"));
        System.out.println(AnsiColors.naranja("9.") + AnsiColors.blanco(" Ejecutar Tests"));
        System.out.println(AnsiColors.gris("0.") + AnsiColors.gris(" Salir"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
    }
    
    private boolean cargarDatosIniciales() {
        System.out.println("\n" + AnsiColors.naranjaNegrita("=".repeat(60)));
        System.out.println(AnsiColors.naranjaNegrita("  CARGANDO SISTEMA..."));
        System.out.println(AnsiColors.naranjaNegrita("=".repeat(60)));
        
        try {
            CargadorCSV cargador = new CargadorCSV();
            
            System.out.print(AnsiColors.blanco("> Pacientes... "));
            cargador.loadPacientes(CargadorCSV.DEFAULT_PACIENTES);
            pacientes = cargador.getPacientes();
            System.out.println(AnsiColors.verde("[OK] " + pacientes.getSize() + " registros"));
            
            System.out.print(AnsiColors.blanco("> Medicos... "));
            cargador.loadMedicos(CargadorCSV.DEFAULT_MEDICOS);
            medicos = cargador.getMedicos();
            System.out.println(AnsiColors.verde("[OK] " + medicos.getSize() + " registros"));
            
            System.out.print(AnsiColors.blanco("> Turnos... "));
            cargador.loadTurnos(CargadorCSV.DEFAULT_TURNOS);
            turnos = cargador.getTurnos();
            System.out.println(AnsiColors.verde("[OK] " + turnos.getSize() + " registros"));
            
            System.out.print(AnsiColors.blanco("> Inicializando estructuras... "));
            inicializarEstructuras();
            System.out.println(AnsiColors.verde("[OK]"));
            
            return true;
        } catch (Exception e) {
            System.out.println(AnsiColors.rojo("[ERROR] " + e.getMessage()));
            return false;
        }
    }
    
    private void inicializarEstructuras() {
        // TablaHash de pacientes
        indicePacientes = new IndicePacientes(20);
        Nodo<Paciente> nodoPac = pacientes.getHead();
        while (nodoPac != null) {
            Paciente p = nodoPac.getData();
            indicePacientes.put(p.getDni(), p);
            nodoPac = nodoPac.getNext();
        }
        
        // Arbol AVL de agenda
        agendaMedico = new AgendaMedicoTree();
        if (medicos.getSize() > 0) {
            Medico primerMedico = medicos.getHead().getData();
            Nodo<Turno> nodoTurno = turnos.getHead();
            while (nodoTurno != null) {
                Turno t = nodoTurno.getData();
                if (t.getMatriculaMedico().equals(primerMedico.getMatricula())) {
                    agendaMedico.agendar(t);
                }
                nodoTurno = nodoTurno.getNext();
            }
        }
        
        // Cola circular
        salaEspera = new SalaEspera(5);
        
        // Monticulo indexado
        planificadorRecordatorios = new PlanificadorRecordatorios();
        
        // Min-Heap de quirofanos
        planificadorQuirofano = new PlanificadorQuirofanoImpl(3, LocalDateTime.now());
    }
    
    // ========== OPCION 1: INDICE DE PACIENTES ==========
    private void verIndicePacientes() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  INDICE DE PACIENTES (TABLA HASH)"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.println(AnsiColors.cyan("Capacidad: ") + indicePacientes.getCapacidad());
        System.out.println(AnsiColors.cyan("Elementos: ") + indicePacientes.size());
        System.out.println(AnsiColors.cyan("Load Factor: ") + String.format("%.2f", indicePacientes.getLoadFactor()));
        
        int[] stats = indicePacientes.getCollisionStats();
        System.out.println(AnsiColors.cyan("Buckets usados: ") + stats[0]);
        System.out.println(AnsiColors.cyan("Colisiones: ") + stats[2]);
        
        System.out.println("\n" + AnsiColors.naranjaNegrita("PACIENTES (primeros 10):"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        int count = 0;
        for (String dni : indicePacientes.keys()) {
            if (count >= 10) break;
            Paciente p = indicePacientes.get(dni);
            System.out.printf("%s%-12s%s | %s%n",
                AnsiColors.cyan(""), dni, AnsiColors.blanco(""), p.getNombre()
            );
            count++;
        }
        
        System.out.println(AnsiColors.gris("\n... y " + (indicePacientes.size() - 10) + " mas"));
        System.out.println(AnsiColors.gris("Complejidad: O(1) promedio - get, put, remove"));
    }
    
    // ========== OPCION 2: AGENDA MEDICA ==========
    private void verAgendaMedica() {
        if (medicos.getSize() == 0) {
            System.out.println(AnsiColors.rojo("No hay medicos disponibles"));
            return;
        }
        
        Medico medico = medicos.getHead().getData();
        
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  AGENDA MEDICA (ARBOL AVL)"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.verde("Medico: ") + medico.getNombre() + " - " + medico.getEspecialidad());
        System.out.println(AnsiColors.cyan("Turnos: ") + agendaMedico.cantidadTurnos());
        
        System.out.println("\n" + AnsiColors.naranjaNegrita("TURNOS (primeros 10):"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        int count = 0;
        Nodo<Turno> nodo = turnos.getHead();
        while (nodo != null && count < 10) {
            Turno t = nodo.getData();
            if (t.getMatriculaMedico().equals(medico.getMatricula())) {
                System.out.printf("%s | %s | %s%n",
                    t.getId(),
                    t.getFechaHora().format(FORMATO_FECHA),
                    t.getMotivo()
                );
                count++;
            }
            nodo = nodo.getNext();
        }
        
        System.out.println(AnsiColors.gris("\nComplejidad: O(log n) - insercion, busqueda, eliminacion"));
    }
    
    // ========== OPCION 3: SALA DE ESPERA ==========
    private void verSalaEspera() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  SALA DE ESPERA (COLA CIRCULAR)"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.cyan("Capacidad: ") + salaEspera.getCapacidad());
        System.out.println(AnsiColors.cyan("En espera: ") + salaEspera.size());
        System.out.println(AnsiColors.cyan("Estado: ") + 
                          (salaEspera.isFull() ? AnsiColors.rojo("LLENA") : AnsiColors.verde("Disponible")));
        
        // Simular operaciones
        System.out.println("\n" + AnsiColors.naranjaNegrita("SIMULACION:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        // Agregar 3 pacientes
        int agregados = 0;
        Nodo<Paciente> nodo = pacientes.getHead();
        while (nodo != null && agregados < 3) {
            Paciente p = nodo.getData();
            salaEspera.llega(p.getDni());
            System.out.println(AnsiColors.verde("Llega: ") + p.getDni() + " - " + p.getNombre());
            agregados++;
            nodo = nodo.getNext();
        }
        
        System.out.println(AnsiColors.cyan("\nEstado: ") + salaEspera.size() + "/" + salaEspera.getCapacidad());
        
        // Atender 1
        String atendido = salaEspera.atiende();
        if (atendido != null) {
            System.out.println(AnsiColors.amarillo("Atendido: ") + atendido);
        }
        
        System.out.println(AnsiColors.cyan("Estado final: ") + salaEspera.size() + "/" + salaEspera.getCapacidad());
        System.out.println(AnsiColors.gris("Complejidad: O(1) - enqueue, dequeue"));
    }
    
    // ========== OPCION 4: RECORDATORIOS ==========
    private void verRecordatorios() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  RECORDATORIOS (MONTICULO INDEXADO)"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        // Cargar recordatorios desde turnos
        if (planificadorRecordatorios.isEmpty()) {
            System.out.println(AnsiColors.blanco("Cargando recordatorios..."));
            LocalDateTime ahora = LocalDateTime.now();
            int count = 0;
            Nodo<Turno> nodo = turnos.getHead();
            
            while (nodo != null && count < 5) {
                Turno t = nodo.getData();
                if (t.getFechaHora().isAfter(ahora)) {
                    Recordatorio r = new Recordatorio(
                        "R-" + t.getId(),
                        t.getFechaHora().minusHours(2),
                        t.getDniPaciente(),
                        "Recordatorio: Turno " + t.getId()
                    );
                    planificadorRecordatorios.programar(r);
                    count++;
                }
                nodo = nodo.getNext();
            }
        }
        
        System.out.println(AnsiColors.cyan("Programados: ") + planificadorRecordatorios.size());
        
        if (!planificadorRecordatorios.isEmpty()) {
            Recordatorio proximo = planificadorRecordatorios.peek();
            System.out.println(AnsiColors.verde("Proximo: ") + proximo.getId() + 
                              " - " + proximo.getFecha().format(FORMATO_FECHA));
        }
        
        System.out.println("\n" + AnsiColors.naranjaNegrita("TODOS LOS RECORDATORIOS:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        List<Recordatorio> recordatorios = planificadorRecordatorios.getRecordatorios();
        for (Recordatorio r : recordatorios) {
            System.out.printf("%s | %s | %s%n",
                r.getId(),
                r.getFecha().format(FORMATO_FECHA),
                r.getMensaje()
            );
        }
        
        System.out.println(AnsiColors.gris("\nComplejidad: O(log n) - insertar, extraer, reprogramar"));
    }
    
    // ========== OPCION 5: QUIROFANOS ==========
    private void verQuirofanos() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  PLANIFICADOR DE QUIROFANOS (MIN-HEAP)"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        // Procesar 3 cirugias de prueba
        System.out.println(AnsiColors.naranjaNegrita("PROCESANDO CIRUGIAS:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        LocalDateTime ahora = LocalDateTime.now();
        Nodo<Medico> nodo = medicos.getHead();
        
        for (int i = 1; i <= 3 && nodo != null; i++) {
            Medico m = nodo.getData();
            SolicitudCirugia cirugia = new SolicitudCirugia(
                "C-" + i,
                m.getMatricula(),
                120 + (i * 20),
                ahora.plusDays(i)
            );
            planificadorQuirofano.procesar(cirugia);
            nodo = nodo.getNext();
        }
        
        System.out.println();
        planificadorQuirofano.mostrarEstadoQuirofanos();
        
        System.out.println("\n" + AnsiColors.naranjaNegrita("TOP-3 MEDICOS CON MAS TIEMPO:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        List<String> topK = planificadorQuirofano.topKMedicosBloqueados(3);
        int pos = 1;
        for (String medicoInfo : topK) {
            System.out.println(pos++ + ". " + medicoInfo);
        }
        
        System.out.println(AnsiColors.gris("\nComplejidad: O(log Q) - asignacion de quirofano"));
    }
    
    // ========== OPCION 6: CONSOLIDACION ==========
    private void consolidarAgendas() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  CONSOLIDACION DE AGENDAS (MERGE)"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        // Dividir turnos en dos agendas
        ListaEnlazada<Turno> agendaLocal = new ListaEnlazada<>();
        ListaEnlazada<Turno> agendaNube = new ListaEnlazada<>();
        
        int count = 0;
        Nodo<Turno> nodo = turnos.getHead();
        while (nodo != null && count < 20) {
            if (count % 2 == 0) {
                agendaLocal.insertLast(nodo.getData());
            } else {
                agendaNube.insertLast(nodo.getData());
            }
            count++;
            nodo = nodo.getNext();
        }
        
        // Agregar duplicado
        if (agendaLocal.getSize() > 0) {
            agendaNube.insertLast(agendaLocal.getHead().getData());
        }
        
        System.out.println(AnsiColors.verde("Agenda Local: ") + agendaLocal.getSize() + " turnos");
        System.out.println(AnsiColors.cyan("Agenda Nube: ") + agendaNube.getSize() + " turnos");
        
        // Realizar merge
        ConsolidadorAgendas.ResultadoMerge resultado = ConsolidadorAgendas.merge(agendaLocal, agendaNube);
        
        System.out.println("\n" + AnsiColors.verde("Consolidacion completada"));
        System.out.println(AnsiColors.cyan("Turnos consolidados: ") + resultado.cantidadTurnos());
        System.out.println(AnsiColors.amarillo("Conflictos detectados: ") + resultado.cantidadConflictos());
        
        if (resultado.cantidadConflictos() > 0) {
            System.out.println("\n" + AnsiColors.rojo("CONFLICTOS:"));
            Nodo<String> conflictoNodo = resultado.getConflictos().getHead();
            while (conflictoNodo != null) {
                System.out.println(AnsiColors.amarillo("- ") + conflictoNodo.getData());
                conflictoNodo = conflictoNodo.getNext();
            }
        }
        
        System.out.println(AnsiColors.gris("\nComplejidad: O(n + m) - merge lineal"));
    }
    
    // ========== OPCION 7: DATOS COMPLETOS ==========
    private void verDatosCompletos() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  DATOS COMPLETOS"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        // Pacientes
        System.out.println("\n" + AnsiColors.naranjaNegrita("PACIENTES (" + pacientes.getSize() + "):"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        Nodo<Paciente> nodoPac = pacientes.getHead();
        int count = 0;
        while (nodoPac != null && count < 15) {
            Paciente p = nodoPac.getData();
            System.out.printf("%-12s | %s%n", p.getDni(), p.getNombre());
            count++;
            nodoPac = nodoPac.getNext();
        }
        if (pacientes.getSize() > 15) {
            System.out.println(AnsiColors.gris("... y " + (pacientes.getSize() - 15) + " mas"));
        }
        
        esperarEnter();
        
        // Medicos
        System.out.println("\n" + AnsiColors.naranjaNegrita("MEDICOS (" + medicos.getSize() + "):"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        Nodo<Medico> nodoMed = medicos.getHead();
        while (nodoMed != null) {
            Medico m = nodoMed.getData();
            System.out.printf("%-10s | %-35s | %s%n",
                m.getMatricula(), m.getNombre(), m.getEspecialidad()
            );
            nodoMed = nodoMed.getNext();
        }
        
        esperarEnter();
        
        // Turnos
        System.out.println("\n" + AnsiColors.naranjaNegrita("TURNOS (" + turnos.getSize() + " - mostrando 15):"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        Nodo<Turno> nodoTurno = turnos.getHead();
        count = 0;
        while (nodoTurno != null && count < 15) {
            Turno t = nodoTurno.getData();
            System.out.printf("%-8s | %s | %s%n",
                t.getId(),
                t.getFechaHora().format(FORMATO_FECHA),
                t.getMotivo()
            );
            count++;
            nodoTurno = nodoTurno.getNext();
        }
        if (turnos.getSize() > 15) {
            System.out.println(AnsiColors.gris("... y " + (turnos.getSize() - 15) + " mas"));
        }
    }
    
    // ========== OPCION 8: ESTADISTICAS ==========
    private void verEstadisticas() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  ESTADISTICAS DEL SISTEMA"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.println(AnsiColors.cyan("\nDatos cargados:"));
        System.out.println(AnsiColors.verde("  - Pacientes: ") + pacientes.getSize());
        System.out.println(AnsiColors.verde("  - Medicos: ") + medicos.getSize());
        System.out.println(AnsiColors.verde("  - Turnos: ") + turnos.getSize());
        
        System.out.println(AnsiColors.cyan("\nEstructuras activas:"));
        System.out.println(AnsiColors.verde("  - TablaHash:"));
        System.out.println(AnsiColors.gris("      Elementos: ") + indicePacientes.size());
        System.out.println(AnsiColors.gris("      Capacidad: ") + indicePacientes.getCapacidad());
        System.out.println(AnsiColors.gris("      Load Factor: ") + String.format("%.2f", indicePacientes.getLoadFactor()));
        
        int[] stats = indicePacientes.getCollisionStats();
        System.out.println(AnsiColors.gris("      Buckets usados: ") + stats[0]);
        System.out.println(AnsiColors.gris("      Colisiones: ") + stats[2]);
        
        System.out.println(AnsiColors.verde("  - Arbol AVL:"));
        System.out.println(AnsiColors.gris("      Turnos: ") + agendaMedico.cantidadTurnos());
        
        System.out.println(AnsiColors.verde("  - Cola Circular:"));
        System.out.println(AnsiColors.gris("      En espera: ") + salaEspera.size() + "/" + salaEspera.getCapacidad());
        
        System.out.println(AnsiColors.verde("  - Monticulo:"));
        System.out.println(AnsiColors.gris("      Recordatorios: ") + planificadorRecordatorios.size());
        
        System.out.println(AnsiColors.cyan("\nComplejidades:"));
        System.out.println(AnsiColors.gris("  - TablaHash: O(1) - busqueda/insercion promedio"));
        System.out.println(AnsiColors.gris("  - AVL: O(log n) - insercion/busqueda/eliminacion"));
        System.out.println(AnsiColors.gris("  - Cola: O(1) - enqueue/dequeue"));
        System.out.println(AnsiColors.gris("  - Heap: O(log n) - insertar/extraer"));
        System.out.println(AnsiColors.gris("  - Merge: O(n + m) - consolidacion"));
    }
    
    // ========== OPCION 9: TESTS ==========
    private void ejecutarTests() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  TESTS DEL SISTEMA"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.println(AnsiColors.naranja("\n1. Test TablaHash..."));
        testTablaHash();
        
        System.out.println(AnsiColors.naranja("\n2. Test Cola Circular..."));
        testColaCircular();
        
        System.out.println(AnsiColors.naranja("\n3. Test Consolidacion..."));
        consolidarAgendas();
        
        System.out.println(AnsiColors.verde("\nTests completados"));
    }
    
    private void testTablaHash() {
        IndicePacientes testHash = new IndicePacientes(100);
        long inicio = System.nanoTime();
        
        for (int i = 0; i < 1000; i++) {
            String dni = String.format("TEST-%06d", i);
            Paciente p = new Paciente(dni, "Paciente Test " + i);
            testHash.put(dni, p);
        }
        
        long fin = System.nanoTime();
        double ms = (fin - inicio) / 1_000_000.0;
        
        System.out.println(AnsiColors.verde("  1000 inserciones en " + String.format("%.2f", ms) + " ms"));
        System.out.println(AnsiColors.cyan("  Promedio: " + String.format("%.4f", ms/1000) + " ms/insercion"));
        System.out.println(AnsiColors.cyan("  Load Factor: " + String.format("%.2f", testHash.getLoadFactor())));
    }
    
    private void testColaCircular() {
        SalaEspera testCola = new SalaEspera(5);
        
        System.out.println(AnsiColors.blanco("  Agregando 7 elementos a cola de capacidad 5:"));
        for (int i = 1; i <= 7; i++) {
            String dni = "TEST-" + i;
            testCola.llega(dni);
            System.out.println(AnsiColors.cyan("    " + i + ". ") + dni + 
                              (i > 5 ? AnsiColors.amarillo(" (overflow)") : ""));
        }
        
        System.out.println(AnsiColors.verde("  Estado final: " + testCola.size() + "/5"));
    }
    
    private void esperarEnter() {
        System.out.print(AnsiColors.gris("\nPresione Enter para continuar..."));
        scanner.nextLine();
    }
    
    public static void main(String[] args) {
        IntegradorMenu menu = new IntegradorMenu();
        menu.mostrar();
    }
}
