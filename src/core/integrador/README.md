# Sistema de Gestión de Turnos Médicos - INTEGRADOR

## 📋 10 Ejercicios - Distribución Simple

### **COMPARTIDO (Todos):**
- `CargadorCSV.java` - Cargar datos desde CSV (Ejercicio 1) ⚠️ **Todos lo necesitan**

### **Integrante 1: Ejercicios 2, 3**
2. **Agenda AVL** - Gestionar turnos con árbol AVL (`AgendaMedico.java`)
3. **Buscar Hueco** - Encontrar primer hueco disponible (`BuscadorHueco.java`)

### **Integrante 2: Ejercicios 4, 5, 6, 7**
4. **Cola Circular** - Sala de espera (`SalaEspera.java`)
5. **Heap Recordatorios** - Planificador con montículo (`Planner.java`)
6. **Hash Pacientes** - Índice rápido de pacientes (`IndicePacientes.java`)
7. **Merge Agendas** - Consolidar agendas (`ConsolidadorAgendas.java`)

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

## 📝 Notas Importantes

- **Archivos compartidos**: `Paciente`, `Medico`, `Turno`, `CargadorCSV` → NO modificar sin coordinar
- **CargadorCSV**: Lo crea primero quien termine, los demás lo usan ⚠️
- **Cada archivo = 1 ejercicio** → Mínima interferencia
- **Imports**: `import core.estructuras.arboles.ArbolAVL;`
- **Testing**: Cada uno testea sus propios archivos primero con datos hardcodeados
- **Una vez CargadorCSV listo**: Todos pueden cargar datos desde CSV
- **Integración final**: `MenuIntegrador.java` usa todos los ejercicios
