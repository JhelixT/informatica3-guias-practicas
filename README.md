# 🎓 Informática 3 - Guías Prácticas

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-1.0-blue?style=for-the-badge)

## 📋 Descripción

Este proyecto unifica múltiples guías prácticas de la materia **Informática 3**, implementando diversos conceptos de estructuras de datos y algoritmos en Java. El sistema está diseñado con arquitectura **MVC** para mantener una separación clara entre la lógica de negocio y la interfaz de usuario.

## 🏗️ Arquitectura del Proyecto

```
informatica3-guias-practicas/
├── src/
│   ├── core/                    # 🔧 Implementaciones (Modelo)
│   │   ├── pizzeria/           # Gestión de pizzería y algoritmos de ordenamiento
│   │   ├── tareas/             # Gestión de tareas y operaciones CRUD
│   │   ├── estructuras/        # Estructuras de datos
│   │   │   ├── arboles/        # Árboles AVL
│   │   │   ├── pilas/          # Implementación de pilas
│   │   │   ├── colas/          # Implementación de colas
│   │   │   └── recursividad/   # Ejercicios recursivos
│   │   └── utils/              # Utilidades y validadores
│   ├── views/                  # 🖥️ Interfaces de usuario (Vista)
│   │   ├── PizzeriaMenu.java   # Menú de gestión de pizzería
│   │   ├── TareasMenu.java     # Menú de gestión de tareas
│   │   └── PilasColasMenu.java # Menú de pilas y colas
│   └── Main.java              # 🎯 Controlador principal
├── README.md                   # Este archivo
└── .gitignore                 # Archivos ignorados por Git
```

## 🚀 Características Principales

### 🍕 Gestión de Pizzería
- **Algoritmos de Ordenamiento**: Inserción, Shell Sort, Quick Sort
- **Análisis de Rendimiento**: Comparación de tiempos de ejecución
- **Gestión de Pedidos**: CRUD completo con validaciones

### 📋 Gestión de Tareas
- **Operaciones CRUD**: Crear, leer, actualizar, eliminar tareas
- **Estados**: Pendiente/Completada con filtros avanzados
- **Estadísticas**: Progreso visual y métricas

### 📚 Pilas y Colas
- **Pila (LIFO)**: Implementación con arreglo dinámico
- **Cola (FIFO)**: Implementación circular con arreglo
- **Operaciones**: Push/Pop, Enqueue/Dequeue con validaciones

### 🌳 Árboles AVL
- **Auto-balanceado**: Mantiene balance automáticamente
- **Operaciones**: Inserción, eliminación, búsqueda
- **Visualización**: Representación del árbol

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Java 17+
- **Paradigma**: Programación Orientada a Objetos
- **Arquitectura**: Modelo-Vista-Controlador (MVC)
- **Estructuras**: ArrayList, Arrays, Árboles binarios
- **Algoritmos**: Ordenamiento, Búsqueda, Recursividad

## 📦 Instalación y Ejecución

### Prerrequisitos
- Java JDK 17 o superior
- Terminal/Command Prompt

### Pasos de instalación

1. **Clonar el repositorio**
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   cd informatica3-guias-practicas
   ```

2. **Compilar el proyecto**
   ```bash
   javac -d bin src/**/*.java src/*.java
   ```

3. **Ejecutar la aplicación**
   ```bash
   java -cp bin Main
   ```

## 🎯 Uso del Sistema

### Menú Principal

Al ejecutar la aplicación, se presenta un menú principal con las siguientes opciones:

1. **🍕 Gestión de Pizzería** - Algoritmos de ordenamiento y análisis
2. **📋 Gestión de Tareas** - CRUD básico con listas y filtros
3. **📚 Pilas y Colas** - Estructuras LIFO y FIFO
4. **🌳 Árboles AVL** - Árboles binarios auto-balanceados
5. **🔄 Recursividad** - Ejercicios y ejemplos recursivos

### Navegación

- Use los números para seleccionar opciones
- Presione `0` para volver al menú anterior
- El sistema incluye validación de entrada robusta
- Mensajes informativos guían al usuario en cada paso

## 📚 Conceptos Implementados

### Algoritmos de Ordenamiento
- **Inserción**: O(n²) - Eficiente para conjuntos pequeños
- **Shell Sort**: O(n log n) - Mejora del ordenamiento por inserción
- **Quick Sort**: O(n log n) promedio - Divide y vencerás

### Estructuras de Datos
- **Pilas**: LIFO (Last In, First Out)
- **Colas**: FIFO (First In, First Out)
- **Árboles AVL**: Auto-balanceados para búsquedas eficientes
- **Listas**: ArrayList para manejo dinámico de datos

### Técnicas de Programación
- **Recursividad**: Fibonacci, factorial, búsquedas
- **Validación de datos**: Entrada robusta del usuario
- **Manejo de excepciones**: Gestión de errores elegante
- **Encapsulación**: Acceso controlado a datos

## 🧪 Funcionalidades de Prueba

### Datos de Prueba
- **Generación automática**: Crea conjuntos de datos para testing
- **Análisis de rendimiento**: Mide tiempos de ejecución
- **Casos extremos**: Prueba con conjuntos vacíos, grandes, etc.

### Validaciones
- **Entrada de datos**: Validación de tipos y rangos
- **Estados consistentes**: Verificación de integridad
- **Mensajes informativos**: Feedback claro al usuario

## 🤝 Contribución

Este proyecto está diseñado para fines educativos. Las contribuciones son bienvenidas:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Notas de Desarrollo

### Patrones Utilizados
- **MVC**: Separación clara de responsabilidades
- **Factory**: Para creación de estructuras de datos
- **Singleton**: Para validadores y utilidades

### Mejores Prácticas
- **Código limpio**: Nombres descriptivos y comentarios útiles
- **Modularidad**: Clases pequeñas y enfocadas
- **Reutilización**: Componentes reutilizables
- **Documentación**: JavaDoc en métodos importantes

## 📄 Licencia

Este proyecto es de uso educativo para la materia Informática 3.

## 👥 Autores

- **Equipo de Informática 3** - Desarrollo e implementación

## 🆘 Soporte

Si encuentras algún problema o tienes sugerencias:

1. Revisa la documentación en código
2. Verifica los mensajes de error del sistema
3. Consulta los ejemplos de uso en cada módulo

---

**¡Gracias por usar las Guías Prácticas de Informática 3!** 🎓✨