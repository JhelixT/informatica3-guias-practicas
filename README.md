# 🎓 Informática 3 - Guías Prácticas

Colección integrada de implementaciones académicas de estructuras de datos y algoritmos fundamentales, desarrollada como material de estudio para la materia Informática 3.

## 📋 Descripción Académica

Este repositorio unifica múltiples módulos de práctica que cubren los conceptos fundamentales de:
- **Algoritmos de Ordenamiento** con análisis de complejidad temporal
- **Estructuras de Datos Lineales** (Listas Enlazadas, Pilas y Colas)
- **Estructuras de Datos No Lineales** (Árboles AVL, BST, Rojinegro)
- **Tablas Hash** con encadenamiento y rehashing automático
- **Montículos Binarios** (Min-Heap y Max-Heap con indexación)
- **Programación Orientada a Objetos** con patrones de diseño
- **Técnicas de Recursividad** aplicadas a problemas clásicos
- **Proyecto Integrador** - Sistema completo de gestión médica

## 🏗️ Arquitectura del Sistema

```
src/
├── app/                    # Controlador principal unificado
├── core/                   # Núcleo de implementaciones
│   ├── pizzeria/          # Caso de estudio: algoritmos de ordenamiento
│   │   ├── Pedido.java    # Entidad de dominio
│   │   ├── Pizzeria.java  # Gestor de colecciones
│   │   ├── Ordenador.java # Implementación de algoritmos
│   │   └── TiempoOrdenamiento.java # Análisis de performance
│   ├── tareas/            # Caso de estudio: operaciones CRUD
│   │   ├── Tarea.java     # Modelo de datos
│   │   └── GestorTareas.java # Operaciones sobre colecciones
│   ├── estructuras/       # Estructuras de datos clásicas
│   │   ├── nodos/         # Nodos para estructuras enlazadas
│   │   │   ├── Nodo.java          # Nodo simple
│   │   │   └── NodoDoble.java     # Nodo doblemente enlazado
│   │   ├── listas/        # Listas enlazadas
│   │   │   ├── ListaEnlazada.java         # Lista simple
│   │   │   └── ListaDoblementeEnlazada.java # Lista doble
│   │   ├── pilas/         # Implementación LIFO
│   │   │   ├── PilaArreglo.java   # Con arreglo dinámico
│   │   │   └── PilaEnlazada.java  # Con nodos enlazados
│   │   ├── colas/         # Implementación FIFO
│   │   │   ├── ColaCircular.java  # Cola circular con overflow
│   │   │   └── ColaEnlazada.java  # Cola con nodos
│   │   ├── arboles/       # Árboles auto-balanceados
│   │   │   ├── ArbolBST.java      # Árbol binario de búsqueda
│   │   │   ├── ArbolAVL.java      # Árbol AVL balanceado
│   │   │   ├── ArbolRojinegro.java # Árbol Rojo-Negro
│   │   │   ├── NodoBST.java       # Nodo para BST
│   │   │   ├── NodoAVL.java       # Nodo para AVL
│   │   │   └── NodoRojinegro.java # Nodo para RB
│   │   ├── hash/          # Tabla hash con encadenamiento
│   │   │   └── TablaHash.java     # Hash con chaining y rehash
│   │   ├── monticulo/     # Montículos binarios
│   │   │   ├── MonticuloBinario.java  # Min/Max Heap
│   │   │   └── MonticuloIndexado.java # Heap con índice inverso
│   │   └── recursividad/  # Técnicas recursivas diversas
│   │       ├── Fibonacci.java          # Fibonacci tradicional
│   │       ├── FibonacciOptimizado.java # Con memoización
│   │       ├── BuscarEnArreglo.java    # Búsqueda recursiva
│   │       ├── SumarArreglo.java       # Suma recursiva
│   │       ├── Palindromo.java         # Verificación de palíndromos
│   │       ├── ConversionBinaria.java  # Decimal a binario
│   │       ├── MaximoComunDivisor.java # Algoritmo de Euclides
│   │       ├── ConteoDigitos.java      # Conteo de dígitos
│   │       └── InvertirCadena.java     # Inversión de strings
│   ├── integrador/        # Proyecto integrador médico
│   │   ├── modelo/        # Modelos de dominio
│   │   ├── agenda/        # Sistema de agendas con AVL
│   │   ├── salaespera/    # Cola de espera
│   │   ├── recordatorios/ # Planificador con heap
│   │   ├── pacientes/     # Índice con hash
│   │   ├── quirofano/     # Asignación de quirófanos
│   │   ├── merge/         # Consolidación de agendas
│   │   ├── carga/         # Carga de datos CSV
│   │   └── datos/         # Archivos CSV de prueba
│   ├── ejercicios/        # Ejercicios por tema
│   │   ├── listas/        # 10 ejercicios de listas
│   │   ├── arboles/       # 10 ejercicios de AVL
│   │   ├── monticulo/     # 10 ejercicios de heaps
│   │   └── rojinegro/     # 10 ejercicios de RB trees
│   └── utils/             # Utilidades de sistema
│       ├── InputValidator.java  # Validación de entrada
│       ├── MenuFormatter.java   # Formateo de menús
│       ├── AnsiColors.java      # Colores ANSI
│       └── DemoColores.java     # Demo de colores
└── views/                  # Interfaces de usuario
    ├── EstructurasLinealesMenu.java
    ├── ArbolAVLMenu.java
    ├── ArbolBSTMenu.java
    ├── ArbolRojinegroMenu.java
    ├── MonticuloMenu.java
    ├── TablaHashMenu.java
    ├── RecursividadMenu.java
    ├── PizzeriaMenu.java
    ├── TareasMenu.java
    ├── EjerciciosListasMenu.java
    ├── EjerciciosArbolesAVLMenu.java
    ├── EjerciciosMonticuloMenu.java
    └── EjerciciosRojinegroMenu.java
```

## � Implementaciones Técnicas

### 🍕 Módulo Pizzería - Algoritmos de Ordenamiento

**Objetivo:** Comparar algoritmos de ordenamiento en un contexto práctico.

- **Inserción (Insertion Sort)** - O(n²)
  - Ideal para conjuntos pequeños o parcialmente ordenados
  - Implementación estable y adaptativa

- **Shell Sort** - O(n log n) promedio
  - Mejora del ordenamiento por inserción
  - Utiliza secuencia de gaps para optimización

- **Quick Sort** - O(n log n) promedio, O(n²) peor caso
  - Algoritmo divide y vencerás
  - Implementación con particionado Lomuto

**Características:**
- Análisis empírico de rendimiento con conjuntos de 100, 1,000 y 10,000 elementos
- Generación automática de datos de prueba
- Medición precisa de tiempos de ejecución en nanosegundos

### 📋 Módulo Tareas - Operaciones CRUD

**Objetivo:** Demostrar manipulación de colecciones con operaciones básicas.

- **Create:** Validación de entrada y agregado a colección
- **Read:** Filtrado por estado y listado completo
- **Update:** Cambio de estados con validación
- **Delete:** Eliminación individual y por lotes

**Características:**
- Uso de Java Streams para filtrado eficiente
- Validación robusta de datos de entrada
- Estadísticas en tiempo real de progreso

### 📚 Módulo Estructuras Lineales

#### 🔗 Listas Enlazadas Simples

**Objetivo:** Implementar estructura dinámica con nodos y referencias.

**Características:**
- Nodo base con dato (int) y referencia al siguiente
- Tamaño dinámico sin límite de capacidad
- Operaciones de inserción: inicio, final, posición específica
- Operaciones de eliminación: inicio, final, posición específica
- Búsqueda y acceso por posición
- Inversión de lista in-place

**Complejidad:**
- Inserción al inicio: `O(1)`
- Inserción al final: `O(n)`
- Búsqueda: `O(n)`
- Eliminación: `O(n)`

#### 📚 Pilas (LIFO - Last In, First Out)

**Implementación con Arreglo (PilaArreglo):**
- Arreglo dinámico que crece automáticamente
- Redimensionamiento 150% cuando se llena
- Operaciones: `push()`, `pop()`, `top()`, `isEmpty()`, `isFull()`
- Complejidad: `O(1)` todas las operaciones (`O(n)` al redimensionar)

**Implementación Enlazada (PilaEnlazada):**
- Basada en nodos con referencias
- Sin límite de capacidad
- Operaciones: `push()`, `pop()`, `top()`, `isEmpty()`, `buscar()`
- Complejidad: `O(1)` todas las operaciones

#### 🎯 Colas (FIFO - First In, First Out)

**Implementación Circular (ColaCircular):**
- Arreglo con índices circulares usando módulo (%)
- Reutiliza espacio liberado al frente
- Redimensionamiento automático al llenarse
- Operaciones: `enqueue()`, `dequeue()`, `front()`, `isEmpty()`, `isFull()`
- Complejidad: `O(1)` todas las operaciones (`O(n)` al redimensionar)

**Implementación Enlazada (ColaEnlazada):**
- Referencias a frente (front) y final (rear)
- Sin límite de capacidad
- Operaciones: `enqueue()`, `dequeue()`, `front()`, `back()`, `buscar()`
- Complejidad: `O(1)` para enqueue y dequeue

### 🌳 Módulo Árboles - Estructuras Auto-balanceadas

#### Árbol BST (Binary Search Tree)
**Características:**
- Árbol binario de búsqueda básico sin auto-balanceo
- Propiedad: izquierda < nodo < derecha
- Búsqueda, inserción, eliminación: O(log n) promedio, O(n) peor caso
- Base para árboles balanceados más avanzados

#### Árbol AVL
**Características:**
- Árbol binario de búsqueda auto-balanceado
- Factor de balance mantenido en [-1, 0, 1]
- Rotaciones simples (LL, RR) y dobles (LR, RL) para balance
- Altura logarítmica garantizada: O(log n)
- Más estricto que Rojinegro, mejor para lecturas frecuentes

**Operaciones principales:**
- Inserción con rebalanceo automático: `O(log n)`
- Búsqueda optimizada: `O(log n)`
- Eliminación con rebalanceo: `O(log n)`
- Cálculo dinámico de altura y factor de balance
- Recorridos: inorden, preorden, postorden, por niveles

#### Árbol Rojinegro (Red-Black Tree)
**Características:**
- Árbol binario de búsqueda auto-balanceado con colores
- 5 propiedades invariantes garantizan balance
- Altura máxima: 2*log₂(n+1)
- Menos rotaciones en inserción que AVL
- Mejor para escrituras frecuentes

**Propiedades:**
1. Cada nodo es ROJO o NEGRO
2. La raíz es siempre NEGRA
3. Todas las hojas (NIL) son NEGRAS
4. Si un nodo es ROJO, ambos hijos son NEGROS
5. Todos los caminos de un nodo a hojas tienen igual número de nodos NEGROS

**Operaciones:**
- Inserción: `O(log n)` con recoloreo y rotaciones
- Búsqueda: `O(log n)`
- Eliminación: `O(log n)` (más complejo que inserción)

### 🗄️ Módulo Tablas Hash

**Características:**
- Implementación con encadenamiento (chaining)
- Cada bucket es una ListaEnlazada
- Factor de carga máximo: 0.75
- Rehashing automático al alcanzar el límite
- Soporte completo para claves null (null-safe)

**Función Hash:**
- Utiliza `hashCode()` nativo de las claves
- Para Strings: algoritmo polinomial con primo 31
- Módulo sobre capacidad para índice del bucket

**Complejidad:**
- Inserción: `O(1)` promedio, `O(n)` peor caso
- Búsqueda: `O(1)` promedio, `O(n)` peor caso
- Eliminación: `O(1)` promedio, `O(n)` peor caso
- Rehashing: `O(n)` cuando se alcanza el factor de carga

### ⛰️ Módulo Montículos (Heaps)

#### MonticuloBinario
**Características:**
- Árbol binario completo implementado con arreglo
- Soporta Min-Heap y Max-Heap
- Representación: hijo izq = 2*i, hijo der = 2*i+1, padre = i/2

**Operaciones:**
- Inserción (add): `O(log n)` - percolate up
- Extracción (poll): `O(log n)` - percolate down
- Consulta (peek): `O(1)`
- Heapify desde arreglo: `O(n)`
- Heapsort: `O(n log n)`

#### MonticuloIndexado
**Extensión de MonticuloBinario que añade:**
- Índice inverso: elemento → posición en el heap
- Permite actualización de elementos: `O(log n)`
- Búsqueda de elementos: `O(1)` usando el índice
- Esencial para algoritmos como Dijkstra y reprogramación de eventos

**Operaciones adicionales:**
- update(viejo, nuevo): `O(log n)` - búsqueda O(1) + reordenamiento
- contains(elemento): `O(1)` usando índice interno

### 🔄 Módulo Recursividad

**Objetivo:** Implementar técnicas recursivas clásicas aplicadas a problemas fundamentales.

**Algoritmos Implementados:**
- **Fibonacci:** Implementación tradicional y optimizada con memoización
- **Búsqueda en Arreglos:** Búsqueda lineal recursiva
- **Suma de Arreglos:** Procesamiento recursivo de colecciones
- **Palíndromo:** Verificación recursiva de cadenas
- **Conversión Binaria:** Conversión de base decimal a binaria
- **MCD (Máximo Común Divisor):** Algoritmo de Euclides recursivo
- **Conteo de Dígitos:** Análisis recursivo de números
- **Inversión de Cadenas:** Manipulación recursiva de strings

**Características:**
- Casos base claramente definidos
- Análisis de complejidad temporal y espacial
- Comparación entre soluciones iterativas y recursivas
- Optimizaciones mediante memoización (Fibonacci optimizado)

## 🎯 Objetivos de Aprendizaje

### Estructuras de Datos Fundamentales

#### Listas Enlazadas
- Comprender estructuras dinámicas con punteros/referencias
- Implementar operaciones básicas: inserción, eliminación, búsqueda
- Analizar ventajas/desventajas vs arreglos
- Manejar casos borde: lista vacía, un elemento, múltiples

#### Pilas y Colas
- Entender disciplinas de acceso LIFO y FIFO
- Implementar con arreglos (circulares) y nodos
- Aplicar a problemas reales: undo/redo, procesamiento de eventos
- Comparar trade-offs entre implementaciones

#### Árboles Balanceados
- Dominar árboles binarios de búsqueda
- Implementar rotaciones simples y dobles
- Mantener invariantes de balance (AVL) y color (Rojinegro)
- Analizar garantías de complejidad O(log n)

#### Tablas Hash
- Diseñar funciones hash efectivas
- Resolver colisiones con encadenamiento
- Implementar rehashing automático
- Analizar factor de carga y distribución

#### Montículos
- Representar árboles en arreglos
- Implementar percolate up/down
- Construir heaps eficientemente (heapify)
- Aplicar a ordenamiento (heapsort) y colas de prioridad

### Algoritmos Fundamentales

#### Ordenamiento
- Comparar algoritmos por estabilidad y complejidad
- Insertion Sort: O(n²) pero estable y adaptativo
- Shell Sort: O(n log n) con secuencias de gaps óptimas
- Quick Sort: O(n log n) promedio, divide y vencerás

#### Búsqueda
- Búsqueda lineal en listas: O(n)
- Búsqueda binaria en árboles: O(log n)
- Búsqueda en hash: O(1) promedio

#### Recursividad
- Identificar casos base y recursivos
- Optimizar con memoización
- Analizar stack overflow y profundidad
- Convertir entre iterativo y recursivo

### Paradigmas de Programación

#### Encapsulación
- Acceso controlado a datos mediante getters/setters
- Validación de invariantes en constructores
- Inmutabilidad cuando sea apropiado

#### Modularidad
- Separación clara de responsabilidades
- Interfaces para contratos claros
- Componentes reutilizables e independientes

#### Abstracción
- TADs (Tipos Abstractos de Datos)
- Interfaces que ocultan implementación
- Genericidad con tipos parametrizados

### Patrones de Diseño

#### MVC (Modelo-Vista-Controlador)
- **Modelo**: Estructuras de datos + lógica de negocio
- **Vista**: Menús interactivos + formateo
- **Controlador**: Main unificado que coordina

#### Strategy
- Diferentes algoritmos de ordenamiento intercambiables
- Diferentes implementaciones de estructuras (arreglo vs enlazada)

#### Template Method
- Estructura común para menús con personalización
- Algoritmos con pasos fijos y detalles variables

#### Composite
- Árboles con operaciones recursivas
- Nodos que contienen otros nodos

## 📊 Métricas y Análisis

El sistema incluye herramientas de análisis para:
- **Benchmarking** de algoritmos de ordenamiento
- **Visualización** de estructuras de datos
- **Estadísticas** de uso y rendimiento
- **Validación** automática de integridad de datos

## 🧪 Casos de Uso Académicos

### Ejercicios Progresivos (218 archivos Java)

#### Ejercicios de Listas (10 ejercicios)
1. **Creación de nodos** - Fundamentos de referencias
2. **Inserción al inicio** - Operación O(1)
3. **Inserción al final** - Recorrido completo O(n)
4. **Eliminación por valor** - Búsqueda + eliminación
5. **Búsqueda de valores** - Recorrido lineal
6. **Conteo de elementos** - Iteración completa
7. **Inversión de lista** - Manipulación de referencias
8. **Inserción en posición** - Validación de índices
9. **Eliminación de duplicados** - Comparación entre nodos
10. **Registro de alumnos** - Aplicación práctica completa

#### Ejercicios de Árboles AVL (10 ejercicios)
1. **Inserciones LL/RR** - Rotaciones simples
2. **Rotación doble** - Casos LR/RL complejos
3. **Secuencia ordenada** - Verificación de BST
4. **Eliminación con rebalanceo** - Operación más compleja
5. **Comprobador AVL** - Validación de invariantes
6. **Factor de equilibrio** - Cálculo recursivo
7. **Implementación rotación izquierda** - Paso a paso
8. **Rotación doble LR** - Descomposición detallada
9. **Casos de prueba** - Testing exhaustivo
10. **Pruebas unitarias** - Framework de testing

#### Ejercicios de Montículos (10 ejercicios)
1. **Min-Heap básico** - Construcción desde cero
2. **Percolate Up** - Mantener propiedad al insertar
3. **Percolate Down** - Mantener propiedad al eliminar
4. **Visualización árbol** - Representación en niveles
5. **Heapify** - Construcción eficiente O(n)
6. **Heapsort** - Algoritmo de ordenamiento
7. **Max-Heap** - Variante con máximo en raíz
8. **Cola de pacientes** - Aplicación con prioridades
9. **Seguimiento de estado** - Heap con updates
10. **Agenda de tareas** - Sistema completo

#### Ejercicios de Árboles Rojinegro (10 ejercicios)
1. **Nodo y NIL** - Sentinelas y colores
2. **Rotación izquierda** - Transformación básica
3. **Rotación derecha** - Transformación inversa
4. **Inserción BST** - Sin considerar colores
5. **Clasificador de casos** - Identificar escenarios
6. **Recoloreo tío rojo** - Caso simple
7. **Rotaciones simples/dobles** - Casos complejos
8. **Successor/Predecessor** - Navegación en árbol
9. **Consulta por rango** - Búsquedas acotadas
10. **Verificadores invariantes** - Validación completa

### Proyecto Integrador - Sistema Médico

Sistema completo que integra todas las estructuras:
- **10 componentes funcionales** usando diferentes estructuras
- **CSV loading** con validaciones de dominio
- **Operaciones CRUD** completas
- **Análisis de complejidad** en contexto real
- **Manejo de conflictos** y edge cases

### Comparaciones Prácticas

1. **Estudio Comparativo de Algoritmos**
   - Benchmarking con 100, 1,000, 10,000 elementos
   - Medición precisa en nanosegundos
   - Comparación empírica vs teórica

2. **Implementaciones Alternativas**
   - Pila con arreglo vs pila enlazada
   - Cola circular vs cola enlazada
   - AVL vs Rojinegro para diferentes workloads

3. **Análisis de Trade-offs**
   - Tiempo vs espacio
   - Simplicidad vs eficiencia
   - Garantías worst-case vs average-case

4. **Validación de Conceptos**
   - Verificación empírica de Big O notation
   - Testing de invariantes estructurales
   - Pruebas de correctitud con casos borde

## 📦 Clases Implementadas

### Estructuras de Datos Core

#### Nodos
- **`Nodo.java`** - Nodo simple con dato y referencia siguiente
- **`NodoDoble.java`** - Nodo doblemente enlazado con prev y next
- **`NodoAVL.java`** - Nodo para AVL con altura y balance
- **`NodoBST.java`** - Nodo para BST básico
- **`NodoRojinegro.java`** - Nodo con color (ROJO/NEGRO) y NIL sentinels

#### Listas
- **`ListaEnlazada.java`** - Lista simple completa con operaciones básicas
- **`ListaDoblementeEnlazada.java`** - Lista doble con navegación bidireccional

#### Pilas (LIFO)
- **`PilaArreglo.java`** - Pila con arreglo dinámico (crece 150%)
- **`PilaEnlazada.java`** - Pila con nodos enlazados (sin límite)

#### Colas (FIFO)
- **`ColaCircular.java`** - Cola circular con overflow control
- **`ColaEnlazada.java`** - Cola con nodos y referencias front/rear

#### Árboles
- **`ArbolBST.java`** - Árbol binario de búsqueda básico
- **`ArbolAVL.java`** - Árbol AVL con rotaciones y balanceo automático
- **`ArbolRojinegro.java`** - Árbol Rojinegro con propiedades invariantes

#### Hashing
- **`TablaHash.java`** - Hash con chaining, rehash automático y null-safe

#### Montículos
- **`MonticuloBinario.java`** - Min/Max Heap con arreglo
- **`MonticuloIndexado.java`** - Heap con índice inverso para updates O(log n)

### Algoritmos Recursivos
- **`Fibonacci.java`** - Implementación tradicional recursiva
- **`FibonacciOptimizado.java`** - Con memoización para O(n)
- **`BuscarEnArreglo.java`** - Búsqueda lineal recursiva
- **`SumarArreglo.java`** - Suma recursiva de elementos
- **`Palindromo.java`** - Verificación recursiva de palíndromos
- **`ConversionBinaria.java`** - Conversión decimal a binario
- **`MaximoComunDivisor.java`** - Algoritmo de Euclides recursivo
- **`ConteoDigitos.java`** - Conteo recursivo de dígitos
- **`InvertirCadena.java`** - Inversión recursiva de strings

### Proyecto Integrador - Sistema Médico

#### Modelos de Dominio
- **`Paciente.java`** - DNI, nombre con equals/hashCode
- **`Medico.java`** - Matrícula, nombre, especialidad
- **`Turno.java`** - ID, paciente, médico, fecha/hora, duración, comparable
- **`Recordatorio.java`** - ID, fecha, paciente, mensaje, comparable
- **`SolicitudCirugia.java`** - ID, matrícula, duración, deadline

#### Componentes Funcionales
- **`IndicePacientes.java`** - Índice rápido con TablaHash (O(1))
- **`SalaEspera.java`** - Cola circular con overflow automático
- **`PlanificadorRecordatorios.java`** - Min-heap + TablaHash para reprogramación O(log n)
- **`AgendaMedico.java`** - Interface para agendas con AVL
- **`AgendaMedicoTree.java`** - Implementación con ArbolAVL por fechaHora
- **`AgendaConHistorial.java`** - Con pilas para Undo/Redo
- **`ConsolidadorAgendas.java`** - Merge de listas ordenadas O(n+m)
- **`PlanificadorQuirofano.java`** - Interface para asignación de quirófanos
- **`PlanificadorQuirofanoImpl.java`** - Min-heap de quirófanos + top-K con heap
- **`CargadorCSV.java`** - Parseo y validación de archivos CSV

### Módulos de Aplicación
- **`Pedido.java`** - Entidad de dominio para pizzería
- **`Pizzeria.java`** - Gestor de colecciones de pedidos
- **`Ordenador.java`** - Insertion Sort, Shell Sort, Quick Sort
- **`TiempoOrdenamiento.java`** - Benchmarking con 100/1K/10K elementos
- **`Tarea.java`** - Modelo de datos con estados (PENDIENTE/EN_PROCESO/COMPLETADA)
- **`GestorTareas.java`** - Operaciones CRUD con Streams y filtros

### Interfaces de Usuario (Menús)
- **`EstructurasLinealesMenu.java`** - Listas, pilas, colas
- **`ArbolBSTMenu.java`** - Operaciones BST básicas
- **`ArbolAVLMenu.java`** - Operaciones AVL con visualización
- **`ArbolRojinegroMenu.java`** - Operaciones Rojinegro con colores
- **`MonticuloMenu.java`** - Min/Max heap, heapify, heapsort
- **`TablaHashMenu.java`** - Hash con estadísticas de colisiones
- **`RecursividadMenu.java`** - Ejercicios recursivos clásicos
- **`PizzeriaMenu.java`** - Comparación de algoritmos de ordenamiento
- **`TareasMenu.java`** - CRUD de tareas con filtros
- **`EjerciciosListasMenu.java`** - 10 ejercicios progresivos de listas
- **`EjerciciosArbolesAVLMenu.java`** - 10 ejercicios de AVL
- **`EjerciciosMonticuloMenu.java`** - 10 ejercicios de heaps
- **`EjerciciosRojinegroMenu.java`** - 10 ejercicios de RB trees

### Utilidades del Sistema
- **`InputValidator.java`** - Validación robusta de entrada con excepciones
- **`MenuFormatter.java`** - Formateo consistente con bordes y colores
- **`AnsiColors.java`** - Códigos ANSI para colores en terminal
- **`DemoColores.java`** - Demostración de paleta de colores

## ✨ Características Destacadas

### Estructuras de Datos - Características Específicas

#### Listas Enlazadas
- **Tamaño dinámico** sin límite de capacidad
- **Inserción O(1)** al inicio, O(n) al final
- **Búsqueda lineal** O(n) con recorrido completo
- **Eliminación** por valor, posición o referencia
- **Operaciones especiales:** inversión, eliminación de duplicados
- **Navegación unidireccional** (simple) y bidireccional (doble)

#### Pilas (LIFO)
**Implementación con Arreglo:**
- Redimensionamiento automático (150% cuando llena)
- Operaciones O(1): push, pop, top
- Memoria continua, cache-friendly
- Factor de crecimiento configurable

**Implementación Enlazada:**
- Sin límite de capacidad
- Operaciones O(1) todas
- Búsqueda O(n) de elementos específicos
- Mayor overhead por punteros

#### Colas (FIFO)
**Implementación Circular:**
- Índices circulares con módulo (%)
- Reutilización de espacio liberado
- Modo overflow opcional (reemplaza más antiguo)
- Capacidad fija configurable

**Implementación Enlazada:**
- Referencias front y rear para O(1)
- Crecimiento dinámico ilimitado
- Búsqueda O(n) de elementos

#### Árboles Binarios de Búsqueda

**BST (Binary Search Tree):**
- Sin auto-balanceo
- Altura: O(log n) promedio, O(n) peor caso
- Recorridos: inorden (ordenado), preorden, postorden, por niveles

**AVL:**
- Factor de balance: [-1, 0, 1] estrictamente mantenido
- Rotaciones: simples (LL, RR) y dobles (LR, RL)
- Altura garantizada: h ≤ 1.44 log₂(n)
- Mejor para lecturas frecuentes

**Rojinegro:**
- Propiedades invariantes con colores
- Altura máxima: 2 log₂(n+1)
- Menos rotaciones en inserción que AVL
- Mejor para escrituras frecuentes
- Nodos NIL como sentinelas

#### Tabla Hash
- **Resolución de colisiones:** Encadenamiento con ListaEnlazada
- **Factor de carga:** 0.75 máximo
- **Rehashing automático:** duplica capacidad al alcanzar límite
- **Función hash:** Basada en hashCode() nativo (primo 31 para Strings)
- **Null-safe:** maneja claves y valores null correctamente
- **Estadísticas:** tracking de colisiones y distribución

#### Montículos Binarios
**MonticuloBinario:**
- Representación en arreglo (índice 1-based)
- Soporta Min-Heap y Max-Heap
- Percolate up/down para mantener propiedad
- Heapify en O(n) desde arreglo
- Heapsort en O(n log n)

**MonticuloIndexado (extensión):**
- Índice inverso: elemento → posición
- Update de elementos en O(log n)
- Búsqueda en O(1) con índice
- Esencial para Dijkstra, reprogramación de eventos

### Menú Interactivo Completo
- **Sistema unificado** para navegación entre todos los módulos
- **18 menús especializados** para cada tipo de estructura
- **Comparación visual** entre implementaciones (arreglos vs enlazadas)
- **Validación robusta** de entrada de datos con InputValidator
- **Formateo profesional** con bordes y colores ANSI
- **Operaciones interactivas** con retroalimentación inmediata

### Análisis de Rendimiento
- **Benchmarking preciso** con System.nanoTime()
- **Múltiples tamaños:** 100, 1,000, 10,000 elementos
- **Comparación de algoritmos:** Insertion, Shell, Quick Sort
- **Visualización de resultados** con formateo de tiempos
- **Generación automática** de datos de prueba
- **Medición de memoria** y overhead de estructuras

### Estándares de Código
- **Documentación Javadoc** completa en todas las clases
- **Análisis de complejidad** en cada método crítico
- **Manejo robusto** de excepciones con mensajes descriptivos
- **Nombres descriptivos** siguiendo convenciones Java
- **Arquitectura MVC** bien definida
- **Principios SOLID** aplicados consistentemente
- **Testing exhaustivo** de casos borde

## 🏥 Proyecto Integrador - Sistema de Gestión Hospitalaria

El proyecto incluye un **sistema integrador completo** que aplica todas las estructuras de datos en un contexto real del dominio médico.

### Componentes del Integrador
1. **Índice de Pacientes** - TablaHash para búsqueda O(1) por DNI
2. **Sala de Espera** - ColaCircular con overflow automático
3. **Recordatorios** - MonticuloIndexado con reprogramación O(log n)
4. **Agendas Médicas** - ArbolAVL para turnos ordenados cronológicamente
5. **Consolidación de Agendas** - Merge O(n+m) de listas ordenadas
6. **Planificador de Quirófanos** - Min-Heap para asignación eficiente

### Objetivos del Integrador
- ✅ Aplicar estructuras en problemas reales
- ✅ Analizar trade-offs entre implementaciones
- ✅ Justificar selección según requisitos
- ✅ Integrar múltiples estructuras cohesivamente
- ✅ Validar complejidades teóricas empíricamente

### Documentación Completa
Ver **[README_INTEGRADOR_COMPLETO.md](README_INTEGRADOR_COMPLETO.md)** para:
- Análisis detallado de cada componente
- Decisiones de diseño justificadas
- Diagramas de complejidad
- Casos de prueba críticos
- Ejemplos de uso completos

---

## 🚀 Compilación y Ejecución

### Compilar Todo el Proyecto
```bash
# Windows (PowerShell)
javac -d bin -cp src (Get-ChildItem -Path src -Filter *.java -Recurse).FullName

# Linux/Mac
find src -name "*.java" | xargs javac -d bin -cp src
```

### Ejecutar Menú Principal
```bash
java -cp bin app.Main
```

### Compilar Solo el Integrador
```bash
javac -d bin -cp src src/core/integrador/**/*.java src/core/estructuras/**/*.java
```

### Estructura de Directorios
```bash
informatica3-guias-practicas/
├── src/          # Código fuente
├── bin/          # Archivos compilados (.class)
├── README.md     # Documentación principal
└── README_INTEGRADOR_COMPLETO.md  # Documentación del integrador
```

---

## 📊 Estadísticas del Proyecto

- **218 archivos Java** implementados
- **40+ ejercicios** progresivos
- **10 componentes** del integrador
- **8 estructuras de datos** fundamentales
- **15+ algoritmos** clásicos implementados
- **18 menús interactivos** especializados
- **100% documentación** Javadoc

---

## 🎓 Temas Cubiertos

### Estructuras de Datos
✅ Listas Enlazadas (Simple y Doble)  
✅ Pilas (Arreglo y Enlazada)  
✅ Colas (Circular y Enlazada)  
✅ Árboles Binarios (BST, AVL, Rojinegro)  
✅ Tablas Hash con Encadenamiento  
✅ Montículos Binarios (Min/Max)  
✅ Montículos Indexados  

### Algoritmos
✅ Ordenamiento (Insertion, Shell, Quick)  
✅ Búsqueda (Lineal, Binaria en árboles)  
✅ Recursividad (Fibonacci, MCD, Palíndromos)  
✅ Merge de listas ordenadas  
✅ Heapify y Heapsort  
✅ Top-K con heaps  

### Conceptos Avanzados
✅ Análisis de Complejidad (Big O)  
✅ Invariantes de Estructuras  
✅ Auto-balanceo de Árboles  
✅ Resolución de Colisiones  
✅ Rehashing Dinámico  
✅ Trade-offs Tiempo-Espacio  

---

## 📚 Referencias Académicas

### Libros Consultados
- **"Introduction to Algorithms"** - Cormen, Leiserson, Rivest, Stein (CLRS)
- **"Data Structures and Algorithms in Java"** - Goodrich, Tamassia
- **"Algorithms"** - Sedgewick, Wayne

### Complejidades Teóricas Verificadas
Todas las implementaciones han sido verificadas contra las complejidades teóricas estándar de la literatura académica.

---

## 🤝 Contribuciones

Este es un proyecto académico con fines educativos. Las contribuciones son bienvenidas siguiendo estos lineamientos:

1. **Documentación completa** con Javadoc
2. **Análisis de complejidad** en métodos críticos
3. **Testing de casos borde**
4. **Seguir convenciones** Java establecidas
5. **Mantener arquitectura MVC**

---

## 📝 Notas Finales

### Restricciones del Proyecto
- ❌ No usar colecciones de Java (`ArrayList`, `HashMap`, `TreeMap`, `PriorityQueue`)
- ✅ Implementar todo desde cero con estructuras propias
- ✅ Justificar todas las decisiones de diseño
- ✅ Documentar complejidades alcanzadas

**Requisitos:** Java 21+ | **Propósito:** Material educativo para Informática 3  
**Institución:** Instituto Universitario Aeronáutico | **Año:** 2025