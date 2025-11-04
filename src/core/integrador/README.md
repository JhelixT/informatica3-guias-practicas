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
8. **Ordenamiento** - Reportes ordenados (`OrdenadorTurnos.java`)
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

## 📝 Notas Importantes

- **Archivos compartidos**: `Paciente`, `Medico`, `Turno`, `CargadorCSV` → NO modificar sin coordinar
- **CargadorCSV**: Lo crea primero quien termine, los demás lo usan ⚠️
- **Cada archivo = 1 ejercicio** → Mínima interferencia
- **Imports**: `import core.estructuras.arboles.ArbolAVL;`
- **Testing**: Cada uno testea sus propios archivos primero con datos hardcodeados
- **Una vez CargadorCSV listo**: Todos pueden cargar datos desde CSV
- **Integración final**: `MenuIntegrador.java` usa todos los ejercicios
