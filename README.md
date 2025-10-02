# 🎓 Informática 3 - Guías Prácticas

Colección integrada de implementaciones académicas de estructuras de datos y algoritmos fundamentales, desarrollada como material de estudio para la materia Informática 3.

## 📋 Descripción Académica

Este repositorio unifica múltiples módulos de práctica que cubren los conceptos fundamentales de:
- **Algoritmos de Ordenamiento** con análisis de complejidad temporal
- **Estructuras de Datos Lineales** (Pilas y Colas)
- **Estructuras de Datos No Lineales** (Árboles AVL)
- **Programación Orientada a Objetos** con patrones de diseño
- **Técnicas de Recursividad** aplicadas

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
│   │   ├── pilas/         # Implementación LIFO
│   │   ├── colas/         # Implementación FIFO
│   │   ├── arboles/       # Árboles auto-balanceados
│   │   └── recursividad/  # Técnicas recursivas
│   └── utils/             # Utilidades de sistema
└── views/                  # Interfaces de usuario
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

### 📚 Módulo Pilas y Colas - Estructuras Lineales

**Pilas (LIFO - Last In, First Out):**
- Implementación con arreglo dinámico
- Redimensionamiento automático (150% de capacidad)
- Operaciones: `push()`, `pop()`, `top()`, `isEmpty()`, `isFull()`

**Colas (FIFO - First In, First Out):**
- Implementación circular con arreglo
- Manejo eficiente de memoria con índices circulares
- Operaciones: `enqueue()`, `dequeue()`, `front()`, `isEmpty()`, `isFull()`

### 🌳 Módulo Árboles AVL - Estructuras Auto-balanceadas

**Características del AVL:**
- Árbol binario de búsqueda auto-balanceado
- Factor de balance mantenido en [-1, 0, 1]
- Rotaciones simples y dobles para balance
- Altura logarítmica garantizada: O(log n)

**Operaciones principales:**
- Inserción con rebalanceo automático
- Búsqueda optimizada O(log n)
- Cálculo dinámico de altura y factor de balance

## 🎯 Conceptos Pedagógicos Implementados

### Paradigmas de Programación
- **Encapsulación:** Acceso controlado a datos mediante getters/setters
- **Modularidad:** Separación clara de responsabilidades
- **Reutilización:** Componentes genéricos y especializados

### Patrones de Diseño
- **MVC (Modelo-Vista-Controlador):** Separación de lógica de negocio e interfaz
- **Template Method:** Estructura común para menús
- **Strategy:** Diferentes algoritmos de ordenamiento intercambiables

### Análisis de Algoritmos
- **Complejidad Temporal:** Medición empírica vs. teórica
- **Complejidad Espacial:** Uso eficiente de memoria
- **Casos de Prueba:** Mejor caso, caso promedio, peor caso

## 📊 Métricas y Análisis

El sistema incluye herramientas de análisis para:
- **Benchmarking** de algoritmos de ordenamiento
- **Visualización** de estructuras de datos
- **Estadísticas** de uso y rendimiento
- **Validación** automática de integridad de datos

## 🧪 Casos de Uso Académicos

1. **Estudio Comparativo de Algoritmos:** Análisis empírico de performance
2. **Implementación de TADs:** Tipos Abstractos de Datos clásicos
3. **Validación de Conceptos:** Verificación práctica de teoría
4. **Experimentación:** Modificación de parámetros y observación de resultados

## � Ejecución

```bash
# Compilar
javac -d bin -cp src src/app/*.java src/views/*.java src/core/*/*.java src/core/*/*/*.java

# Ejecutar
java -cp bin app.Main
```

---

**Requisitos:** Java 21+ | **Propósito:** Material educativo para Informática 3