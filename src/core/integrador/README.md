# Sistema de Gestión de Turnos Médicos - INTEGRADOR

## 📋 10 Ejercicios - Distribución Simple

### **Integrante 1: Ejercicios 1, 2, 3**
1. **CSV Loader** - Cargar datos desde CSV (`CargadorCSV.java`)
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

---

## 🏗️ Estructura Simple

```
src/core/integrador/
├── Paciente.java           ✅ (compartido)
├── Medico.java             ✅ (compartido)
├── Turno.java              ✅ (compartido)
├── README.md               ✅
│
├── CargadorCSV.java        → Integrante 1
├── AgendaMedico.java       → Integrante 1
├── BuscadorHueco.java      → Integrante 1
│
├── SalaEspera.java         → Integrante 2
├── Planner.java            → Integrante 2
├── IndicePacientes.java    → Integrante 2
├── ConsolidadorAgendas.java → Integrante 2
│
├── OrdenadorTurnos.java    → Integrante 3
├── Historial.java          → Integrante 3
├── PlanificadorQuirofano.java → Integrante 3
└── MenuIntegrador.java     → Integrante 3 (final)
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

- **Modelos compartidos**: `Paciente`, `Medico`, `Turno` → NO modificar sin coordinar
- **Cada archivo = 1 ejercicio** → Mínima interferencia
- **Imports**: `import core.estructuras.arboles.ArbolAVL;`
- **Testing**: Cada uno testea sus propios archivos
- **Integración final**: `MenuIntegrador.java` usa todos los ejercicios
