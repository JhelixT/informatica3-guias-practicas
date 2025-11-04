package core.utils;

/**
 * Formateador de menus con colores ANSI.
 * 
 * Esquema de colores:
 * - Naranja: Titulos principales y opciones
 * - Azul: Titulos secundarios y separadores
 * - Blanco: Texto general
 * - Verde: Mensajes de exito
 * - Rojo: Mensajes de error
 * 
 * Caracteres permitidos: =, -, *, #, |, +, /, \, [ ], ( ), { }
 * 
 * @author JhelixT
 * @version 3.0 (con soporte de colores ANSI)
 */
public class MenuFormatter {
    
    // ==================== TITULOS ====================
    
    /**
     * Muestra un titulo principal (nivel 1) con color naranja
     * Formato:
     * ==============================================================
     *   TITULO EN MAYUSCULAS
     * ==============================================================
     */
    public static void mostrarTituloPrincipal(String titulo) {
        String linea = "=".repeat(60);
        System.out.println("\n" + AnsiColors.naranja(linea));
        System.out.println(AnsiColors.naranjaNegrita("  " + titulo.toUpperCase()));
        System.out.println(AnsiColors.naranja(linea));
    }
    
    /**
     * Muestra un titulo secundario (nivel 2) con color azul
     * Formato:
     * ----------------------------------------
     *   Titulo Secundario
     * ----------------------------------------
     */
    public static void mostrarTituloSecundario(String titulo) {
        String linea = "-".repeat(40);
        System.out.println("\n" + AnsiColors.azul(linea));
        System.out.println(AnsiColors.azulNegrita("  " + titulo));
        System.out.println(AnsiColors.azul(linea));
    }
    
    // ==================== OPCIONES ====================
    
    /**
     * Muestra una opcion de menu con formato:
     *   [N] Descripcion
     */
    public static void mostrarOpcion(int numero, String descripcion) {
        System.out.printf("  %s %s%n", 
            AnsiColors.naranja("[" + numero + "]"), 
            AnsiColors.blanco(descripcion));
    }
    
    /**
     * Muestra opcion de salir
     */
    public static void mostrarOpcionSalir(int numero) {
        System.out.printf("  %s %s%n", 
            AnsiColors.gris("[" + numero + "]"), 
            AnsiColors.gris("Volver al menu anterior"));
    }
    
    public static void mostrarOpcionSalir() {
        mostrarOpcionSalir(0);
    }
    
    // ==================== SEPARADORES ====================
    
    /**
     * Muestra un separador simple con color azul
     */
    public static void mostrarSeparador() {
        System.out.println(AnsiColors.azul("  " + "-".repeat(35)));
    }
    
    /**
     * Muestra un separador de sección
     */
    public static void mostrarSeparadorSeccion() {
        System.out.println(AnsiColors.gris("  " + "=".repeat(50)));
    }
    
    // ==================== MENSAJES ====================
    
    /**
     * Muestra mensaje de exito con color verde
     * Formato: [OK] Mensaje
     */
    public static void mostrarMensajeExito(String mensaje) {
        System.out.println(AnsiColors.verde("[OK] ") + AnsiColors.blanco(mensaje));
    }
    
    /**
     * Muestra mensaje de error con color rojo
     * Formato: [ERROR] Mensaje
     */
    public static void mostrarMensajeError(String mensaje) {
        System.out.println(AnsiColors.rojo("[ERROR] ") + AnsiColors.blanco(mensaje));
    }
    
    /**
     * Muestra mensaje informativo con color cyan
     * Formato: [INFO] Mensaje
     */
    public static void mostrarMensajeInfo(String mensaje) {
        System.out.println(AnsiColors.cyan("[INFO] ") + AnsiColors.blanco(mensaje));
    }
    
    /**
     * Muestra mensaje de advertencia con color amarillo
     * Formato: [WARN] Mensaje
     */
    public static void mostrarMensajeAdvertencia(String mensaje) {
        System.out.println(AnsiColors.amarillo("[WARN] ") + AnsiColors.blanco(mensaje));
    }
    
    // ==================== UTILIDADES ====================
    
    /**
     * Simula limpiar pantalla con espacios
     */
    public static void limpiarPantalla() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }
    
    /**
     * Muestra mensaje de despedida con color naranja
     */
    public static void mostrarDespedida() {
        System.out.println("\n" + AnsiColors.naranjaNegrita("Gracias por usar las Guias Practicas de Informatica 3!"));
        System.out.println(AnsiColors.azul("Sigue practicando y mejorando tus habilidades!"));
    }
    
    /**
     * Muestra un banner decorativo
     * Formato:
     * ************************************************************
     * *                        MENSAJE                           *
     * ************************************************************
     */
    public static void mostrarBanner(String mensaje) {
        String linea = "*".repeat(60);
        int espacios = (60 - mensaje.length() - 2) / 2;
        String padding = " ".repeat(Math.max(0, espacios));
        
        System.out.println("\n" + AnsiColors.naranja(linea));
        System.out.println(AnsiColors.naranja("*") + padding + AnsiColors.blancoNegrita(mensaje) + padding + AnsiColors.naranja("*"));
        System.out.println(AnsiColors.naranja(linea));
    }
    
    /**
     * Muestra una caja de texto
     * Formato:
     * +----------------------------------+
     * | Mensaje                          |
     * +----------------------------------+
     */
    public static void mostrarCaja(String mensaje) {
        int ancho = 40;
        String borde = "+" + "-".repeat(ancho - 2) + "+";
        int espacios = ancho - mensaje.length() - 4;
        String padding = " ".repeat(Math.max(0, espacios));
        
        System.out.println(AnsiColors.azul(borde));
        System.out.println(AnsiColors.azul("| ") + AnsiColors.blanco(mensaje) + padding + AnsiColors.azul(" |"));
        System.out.println(AnsiColors.azul(borde));
    }
    
    /**
     * Muestra estado actual de una estructura
     * Formato:
     * >> Estado: [contenido]
     */
    public static void mostrarEstado(String etiqueta, String contenido) {
        System.out.printf("%s %s %s%n", 
            AnsiColors.azul(">>"), 
            AnsiColors.blanco(etiqueta + ":"),
            AnsiColors.naranja(contenido));
    }
}