# Sistema Integrador - Gestión de Turnos Médicos

Sistema completo de gestión de turnos médicos implementando estructuras de datos avanzadas.

## Estructura del Proyecto

```
src/core/integrador/
├── modelo/                  # Modelos de dominio
│   ├── Paciente.java
│   ├── Medico.java
│   ├── Turno.java
│   ├── Recordatorio.java
│   └── SolicitudCirugia.java
│
├── agenda/                  # Sistema de agendas (Ejercicios 2, 3, 9)
│   ├── AgendaMedico.java           # Interface principal
│   ├── BuscadorHuecos.java         # Búsqueda de huecos libres
│   ├── AgendaConHistorial.java    # Con Undo/Redo
│   └── impl/                       # Implementaciones
│
├── salaespera/             # Sala de espera (Ejercicio 4)
│   └── SalaEspera.java            # Cola circular con overflow
│
├── recordatorios/          # Sistema de recordatorios (Ejercicio 5)
│   ├── PlanificadorRecordatorios.java  # Interface
│   └── impl/                          # Implementación con min-heap
│
├── pacientes/              # Gestión de pacientes (Ejercicio 6)
│   ├── MapaPacientes.java         # Interface
│   └── impl/                      # Hash con chaining + rehash
│
├── merge/                  # Consolidación de agendas (Ejercicio 7)
│   ├── ConsolidadorAgendas.java   # Interface
│   └── impl/                      # Merge con deduplicación
│
├── reportes/               # Reportes con ordenamiento (Ejercicio 8)
│   ├── GeneradorReportes.java     # Interface
│   └── impl/                      # Inserción, Shellsort, Quicksort
│
├── quirofano/              # Planificador de quirófanos (Ejercicio 10)
│   ├── PlanificadorQuirofano.java # Interface
│   └── impl/                      # Heaps para asignación y top-K
│
└── carga/                  # Carga inicial de datos (Ejercicio 1)
    ├── CargadorDatos.java         # Interface
    └── impl/                      # Parseo CSV + validaciones
```

## Ejercicios y Complejidades

### Ejercicio 1: Carga Inicial y Validaciones
- **Componente**: `carga/CargadorDatos`
- **Función**: Parsear CSV y validar dominio
- **Validaciones**: Existencia, fechas futuras, duplicados

### Ejercicio 2: Agenda por Médico
- **Componente**: `agenda/AgendaMedico`
- **Estructura**: Árbol AVL por fechaHora
- **Complejidades**: insert O(log n), remove O(log n), siguiente O(log n)
- **Invariante**: No doble booking

### Ejercicio 3: Búsqueda de Huecos
- **Componente**: `agenda/BuscadorHuecos`
- **Función**: Encontrar primer hueco libre ≥ duración
- **Complejidad**: O(log n + k) donde k = turnos saltados

### Ejercicio 4: Sala de Espera
- **Componente**: `salaespera/SalaEspera`
- **Estructura**: Cola circular con overflow control
- **Complejidades**: Todas O(1)
- **Detalles**: Índices circulares, pisa más antiguo si llena

### Ejercicio 5: Recordatorios
- **Componente**: `recordatorios/PlanificadorRecordatorios`
- **Estructura**: Min-heap por fecha
- **Complejidades**: push O(log n), pop O(log n), reprogramar O(log n)

### Ejercicio 6: Índice de Pacientes
- **Componente**: `pacientes/MapaPacientes`
- **Estructura**: Hash con chaining
- **Complejidades**: O(1) promedio
- **Detalles**: Rehash cuando loadFactor > 0.75

### Ejercicio 7: Consolidación de Agendas
- **Componente**: `merge/ConsolidadorAgendas`
- **Función**: Merge de dos agendas ordenadas
- **Complejidad**: O(|A| + |B|)
- **Detalles**: Deduplicación por ID y superposición

### Ejercicio 8: Reportes Operativos
- **Componente**: `reportes/GeneradorReportes`
- **Algoritmos**: 
  - Inserción (estable) - por hora
  - Shellsort (gap sequence) - por duración
  - Quicksort (Lomuto) - por apellido
- **Mediciones**: 1k, 10k, 50k turnos

### Ejercicio 9: Undo/Redo
- **Componente**: `agenda/AgendaConHistorial`
- **Estructura**: Dos pilas (acciones y rehacer)
- **Operaciones**: agendar, cancelar, reprogramar con undo/redo

### Ejercicio 10: Planificador de Quirófano
- **Componente**: `quirofano/PlanificadorQuirofano`
- **Estructuras**:
  - Min-heap de quirófanos (por finOcupado)
  - Min-heap tamaño K (top-K médicos)
- **Complejidad**: O(log Q + log K) por evento

## Decisiones de Diseño

### Hash Function (Ejercicio 6)
**Elección**: Polynomial rolling hash con primo 31
```java
hash = (31 * hash + c) % capacidad
```
**Justificación**: Buena distribución para strings, bajo costo, usado en Java String.hashCode()

### Árbol de Balance (Ejercicio 2)
**Elección**: AVL
**Justificación**: 
- Garantiza O(log n) en peor caso
- Mejor para lecturas frecuentes (siguiente turno)
- Balance más estricto que Red-Black

### Gap Sequence (Ejercicio 8)
**Elección**: Knuth's sequence: ..., 364, 121, 40, 13, 4, 1
**Justificación**: Complejidad probada O(n^1.5), buena performance práctica

### Horarios Laborales (Ejercicio 3)
**Default**: 08:00 - 18:00 lunes a viernes
**Configurables**: Por médico/especialidad

## Tests Críticos

### Ejercicio 2 (Agenda)
- Inserciones desbalanceantes
- Cancelación hoja/intermedio/raíz
- siguiente() al borde de día

### Ejercicio 4 (Sala Espera)
- Múltiples vueltas de índice
- Overflow con secuencia larga
- Vacía/llena alternadas

### Ejercicio 5 (Recordatorios)
- Reprogramaciones múltiples
- Elementos con misma fecha
- Heapify desde lista

### Ejercicio 6 (Hash)
- Fuerza colisiones
- Rehash al límite
- Eliminación cabeza/medio/cola

### Ejercicio 7 (Merge)
- Duplicados exactos
- Superposiciones parciales
- Agendas desbalanceadas

## Compilación y Ejecución

```bash
# Compilar
javac -d bin src/core/integrador/**/*.java

# Ejecutar tests
java -cp bin views.IntegradorMenu
```

## Notas de Implementación

- **Sin colecciones prohibidas**: No TreeMap, HashMap, PriorityQueue (excepto si el ejercicio lo habilita)
- **Manejo de empates**: Usar ID como tiebreaker cuando fechas iguales
- **Logging de conflictos**: Todos los conflictos de merge/asignación se loguean
- **Validaciones**: Siempre validar precondiciones antes de modificar estado

## Autores

- Equipo Informática 3
- Universidad: [Instituto Universitario Aeronautico]
- Año: 2025
