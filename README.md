# 🎓 Informática 3 - Guías Prácticas

Colección integrada de implementaciones académicas de estructuras de datos y algoritmos fundamentales, desarrollada como material de estudio para la materia Informática 3.

## 📋 Descripción Académica

Este repositorio unifica múltiples módulos de práctica que cubren los conceptos fundamentales de:
- **Algoritmos de Ordenamiento** con análisis de complejidad temporal
- **Estructuras de Datos Lineales** (Listas Enlazadas, Pilas y Colas)
- **Estructuras de Datos No Lineales** (Árboles AVL)
- **Programación Orientada a Objetos** con patrones de diseño
- **Técnicas de Recursividad** aplicadas a problemas clásicos

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
│   │   ├── listas/        # Listas enlazadas simples
│   │   ├── pilas/         # Implementación LIFO (arreglo y enlazada)
│   │   ├── colas/         # Implementación FIFO (circular y enlazada)
│   │   ├── arboles/       # Árboles auto-balanceados
│   │   └── recursividad/  # Técnicas recursivas diversas
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

### 🌳 Módulo Árboles AVL - Estructuras Auto-balanceadas

**Características del AVL:**
- Árbol binario de búsqueda auto-balanceado
- Factor de balance mantenido en [-1, 0, 1]
- Rotaciones simples y dobles para balance
- Altura logarítmica garantizada: O(log n)

**Operaciones principales:**
- Inserción con rebalanceo automático: `O(log n)`
- Búsqueda optimizada: `O(log n)`
- Cálculo dinámico de altura y factor de balance
- Recorridos: inorden, preorden, postorden, por niveles

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
5. **Comparación de Implementaciones:** Arreglos vs estructuras enlazadas
6. **Análisis de Complejidad:** Verificación empírica de Big O notation

## 📦 Clases Implementadas

### Estructuras de Datos Core
- **`Nodo.java`** - Nodo base para estructuras enlazadas
- **`ListaEnlazada.java`** - Lista enlazada simple completa
- **`PilaArreglo.java`** - Pila con implementación en arreglo dinámico
- **`PilaEnlazada.java`** - Pila con implementación enlazada
- **`ColaCircular.java`** - Cola circular con arreglo
- **`ColaEnlazada.java`** - Cola con implementación enlazada
- **`ArbolAVL.java`** - Árbol AVL auto-balanceado
- **`NodoAVL.java`** - Nodo especializado para árbol AVL

### Algoritmos Recursivos
- **`Fibonacci.java`** y **`FibonacciOptimizado.java`**
- **`BuscarEnArreglo.java`** - Búsqueda lineal recursiva
- **`SumarArreglo.java`** - Suma recursiva de elementos
- **`Palindromo.java`** - Verificación recursiva de palíndromos
- **`ConversionBinaria.java`** - Conversión decimal a binario
- **`MaximoComunDivisor.java`** - Algoritmo de Euclides
- **`ConteoDigitos.java`** - Conteo recursivo de dígitos
- **`InvertirCadena.java`** - Inversión recursiva de strings

### Módulos de Aplicación
- **`Pedido.java`** - Entidad de dominio para pizzería
- **`Pizzeria.java`** - Gestor de colecciones de pedidos
- **`Ordenador.java`** - Algoritmos de ordenamiento (Insertion, Shell, Quick)
- **`TiempoOrdenamiento.java`** - Análisis de rendimiento
- **`Tarea.java`** - Modelo de datos para gestión de tareas
- **`GestorTareas.java`** - Operaciones CRUD sobre tareas

### Interfaces de Usuario
- **`EstructurasLinealesMenu.java`** - Menú unificado para estructuras lineales
- **`PizzeriaMenu.java`** - Interfaz para algoritmos de ordenamiento
- **`TareasMenu.java`** - Interfaz para operaciones CRUD
- **`ArbolAVLMenu.java`** - Interfaz para árboles balanceados
- **`RecursividadMenu.java`** - Interfaz para ejercicios recursivos

### Utilidades del Sistema
- **`InputValidator.java`** - Validación robusta de entrada de usuario
- **`MenuFormatter.java`** - Formateo consistente de interfaces

## ✨ Características Destacadas

### Menú Interactivo Completo
- **Sistema unificado** para navegación entre todos los módulos
- **Submenús especializados** para cada tipo de estructura
- **Comparación visual** entre implementaciones (arreglos vs enlazadas)
- **Validación robusta** de entrada de datos

### Análisis de Rendimiento
- **Medición precisa** de tiempos de ejecución en nanosegundos
- **Generación automática** de datos de prueba
- **Análisis empírico** con diferentes tamaños de datos
- **Visualización** de resultados de benchmarking

### Estándares de Código
- **Documentación Javadoc** completa en todas las clases
- **Manejo robusto** de excepciones
- **Nombres descriptivos** siguiendo convenciones Java
- **Arquitectura MVC** bien definida

## � Ejecución

```bash
# Compilar
javac -d bin -cp src src/app/*.java src/views/*.java src/core/*/*.java src/core/*/*/*.java

# Ejecutar
java -cp bin app.Main
```

---

**Requisitos:** Java 21+ | **Propósito:** Material educativo para Informática 3