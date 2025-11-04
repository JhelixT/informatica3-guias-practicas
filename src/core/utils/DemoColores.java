package core.utils;

/**
 * Demostracion de los colores ANSI disponibles en el sistema.
 * 
 * Ejecuta este archivo para ver como se ven los colores en tu terminal.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class DemoColores {
    
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(AnsiColors.naranjaNegrita("  DEMOSTRACION DE COLORES ANSI"));
        System.out.println("=".repeat(60) + "\n");
        
        // Colores principales del esquema
        System.out.println(AnsiColors.azulNegrita("COLORES PRINCIPALES DEL ESQUEMA:"));
        System.out.println(AnsiColors.naranja("  [NARANJA] ") + "Color para titulos y destacados");
        System.out.println(AnsiColors.azul("  [AZUL]    ") + "Color para subtitulos y separadores");
        System.out.println(AnsiColors.blanco("  [BLANCO]  ") + "Color para texto general");
        System.out.println();
        
        // Colores de estado
        System.out.println(AnsiColors.azulNegrita("COLORES DE ESTADO:"));
        System.out.println(AnsiColors.verde("  [VERDE]   ") + "Mensajes de exito");
        System.out.println(AnsiColors.rojo("  [ROJO]    ") + "Mensajes de error");
        System.out.println(AnsiColors.amarillo("  [AMARILLO]") + "Mensajes de advertencia");
        System.out.println(AnsiColors.cyan("  [CYAN]    ") + "Mensajes informativos");
        System.out.println(AnsiColors.gris("  [GRIS]    ") + "Texto secundario/desactivado");
        System.out.println();
        
        // Estilos de texto
        System.out.println(AnsiColors.azulNegrita("ESTILOS DE TEXTO:"));
        System.out.println(AnsiColors.negrita("  Texto en negrita"));
        System.out.println(AnsiColors.subrayado("  Texto subrayado"));
        System.out.println(AnsiColors.naranjaNegrita("  Naranja en negrita"));
        System.out.println(AnsiColors.azulNegrita("  Azul en negrita"));
        System.out.println();
        
        // Ejemplos de uso en menus
        System.out.println(AnsiColors.azulNegrita("EJEMPLOS DE USO EN MENUS:"));
        System.out.println();
        
        // Titulo principal
        String lineaPrincipal = "=".repeat(50);
        System.out.println(AnsiColors.naranja(lineaPrincipal));
        System.out.println(AnsiColors.naranjaNegrita("  MENU PRINCIPAL"));
        System.out.println(AnsiColors.naranja(lineaPrincipal));
        System.out.println();
        
        // Opciones
        System.out.println("  " + AnsiColors.naranja("[1]") + " " + AnsiColors.blanco("Primera opcion"));
        System.out.println(AnsiColors.gris("     |-- Descripcion de la opcion"));
        System.out.println("  " + AnsiColors.naranja("[2]") + " " + AnsiColors.blanco("Segunda opcion"));
        System.out.println(AnsiColors.gris("     |-- Otra descripcion"));
        System.out.println();
        
        // Separador
        System.out.println(AnsiColors.azul("  " + "-".repeat(35)));
        System.out.println("  " + AnsiColors.gris("[0]") + " " + AnsiColors.gris("Volver al menu anterior"));
        System.out.println();
        
        // Mensajes de estado
        System.out.println(AnsiColors.verde("[OK] ") + AnsiColors.blanco("Operacion realizada con exito"));
        System.out.println(AnsiColors.rojo("[ERROR] ") + AnsiColors.blanco("No se pudo completar la operacion"));
        System.out.println(AnsiColors.cyan("[INFO] ") + AnsiColors.blanco("Informacion importante"));
        System.out.println(AnsiColors.amarillo("[WARN] ") + AnsiColors.blanco("Advertencia al usuario"));
        System.out.println();
        
        // Banner
        String banner = "*".repeat(50);
        System.out.println(AnsiColors.naranja(banner));
        System.out.println(AnsiColors.naranja("*") + "        " + AnsiColors.blancoNegrita("SISTEMA DE GESTION") + "        " + AnsiColors.naranja("*"));
        System.out.println(AnsiColors.naranja(banner));
        System.out.println();
        
        // Caja
        String cajaBorde = "+" + "-".repeat(38) + "+";
        System.out.println(AnsiColors.azul(cajaBorde));
        System.out.println(AnsiColors.azul("| ") + AnsiColors.blanco("Mensaje dentro de una caja") + "           " + AnsiColors.azul("|"));
        System.out.println(AnsiColors.azul(cajaBorde));
        System.out.println();
        
        // Estado
        System.out.println(AnsiColors.azul(">> ") + AnsiColors.blanco("Estado actual:") + " " + AnsiColors.naranja("[Cola vacia]"));
        System.out.println();
        
        // Footer
        System.out.println(AnsiColors.gris("=".repeat(60)));
        System.out.println(AnsiColors.gris("  Fin de la demostracion"));
        System.out.println(AnsiColors.gris("=".repeat(60)));
        System.out.println();
    }
}
