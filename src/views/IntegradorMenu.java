package views;

import core.integrador.carga.CargadorCSV;
import core.integrador.modelo.*;
import core.integrador.pacientes.IndicePacientes;
import core.integrador.pacientes.GestorPacientes;
import core.integrador.salaespera.SalaEspera;
import core.integrador.salaespera.GestorSalaEspera;
import core.integrador.recordatorios.PlanificadorRecordatorios;
import core.integrador.recordatorios.GestorRecordatorios;
import core.integrador.agenda.AgendaMedicoConHistorial;
import core.integrador.merge.ConsolidadorAgendas;
import core.integrador.quirofano.PlanificadorQuirofanoImpl;
import core.integrador.quirofano.GestorQuirofanos;
import core.integrador.reportes.GestorReportes;
import core.estructuras.listas.ListaEnlazada;
import core.estructuras.hash.TablaHash;
import core.estructuras.nodos.Nodo;
import core.utils.AnsiColors;
import core.utils.InputValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;

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
    private AgendaMedicoConHistorial agendaMedico;
    private SalaEspera salaEspera;
    private PlanificadorRecordatorios planificadorRecordatorios;
    private PlanificadorQuirofanoImpl planificadorQuirofano;
    
    // Gestores para lógica de negocio
    private GestorPacientes gestorPacientes;
    private GestorSalaEspera gestorSala;
    private GestorRecordatorios gestorRecordatorios;
    private GestorQuirofanos gestorQuirofanos;
    private GestorReportes gestorReportes;
    
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
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
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opcion: ", 0, 10);
            
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
                case 10 -> verReportesOperativos();
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
        System.out.println(AnsiColors.naranja("10.") + AnsiColors.blanco(" Reportes Operativos (Ejercicio 8)"));
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
        
        // Agenda con historial - Cargar TODOS los turnos de TODOS los médicos
        agendaMedico = new AgendaMedicoConHistorial();
        Nodo<Turno> nodoTurno = turnos.getHead();
        while (nodoTurno != null) {
            Turno t = nodoTurno.getData();
            agendaMedico.agendar(t);
            nodoTurno = nodoTurno.getNext();
        }
        
        // Cola circular
        salaEspera = new SalaEspera(5);
        
        // Monticulo indexado
        planificadorRecordatorios = new PlanificadorRecordatorios();
        
        // Min-Heap de quirofanos - Inicializar con fecha de la primera cirugía para verlas activas
        LocalDateTime fechaInicio = LocalDateTime.of(2025, 11, 14, 8, 0); // 14/11/2025 08:00
        planificadorQuirofano = new PlanificadorQuirofanoImpl(3, fechaInicio);
        
        // Registrar TODOS los médicos PRIMERO
        Nodo<Medico> nodoMed = medicos.getHead();
        while (nodoMed != null) {
            Medico m = nodoMed.getData();
            planificadorQuirofano.registrarMedico(m.getMatricula(), m.getNombre());
            nodoMed = nodoMed.getNext();
        }
        
        // Luego cargar cirugías de ejemplo (buscar en turnos los que sean cirugías)
        Nodo<Turno> nodoTurnoCir = turnos.getHead();
        while (nodoTurnoCir != null) {
            Turno t = nodoTurnoCir.getData();
            String motivo = t.getMotivo().toLowerCase();
            // Identificar cirugías por el motivo
            if (motivo.contains("cirugia") || motivo.contains("operatorio") || 
                motivo.contains("anestesia") || motivo.contains("preoperatoria")) {
                SolicitudCirugia solicitud = new SolicitudCirugia(
                    t.getId(),
                    t.getMatriculaMedico(),
                    t.getDuracionMin(),
                    t.getFechaHora()
                );
                planificadorQuirofano.procesar(solicitud);
            }
            nodoTurnoCir = nodoTurnoCir.getNext();
        }
        
        // Inicializar gestores (lógica de negocio modularizada)
        gestorPacientes = new GestorPacientes(indicePacientes, pacientes);
        gestorSala = new GestorSalaEspera(salaEspera, indicePacientes);
        gestorRecordatorios = new GestorRecordatorios(planificadorRecordatorios, indicePacientes);
        gestorQuirofanos = new GestorQuirofanos(planificadorQuirofano);
        // Crear una TablaHash de pacientes para reportes
        TablaHash<String, Paciente> pacientesPorDni = new TablaHash<>();
        Nodo<Paciente> nodo = pacientes.getHead();
        while (nodo != null) {
            Paciente p = nodo.getData();
            pacientesPorDni.put(p.getDni(), p);
            nodo = nodo.getNext();
        }
        gestorReportes = new GestorReportes(pacientesPorDni);
    }
    
    // ========== OPCION 1: INDICE DE PACIENTES ==========
    private void verIndicePacientes() {
        boolean continuar = true;
        while (continuar) {
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
            
            // Submenú
            System.out.println("\n" + AnsiColors.naranja("Opciones:"));
            System.out.println(AnsiColors.blanco("1. Ver estructura interna (buckets con colisiones)"));
            System.out.println(AnsiColors.blanco("2. Buscar paciente por DNI"));
            System.out.println(AnsiColors.blanco("3. Agregar nuevo paciente"));
            System.out.println(AnsiColors.blanco("4. Eliminar paciente"));
            System.out.println(AnsiColors.gris("0. Volver al menu principal"));
            
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opcion: ", 0, 4);
            
            switch (opcion) {
                case 1 -> {
                    verBucketsHash();
                    esperarEnter();
                }
                case 2 -> {
                    buscarPaciente();
                    esperarEnter();
                }
                case 3 -> {
                    agregarPaciente();
                    esperarEnter();
                }
                case 4 -> {
                    eliminarPaciente();
                    esperarEnter();
                }
                case 0 -> continuar = false;
            }
        }
    }
    
    private void buscarPaciente() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  BUSCAR PACIENTE"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.print(AnsiColors.blanco("Ingrese DNI del paciente: "));
        String dni = scanner.nextLine().trim();
        
        System.out.println(AnsiColors.amarillo("\nBuscando en TablaHash..."));
        GestorPacientes.ResultadoBusqueda resultado = gestorPacientes.buscar(dni);
        
        if (resultado.encontrado()) {
            Paciente p = resultado.getPaciente();
            System.out.println(AnsiColors.verde("\n Paciente encontrado:"));
            System.out.println(AnsiColors.cyan("  DNI: ") + p.getDni());
            System.out.println(AnsiColors.cyan("  Nombre: ") + p.getNombre());
            System.out.println(AnsiColors.gris("  Tiempo de búsqueda: " + 
                String.format("%.2f", resultado.getTiempoMicrosegundos()) + " μs"));
        } else {
            System.out.println(AnsiColors.rojo("\n Paciente no encontrado"));
            System.out.println(AnsiColors.gris("  Tiempo de búsqueda: " + 
                String.format("%.2f", resultado.getTiempoMicrosegundos()) + " μs"));
        }
    }
    
    private void agregarPaciente() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  AGREGAR NUEVO PACIENTE"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.print(AnsiColors.blanco("DNI: "));
        String dni = scanner.nextLine().trim();
        
        System.out.print(AnsiColors.blanco("Nombre completo: "));
        String nombre = scanner.nextLine().trim();
        
        GestorPacientes.ResultadoOperacion resultado = gestorPacientes.agregar(dni, nombre);
        
        if (resultado.isExito()) {
            System.out.println(AnsiColors.verde("\n✓ " + resultado.getMensaje()));
            GestorPacientes.EstadisticasIndice stats = gestorPacientes.obtenerEstadisticas();
            System.out.println(AnsiColors.cyan("  Total pacientes: ") + stats.getElementos());
            System.out.println(AnsiColors.cyan("  Load Factor: ") + String.format("%.2f", stats.getLoadFactor()));
        } else {
            System.out.println(AnsiColors.rojo("\n✗ " + resultado.getMensaje()));
        }
    }
    
    private void eliminarPaciente() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  ELIMINAR PACIENTE"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.print(AnsiColors.blanco("Ingrese DNI del paciente a eliminar: "));
        String dni = scanner.nextLine().trim();
        
        // Buscar primero para mostrar info
        GestorPacientes.ResultadoBusqueda busqueda = gestorPacientes.buscar(dni);
        
        if (!busqueda.encontrado()) {
            System.out.println(AnsiColors.rojo("\n✗ Error: Paciente no encontrado"));
            return;
        }
        
        Paciente p = busqueda.getPaciente();
        System.out.println(AnsiColors.amarillo("\nPaciente encontrado:"));
        System.out.println(AnsiColors.cyan("  DNI: ") + p.getDni());
        System.out.println(AnsiColors.cyan("  Nombre: ") + p.getNombre());
        
        System.out.print(AnsiColors.rojo("\n¿Confirma eliminación? (S/N): "));
        String confirma = scanner.nextLine().trim().toUpperCase();
        
        if (confirma.equals("S")) {
            GestorPacientes.ResultadoOperacion resultado = gestorPacientes.eliminar(dni);
            if (resultado.isExito()) {
                System.out.println(AnsiColors.verde("\n✓ " + resultado.getMensaje()));
                GestorPacientes.EstadisticasIndice stats = gestorPacientes.obtenerEstadisticas();
                System.out.println(AnsiColors.cyan("  Total pacientes: ") + stats.getElementos());
            } else {
                System.out.println(AnsiColors.rojo("\n✗ " + resultado.getMensaje()));
            }
        } else {
            System.out.println(AnsiColors.amarillo("\n✗ Operación cancelada"));
        }
    }
    
    private void verBucketsHash() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  ESTRUCTURA INTERNA - BUCKETS CON COLISIONES"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.amarillo("Mostrando distribución de elementos en la tabla hash..."));
        System.out.println();
        indicePacientes.display();
    }
    
    // ========== OPCION 2: AGENDA MEDICA ==========
    private void verAgendaMedica() {
        if (medicos.getSize() == 0) {
            System.out.println(AnsiColors.rojo("No hay medicos disponibles"));
            return;
        }
        
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
            System.out.println(AnsiColors.azulNegrita("  AGENDA MEDICA (ARBOL AVL)"));
            System.out.println(AnsiColors.azul("=".repeat(60)));
            
            // Seleccionar médico
            Medico medico = seleccionarMedico();
            if (medico == null) {
                return; // Usuario canceló
            }
            
            System.out.println("\n" + AnsiColors.verde("Medico seleccionado: ") + medico.getNombre() + " - " + medico.getEspecialidad());
            System.out.println(AnsiColors.cyan("Matricula: ") + medico.getMatricula());
            
            // Obtener turnos actuales del AVL (reflejan cambios en tiempo real)
            ListaEnlazada<Turno> turnosActuales = agendaMedico.getAgendaBase().turnosPorMedico(medico.getMatricula());
            System.out.println(AnsiColors.cyan("Turnos agendados: ") + turnosActuales.getSize());
            
            System.out.println("\n" + AnsiColors.naranjaNegrita("TURNOS DEL MEDICO (primeros 10):"));
            System.out.println(AnsiColors.gris("-".repeat(60)));
            
            if (turnosActuales.isEmpty()) {
                System.out.println(AnsiColors.amarillo("  No hay turnos agendados"));
            } else {
                int count = 0;
                Nodo<Turno> nodo = turnosActuales.getHead();
                while (nodo != null && count < 10) {
                    Turno t = nodo.getData();
                    System.out.printf("%s%-8s%s | %s | %s%n",
                        AnsiColors.cyan(""),
                        t.getId(),
                        AnsiColors.blanco(""),
                        t.getFechaHora().format(FORMATO_FECHA),
                        t.getMotivo()
                    );
                    count++;
                    nodo = nodo.getNext();
                }
                if (turnosActuales.getSize() > 10) {
                    System.out.println(AnsiColors.gris("  ... y " + (turnosActuales.getSize() - 10) + " mas"));
                }
            }
            
            System.out.println(AnsiColors.gris("\nComplejidad: O(log n) - insercion, busqueda, eliminacion"));
            
            // Submenú
            System.out.println("\n" + AnsiColors.naranja("Opciones:"));
            System.out.println(AnsiColors.blanco("1. Buscar proximo turno disponible"));
            System.out.println(AnsiColors.blanco("2. Agendar nuevo turno"));
            System.out.println(AnsiColors.blanco("3. Cancelar turno"));
            System.out.println(AnsiColors.blanco("4. Reprogramar turno (con Undo/Redo)"));
            System.out.println(AnsiColors.blanco("5. Deshacer ultima reprogramacion (Undo)"));
            System.out.println(AnsiColors.blanco("6. Rehacer reprogramacion (Redo)"));
            System.out.println(AnsiColors.blanco("7. Cambiar de medico"));
            System.out.println(AnsiColors.gris("0. Volver al menu principal"));
            
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opcion: ", 0, 7);
            
            switch (opcion) {
                case 1 -> {
                    buscarProximoTurnoDisponible(medico);
                    esperarEnter();
                }
                case 2 -> {
                    agendarNuevoTurno(medico);
                    esperarEnter();
                }
                case 3 -> {
                    cancelarTurno(medico);
                    esperarEnter();
                }
                case 4 -> {
                    reprogramarTurnoConHistorial(medico);
                    esperarEnter();
                }
                case 5 -> {
                    deshacerReprogramacion();
                    esperarEnter();
                }
                case 6 -> {
                    rehacerReprogramacion();
                    esperarEnter();
                }
                case 7 -> {
                    // Continúa el loop para volver a seleccionar médico
                }
                case 0 -> continuar = false;
            }
        }
    }
    
    private Medico seleccionarMedico() {
        System.out.println("\n" + AnsiColors.cyan("SELECCIONAR MEDICO"));
        System.out.println(AnsiColors.gris("=".repeat(60)));
        
        // Mostrar lista de médicos
        System.out.println(AnsiColors.naranja("Medicos disponibles:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        ListaEnlazada<Medico> listaMedicos = new ListaEnlazada<>();
        Nodo<Medico> nodo = medicos.getHead();
        int index = 1;
        
        while (nodo != null) {
            Medico m = nodo.getData();
            listaMedicos.insertLast(m);
            System.out.printf("%s%2d.%s %-10s | %-25s | %s%n",
                AnsiColors.cyan(""),
                index++,
                AnsiColors.blanco(""),
                m.getMatricula(),
                m.getNombre(),
                m.getEspecialidad()
            );
            nodo = nodo.getNext();
        }
        
        System.out.println(AnsiColors.gris("-".repeat(60)));
        System.out.println(AnsiColors.gris("0. Cancelar"));
        
        int opcion = InputValidator.leerEnteroEnRango("Seleccione medico (numero): ", 0, listaMedicos.getSize());
        
        if (opcion == 0) {
            return null;
        }
        
        // Obtener médico seleccionado
        nodo = listaMedicos.getHead();
        for (int i = 1; i < opcion && nodo != null; i++) {
            nodo = nodo.getNext();
        }
        
        return nodo != null ? nodo.getData() : null;
    }
    
    private void buscarProximoTurnoDisponible(Medico medico) {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  BUSCAR PROXIMO TURNO DISPONIBLE"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.print(AnsiColors.blanco("Ingrese fecha/hora de referencia (dd/MM/yyyy HH:mm): "));
        String fechaStr = scanner.nextLine();
        
        try {
            LocalDateTime fecha = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            Optional<LocalDateTime> proximoHueco = agendaMedico.getAgendaBase().primerHueco(medico.getMatricula(), fecha, 30); // Duracion por defecto 30 min
            
            if (proximoHueco.isPresent()) {
                LocalDateTime fechaDisponible = proximoHueco.get();
                System.out.println(AnsiColors.verde("\nProximo turno disponible encontrado:"));
                System.out.println(AnsiColors.cyan("  Fecha/Hora: ") + fechaDisponible.format(FORMATO_FECHA));
                System.out.println(AnsiColors.cyan("  Duracion: 30 min"));
            } else {
                System.out.println(AnsiColors.amarillo("\nNo se encontro un hueco disponible"));
            }
        } catch (Exception e) {
            System.out.println(AnsiColors.rojo("Error: Formato de fecha invalido"));
        }
    }
    
    private void reprogramarTurnoConHistorial(Medico medico) {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  REPROGRAMAR TURNO"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        // Mostrar turnos actuales del médico
        ListaEnlazada<Turno> turnosActuales = agendaMedico.getAgendaBase().turnosPorMedico(medico.getMatricula());
        
        if (turnosActuales.isEmpty()) {
            System.out.println(AnsiColors.amarillo("No hay turnos para reprogramar"));
            return;
        }
        
        System.out.println(AnsiColors.naranjaNegrita("TURNOS DISPONIBLES PARA REPROGRAMAR:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        Nodo<Turno> nodo = turnosActuales.getHead();
        int index = 1;
        while (nodo != null) {
            Turno t = nodo.getData();
            System.out.printf("%s%2d.%s %-8s | %s | %s%n",
                AnsiColors.cyan(""),
                index++,
                AnsiColors.blanco(""),
                t.getId(),
                t.getFechaHora().format(FORMATO_FECHA),
                t.getMotivo()
            );
            nodo = nodo.getNext();
        }
        
        System.out.println(AnsiColors.gris("-".repeat(60)));
        System.out.print(AnsiColors.blanco("Ingrese ID del turno a reprogramar: "));
        String idTurno = scanner.nextLine().trim();
        
        System.out.print(AnsiColors.blanco("\nNueva fecha/hora (dd/MM/yyyy HH:mm): "));
        String fechaStr = scanner.nextLine();
        
        try {
            LocalDateTime nuevaFecha = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            
            System.out.println(AnsiColors.amarillo("\nReprogramando..."));
            
            boolean exito = agendaMedico.reprogramar(idTurno, nuevaFecha);
            
            if (exito) {
                System.out.println(AnsiColors.verde("\n✓ Turno reprogramado exitosamente"));
                System.out.println(AnsiColors.verde("  Nueva fecha: ") + nuevaFecha.format(FORMATO_FECHA));
                System.out.println(AnsiColors.gris("\n  (Puede deshacer con opción 3 - Undo)"));
            } else {
                System.out.println(AnsiColors.rojo("\n✗ Error al reprogramar el turno"));
                System.out.println(AnsiColors.amarillo("  Posible conflicto de horario o turno no encontrado"));
            }
        } catch (Exception e) {
            System.out.println(AnsiColors.rojo("✗ Error: Formato de fecha inválido"));
            System.out.println(AnsiColors.gris("  Formato esperado: dd/MM/yyyy HH:mm (ejemplo: 05/11/2025 14:30)"));
        }
    }
    
    private void agendarNuevoTurno(Medico medico) {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  AGENDAR NUEVO TURNO"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.println(AnsiColors.verde("Médico: ") + medico.getNombre());
        
        System.out.print(AnsiColors.blanco("\nDNI del paciente: "));
        String dniPaciente = scanner.nextLine().trim();
        
        // Verificar que el paciente existe
        GestorPacientes.ResultadoBusqueda busqueda = gestorPacientes.buscar(dniPaciente);
        if (!busqueda.encontrado()) {
            System.out.println(AnsiColors.rojo("\n✗ Error: Paciente no encontrado"));
            System.out.println(AnsiColors.amarillo("  Agregue el paciente primero en la opción 1"));
            return;
        }
        
        Paciente paciente = busqueda.getPaciente();
        System.out.println(AnsiColors.verde("Paciente: ") + paciente.getNombre());
        
        System.out.print(AnsiColors.blanco("\nFecha/hora del turno (dd/MM/yyyy HH:mm): "));
        String fechaStr = scanner.nextLine();
        
        System.out.print(AnsiColors.blanco("Duración en minutos: "));
        int duracion = InputValidator.leerEnteroEnRango("", 15, 180);
        scanner.nextLine(); // Consumir newline
        
        System.out.print(AnsiColors.blanco("Motivo de consulta: "));
        String motivo = scanner.nextLine().trim();
        
        try {
            LocalDateTime fechaTurno = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            
            System.out.println(AnsiColors.amarillo("\nAgendando turno..."));
            
            // Generar nuevo ID
            String nuevoId = generarNuevoIdTurno();
            Turno nuevoTurno = new Turno(nuevoId, dniPaciente, medico.getMatricula(), fechaTurno, duracion, motivo);
            
            boolean exito = agendaMedico.agendar(nuevoTurno);
            
            if (exito) {
                // Agregar a la lista global de turnos
                turnos.insertLast(nuevoTurno);
                
                System.out.println(AnsiColors.verde("\n✓ Turno agendado exitosamente"));
                System.out.println(AnsiColors.cyan("  ID: ") + nuevoId);
                System.out.println(AnsiColors.cyan("  Fecha: ") + fechaTurno.format(FORMATO_FECHA));
                System.out.println(AnsiColors.cyan("  Paciente: ") + paciente.getNombre());
                System.out.println(AnsiColors.cyan("  Total turnos: ") + agendaMedico.getAgendaBase().cantidadTurnos());
            } else {
                System.out.println(AnsiColors.rojo("\n✗ Conflicto de horario"));
            }
        } catch (Exception e) {
            System.out.println(AnsiColors.rojo("✗ Error: Formato de fecha inválido"));
            System.out.println(AnsiColors.gris("  Formato esperado: dd/MM/yyyy HH:mm"));
        }
    }
    
    private void cancelarTurno(Medico medico) {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  CANCELAR TURNO"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        ListaEnlazada<Turno> turnosActuales = agendaMedico.getAgendaBase().turnosPorMedico(medico.getMatricula());
        
        if (turnosActuales.isEmpty()) {
            System.out.println(AnsiColors.amarillo("No hay turnos para cancelar"));
            return;
        }
        
        System.out.println(AnsiColors.naranjaNegrita("TURNOS DISPONIBLES PARA CANCELAR:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        Nodo<Turno> nodo = turnosActuales.getHead();
        int index = 1;
        while (nodo != null) {
            Turno t = nodo.getData();
            System.out.printf("%s%2d.%s %-8s | %s | %s%n",
                AnsiColors.cyan(""),
                index++,
                AnsiColors.blanco(""),
                t.getId(),
                t.getFechaHora().format(FORMATO_FECHA),
                t.getMotivo()
            );
            nodo = nodo.getNext();
        }
        
        System.out.println(AnsiColors.gris("-".repeat(60)));
        System.out.print(AnsiColors.blanco("Ingrese ID del turno a cancelar: "));
        String idTurno = scanner.nextLine().trim();
        
        System.out.print(AnsiColors.rojo("\n¿Confirma cancelación? (S/N): "));
        String confirma = scanner.nextLine().trim().toUpperCase();
        
        if (confirma.equals("S")) {
            System.out.println(AnsiColors.amarillo("\nCancelando turno..."));
            
            boolean exito = agendaMedico.cancelar(idTurno);
            
            if (exito) {
                System.out.println(AnsiColors.verde("\n✓ Turno cancelado exitosamente"));
                System.out.println(AnsiColors.cyan("  ID cancelado: ") + idTurno);
                System.out.println(AnsiColors.cyan("  Turnos restantes: ") + agendaMedico.getAgendaBase().cantidadTurnos());
            } else {
                System.out.println(AnsiColors.rojo("\n✗ Error al cancelar el turno"));
            }
        } else {
            System.out.println(AnsiColors.amarillo("\n✗ Operación cancelada"));
        }
    }
    
    private void deshacerReprogramacion() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  DESHACER REPROGRAMACION (UNDO)"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.println(AnsiColors.amarillo("Deshaciendo última reprogramación..."));
        
        boolean exito = agendaMedico.undo();
        
        if (exito) {
            System.out.println(AnsiColors.verde("\n✓ Reprogramación deshecha exitosamente"));
            System.out.println(AnsiColors.gris("  El turno ha vuelto a su fecha original"));
            System.out.println(AnsiColors.gris("  (Puede rehacer con opción 6 - Redo)"));
        } else {
            System.out.println(AnsiColors.amarillo("\n✗ No hay acciones para deshacer"));
            System.out.println(AnsiColors.gris("  La pila de historial está vacía"));
        }
    }
    
    private void rehacerReprogramacion() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  REHACER REPROGRAMACION (REDO)"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.println(AnsiColors.amarillo("Rehaciendo reprogramación..."));
        
        boolean exito = agendaMedico.redo();
        
        if (exito) {
            System.out.println(AnsiColors.verde("\n✓ Reprogramación rehecha exitosamente"));
            System.out.println(AnsiColors.gris("  El turno ha vuelto a la nueva fecha"));
            System.out.println(AnsiColors.gris("  (Puede deshacer nuevamente con opción 3 - Undo)"));
        } else {
            System.out.println(AnsiColors.amarillo("\n✗ No hay acciones para rehacer"));
            System.out.println(AnsiColors.gris("  No hay operaciones deshechas disponibles"));
        }
    }
    
    // ========== OPCION 3: SALA DE ESPERA ==========
    private void verSalaEspera() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
            System.out.println(AnsiColors.azulNegrita("  SALA DE ESPERA (COLA CIRCULAR)"));
            System.out.println(AnsiColors.azul("=".repeat(60)));
            System.out.println(AnsiColors.cyan("Capacidad: ") + salaEspera.getCapacidad());
            System.out.println(AnsiColors.cyan("En espera: ") + salaEspera.size());
            System.out.println(AnsiColors.cyan("Estado: ") + 
                              (salaEspera.isFull() ? AnsiColors.rojo("LLENA") : AnsiColors.verde("Disponible")));
            
            if (!salaEspera.isEmpty()) {
                System.out.println(AnsiColors.verde("Próximo en atención: ") + salaEspera.peek());
            }
            
            System.out.println(AnsiColors.gris("Complejidad: O(1) - enqueue, dequeue"));
            
            // Submenú
            System.out.println("\n" + AnsiColors.naranja("Opciones:"));
            System.out.println(AnsiColors.blanco("1. Agregar paciente a la cola"));
            System.out.println(AnsiColors.blanco("2. Atender siguiente paciente"));
            System.out.println(AnsiColors.blanco("3. Ver todos en espera"));
            System.out.println(AnsiColors.blanco("4. Limpiar sala"));
            System.out.println(AnsiColors.gris("0. Volver al menu principal"));
            
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opcion: ", 0, 4);
            
            switch (opcion) {
                case 1 -> {
                    agregarPacienteSala();
                    esperarEnter();
                }
                case 2 -> {
                    atenderPacienteSala();
                    esperarEnter();
                }
                case 3 -> {
                    verTodosEnEspera();
                    esperarEnter();
                }
                case 4 -> {
                    limpiarSala();
                    esperarEnter();
                }
                case 0 -> continuar = false;
            }
        }
    }
    
    private void agregarPacienteSala() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  AGREGAR PACIENTE A SALA DE ESPERA"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.print(AnsiColors.blanco("DNI del paciente: "));
        String dni = scanner.nextLine().trim();
        
        GestorSalaEspera.ResultadoSala resultado = gestorSala.agregar(dni);
        
        if (resultado.isExito()) {
            Paciente p = resultado.getPaciente();
            System.out.println(AnsiColors.verde("Paciente: ") + p.getNombre());
            System.out.println(AnsiColors.verde("\n✓ " + resultado.getMensaje()));
            GestorSalaEspera.EstadoSala estado = gestorSala.obtenerEstado();
            System.out.println(AnsiColors.cyan("  Posición en cola: ") + estado.getEnEspera());
            System.out.println(AnsiColors.cyan("  Ocupación: ") + estado.getEnEspera() + "/" + estado.getCapacidad());
        } else {
            System.out.println(AnsiColors.rojo("\n✗ " + resultado.getMensaje()));
        }
    }
    
    private void atenderPacienteSala() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  ATENDER PACIENTE"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        GestorSalaEspera.ResultadoAtencion resultado = gestorSala.atender();
        
        if (resultado.isExito()) {
            Paciente p = resultado.getPaciente();
            String nombre = (p != null) ? p.getNombre() : "Desconocido";
            
            System.out.println(AnsiColors.verde("\n✓ Paciente atendido:"));
            System.out.println(AnsiColors.cyan("  DNI: ") + resultado.getDni());
            System.out.println(AnsiColors.cyan("  Nombre: ") + nombre);
            GestorSalaEspera.EstadoSala estado = gestorSala.obtenerEstado();
            System.out.println(AnsiColors.cyan("  Quedan en espera: ") + estado.getEnEspera());
        } else {
            System.out.println(AnsiColors.rojo("\n✗ " + resultado.getMensaje()));
        }
    }
    
    private void verTodosEnEspera() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  PACIENTES EN ESPERA"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        if (salaEspera.isEmpty()) {
            System.out.println(AnsiColors.amarillo("\nNo hay pacientes en espera"));
            return;
        }
        
        System.out.println(AnsiColors.cyan("Total en espera: ") + salaEspera.size() + "/" + salaEspera.getCapacidad());
        
        if (!salaEspera.isEmpty()) {
            String proximo = salaEspera.peek();
            Paciente p = indicePacientes.get(proximo);
            String nombre = (p != null) ? p.getNombre() : "Desconocido";
            
            System.out.println(AnsiColors.verde("\nProximo en atencion:"));
            System.out.println(AnsiColors.cyan("  DNI: ") + proximo);
            System.out.println(AnsiColors.cyan("  Nombre: ") + nombre);
        }
        
        System.out.println(AnsiColors.gris("\nNota: Cola circular - use 'Atender' para ver todos"));
    }
    
    private void limpiarSala() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  LIMPIAR SALA DE ESPERA"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        GestorSalaEspera.EstadoSala estado = gestorSala.obtenerEstado();
        
        if (estado.isVacia()) {
            System.out.println(AnsiColors.amarillo("\nLa sala ya está vacía"));
            return;
        }
        
        System.out.println(AnsiColors.amarillo("\nHay " + estado.getEnEspera() + " pacientes en espera"));
        System.out.print(AnsiColors.rojo("¿Confirma limpiar la sala? (S/N): "));
        String confirma = scanner.nextLine().trim().toUpperCase();
        
        if (confirma.equals("S")) {
            GestorSalaEspera.ResultadoLimpieza resultado = gestorSala.limpiar();
            
            if (resultado.isExito()) {
                System.out.println(AnsiColors.verde("\n✓ " + resultado.getMensaje()));
                System.out.println(AnsiColors.cyan("  Pacientes removidos: ") + resultado.getPacientesRemovidos());
            } else {
                System.out.println(AnsiColors.rojo("\n✗ " + resultado.getMensaje()));
            }
        } else {
            System.out.println(AnsiColors.amarillo("\n✗ Operación cancelada"));
        }
    }
    
    // ========== OPCION 4: RECORDATORIOS ==========
    private void verRecordatorios() {
        // Cargar recordatorios iniciales si está vacío
        if (planificadorRecordatorios.isEmpty()) {
            cargarRecordatoriosIniciales();
        }
        
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
            System.out.println(AnsiColors.azulNegrita("  RECORDATORIOS (MONTICULO INDEXADO)"));
            System.out.println(AnsiColors.azul("=".repeat(60)));
            
            System.out.println(AnsiColors.cyan("Programados: ") + planificadorRecordatorios.size());
            
            if (!planificadorRecordatorios.isEmpty()) {
                Recordatorio proximo = planificadorRecordatorios.peek();
                System.out.println(AnsiColors.verde("Próximo: ") + proximo.getId() + 
                                  " - " + proximo.getFecha().format(FORMATO_FECHA));
            }
            
            System.out.println("\n" + AnsiColors.naranjaNegrita("RECORDATORIOS (primeros 10):"));
            System.out.println(AnsiColors.gris("-".repeat(60)));
            
            List<Recordatorio> recordatorios = planificadorRecordatorios.getRecordatorios();
            int count = 0;
            for (Recordatorio r : recordatorios) {
                if (count >= 10) break;
                System.out.printf("%s%-10s%s | %s | %s%n",
                    AnsiColors.cyan(""),
                    r.getId(),
                    AnsiColors.blanco(""),
                    r.getFecha().format(FORMATO_FECHA),
                    r.getMensaje().substring(0, Math.min(40, r.getMensaje().length()))
                );
                count++;
            }
            
            if (recordatorios.size() > 10) {
                System.out.println(AnsiColors.gris("... y " + (recordatorios.size() - 10) + " más"));
            }
            
            System.out.println(AnsiColors.gris("\nComplejidad: O(log n) - insertar, extraer, reprogramar"));
            
            // Submenú
            System.out.println("\n" + AnsiColors.naranja("Opciones:"));
            System.out.println(AnsiColors.blanco("1. Programar nuevo recordatorio"));
            System.out.println(AnsiColors.blanco("2. Extraer próximo recordatorio"));
            System.out.println(AnsiColors.blanco("3. Reprogramar recordatorio"));
            System.out.println(AnsiColors.blanco("4. Ver todos los recordatorios"));
            System.out.println(AnsiColors.gris("0. Volver al menú principal"));
            
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opción: ", 0, 4);
            
            switch (opcion) {
                case 1 -> {
                    programarRecordatorio();
                    esperarEnter();
                }
                case 2 -> {
                    extraerRecordatorio();
                    esperarEnter();
                }
                case 3 -> {
                    reprogramarRecordatorio();
                    esperarEnter();
                }
                case 4 -> {
                    verTodosRecordatorios();
                    esperarEnter();
                }
                case 0 -> continuar = false;
            }
        }
    }
    
    private void cargarRecordatoriosIniciales() {
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
    
    private void programarRecordatorio() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  PROGRAMAR NUEVO RECORDATORIO"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.print(AnsiColors.blanco("DNI del paciente: "));
        String dni = scanner.nextLine().trim();
        
        System.out.print(AnsiColors.blanco("Fecha/hora (dd/MM/yyyy HH:mm): "));
        String fechaStr = scanner.nextLine();
        
        System.out.print(AnsiColors.blanco("Mensaje: "));
        String mensaje = scanner.nextLine().trim();
        
        try {
            LocalDateTime fecha = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            
            GestorRecordatorios.ResultadoRecordatorio resultado = gestorRecordatorios.programar(dni, fecha, mensaje);
            
            if (resultado.isExito()) {
                Recordatorio r = resultado.getRecordatorio();
                System.out.println(AnsiColors.verde("\n✓ " + resultado.getMensaje()));
                System.out.println(AnsiColors.cyan("  ID: ") + r.getId());
                System.out.println(AnsiColors.cyan("  Fecha: ") + fecha.format(FORMATO_FECHA));
                GestorRecordatorios.EstadisticasRecordatorios stats = gestorRecordatorios.obtenerEstadisticas();
                System.out.println(AnsiColors.cyan("  Total: ") + stats.getTotalRecordatorios());
            } else {
                System.out.println(AnsiColors.rojo("\n✗ " + resultado.getMensaje()));
            }
        } catch (Exception e) {
            System.out.println(AnsiColors.rojo("✗ Error: Formato de fecha inválido"));
        }
    }
    
    private void extraerRecordatorio() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  EXTRAER PRÓXIMO RECORDATORIO"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        GestorRecordatorios.ResultadoExtraccion resultado = gestorRecordatorios.extraer();
        
        if (resultado.isExito()) {
            Recordatorio r = resultado.getRecordatorio();
            System.out.println(AnsiColors.verde("\n✓ Recordatorio extraído:"));
            System.out.println(AnsiColors.cyan("  ID: ") + r.getId());
            System.out.println(AnsiColors.cyan("  Fecha: ") + r.getFecha().format(FORMATO_FECHA));
            System.out.println(AnsiColors.cyan("  Paciente: ") + r.getDniPaciente());
            System.out.println(AnsiColors.cyan("  Mensaje: ") + r.getMensaje());
            GestorRecordatorios.EstadisticasRecordatorios stats = gestorRecordatorios.obtenerEstadisticas();
            System.out.println(AnsiColors.cyan("  Quedan: ") + stats.getTotalRecordatorios());
        } else {
            System.out.println(AnsiColors.rojo("\n✗ " + resultado.getMensaje()));
        }
    }
    
    private void reprogramarRecordatorio() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  REPROGRAMAR RECORDATORIO"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.print(AnsiColors.blanco("ID del recordatorio: "));
        String id = scanner.nextLine().trim();
        
        System.out.print(AnsiColors.blanco("\nNueva fecha/hora (dd/MM/yyyy HH:mm): "));
        String fechaStr = scanner.nextLine();
        
        try {
            LocalDateTime nuevaFecha = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            
            GestorRecordatorios.ResultadoReprogramacion resultado = gestorRecordatorios.reprogramar(id, nuevaFecha);
            
            if (resultado.isExito()) {
                System.out.println(AnsiColors.verde("\n✓ " + resultado.getMensaje()));
                System.out.println(AnsiColors.cyan("  Fecha anterior: ") + resultado.getFechaAnterior().format(FORMATO_FECHA));
                System.out.println(AnsiColors.verde("  Nueva fecha: ") + nuevaFecha.format(FORMATO_FECHA));
            } else {
                System.out.println(AnsiColors.rojo("\n✗ " + resultado.getMensaje()));
            }
        } catch (Exception e) {
            System.out.println(AnsiColors.rojo("✗ Error: Formato de fecha inválido"));
        }
    }
    
    private void verTodosRecordatorios() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  TODOS LOS RECORDATORIOS"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        if (planificadorRecordatorios.isEmpty()) {
            System.out.println(AnsiColors.amarillo("\nNo hay recordatorios programados"));
            return;
        }
        
        List<Recordatorio> recordatorios = planificadorRecordatorios.getRecordatorios();
        
        System.out.println(AnsiColors.naranjaNegrita("LISTA COMPLETA:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        int pos = 1;
        for (Recordatorio r : recordatorios) {
            System.out.printf("%s%2d.%s %-10s | %s | %s%n",
                AnsiColors.cyan(""),
                pos++,
                AnsiColors.blanco(""),
                r.getId(),
                r.getFecha().format(FORMATO_FECHA),
                r.getMensaje()
            );
        }
        
        System.out.println(AnsiColors.gris("-".repeat(60)));
        System.out.println(AnsiColors.cyan("Total: ") + recordatorios.size());
    }
    
    // ========== OPCION 5: QUIROFANOS ==========
    private void verQuirofanos() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
            System.out.println(AnsiColors.azulNegrita("  PLANIFICADOR DE QUIROFANOS (MIN-HEAP)"));
            System.out.println(AnsiColors.azul("=".repeat(60)));
            
            planificadorQuirofano.mostrarEstadoQuirofanos();
            
            System.out.println("\n" + AnsiColors.naranjaNegrita("TOP-3 MEDICOS CON MAS MINUTOS PROGRAMADOS:"));
            System.out.println(AnsiColors.gris("-".repeat(60)));
            
            ListaEnlazada<String> topK = planificadorQuirofano.topKMedicosBloqueados(3);
            int pos = 1;
            Nodo<String> nodoTopK = topK.getHead();
            while (nodoTopK != null) {
                System.out.println(pos++ + ". " + nodoTopK.getData());
                nodoTopK = nodoTopK.getNext();
            }
            
            System.out.println(AnsiColors.gris("\nComplejidad por evento: O(log Q + log K)"));
            System.out.println(AnsiColors.gris("  • log Q = asignación de quirófano (Min-Heap)"));
            System.out.println(AnsiColors.gris("  • log K = actualización top-K médicos"));
            
            // Submenú
            System.out.println("\n" + AnsiColors.naranja("Opciones:"));
            System.out.println(AnsiColors.blanco("1. Procesar nueva cirugía"));
            System.out.println(AnsiColors.blanco("2. Ver estado detallado"));
            System.out.println(AnsiColors.blanco("3. Avanzar/Cambiar fecha del sistema"));
            System.out.println(AnsiColors.gris("0. Volver al menú principal"));
            
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opción: ", 0, 3);
            
            switch (opcion) {
                case 1 -> {
                    procesarNuevaCirugia();
                    esperarEnter();
                }
                case 2 -> {
                    verEstadoDetalladoQuirofanos();
                    esperarEnter();
                }
                case 3 -> {
                    avanzarFechaSistema();
                    esperarEnter();
                }
                case 0 -> continuar = false;
            }
        }
    }
    
    private void procesarNuevaCirugia() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  PROCESAR NUEVA CIRUGIA"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        // Mostrar médicos disponibles
        System.out.println(AnsiColors.naranjaNegrita("MEDICOS DISPONIBLES:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        int index = 1;
        Nodo<Medico> nodo = medicos.getHead();
        while (nodo != null) {
            Medico m = nodo.getData();
            System.out.printf("%s%2d.%s %-10s | %s%n",
                AnsiColors.cyan(""),
                index++,
                AnsiColors.blanco(""),
                m.getMatricula(),
                m.getNombre()
            );
            nodo = nodo.getNext();
        }
        
        System.out.println(AnsiColors.gris("-".repeat(60)));
        String matricula = InputValidator.leerCadenaNoVacia("Matrícula del médico: ");
        
        // Verificar que el médico existe
        Medico medicoSeleccionado = null;
        nodo = medicos.getHead();
        while (nodo != null) {
            if (nodo.getData().getMatricula().equals(matricula)) {
                medicoSeleccionado = nodo.getData();
                break;
            }
            nodo = nodo.getNext();
        }
        
        if (medicoSeleccionado == null) {
            System.out.println(AnsiColors.rojo("\n✗ Médico no encontrado"));
            return;
        }
        
        System.out.println(AnsiColors.verde("Médico: ") + medicoSeleccionado.getNombre());
        
        System.out.print(AnsiColors.blanco("\nID de la cirugía: "));
        String idCirugia = InputValidator.leerCadenaNoVacia("");
        
        int duracion = InputValidator.leerEnteroEnRango("Duración en minutos (30-480): ", 30, 480);
        
        System.out.print(AnsiColors.blanco("Fecha/hora programada (dd/MM/yyyy HH:mm): "));
        String fechaStr = InputValidator.getScanner().nextLine();
        
        try {
            LocalDateTime fecha = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            
            SolicitudCirugia cirugia = new SolicitudCirugia(idCirugia, matricula, duracion, fecha);
            
            System.out.println(AnsiColors.amarillo("\nProcesando solicitud..."));
            
            GestorQuirofanos.ResultadoCirugia resultado = gestorQuirofanos.procesar(cirugia);
            
            if (resultado.isExito()) {
                System.out.println(AnsiColors.verde("\n✓ " + resultado.getMensaje()));
                System.out.println(AnsiColors.cyan("  ID: ") + idCirugia);
                System.out.println(AnsiColors.cyan("  Médico: ") + medicoSeleccionado.getNombre());
                System.out.println(AnsiColors.cyan("  Duración: ") + duracion + " min");
            } else {
                System.out.println(AnsiColors.rojo("\n✗ " + resultado.getMensaje()));
            }
        } catch (Exception e) {
            System.out.println(AnsiColors.rojo("✗ Error: Formato de fecha inválido"));
        }
    }
    
    private void avanzarFechaSistema() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  AVANZAR/CAMBIAR FECHA DEL SISTEMA"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.print(AnsiColors.blanco("Ingrese nueva fecha/hora (dd/MM/yyyy HH:mm): "));
        String fechaStr = scanner.nextLine().trim();
        
        try {
            LocalDateTime nuevaFecha = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            
            System.out.println(AnsiColors.amarillo("\nAvanzando tiempo del sistema..."));
            
            GestorQuirofanos.ResultadoAvance resultado = gestorQuirofanos.avanzarTiempo(nuevaFecha);
            
            if (resultado.isExito()) {
                System.out.println(AnsiColors.verde("\n✓ " + resultado.getMensaje()));
                System.out.println(AnsiColors.cyan("  Nueva fecha del sistema: ") + nuevaFecha.format(FORMATO_FECHA));
                System.out.println(AnsiColors.amarillo("\n  Los quirófanos se actualizaron según la nueva fecha"));
            } else {
                System.out.println(AnsiColors.rojo("\n✗ " + resultado.getMensaje()));
            }
        } catch (Exception e) {
            System.out.println(AnsiColors.rojo("✗ Error: Formato de fecha inválido. Use dd/MM/yyyy HH:mm"));
        }
    }
    
    private void verEstadoDetalladoQuirofanos() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  ESTADO DETALLADO DE QUIROFANOS"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        planificadorQuirofano.mostrarEstadoQuirofanos();
        
        System.out.println("\n" + AnsiColors.naranjaNegrita("RANKING COMPLETO DE MEDICOS:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        int K = medicos.getSize();
        ListaEnlazada<String> ranking = planificadorQuirofano.topKMedicosBloqueados(K);
        int pos = 1;
        Nodo<String> nodo = ranking.getHead();
        
        while (nodo != null) {
            System.out.println(pos++ + ". " + nodo.getData());
            nodo = nodo.getNext();
        }
    }
    
    // ========== OPCION 6: CONSOLIDACION ==========
    private void consolidarAgendas() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  CONSOLIDACION DE AGENDAS (MERGE)"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        // Cargar agendas desde archivos CSV
        ListaEnlazada<Turno> agendaLocal = new ListaEnlazada<>();
        ListaEnlazada<Turno> agendaNube = new ListaEnlazada<>();
        
        try {
            System.out.println(AnsiColors.cyan("Cargando agendas..."));
            
            // Crear cargadores independientes para cada agenda
            CargadorCSV cargadorLocal = new CargadorCSV();
            CargadorCSV cargadorNube = new CargadorCSV();
            
            // Cargar pacientes y médicos (necesarios para validar turnos)
            // Reusar los datos ya cargados del sistema
            cargadorLocal.loadPacientes(CargadorCSV.DEFAULT_PACIENTES);
            cargadorLocal.loadMedicos(CargadorCSV.DEFAULT_MEDICOS);
            
            cargadorNube.loadPacientes(CargadorCSV.DEFAULT_PACIENTES);
            cargadorNube.loadMedicos(CargadorCSV.DEFAULT_MEDICOS);
            
            // Cargar agenda local
            cargadorLocal.loadTurnos("src/core/integrador/datos/turnos_local.csv");
            agendaLocal = cargadorLocal.getTurnos();
            System.out.println(AnsiColors.verde("✓ Agenda Local: ") + agendaLocal.getSize() + " turnos");
            
            // Cargar agenda nube
            cargadorNube.loadTurnos("src/core/integrador/datos/turnos_nube.csv");
            agendaNube = cargadorNube.getTurnos();
            System.out.println(AnsiColors.cyan("✓ Agenda Nube: ") + agendaNube.getSize() + " turnos");
            
        } catch (Exception e) {
            System.out.println(AnsiColors.rojo("✗ Error al cargar agendas: ") + e.getMessage());
            return;
        }
        
        System.out.println("\n" + AnsiColors.amarillo("Ejecutando merge..."));
        
        // Realizar merge
        ConsolidadorAgendas.ResultadoMerge resultado = ConsolidadorAgendas.merge(agendaLocal, agendaNube);
        
        System.out.println("\n" + AnsiColors.verde("═".repeat(60)));
        System.out.println(AnsiColors.verde("  CONSOLIDACION COMPLETADA"));
        System.out.println(AnsiColors.verde("═".repeat(60)));
        System.out.println(AnsiColors.cyan("Turnos consolidados: ") + resultado.cantidadTurnos());
        System.out.println(AnsiColors.amarillo("Conflictos detectados: ") + resultado.cantidadConflictos());
        
        if (resultado.cantidadConflictos() > 0) {
            System.out.println("\n" + AnsiColors.rojo("CONFLICTOS ENCONTRADOS:"));
            Nodo<String> conflictoNodo = resultado.getConflictos().getHead();
            int num = 1;
            while (conflictoNodo != null) {
                System.out.println(AnsiColors.amarillo(num + ". ") + conflictoNodo.getData());
                conflictoNodo = conflictoNodo.getNext();
                num++;
            }
        }
        
        System.out.println("\n" + AnsiColors.gris("Complejidad: O(n + m) - merge lineal"));
    }
    
    // ========== OPCION 7: DATOS COMPLETOS ==========
    private void verDatosCompletos() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
            System.out.println(AnsiColors.azulNegrita("  DATOS COMPLETOS"));
            System.out.println(AnsiColors.azul("=".repeat(60)));
            
            System.out.println(AnsiColors.cyan("Total Pacientes: ") + pacientes.getSize());
            System.out.println(AnsiColors.cyan("Total Médicos: ") + medicos.getSize());
            System.out.println(AnsiColors.cyan("Total Turnos: ") + turnos.getSize());
            
            // Submenú
            System.out.println("\n" + AnsiColors.naranja("Opciones:"));
            System.out.println(AnsiColors.blanco("1. Ver todos los pacientes"));
            System.out.println(AnsiColors.blanco("2. Ver todos los médicos"));
            System.out.println(AnsiColors.blanco("3. Ver todos los turnos"));
            System.out.println(AnsiColors.blanco("4. Buscar por DNI/matrícula"));
            System.out.println(AnsiColors.blanco("5. Filtrar médicos por especialidad"));
            System.out.println(AnsiColors.gris("0. Volver al menú principal"));
            
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opción: ", 0, 5);
            
            switch (opcion) {
                case 1 -> {
                    verTodosPacientes();
                    esperarEnter();
                }
                case 2 -> {
                    verTodosMedicos();
                    esperarEnter();
                }
                case 3 -> {
                    verTodosTurnos();
                    esperarEnter();
                }
                case 4 -> {
                    buscarPorDniMatricula();
                    esperarEnter();
                }
                case 5 -> {
                    filtrarPorEspecialidad();
                    esperarEnter();
                }
                case 0 -> continuar = false;
            }
        }
    }
    
    private void verTodosPacientes() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  TODOS LOS PACIENTES"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.println(AnsiColors.naranjaNegrita("LISTADO COMPLETO (" + pacientes.getSize() + "):"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        int count = 1;
        Nodo<Paciente> nodo = pacientes.getHead();
        while (nodo != null) {
            Paciente p = nodo.getData();
            System.out.printf("%s%3d.%s %-12s | %s%n",
                AnsiColors.cyan(""),
                count++,
                AnsiColors.blanco(""),
                p.getDni(),
                p.getNombre()
            );
            nodo = nodo.getNext();
        }
    }
    
    private void verTodosMedicos() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  TODOS LOS MEDICOS"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.println(AnsiColors.naranjaNegrita("LISTADO COMPLETO (" + medicos.getSize() + "):"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        int count = 1;
        Nodo<Medico> nodo = medicos.getHead();
        while (nodo != null) {
            Medico m = nodo.getData();
            System.out.printf("%s%2d.%s %-10s | %-35s | %s%n",
                AnsiColors.cyan(""),
                count++,
                AnsiColors.blanco(""),
                m.getMatricula(),
                m.getNombre(),
                m.getEspecialidad()
            );
            nodo = nodo.getNext();
        }
    }
    
    private void verTodosTurnos() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  TODOS LOS TURNOS"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.println(AnsiColors.naranjaNegrita("LISTADO COMPLETO (" + turnos.getSize() + "):"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        int count = 1;
        Nodo<Turno> nodo = turnos.getHead();
        while (nodo != null) {
            Turno t = nodo.getData();
            System.out.printf("%s%3d.%s %-8s | %s | %s%n",
                AnsiColors.cyan(""),
                count++,
                AnsiColors.blanco(""),
                t.getId(),
                t.getFechaHora().format(FORMATO_FECHA),
                t.getMotivo().substring(0, Math.min(30, t.getMotivo().length()))
            );
            nodo = nodo.getNext();
            
            // Pausar cada 20 líneas
            if (count % 20 == 0) {
                System.out.print(AnsiColors.gris("\nPresione Enter para continuar..."));
                scanner.nextLine();
            }
        }
    }
    
    private void buscarPorDniMatricula() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  BUSCAR POR DNI/MATRICULA"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.print(AnsiColors.blanco("Ingrese DNI o matrícula: "));
        String busqueda = scanner.nextLine().trim();
        
        // Buscar en pacientes
        Paciente paciente = indicePacientes.get(busqueda);
        if (paciente != null) {
            System.out.println(AnsiColors.verde("\n✓ PACIENTE ENCONTRADO:"));
            System.out.println(AnsiColors.cyan("  DNI: ") + paciente.getDni());
            System.out.println(AnsiColors.cyan("  Nombre: ") + paciente.getNombre());
        }
        
        // Buscar en médicos
        Nodo<Medico> nodo = medicos.getHead();
        while (nodo != null) {
            Medico m = nodo.getData();
            if (m.getMatricula().equals(busqueda)) {
                System.out.println(AnsiColors.verde("\n✓ MEDICO ENCONTRADO:"));
                System.out.println(AnsiColors.cyan("  Matrícula: ") + m.getMatricula());
                System.out.println(AnsiColors.cyan("  Nombre: ") + m.getNombre());
                System.out.println(AnsiColors.cyan("  Especialidad: ") + m.getEspecialidad());
                break;
            }
            nodo = nodo.getNext();
        }
        
        if (paciente == null && nodo == null) {
            System.out.println(AnsiColors.rojo("\n✗ No se encontraron resultados"));
        }
    }
    
    private void filtrarPorEspecialidad() {
        System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
        System.out.println(AnsiColors.azulNegrita("  FILTRAR POR ESPECIALIDAD"));
        System.out.println(AnsiColors.azul("=".repeat(60)));
        
        System.out.print(AnsiColors.blanco("Especialidad a buscar: "));
        String especialidad = scanner.nextLine().trim();
        
        System.out.println(AnsiColors.naranjaNegrita("\nRESULTADOS:"));
        System.out.println(AnsiColors.gris("-".repeat(60)));
        
        int encontrados = 0;
        Nodo<Medico> nodo = medicos.getHead();
        while (nodo != null) {
            Medico m = nodo.getData();
            if (m.getEspecialidad().toLowerCase().contains(especialidad.toLowerCase())) {
                System.out.printf("%-10s | %-35s | %s%n",
                    m.getMatricula(),
                    m.getNombre(),
                    m.getEspecialidad()
                );
                encontrados++;
            }
            nodo = nodo.getNext();
        }
        
        if (encontrados == 0) {
            System.out.println(AnsiColors.amarillo("No se encontraron médicos con esa especialidad"));
        } else {
            System.out.println(AnsiColors.gris("-".repeat(60)));
            System.out.println(AnsiColors.cyan("Total encontrados: ") + encontrados);
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
        System.out.println(AnsiColors.gris("      Turnos: ") + agendaMedico.getAgendaBase().cantidadTurnos());
        
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
    
    // ========== OPCION 10: REPORTES OPERATIVOS ==========
    
    private void verReportesOperativos() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n" + AnsiColors.azul("=".repeat(60)));
            System.out.println(AnsiColors.azulNegrita("  REPORTES OPERATIVOS - EJERCICIO 8"));
            System.out.println(AnsiColors.azul("=".repeat(60)));
            System.out.println(AnsiColors.naranja("1.") + AnsiColors.blanco(" Reporte por Hora (Insertion Sort - Estable)"));
            System.out.println(AnsiColors.naranja("2.") + AnsiColors.blanco(" Reporte por Duración (Shell Sort - Eficiente)"));
            System.out.println(AnsiColors.naranja("3.") + AnsiColors.blanco(" Reporte por Apellido (Quick Sort - Rápido)"));
            System.out.println(AnsiColors.naranja("4.") + AnsiColors.blanco(" Comparar Algoritmos (todos con timing)"));
            System.out.println(AnsiColors.gris("0.") + AnsiColors.gris(" Volver al menú principal"));
            System.out.println(AnsiColors.azul("=".repeat(60)));
            
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opción: ", 0, 4);
            
            switch (opcion) {
                case 1 -> {
                    generarReportePorHora();
                    esperarEnter();
                }
                case 2 -> {
                    generarReportePorDuracion();
                    esperarEnter();
                }
                case 3 -> {
                    generarReportePorApellido();
                    esperarEnter();
                }
                case 4 -> {
                    compararAlgoritmos();
                    esperarEnter();
                }
                case 0 -> continuar = false;
            }
        }
    }
    
    private void generarReportePorHora() {
        System.out.println("\n" + AnsiColors.verde("=".repeat(60)));
        System.out.println(AnsiColors.verde("  REPORTE POR HORA - INSERTION SORT (ESTABLE)"));
        System.out.println(AnsiColors.verde("=".repeat(60)));
        
        ListaEnlazada<Turno> turnosDelDia = obtenerTurnosDelDia();
        
        if (turnosDelDia.isEmpty()) {
            System.out.println(AnsiColors.amarillo("No hay turnos para mostrar"));
            return;
        }
        
        System.out.println(AnsiColors.amarillo("Ordenando turnos por hora usando Insertion Sort..."));
        
        GestorReportes.ResultadoReporte resultado = gestorReportes.reportePorHora(turnosDelDia);
        
        mostrarReporteTurnos(resultado.getTurnos(), "HORA", resultado.getTiempoMs());
    }
    
    private void generarReportePorDuracion() {
        System.out.println("\n" + AnsiColors.verde("=".repeat(60)));
        System.out.println(AnsiColors.verde("  REPORTE POR DURACIÓN - SHELL SORT (EFICIENTE)"));
        System.out.println(AnsiColors.verde("=".repeat(60)));
        
        ListaEnlazada<Turno> turnosDelDia = obtenerTurnosDelDia();
        
        if (turnosDelDia.isEmpty()) {
            System.out.println(AnsiColors.amarillo("No hay turnos para mostrar"));
            return;
        }
        
        System.out.println(AnsiColors.amarillo("Ordenando turnos por duración usando Shell Sort..."));
        
        GestorReportes.ResultadoReporte resultado = gestorReportes.reportePorDuracion(turnosDelDia);
        
        mostrarReporteTurnos(resultado.getTurnos(), "DURACIÓN", resultado.getTiempoMs());
    }
    
    private void generarReportePorApellido() {
        System.out.println("\n" + AnsiColors.verde("=".repeat(60)));
        System.out.println(AnsiColors.verde("  REPORTE POR APELLIDO - QUICK SORT (RÁPIDO)"));
        System.out.println(AnsiColors.verde("=".repeat(60)));
        
        ListaEnlazada<Turno> turnosDelDia = obtenerTurnosDelDia();
        
        if (turnosDelDia.isEmpty()) {
            System.out.println(AnsiColors.amarillo("No hay turnos para mostrar"));
            return;
        }
        
        System.out.println(AnsiColors.amarillo("Ordenando turnos por apellido del paciente usando Quick Sort..."));
        
        GestorReportes.ResultadoReporte resultado = gestorReportes.reportePorApellido(turnosDelDia);
        
        mostrarReporteTurnos(resultado.getTurnos(), "APELLIDO PACIENTE", resultado.getTiempoMs());
    }
    
    private void compararAlgoritmos() {
        System.out.println("\n" + AnsiColors.cyan("=".repeat(70)));
        System.out.println(AnsiColors.cyan("  COMPARACIÓN DE ALGORITMOS DE ORDENAMIENTO"));
        System.out.println(AnsiColors.cyan("=".repeat(70)));
        
        ListaEnlazada<Turno> turnosDelDia = obtenerTurnosDelDia();
        
        if (turnosDelDia.isEmpty()) {
            System.out.println(AnsiColors.amarillo("No hay turnos para mostrar"));
            return;
        }
        
        int cantidadTurnos = turnosDelDia.getSize();
        System.out.println(AnsiColors.blanco("Dataset: ") + AnsiColors.cyan(cantidadTurnos + " turnos"));
        System.out.println(AnsiColors.gris("-".repeat(70)));
        
        // Insertion Sort (por hora)
        System.out.print(AnsiColors.blanco("1. Insertion Sort (estable): "));
        GestorReportes.ResultadoReporte resultado1 = gestorReportes.reportePorHora(turnosDelDia);
        System.out.printf(AnsiColors.verde("%.3f ms") + "\n", resultado1.getTiempoMs());
        
        // Shell Sort (por duración)
        System.out.print(AnsiColors.blanco("2. Shell Sort (eficiente):   "));
        GestorReportes.ResultadoReporte resultado2 = gestorReportes.reportePorDuracion(turnosDelDia);
        System.out.printf(AnsiColors.verde("%.3f ms") + "\n", resultado2.getTiempoMs());
        
        // Quick Sort (por apellido)
        System.out.print(AnsiColors.blanco("3. Quick Sort (rápido):      "));
        GestorReportes.ResultadoReporte resultado3 = gestorReportes.reportePorApellido(turnosDelDia);
        System.out.printf(AnsiColors.verde("%.3f ms") + "\n", resultado3.getTiempoMs());
        
        System.out.println(AnsiColors.gris("-".repeat(70)));
        
        // Análisis de rendimiento
        double[] tiempos = {resultado1.getTiempoMs(), resultado2.getTiempoMs(), resultado3.getTiempoMs()};
        String[] algoritmos = {"Insertion Sort", "Shell Sort", "Quick Sort"};
        String[] complejidades = {"O(n²)", "O(n log n)", "O(n log n)"};
        
        double minTiempo = Double.MAX_VALUE;
        int indiceMasRapido = 0;
        
        for (int i = 0; i < tiempos.length; i++) {
            if (tiempos[i] < minTiempo) {
                minTiempo = tiempos[i];
                indiceMasRapido = i;
            }
        }
        
        System.out.println(AnsiColors.amarillo("ANÁLISIS DE RENDIMIENTO:"));
        System.out.printf(AnsiColors.blanco("• Más rápido: ") + AnsiColors.cyan("%s") + 
                         AnsiColors.blanco(" (%.3f ms) - %s\n"),
                         algoritmos[indiceMasRapido], minTiempo, complejidades[indiceMasRapido]);
                         
        for (int i = 0; i < tiempos.length; i++) {
            if (i != indiceMasRapido) {
                double factor = tiempos[i] / minTiempo;
                System.out.printf(AnsiColors.blanco("• %s: ") + AnsiColors.gris("%.1fx más lento\n"),
                                 algoritmos[i], factor);
            }
        }
    }
    
    private ListaEnlazada<Turno> obtenerTurnosDelDia() {
        // Filtrar turnos del día actual (o usar todos si no hay filtro específico)
        return turnos; // Por simplicidad, usar todos los turnos
    }
    
    private void mostrarReporteTurnos(ListaEnlazada<Turno> turnosOrdenados, String criterio, double tiempoMs) {
        System.out.println(AnsiColors.gris("-".repeat(80)));
        System.out.printf(AnsiColors.blanco("ORDENAMIENTO COMPLETADO - ") + AnsiColors.cyan("%.3f ms") + "\n", tiempoMs);
        System.out.printf(AnsiColors.blanco("CRITERIO: ") + AnsiColors.cyan("%s") + "\n", criterio);
        System.out.printf(AnsiColors.blanco("TOTAL: ") + AnsiColors.cyan("%d turnos") + "\n", turnosOrdenados.getSize());
        System.out.println(AnsiColors.gris("-".repeat(80)));
        System.out.printf("%-8s %-12s %-20s %-15s %s\n",
            AnsiColors.amarillo("ID"),
            AnsiColors.amarillo("FECHA/HORA"),
            AnsiColors.amarillo("PACIENTE"),
            AnsiColors.amarillo("DURACIÓN"),
            AnsiColors.amarillo("MOTIVO"));
        System.out.println(AnsiColors.gris("-".repeat(80)));
        
        Nodo<Turno> nodo = turnosOrdenados.getHead();
        int contador = 0;
        
        while (nodo != null && contador < 15) { // Mostrar solo primeros 15
            Turno turno = nodo.getData();
            Paciente paciente = gestorReportes.buscarPaciente(turno.getDniPaciente());
            String nombrePaciente = paciente != null ? paciente.getNombre() : "Desconocido";
            
            System.out.printf("%-8s %-12s %-20s %13d min %s\n",
                AnsiColors.cyan(turno.getId()),
                AnsiColors.blanco(turno.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))),
                AnsiColors.verde(nombrePaciente.length() > 18 ? 
                    nombrePaciente.substring(0, 15) + "..." : nombrePaciente),
                turno.getDuracionMin(),
                AnsiColors.gris(turno.getMotivo().length() > 25 ? 
                    turno.getMotivo().substring(0, 22) + "..." : turno.getMotivo())
            );
            
            nodo = nodo.getNext();
            contador++;
        }
        
        if (turnosOrdenados.getSize() > 15) {
            System.out.println(AnsiColors.gris("... y " + (turnosOrdenados.getSize() - 15) + " turnos más"));
        }
        
        System.out.println(AnsiColors.gris("-".repeat(80)));
    }
    
    private String generarNuevoIdTurno() {
        int maxId = 0;
        Nodo<Turno> nodo = turnos.getHead();
        
        while (nodo != null) {
            String id = nodo.getData().getId();
            if (id.startsWith("T-")) {
                try {
                    int num = Integer.parseInt(id.substring(2));
                    if (num > maxId) maxId = num;
                } catch (NumberFormatException e) {}
            }
            nodo = nodo.getNext();
        }
        
        return String.format("T-%03d", maxId + 1);
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
