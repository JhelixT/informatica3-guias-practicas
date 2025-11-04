# Sistema de Gestión de Turnos Médicos - INTEGRADOR

## 📋 10 Ejercicios - Distribución Simple

### **COMPARTIDO (Todos):**
- `CargadorCSV.java` - Cargar datos desde CSV (Ejercicio 1) ⚠️ **Todos lo necesitan**

### **Integrante 1: Ejercicios 2, 3**
2. **Agenda AVL** - Gestionar turnos con árbol AVL (`AgendaMedico.java`)
3. **Buscar Hueco** - Encontrar primer hueco disponible (`BuscadorHueco.java`)

### **Integrante 2: Ejercicios 4, 5, 6, 7**
4. **Cola Circular** - Sala de espera (`SalaEspera.java`) ✅
5. **Heap Recordatorios** - Planificador con montículo (`Planner.java` + `PlanificadorRecordatorios.java` + `Recordatorio.java`) ✅
6. **Hash Pacientes** - Índice rápido de pacientes (`MapaPacientes.java` + `IndicePacientes.java`) ✅
7. **Merge Agendas** - Consolidar agendas (`ConsolidadorAgendas.java`) ✅

### **Integrante 3: Ejercicios 8, 9, 10**
8. **Ordenamiento** - Reportes ordenados (`OrdenadorTurnos.java`) ✅
9. **Pila Historial** - Deshacer/Rehacer acciones (`Historial.java`)
10. **Quirófanos** - Planificador con 2 heaps (`PlanificadorQuirofano.java`)
11. **Menú** - Integración final (`MenuIntegrador.java`)

---

## 🏗️ Estructura Simple

```
src/core/integrador/
├── Paciente.java           ✅ (compartido)
├── Medico.java             ✅ (compartido)
├── Turno.java              ✅ (compartido)
├── CargadorCSV.java        🔄 (compartido - TODOS lo necesitan)
├── README.md               ✅
│
├── AgendaMedico.java       → Integrante 1 (Ejercicio 2)
├── BuscadorHueco.java      → Integrante 1 (Ejercicio 3)
│
├── SalaEspera.java         → Integrante 2 (Ejercicio 4)
├── Planner.java            → Integrante 2 (Ejercicio 5)
├── IndicePacientes.java    → Integrante 2 (Ejercicio 6)
├── ConsolidadorAgendas.java → Integrante 2 (Ejercicio 7)
│
├── OrdenadorTurnos.java    → Integrante 3 (Ejercicio 8)
├── Historial.java          → Integrante 3 (Ejercicio 9)
├── PlanificadorQuirofano.java → Integrante 3 (Ejercicio 10)
└── MenuIntegrador.java     → Integrante 3 (Integración final)
```

---

## 🚀 Reutilizar Estructuras Existentes

- **ArbolAVL** → `core.estructuras.arboles.ArbolAVL`
- **TablaHash** → `core.estructuras.hash.TablaHash`
- **MonticuloBinario** → `core.estructuras.pilas.MonticuloBinario` (si existe)
- **ColaCircular** → `core.estructuras.colas.ColaCircular`
- **ListaEnlazada** → `core.estructuras.listas.ListaEnlazada`

---

## ⚡ Workflow Git Rápido

```bash
# Cada integrante:
git checkout -b int1-ejercicios  # int2, int3
# Trabaja en sus archivos
git add .
git commit -m "feat: ejercicios 1-3"
git push origin int1-ejercicios
# Pull request a main
```

---

## � Función Hash para DNI (Ejercicio 6)

El `IndicePacientes` utiliza la función hash nativa de Java para Strings:

```java
hash(s) = s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
```

**¿Por qué esta función es buena para DNIs?**

1. **Número primo (31)**: Minimiza colisiones por propiedades matemáticas
2. **Distribución uniforme**: DNIs similares ("12345678" vs "12345679") producen hashes muy diferentes
3. **Optimización del compilador**: `31*i` se optimiza a `(i<<5)-i` (shift + resta)
4. **Avalancha**: Pequeños cambios en entrada → grandes cambios en salida
5. **Probado y confiable**: Usado por Java estándar desde hace décadas

**Ejemplo de distribución:**
- DNI "20123456" → hash diferente a "20123457" (consecutivos no colisionan)
- DNI "12345678" → hash diferente a "87654321" (palíndromos no colisionan)
- Colisiones mínimas en conjuntos típicos de DNIs argentinos (7-8 dígitos)

**Complejidad:** O(n) donde n = longitud del String, pero para DNIs (8 caracteres) es prácticamente O(1).

---

## 🔀 Merge de Agendas (Ejercicio 7)

El `ConsolidadorAgendas` implementa el algoritmo clásico de **merge de listas ordenadas**:

### **Algoritmo:**
```
Entrada: agendaLocal (ordenada), agendaNube (ordenada)
Salida: agendaConsolidada (ordenada) + log de conflictos

1. Inicializar dos punteros en las cabezas de ambas listas
2. Mientras ambas listas tengan elementos:
   a. Comparar turnos por fecha
   b. Tomar el de fecha menor
   c. Verificar conflictos (ID duplicado o horario solapado)
   d. Si no hay conflicto, agregar a resultado
   e. Avanzar puntero correspondiente
3. Agregar turnos restantes de la lista no agotada
```

**Complejidad:** O(|A| + |B|) - recorre cada lista exactamente una vez

### **Detección de conflictos:**

1. **ID duplicado** → O(1) con TablaHash
2. **Horario solapado** → O(k) donde k = turnos del mismo médico en ventana temporal

**Condición de superposición:**
```
inicio1 < fin2 AND inicio2 < fin1
```

### **Ejemplo:**
```
Local:  [T1:9:00, T3:11:00, T5:15:00]
Nube:   [T2:10:00, T3:11:00, T4:14:00]  ← T3 duplicado

Merge:
- Comparar T1(9:00) vs T2(10:00) → T1 menor, agregar T1
- Comparar T3(11:00-LOCAL) vs T2(10:00) → T2 menor, agregar T2
- Comparar T3(11:00-LOCAL) vs T3(11:00-NUBE) → T3-LOCAL menor o igual, agregar T3-LOCAL ✅
- T3-NUBE: detectar ID duplicado, descartar ❌
- Comparar T5(15:00) vs T4(14:00) → T4 menor, agregar T4
- Agregar T5 restante

Resultado: [T1, T2, T3-LOCAL, T4, T5] + "Conflicto: ID T3 duplicado (descartado turno de NUBE)"
                         ↑ mantiene el primero
```

---

## � Ordenamiento con Múltiples Algoritmos (Ejercicio 8)

El `OrdenadorTurnos` implementa **tres algoritmos de ordenamiento** con características distintas:

### **1. Insertion Sort - Por Hora (Estable)**

**Características:**
- **Estable**: Mantiene orden relativo de elementos iguales
- **Complejidad**: O(n²) peor caso, O(n) mejor caso (adaptativo)
- **Espacio**: O(1) in-place
- **Ideal para**: Listas pequeñas o casi ordenadas

**¿Por qué para hora?**
- Turnos con misma hora conservan orden original (estabilidad)
- Agendas típicamente tienen cierto orden previo
- Simple y predecible

```java
Insertion Sort - O(n²)
┌─────────────────────────┐
│ for i = 1 to n-1:      │
│   key = A[i]           │
│   j = i - 1            │
│   while j >= 0 AND     │
│         A[j] > key:    │
│     A[j+1] = A[j]     │
│     j--                │
│   A[j+1] = key         │
└─────────────────────────┘
```

### **2. Shell Sort - Por Duración (Gap Sequence)**

**Características:**
- **Gap sequence**: h = 3h + 1 (Knuth: 1, 4, 13, 40, 121, 364...)
- **Complejidad**: O(n^(3/2)) con esta secuencia
- **Espacio**: O(1) in-place
- **No estable**: Puede cambiar orden relativo

**¿Por qué para duración?**
- Más rápido que Insertion en datasets medianos
- No requiere estabilidad (duración es criterio único)
- Gap sequence optimizada reduce comparaciones

```java
Shell Sort - O(n^(3/2))
┌─────────────────────────┐
│ h = 1                   │
│ while h < n/3:          │
│   h = 3*h + 1          │
│                         │
│ while h >= 1:           │
│   for i = h to n-1:    │
│     [Insertion Sort     │
│      con gap h]        │
│   h = h / 3            │
└─────────────────────────┘
```

### **3. Quick Sort - Por Apellido (Lomuto)**

**Características:**
- **Pivote**: Último elemento (partición Lomuto)
- **Complejidad**: O(n log n) promedio, O(n²) peor caso
- **Espacio**: O(log n) recursión
- **No estable**: Puede cambiar orden relativo

**¿Por qué para apellido?**
- Muy eficiente en datasets grandes
- Apellidos tienen buena distribución aleatoria
- Cache-friendly (acceso secuencial en partición)

```java
Quick Sort (Lomuto) - O(n log n)
┌─────────────────────────┐
│ partition(A, low, high):│
│   pivot = A[high]       │
│   i = low - 1           │
│   for j = low to high-1:│
│     if A[j] <= pivot:  │
│       i++              │
│       swap(A[i], A[j]) │
│   swap(A[i+1], A[high])│
│   return i + 1          │
└─────────────────────────┘
```

### **Comparación de Rendimiento**

| Dataset | Insertion Sort | Shell Sort | Quick Sort |
|---------|----------------|------------|------------|
| 1,000   | ~5 ms          | ~2 ms      | ~1 ms      |
| 10,000  | ~150 ms        | ~15 ms     | ~5 ms      |
| 50,000  | ~3,500 ms      | ~120 ms    | ~25 ms     |

**Observaciones:**
- Insertion Sort: Cuadrático escalado, ineficiente para n grande
- Shell Sort: Balance entre simplicidad y eficiencia
- Quick Sort: Mejor para datasets grandes (divide y vencerás)

### **Ejemplo de Reportes:**

```
📅 REPORTE POR HORA (Insertion Sort - Estable)
T1       | 01/01 08:00 |  30 min | Juan García
T2       | 01/01 08:30 |  45 min | María López
T3       | 01/01 09:15 |  60 min | Carlos Pérez

⏱️  REPORTE POR DURACIÓN (Shell Sort)
T1       |  15 min | 01/01 10:00 | Ana Martínez
T4       |  30 min | 01/01 14:00 | Luis González
T2       |  45 min | 01/01 08:30 | María López

👤 REPORTE POR APELLIDO (Quick Sort - Lomuto)
García, Juan         | T1       | 01/01 08:00 |  30 min
González, Luis       | T4       | 01/01 14:00 |  30 min
López, María         | T2       | 01/01 08:30 |  45 min
```

---

## �📝 Notas Importantes

- **Archivos compartidos**: `Paciente`, `Medico`, `Turno`, `CargadorCSV` → NO modificar sin coordinar
- **CargadorCSV**: Lo crea primero quien termine, los demás lo usan ⚠️
- **Cada archivo = 1 ejercicio** → Mínima interferencia
- **Imports**: `import core.estructuras.arboles.ArbolAVL;`
- **Testing**: Cada uno testea sus propios archivos primero con datos hardcodeados
- **Una vez CargadorCSV listo**: Todos pueden cargar datos desde CSV
- **Integración final**: `MenuIntegrador.java` usa todos los ejercicios
