# 🏥 Sistema Integrador - Gestión Hospitalaria Completa

Sistema integral de gestión hospitalaria que integra todas las estructuras de datos del curso. Cada componente resuelve un problema real del dominio médico utilizando la estructura de datos óptima según los requisitos de complejidad.

---

## 🎯 Objetivos del Proyecto Integrador

### Objetivos Pedagógicos
1. **Selección apropiada** de estructuras según requisitos funcionales y de performance
2. **Análisis de complejidad** temporal y espacial en contextos reales
3. **Integración** de múltiples estructuras en un sistema cohesivo
4. **Trade-offs** entre diferentes implementaciones y sus justificaciones
5. **Diseño de interfaces** claras con contratos bien definidos
6. **Validación de datos** y manejo robusto de errores
7. **Testing exhaustivo** de casos borde y escenarios complejos

### Habilidades Desarrolladas
- Mapeo de problemas del mundo real a estructuras de datos
- Optimización de operaciones críticas
- Diseño modular con bajo acoplamiento
- Documentación técnica completa con análisis de complejidad
- Testing y validación de invariantes estructurales

---

## 📂 Estructura Completa del Proyecto

```
src/core/integrador/
│
├── modelo/                         # 📦 Modelos de dominio (POJOs)
│   ├── Paciente.java              # DNI (PK), nombre
│   ├── Medico.java                # Matrícula (PK), nombre, especialidad
│   ├── Turno.java                 # ID, DNI paciente, matrícula médico, fecha/hora, duración, motivo
│   ├── Recordatorio.java          # ID, fecha, DNI paciente, mensaje (Comparable por fecha)
│   └── SolicitudCirugia.java      # ID, matrícula médico, duración minutos, deadline
│
├── pacientes/                      # 🗂️ Índice rápido de pacientes
│   ├── MapaPacientes.java         # Interface: put, get, remove, containsKey, size, keys
│   └── IndicePacientes.java       # Implementación con TablaHash<String, Paciente>
│
├── salaespera/                     # 🪑 Cola de espera con capacidad fija
│   └── SalaEspera.java            # Wrapper sobre ColaCircular con overflow control
│
├── recordatorios/                  # ⏰ Sistema de recordatorios temporales
│   ├── Planner.java               # Interface: programar, proximo, reprogramar, size
│   └── PlanificadorRecordatorios.java # MonticuloIndexado + TablaHash para reprogramación O(log n)
│
├── agenda/                         # 📅 Sistema de agendas médicas
│   ├── AgendaMedico.java          # Interface: agendar, cancelar, siguiente
│   ├── AgendaMedicoTree.java      # Implementación con ArbolAVL ordenado por fechaHora
│   ├── AgendaConHistorial.java    # Decorador con Undo/Redo usando pilas
│   └── Historial.java             # Gestor de comandos para deshacer/rehacer
│
├── quirofano/                      # 🏥 Asignación de quirófanos
│   ├── PlanificadorQuirofano.java # Interface: procesar, topKMedicosBloqueados
│   └── PlanificadorQuirofanoImpl.java # Min-heap de quirófanos + top-K con heap
│
├── merge/                          # 🔀 Consolidación de agendas
│   └── ConsolidadorAgendas.java   # Merge de ListaEnlazada ordenadas con deduplicación
│
├── carga/                          # 📥 Carga inicial de datos
│   └── CargadorCSV.java           # Parser de archivos CSV con validaciones de dominio
│
└── datos/                          # 📄 Archivos CSV de prueba
    ├── pacientes.csv              # 10 pacientes (dni, nombre)
    ├── medicos.csv                # 10 médicos (matricula, nombre, especialidad)
    ├── turnos.csv                 # 10 turnos (id, dniPaciente, matriculaMedico, fechaHora, duracionMin, motivo)
    └── README.md                  # Documentación de formatos CSV
```

---

## 📦 Modelos de Dominio

### `Paciente.java`
```java
- String dni        // Primary Key, usado en hash
- String nombre
+ equals(Object)    // Compara por DNI
+ hashCode()        // Basado en DNI para TablaHash
```

**Decisiones de diseño:**
- DNI como identificador único (PK)
- `equals()` y `hashCode()` basados en DNI para búsqueda eficiente
- Sin lógica de negocio, solo datos (POJO puro)

---

### `Medico.java`
```java
- String matricula      // Primary Key (formato: MP#####)
- String nombre
- String especialidad   // Cardiología, Traumatología, etc.
```

**Decisiones de diseño:**
- Matrícula única como identificador
- Especialidad para filtrado y reportes
- Inmutable una vez creado

---

### `Turno.java` (Comparable)
```java
- String id                    // Identificador único (T001, T002, ...)
- String dniPaciente          // FK a Paciente
- String matriculaMedico      // FK a Medico
- LocalDateTime fechaHora     // Fecha y hora de inicio
- int duracionMin             // Duración en minutos (30, 45, 60)
- String motivo               // Motivo de consulta
+ getFechaHoraFin()           // Calcula fechaHora + duracionMin
+ seSuperpone(Turno)          // Verifica solapamiento temporal
+ compareTo(Turno)            // Compara por fechaHora (para AVL)
```

**Decisiones de diseño:**
- Implementa `Comparable<Turno>` para ordenamiento en AVL por fechaHora
- Método `seSuperpone()` para detección de conflictos
- `getFechaHoraFin()` calculado, no almacenado (menor memoria)

---

### `Recordatorio.java` (Comparable)
```java
- String id                   // Identificador único
- LocalDateTime fecha         // Cuándo debe dispararse
- String dniPaciente         // A quién va dirigido
- String mensaje             // Contenido del recordatorio
+ compareTo(Recordatorio)    // Compara por fecha (para Min-Heap)
+ setFecha(LocalDateTime)    // Permite reprogramación
```

**Decisiones de diseño:**
- Implementa `Comparable<Recordatorio>` para Min-Heap por fecha
- Mutable (setFecha) para permitir reprogramación eficiente
- ID único para búsqueda en TablaHash

---

### `SolicitudCirugia.java`
```java
- String id                   // Identificador único
- String matricula           // Médico que realiza la cirugía
- int durMin                 // Duración estimada en minutos
- LocalDateTime deadline     // Fecha límite para realizarla
```

**Decisiones de diseño:**
- Deadline para validación de cumplimiento
- Duración para calcular ocupación de quirófanos
- Médico asociado para estadísticas de uso

---

## 🏗️ Componentes Funcionales - Análisis Detallado

---

## 1️⃣ Índice de Pacientes - `IndicePacientes`

### 🎯 Objetivo
Proporcionar búsqueda O(1) de pacientes por DNI para validaciones rápidas durante la carga de turnos.

### 🔧 Estructura de Datos Utilizada
**TablaHash<String, Paciente>** con encadenamiento (chaining)

### 📊 Complejidades Alcanzadas
| Operación | Complejidad Promedio | Complejidad Peor Caso |
|-----------|---------------------|----------------------|
| put(dni, paciente) | O(1) | O(n) |
| get(dni) | O(1) | O(n) |
| remove(dni) | O(1) | O(n) |
| containsKey(dni) | O(1) | O(n) |
| size() | O(1) | O(1) |
| keys() | O(n) | O(n) |

### 🧮 Función Hash
```java
// Usa el hashCode() nativo de String
hash(dni) = dni.hashCode() % capacidad

// String.hashCode() implementa:
h = 0
for (char c : dni) {
    h = 31 * h + c
}
```

**¿Por qué el primo 31?**
- Distribución uniforme: minimiza colisiones
- Optimización del compilador: `31*h = (h << 5) - h`
- DNIs similares (ej: "12345678" vs "12345679") generan hashes muy diferentes
- Estándar de Java: probado y confiable

### ⚙️ Rehashing Automático
- **Trigger:** loadFactor > 0.75
- **Acción:** duplicar capacidad y redistribuir elementos
- **Complejidad del rehash:** O(n)
- **Justificación:** mantiene operaciones O(1) promedio a largo plazo

### 📝 Decisiones de Diseño

#### ¿Por qué TablaHash y no ArbolAVL?
| Criterio | TablaHash | ArbolAVL |
|----------|-----------|----------|
| Búsqueda por DNI | O(1) promedio | O(log n) |
| Inserción | O(1) promedio | O(log n) |
| Orden | No garantizado | Ordenado |
| Memoria | Más overhead | Menos overhead |
| **Decisión** | ✅ **Óptimo para nuestro caso** | Más complejo sin beneficio |

**Justificación:** Solo necesitamos búsqueda por clave exacta (DNI), no rangos ni orden. O(1) > O(log n).

#### Manejo de Colisiones
- **Método:** Encadenamiento con ListaEnlazada
- **Ventaja:** simple de implementar, sin clustering
- **Desventaja:** overhead de punteros

### 🧪 Casos de Prueba Críticos
```java
// 1. Forzar colisiones con DNIs que dan mismo hash % capacidad
// 2. Provocar rehashing insertando hasta loadFactor > 0.75
// 3. Eliminar de cabeza/medio/cola de cadenas
// 4. Operaciones sobre tabla vacía
// 5. Claves null (debe manejarse correctamente)
```

### 📌 Estadísticas de Rendimiento
```java
indice.getStats() retorna:
- Capacidad actual
- Tamaño (elementos almacenados)
- Load Factor actual
- Buckets utilizados (%)
- Longitud máxima de cadena
- Total de colisiones
```

---

## 2️⃣ Sala de Espera - `SalaEspera`

### 🎯 Objetivo
Gestionar cola de espera con capacidad fija. Cuando está llena, nuevos pacientes reemplazan al más antiguo (overflow automático).

### 🔧 Estructura de Datos Utilizada
**ColaCircular<String>** con modo overflow activado

### 📊 Complejidades Alcanzadas
| Operación | Complejidad |
|-----------|-------------|
| llega(dni) | O(1) |
| atiende() | O(1) |
| peek() | O(1) |
| size() | O(1) |
| isEmpty() | O(1) |
| isFull() | O(1) |

### 🧮 Implementación Circular
```java
// Arreglo circular con índices front y rear
capacidad = 10
arreglo[10]
front = 0     // Índice del próximo a atender
rear = 0      // Índice donde insertar el próximo

// Inserción (enqueue):
arreglo[rear] = dni
rear = (rear + 1) % capacidad  // Índice circular

// Extracción (dequeue):
dni = arreglo[front]
front = (front + 1) % capacidad  // Índice circular
```

**Ventajas del índice circular:**
- Reutiliza espacio liberado al frente
- No necesita desplazar elementos (como en arreglo simple)
- Todas las operaciones son O(1)

### ⚙️ Overflow Control
```java
// Modo: allowOverflow = true
if (isFull() && allowOverflow) {
    // Sobrescribir al más antiguo (front)
    front = (front + 1) % capacidad  // Avanzar front
    size--  // No aumenta tamaño
}
```

**Justificación del overflow:**
- **Contexto:** Sala física con asientos limitados
- **Comportamiento esperado:** El paciente más antiguo se retira (cansado de esperar)
- **Alternativa rechazada:** Rechazar nuevos pacientes → peor experiencia

### 📝 Decisiones de Diseño

#### ¿Por qué ColaCircular y no ColaEnlazada?
| Criterio | ColaCircular | ColaEnlazada |
|----------|--------------|--------------|
| Capacidad fija | ✅ Nativa | ❌ Requiere lógica extra |
| Overflow control | ✅ Fácil de implementar | ❌ Complejo |
| Memoria | O(capacidad) constante | O(n) crece dinámicamente |
| Complejidad operaciones | O(1) | O(1) |
| **Decisión** | ✅ **Óptimo para capacidad fija** | Innecesariamente complejo |

#### ¿Por qué String (DNI) y no Paciente completo?
- **Menor memoria:** Solo 8-10 bytes por slot vs objeto completo
- **Suficiente información:** DNI identifica únicamente
- **Lookup posterior:** Si necesitamos datos completos, usamos IndicePacientes.get(dni) en O(1)

### 🧪 Casos de Prueba Críticos
```java
// 1. Múltiples vueltas completas del índice circular
// 2. Llenar → vaciar → llenar (reutilización de espacio)
// 3. Overflow con secuencia larga (verificar que front avanza)
// 4. Atender de sala vacía (debe retornar null, no error)
// 5. Verificar que overflow no pierde datos intermedios
```

### 📊 Ejemplo Visual
```
Capacidad: 5
Estado inicial:   [_, _, _, _, _]   front=0, rear=0, size=0

llega("111"):     [111, _, _, _, _] front=0, rear=1, size=1
llega("222"):     [111, 222, _, _, _] front=0, rear=2, size=2
llega("333"):     [111, 222, 333, _, _] front=0, rear=3, size=3
atiende():        [111, 222, 333, _, _] front=1, rear=3, size=2 (retorna "111")
llega("444"):     [111, 222, 333, 444, _] front=1, rear=4, size=3
llega("555"):     [111, 222, 333, 444, 555] front=1, rear=0, size=4 (circular!)
llega("666"):     [666, 222, 333, 444, 555] front=2, rear=1, size=4 (overflow! pisó "111")
```

---

## 3️⃣ Planificador de Recordatorios - `PlanificadorRecordatorios`

### 🎯 Objetivo
Gestionar recordatorios con prioridad temporal. Permitir reprogramación eficiente (O(log n)) de recordatorios ya programados.

### 🔧 Estructuras de Datos Utilizadas
1. **MonticuloIndexado<Recordatorio>** - Min-Heap con índice inverso
2. **TablaHash<String, Recordatorio>** - Índice externo por ID

### 📊 Complejidades Alcanzadas
| Operación | Complejidad | Justificación |
|-----------|-------------|---------------|
| programar(r) | O(log n) | Inserción en heap + O(1) hash |
| proximo() | O(log n) | Extracción raíz + reordenamiento |
| reprogramar(id, fecha) | **O(log n)** | O(1) búsqueda hash + O(log n) update heap |
| size() | O(1) | Contador mantenido |

### 🧮 Estructura Interna: MonticuloIndexado

El **MonticuloIndexado** extiende **MonticuloBinario** añadiendo:

```java
class MonticuloIndexado<T> extends MonticuloBinario<T> {
    private TablaHash<T, Integer> indiceInverso;  // elemento → posición en arreglo
    
    // Al insertar:
    void add(T elemento) {
        super.add(elemento);
        indiceInverso.put(elemento, posicionActual);
    }
    
    // Al actualizar (clave de la eficiencia):
    boolean update(T viejoValor, T nuevoValor) {
        Integer pos = indiceInverso.get(viejoValor);  // O(1) ✅
        if (pos == null) return false;
        
        heap.set(pos, nuevoValor);
        percolate(pos);  // up o down según corresponda - O(log n)
        
        indiceInverso.remove(viejoValor);
        indiceInverso.put(nuevoValor, pos);
        return true;
    }
}
```

### 🎯 Algoritmo de Reprogramación

```java
@Override
public void reprogramar(String id, LocalDateTime nuevaFecha) {
    // Paso 1: Buscar recordatorio en índice externo - O(1)
    Recordatorio encontrado = indice.get(id);
    if (encontrado == null) throw exception;
    
    // Paso 2: Guardar copia del estado anterior (para búsqueda en heap)
    Recordatorio valorAntiguo = new Recordatorio(
        encontrado.getId(), 
        encontrado.getFecha(),  // fecha VIEJA
        encontrado.getDniPaciente(), 
        encontrado.getMensaje()
    );
    
    // Paso 3: Actualizar fecha del recordatorio original
    encontrado.setFecha(nuevaFecha);  // fecha NUEVA
    
    // Paso 4: Actualizar en heap - O(log n)
    // heap.update() usa su índice interno para encontrar valorAntiguo en O(1)
    // luego hace percolate up/down según nueva fecha
    boolean ok = heap.update(valorAntiguo, encontrado);
    
    // índice externo ya tiene la referencia actualizada (mismo objeto)
}
```

**Complejidad total:** O(1) + O(1) + O(1) + O(log n) = **O(log n)** ✅

### 📝 Decisiones de Diseño

#### ¿Por qué MonticuloIndexado y no solo MonticuloBinario?

| Operación | MonticuloBinario | MonticuloIndexado |
|-----------|------------------|-------------------|
| programar | O(log n) | O(log n) |
| proximo | O(log n) | O(log n) |
| reprogramar | **O(n)** búsqueda + O(log n) update | **O(log n)** ✅ |

**Sin índice inverso:**
```java
// Necesitaríamos buscar linealmente:
for (int i = 0; i < heap.size(); i++) {
    if (heap.get(i).getId().equals(id)) {
        // Actualizar y reordenar
        break;
    }
}
// Complejidad: O(n) ❌
```

**Con índice inverso:**
```java
Integer pos = indiceInverso.get(recordatorio);  // O(1) ✅
heap.set(pos, nuevoRecordatorio);
percolate(pos);  // O(log n)
// Complejidad: O(log n) ✅
```

#### ¿Por qué TablaHash externa adicional?

El índice inverso del MonticuloIndexado es: `elemento → posición`

Pero necesitamos: `ID (String) → elemento`

```
TablaHash externa:  "R001" → Recordatorio("R001", fecha1, ...)
                              ↓
Índice interno heap: Recordatorio("R001", ...) → posición 5 en arreglo
```

**Dos índices complementarios:**
1. **Externo (ID → Recordatorio):** Búsqueda por ID en O(1)
2. **Interno (Recordatorio → posición):** Update en heap en O(log n)

### 🧪 Casos de Prueba Críticos
```java
// 1. Reprogramar para fecha anterior → debe subir en el heap (percolate up)
// 2. Reprogramar para fecha posterior → debe bajar en el heap (percolate down)
// 3. Múltiples recordatorios con misma fecha → desempate por ID
// 4. Programar, extraer todo, verificar orden cronológico
// 5. Reprogramar elemento que no existe → debe lanzar excepción
// 6. Heapify desde lista desordenada → verificar propiedad de heap
```

### 📊 Ejemplo de Uso
```java
PlanificadorRecordatorios p = new PlanificadorRecordatorios();

p.programar(new Recordatorio("R001", LocalDateTime.of(2025,11,10,9,0), "111", "Control"));
p.programar(new Recordatorio("R002", LocalDateTime.of(2025,11,8,14,0), "222", "Vacuna"));
p.programar(new Recordatorio("R003", LocalDateTime.of(2025,11,12,10,30), "333", "Análisis"));

Recordatorio prox = p.proximo();  // Retorna R002 (fecha más cercana: 8/11)

// Reprogramar R003 para que sea el próximo
p.reprogramar("R003", LocalDateTime.of(2025,11,7,8,0));

Recordatorio nuevo = p.proximo();  // Ahora retorna R003 (nueva fecha: 7/11)
```

---

## 4️⃣ Agenda Médica - `AgendaMedicoTree`

### 🎯 Objetivo
Mantener turnos de un médico ordenados cronológicamente con garantía de O(log n) para todas las operaciones. Prevenir doble booking.

### 🔧 Estructura de Datos Utilizada
**ArbolAVL<Turno>** ordenado por fechaHora

### 📊 Complejidades Alcanzadas
| Operación | Complejidad | Descripción |
|-----------|-------------|-------------|
| agendar(turno) | O(log n) | Inserción + validación superposición |
| cancelar(idTurno) | O(log n) | Búsqueda + eliminación |
| siguiente(fecha) | O(log n) | Búsqueda de sucesor |

### 🧮 Prevención de Doble Booking

```java
public boolean agendar(Turno nuevo) {
    // Paso 1: Buscar turno inmediatamente anterior - O(log n)
    Optional<Turno> anterior = buscarAnterior(nuevo.getFechaHora());
    
    // Paso 2: Verificar superposición con anterior
    if (anterior.isPresent() && anterior.get().seSuperpone(nuevo)) {
        return false;  // Conflicto con anterior
    }
    
    // Paso 3: Buscar turno inmediatamente posterior - O(log n)
    Optional<Turno> posterior = buscarPosterior(nuevo.getFechaHora());
    
    // Paso 4: Verificar superposición con posterior
    if (posterior.isPresent() && nuevo.seSuperpone(posterior.get())) {
        return false;  // Conflicto con posterior
    }
    
    // Paso 5: Sin conflictos, insertar en AVL - O(log n)
    arbol.insert(nuevo);
    return true;
}
```

**Complejidad total:** O(log n) + O(log n) + O(log n) = **O(log n)** ✅

### 📝 Decisiones de Diseño

#### ¿Por qué ArbolAVL y no ArbolRojinegro?

| Criterio | AVL | Rojinegro |
|----------|-----|-----------|
| Búsqueda (siguiente turno) | O(log n) | O(log n) |
| Balance más estricto | ✅ h ≤ 1.44 log n | ❌ h ≤ 2 log n |
| Rotaciones en inserción | Más (hasta 2) | Menos (hasta 1) |
| Rotaciones en eliminación | Más (hasta O(log n)) | Menos (hasta 3) |
| **Caso de uso:** Más lecturas | ✅ **Óptimo** | Menos eficiente |
| **Caso de uso:** Más escrituras | Menos eficiente | ✅ Mejor |

**Justificación para nuestro caso:**
- **Operación más frecuente:** `siguiente(fecha)` - buscar próximo turno
- **Ratio lectura/escritura:** Alta (muchas consultas, menos agendamientos)
- **AVL:** Balance más estricto → búsquedas más rápidas
- **Conclusión:** AVL es superior para workloads de lectura intensiva ✅

#### ¿Por qué ordenar por fechaHora y no por ID?

| Orden | Búsqueda siguiente | Validación superposición |
|-------|-------------------|--------------------------|
| Por fechaHora | O(log n) ✅ | O(log n) (vecinos inmediatos) |
| Por ID | O(n) recorrido ❌ | O(n²) comparar todos |

**Conclusión:** Ordenar por fechaHora optimiza las operaciones críticas.

### 🧮 Método `siguiente(LocalDateTime desde)`

```java
public Optional<Turno> siguiente(LocalDateTime desde) {
    // Usar búsqueda binaria del AVL
    NodoAVL<Turno> actual = raiz;
    Turno candidato = null;
    
    while (actual != null) {
        Turno turno = actual.getData();
        
        if (turno.getFechaHora().isAfter(desde) || 
            turno.getFechaHora().equals(desde)) {
            // Este turno es >= desde, puede ser el resultado
            candidato = turno;
            actual = actual.getLeft();  // Buscar más a la izquierda (menor)
        } else {
            // Este turno es < desde, buscar a la derecha (mayor)
            actual = actual.getRight();
        }
    }
    
    return Optional.ofNullable(candidato);
}
```

**Complejidad:** O(log n) - Búsqueda binaria en AVL ✅

### 🧪 Casos de Prueba Críticos
```java
// 1. Inserciones que provocan rotaciones LL, RR, LR, RL
// 2. Cancelar nodo hoja, intermedio, raíz (diferentes escenarios)
// 3. Agendar turno que solapa con anterior/posterior
// 4. siguiente() justo al borde del día (23:59)
// 5. Cancelar único turno del día
// 6. Múltiples turnos seguidos sin huecos
// 7. Verificar factor de balance después de cada operación
```

### 📊 Ejemplo de Uso
```java
AgendaMedicoTree agenda = new AgendaMedicoTree("MP12345");

Turno t1 = new Turno("T001", "111", "MP12345", 
                     LocalDateTime.of(2025,11,5,9,0), 30, "Control");
Turno t2 = new Turno("T002", "222", "MP12345", 
                     LocalDateTime.of(2025,11,5,10,0), 30, "Vacuna");
Turno t3 = new Turno("T003", "333", "MP12345", 
                     LocalDateTime.of(2025,11,5,9,15), 30, "Consulta");

agenda.agendar(t1);  // OK
agenda.agendar(t2);  // OK
agenda.agendar(t3);  // RECHAZADO (solapa con t1: 9:00-9:30 vs 9:15-9:45)

Optional<Turno> prox = agenda.siguiente(LocalDateTime.of(2025,11,5,8,0));
// Retorna t1 (primer turno >= 8:00)
```

---

## 5️⃣ Consolidador de Agendas - `ConsolidadorAgendas`

### 🎯 Objetivo
Unificar dos agendas ordenadas (local y nube) en una sola, eliminando duplicados y detectando conflictos.

### 🔧 Estructura de Datos Utilizada
**Merge de ListaEnlazada<Turno> ordenadas** con deduplicación

### 📊 Complejidades Alcanzadas
| Operación | Complejidad |
|-----------|-------------|
| merge(agendaA, agendaB) | O(\|A\| + \|B\|) |

### 🧮 Algoritmo de Merge

```java
public static ResultadoMerge merge(ListaEnlazada<Turno> local, 
                                   ListaEnlazada<Turno> nube) {
    ListaEnlazada<Turno> resultado = new ListaEnlazada<>();
    ListaEnlazada<String> conflictos = new ListaEnlazada<>();
    TablaHash<String, Turno> idsVistos = new TablaHash<>();
    
    Nodo<Turno> nodoLocal = local.getHead();
    Nodo<Turno> nodoNube = nube.getHead();
    
    // Merge clásico de dos listas ordenadas
    while (nodoLocal != null && nodoNube != null) {
        Turno tLocal = nodoLocal.getData();
        Turno tNube = nodoNube.getData();
        
        int cmp = tLocal.getFechaHora().compareTo(tNube.getFechaHora());
        
        if (cmp <= 0) {
            procesarTurno(tLocal, resultado, idsVistos, conflictos, "LOCAL");
            nodoLocal = nodoLocal.getNext();
        } else {
            procesarTurno(tNube, resultado, idsVistos, conflictos, "NUBE");
            nodoNube = nodoNube.getNext();
        }
    }
    
    // Procesar turnos restantes de cada lista
    while (nodoLocal != null) { /* ... */ }
    while (nodoNube != null) { /* ... */ }
    
    return new ResultadoMerge(resultado, conflictos);
}
```

### 🔍 Detección de Conflictos

```java
private static void procesarTurno(Turno turno, /* ... */) {
    // Conflicto 1: ID duplicado (O(1) con TablaHash)
    if (idsVistos.containsKey(turno.getId())) {
        conflictos.insertLast("ID duplicado: " + turno.getId());
        return;  // Descartar este turno
    }
    
    // Conflicto 2: Mismo médico + horario solapado
    boolean hayConflicto = verificarConflictoHorario(turno, resultado);
    
    if (!hayConflicto) {
        resultado.insertLast(turno);
        idsVistos.put(turno.getId(), turno);
    }
}

private static boolean verificarConflictoHorario(Turno nuevo, 
                                                  ListaEnlazada<Turno> existentes) {
    Nodo<Turno> nodo = existentes.getHead();
    
    while (nodo != null) {
        Turno existente = nodo.getData();
        
        // Mismo médico Y horarios se solapan
        if (existente.getMatriculaMedico().equals(nuevo.getMatriculaMedico()) &&
            turnosSeSuperponen(existente, nuevo)) {
            
            conflictos.insertLast("Conflicto: médico " + nuevo.getMatriculaMedico() + 
                                " - turnos " + existente.getId() + " y " + nuevo.getId());
            return true;
        }
        
        nodo = nodo.getNext();
    }
    
    return false;
}

private static boolean turnosSeSuperponen(Turno t1, Turno t2) {
    // Dos intervalos [inicio1, fin1) y [inicio2, fin2) se solapan si:
    // inicio1 < fin2 AND inicio2 < fin1
    return t1.getFechaHora().isBefore(t2.getFechaHoraFin()) &&
           t2.getFechaHora().isBefore(t1.getFechaHoraFin());
}
```

### 📝 Decisiones de Diseño

#### Resolución de Conflictos

| Tipo de Conflicto | Acción | Justificación |
|-------------------|--------|---------------|
| ID duplicado | Mantener primero, descartar segundo | Primer registro es "source of truth" |
| Mismo médico + horario solapado | Descartar segundo, loguear conflicto | Prevenir doble booking |
| Diferente médico + horario solapado | Permitir ambos | Médicos diferentes pueden atender simultáneamente |

#### ¿Por qué mantener log de conflictos?

```java
public static class ResultadoMerge {
    private ListaEnlazada<Turno> turnosConsolidados;
    private ListaEnlazada<String> conflictos;  // ← Log de conflictos
    
    public int cantidadConflictos() { /* ... */ }
}
```

**Beneficios:**
- **Auditoría:** Rastrear qué turnos fueron rechazados y por qué
- **Debugging:** Identificar problemas de sincronización
- **Transparencia:** Informar al usuario sobre discrepancias

### 🧪 Casos de Prueba Críticos
```java
// 1. Duplicados exactos (mismo ID)
// 2. Superposiciones parciales (mismo médico)
// 3. Agendas desbalanceadas (una mucho más grande que otra)
// 4. Lista vacía + lista con elementos
// 5. Ambas listas vacías
// 6. Todos los turnos de una lista tienen conflictos
// 7. Turnos con mismo médico pero sin solapamiento (consecutivos)
```

### 📊 Análisis de Complejidad

```java
n = tamaño de agendaLocal
m = tamaño de agendaNube

Merge principal: O(n + m)  // Recorrer ambas listas una vez
Verificación ID duplicado: O(1) por turno  // TablaHash
Verificación conflicto horario: O(k) por turno  // k = turnos procesados hasta ahora

Peor caso (ningún conflicto):
    O((n + m) * (n + m)) = O((n + m)²)  // k crece hasta n+m

Caso optimizado (con ordenamiento previo):
    O(n + m)  // Solo comparar con turnos cercanos en el tiempo
```

**Optimización posible:** Si las listas vienen ordenadas por fechaHora, solo necesitamos comparar con turnos en una ventana temporal pequeña, reduciendo a O(n + m) lineal.

---

## 6️⃣ Planificador de Quirófanos - `PlanificadorQuirofanoImpl`

### 🎯 Objetivo
1. Asignar cirugías al primer quirófano libre que cumpla el deadline
2. Mantener top-K médicos con más minutos bloqueados (estadística)

### 🔧 Estructuras de Datos Utilizadas
1. **Min-Heap de Quirófanos** - ordenado por `finOcupado` (cuándo se libera)
2. **Min-Heap de tamaño K** - para mantener top-K médicos
3. **Listas paralelas** - para rastrear minutos por médico (reemplazo de HashMap)

### 📊 Complejidades Alcanzadas
| Operación | Complejidad |
|-----------|-------------|
| procesar(solicitud) | O(log Q) | Q = número de quirófanos |
| topKMedicosBloqueados(K) | O(M log K) | M = médicos únicos |

### 🧮 Asignación de Quirófanos

```java
public void procesar(SolicitudCirugia s) {
    // Paso 1: Extraer quirófano que se libera más pronto - O(log Q)
    Quirofano quirofano = quirofanos.poll();  // Min-heap
    
    // Paso 2: Calcular cuándo puede comenzar la cirugía
    LocalDateTime inicio = max(quirofano.finOcupado, ahora);
    LocalDateTime fin = inicio.plusMinutes(s.getDurMin());
    
    // Paso 3: Verificar deadline
    if (fin.isAfter(s.getDeadline())) {
        System.out.println("⚠️ Cirugía NO cumple deadline");
    }
    
    // Paso 4: Actualizar minutos bloqueados del médico - O(1) amortizado
    actualizarMinutosMedico(s.getMatricula(), s.getDurMin());
    
    // Paso 5: Devolver quirófano al heap con nuevo finOcupado - O(log Q)
    quirofano.finOcupado = fin;
    quirofanos.add(quirofano);
}
```

**Complejidad:** O(log Q) + O(1) + O(log Q) = **O(log Q)** ✅

### 🏆 Top-K Médicos con Más Minutos

```java
public List<String> topKMedicosBloqueados(int K) {
    // Min-Heap de tamaño K mantiene los K médicos con MÁS minutos
    MonticuloBinario<MedicoBloqueado> topK = new MonticuloBinario<>(MIN_HEAP);
    
    // Iterar sobre todos los médicos - O(M)
    for (int i = 0; i < matriculasMedicos.size(); i++) {
        MedicoBloqueado medico = new MedicoBloqueado(
            matriculasMedicos.get(i), 
            minutosMedicos.get(i)
        );
        
        if (topK.size() < K) {
            // Aún no tenemos K elementos, agregar - O(log K)
            topK.add(medico);
        } else if (medico.minutosBloqueados > topK.peek().minutosBloqueados) {
            // Este médico tiene más minutos que el mínimo del heap
            topK.poll();     // Sacar el mínimo - O(log K)
            topK.add(medico); // Insertar el nuevo - O(log K)
        }
    }
    
    // Extraer todos y ordenar de mayor a menor
    List<MedicoBloqueado> lista = extraerTodos(topK);  // O(K log K)
    ordenarDescendente(lista);  // O(K²) con insertion sort (K pequeño)
    
    return convertirAStrings(lista);
}
```

**Complejidad:** M iteraciones × O(log K) + O(K log K) + O(K²) = **O(M log K)** (dominante) ✅

### 📝 Decisiones de Diseño

#### ¿Por qué Min-Heap de quirófanos?

| Operación | Min-Heap | Lista ordenada | Arreglo simple |
|-----------|----------|----------------|----------------|
| Obtener próximo libre | O(log n) | O(1) | O(n) búsqueda |
| Actualizar y reinsertar | O(log n) | O(n) | O(n) |
| **Total por evento** | **O(log n)** ✅ | O(n) | O(n) |

**Justificación:** El heap permite extraer el mínimo y reinsertar eficientemente.

#### ¿Por qué Min-Heap de tamaño K para top-K?

**Algoritmo alternativo (ordenar todo):**
```java
List<Medico> todos = obtenerTodos();  // O(M)
Collections.sort(todos);              // O(M log M)
return todos.subList(0, K);           // O(1)
// Total: O(M log M)
```

**Algoritmo con heap de tamaño K:**
```java
MinHeap<Medico> topK = new MinHeap(K);
for (Medico m : todos) {              // O(M) iteraciones
    if (topK.size() < K || m > topK.peek()) {
        topK.poll();                   // O(log K)
        topK.add(m);                   // O(log K)
    }
}
// Total: O(M log K)
```

**Comparación:**
- Si K << M (ej: K=5, M=100): O(M log K) = O(100 × 2.3) = O(230) vs O(M log M) = O(100 × 6.6) = O(660)
- **Ganancia:** ~65% menos operaciones ✅

#### ¿Por qué listas paralelas en lugar de HashMap?

```java
// Opción 1: HashMap (prohibido por ejercicio)
HashMap<String, Integer> minutosPorMedico;

// Opción 2: Listas paralelas (permitido)
List<String> matriculasMedicos;
List<Integer> minutosMedicos;
```

**Justificación:**
- **Restricción del ejercicio:** No usar colecciones de Java estándar
- **Performance:** O(n) búsqueda en listas vs O(1) en hash
- **Aceptable:** M (médicos) es pequeño en la práctica (~10-50)

#### Representación de Quirófano

```java
private static class Quirofano implements Comparable<Quirofano> {
    String id;                  // "Q1", "Q2", ...
    LocalDateTime finOcupado;   // Cuándo se libera
    
    @Override
    public int compareTo(Quirofano otro) {
        return this.finOcupado.compareTo(otro.finOcupado);
    }
}
```

**Decisión:** Comparable por `finOcupado` para ordenamiento natural en Min-Heap.

### 🧪 Casos de Prueba Críticos
```java
// 1. Todas las cirugías del mismo médico → top-1 debe ser ese médico
// 2. Cirugía con deadline imposible (muy corta) → debe loguear advertencia
// 3. Todos los quirófanos ocupados al mismo tiempo → asignar al primero en liberarse
// 4. Top-K con K > M (más K que médicos) → retornar todos los médicos
// 5. Múltiples cirugías seguidas sin huecos → verificar ocupación continua
// 6. Verificar que quirófanos se reutilizan (no se pierden en el heap)
```

### 📊 Ejemplo de Uso
```java
PlanificadorQuirofanoImpl p = new PlanificadorQuirofanoImpl(3, LocalDateTime.now());
// 3 quirófanos: Q1, Q2, Q3

SolicitudCirugia c1 = new SolicitudCirugia("C001", "MP111", 60, deadline1);
SolicitudCirugia c2 = new SolicitudCirugia("C002", "MP111", 90, deadline2);
SolicitudCirugia c3 = new SolicitudCirugia("C003", "MP222", 45, deadline3);
SolicitudCirugia c4 = new SolicitudCirugia("C004", "MP111", 30, deadline4);

p.procesar(c1);  // Q1 ocupado hasta now+60min
p.procesar(c2);  // Q2 ocupado hasta now+90min
p.procesar(c3);  // Q3 ocupado hasta now+45min
p.procesar(c4);  // Q3 se libera primero (45min), reasignado hasta now+45+30=75min

List<String> top3 = p.topKMedicosBloqueados(3);
// Retorna: ["MP111 (180 min)", "MP222 (45 min)"]
```

---

## 🎓 Conclusiones y Aprendizajes

### Selección de Estructuras - Resumen

| Componente | Estructura Elegida | Justificación Principal |
|------------|-------------------|------------------------|
| IndicePacientes | TablaHash | Búsqueda O(1) por clave exacta |
| SalaEspera | ColaCircular | Capacidad fija + overflow |
| PlanificadorRecordatorios | MonticuloIndexado + TablaHash | Reprogramación O(log n) |
| AgendaMedico | ArbolAVL | Búsquedas frecuentes + orden |
| ConsolidadorAgendas | Merge de listas | Aprovecha orden existente |
| PlanificadorQuirofano | Min-Heap | Obtener mínimo eficiente |

### Trade-offs Analizados

#### Tiempo vs Espacio
- **MonticuloIndexado:** Duplica espacio (heap + índice) para ganar tiempo O(log n) en updates
- **TablaHash:** Overhead de buckets vacíos (loadFactor 0.75) por O(1) promedio

#### Simplicidad vs Eficiencia
- **ConsolidadorAgendas:** Merge O(n+m) simple vs algoritmos complejos
- **Listas paralelas:** Búsqueda O(n) simple vs TablaHash O(1) complejo

#### Garantías Worst-Case vs Average-Case
- **AVL vs Rojinegro:** AVL garantiza h ≤ 1.44 log n (peor caso mejor)
- **TablaHash:** O(1) promedio, O(n) peor caso (aceptable con buen hash)

### Complejidades Logradas

| Requisito | Estructura | Operación | Complejidad |
|-----------|-----------|-----------|-------------|
| Búsqueda por DNI | TablaHash | get | O(1) promedio |
| Cola de espera | ColaCircular | enqueue/dequeue | O(1) |
| Reprogramación | MonticuloIndexado | update | O(log n) ✅ |
| Próximo turno | ArbolAVL | siguiente | O(log n) |
| Merge agendas | Listas ordenadas | merge | O(n + m) |
| Asignar quirófano | Min-Heap | poll/add | O(log Q) |
| Top-K médicos | Min-Heap tamaño K | topK | O(M log K) |

### Patrones de Diseño Aplicados

#### Facade
- **MapaPacientes, Planner, AgendaMedico:** Interfaces simples que ocultan complejidad

#### Composite
- **MonticuloIndexado:** Compone MonticuloBinario + TablaHash

#### Template Method
- **procesarTurno():** Esqueleto común con verificaciones específicas

#### Strategy
- **Comparable:** Diferentes criterios de ordenamiento (fecha, prioridad, etc.)

---

## 📚 Compilación y Ejecución

```bash
# Compilar todo el proyecto
javac -d bin -cp src src/**/*.java

# Ejecutar menú principal
java -cp bin app.Main

# Compilar solo integrador
javac -d bin -cp src src/core/integrador/**/*.java

# Ejecutar tests del integrador (si existen)
java -cp bin views.IntegradorMenu
```

---

## 👥 Autores

- **Equipo Informática 3**
- **Instituto:** Instituto Universitario Aeronáutico
- **Año:** 2025

---

## 📄 Licencia

Proyecto académico con fines educativos.
