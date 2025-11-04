# Sistema de Colores ANSI

## 📋 Descripción

El proyecto ahora incluye soporte de colores ANSI para mejorar la experiencia visual en los menús y mensajes del sistema.

## 🎨 Esquema de Colores

### Colores Principales
- **Naranja** (`\033[38;5;214m`): Títulos principales, opciones destacadas
- **Azul** (`\033[38;5;33m`): Títulos secundarios, separadores
- **Blanco** (`\033[97m`): Texto general

### Colores de Estado
- **Verde** (`\033[92m`): Mensajes de éxito `[OK]`
- **Rojo** (`\033[91m`): Mensajes de error `[ERROR]`
- **Amarillo** (`\033[93m`): Advertencias `[WARN]`
- **Cyan** (`\033[96m`): Información `[INFO]`
- **Gris** (`\033[90m`): Texto secundario

## 🛠️ Uso

### Clase AnsiColors

```java
import core.utils.AnsiColors;

// Aplicar colores
System.out.println(AnsiColors.naranja("Texto naranja"));
System.out.println(AnsiColors.azul("Texto azul"));
System.out.println(AnsiColors.blanco("Texto blanco"));

// Estilos
System.out.println(AnsiColors.negrita("Texto en negrita"));
System.out.println(AnsiColors.naranjaNegrita("Naranja en negrita"));
System.out.println(AnsiColors.azulNegrita("Azul en negrita"));

// Mensajes de estado
System.out.println(AnsiColors.verde("Éxito"));
System.out.println(AnsiColors.rojo("Error"));
System.out.println(AnsiColors.amarillo("Advertencia"));
```

### Clase MenuFormatter

```java
import core.utils.MenuFormatter;

// Títulos
MenuFormatter.mostrarTituloPrincipal("MENU PRINCIPAL");
MenuFormatter.mostrarTituloSecundario("Submenu");

// Opciones
MenuFormatter.mostrarOpcion(1, "Primera opcion");
MenuFormatter.mostrarOpcionSalir(0);

// Separadores
MenuFormatter.mostrarSeparador();
MenuFormatter.mostrarSeparadorSeccion();

// Mensajes
MenuFormatter.mostrarMensajeExito("Operacion exitosa");
MenuFormatter.mostrarMensajeError("Error al procesar");
MenuFormatter.mostrarMensajeInfo("Informacion importante");
MenuFormatter.mostrarMensajeAdvertencia("Cuidado");

// Utilidades
MenuFormatter.mostrarBanner("BIENVENIDO");
MenuFormatter.mostrarCaja("Mensaje importante");
MenuFormatter.mostrarEstado("Cola", "[vacia]");
```

## 🖥️ Compatibilidad

### Terminales Compatibles
- ✅ Windows 10+ (PowerShell, CMD, Windows Terminal)
- ✅ Linux (todas las distribuciones)
- ✅ macOS (Terminal, iTerm2)
- ✅ Git Bash en Windows
- ✅ VS Code Terminal

### Verificación
El sistema detecta automáticamente si la terminal soporta colores ANSI. Si no los soporta, muestra un mensaje y continúa sin colores.

```java
// En Main.java
AnsiColors.inicializar(); // Detecta soporte automáticamente
```

## 🎯 Demo

Para ver todos los colores y estilos disponibles:

```bash
# Compilar
javac -d bin -sourcepath src src/core/utils/DemoColores.java

# Ejecutar
java -cp bin core.utils.DemoColores
```

## 📝 Caracteres Permitidos

Solo se usan caracteres ASCII estándar:
- `=` Líneas dobles
- `-` Líneas simples
- `*` Asteriscos para banners
- `#` Numerales para comentarios
- `|` Barras verticales para listas
- `+` Cruz para cajas
- `[` `]` Corchetes para opciones
- `/` `\` Barras para árboles

**NO se usan:**
- ❌ Caracteres Unicode (│, ├, └, →, •, etc.)
- ❌ Emojis (🎨, ✅, ❌, etc.)
- ❌ Símbolos especiales que puedan causar problemas de encoding

## 🔧 Personalización

Para cambiar los colores del esquema, editar `AnsiColors.java`:

```java
// Esquema actual (naranja, azul, blanco)
public static final String NARANJA = "\033[38;5;214m";
public static final String AZUL = "\033[38;5;33m";
public static final String BLANCO = "\033[97m";

// Ejemplo de esquema alternativo (verde, morado, blanco)
public static final String PRINCIPAL = "\033[38;5;40m";   // Verde
public static final String SECUNDARIO = "\033[38;5;141m"; // Morado
public static final String TEXTO = "\033[97m";            // Blanco
```

## 📚 Referencias

- [ANSI Escape Codes](https://en.wikipedia.org/wiki/ANSI_escape_code)
- [256 Color Palette](https://www.ditig.com/256-colors-cheat-sheet)
- [Terminal Colors](https://misc.flogisoft.com/bash/tip_colors_and_formatting)

## 🤝 Contribuir

Al agregar nuevos menús o mensajes, usar siempre `MenuFormatter` y `AnsiColors` para mantener consistencia visual en todo el proyecto.
