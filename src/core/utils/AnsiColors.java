package core.utils;

/**
 * Utilidad para aplicar colores ANSI a los prints de la consola.
 * 
 * Proporciona colores basicos (naranja, azul, blanco) y utilidades para formatear texto.
 * 
 * Codigos ANSI:
 * - \033[XXm -> Establece formato/color
 * - \033[0m  -> Resetea al color por defecto
 * 
 * @author JhelixT
 * @version 1.0
 */
public class AnsiColors {
    
    // ==================== CODIGOS DE COLOR ====================
    
    // Reset
    public static final String RESET = "\033[0m";
    
    // Colores principales del esquema
    public static final String NARANJA = "\033[38;5;214m";  // Naranja vibrante
    public static final String AZUL = "\033[38;5;33m";      // Azul claro
    public static final String BLANCO = "\033[97m";         // Blanco brillante
    
    // Colores adicionales utiles
    public static final String VERDE = "\033[38;2;143;208;78m";          // Verde para exitos
    public static final String ROJO = "\033[91m";           // Rojo para errores
    public static final String AMARILLO = "\033[38;2;255;237;36m";       // Amarillo para advertencias
    public static final String CYAN = "\033[38;2;36;218;255m";           // Cyan para info
    public static final String GRIS = "\033[90m";           // Gris para separadores
    
    // Estilos de texto
    public static final String NEGRITA = "\033[1m";
    public static final String SUBRAYADO = "\033[4m";
    public static final String INVERTIDO = "\033[7m";
    
    // ==================== METODOS AUXILIARES ====================
    
    /**
     * Aplica color naranja al texto
     */
    public static String naranja(String texto) {
        return NARANJA + texto + RESET;
    }
    
    /**
     * Aplica color azul al texto
     */
    public static String azul(String texto) {
        return AZUL + texto + RESET;
    }
    
    /**
     * Aplica color blanco al texto
     */
    public static String blanco(String texto) {
        return BLANCO + texto + RESET;
    }
    
    /**
     * Aplica color verde al texto (exito)
     */
    public static String verde(String texto) {
        return VERDE + texto + RESET;
    }
    
    /**
     * Aplica color rojo al texto (error)
     */
    public static String rojo(String texto) {
        return ROJO + texto + RESET;
    }
    
    /**
     * Aplica color amarillo al texto (advertencia)
     */
    public static String amarillo(String texto) {
        return AMARILLO + texto + RESET;
    }
    
    /**
     * Aplica color cyan al texto (info)
     */
    public static String cyan(String texto) {
        return CYAN + texto + RESET;
    }
    
    /**
     * Aplica color gris al texto (separadores)
     */
    public static String gris(String texto) {
        return GRIS + texto + RESET;
    }
    
    /**
     * Aplica negrita al texto
     */
    public static String negrita(String texto) {
        return NEGRITA + texto + RESET;
    }
    
    /**
     * Aplica subrayado al texto
     */
    public static String subrayado(String texto) {
        return SUBRAYADO + texto + RESET;
    }
    
    /**
     * Combina color naranja con negrita
     */
    public static String naranjaNegrita(String texto) {
        return NARANJA + NEGRITA + texto + RESET;
    }
    
    /**
     * Combina color azul con negrita
     */
    public static String azulNegrita(String texto) {
        return AZUL + NEGRITA + texto + RESET;
    }
    
    /**
     * Combina color blanco con negrita
     */
    public static String blancoNegrita(String texto) {
        return BLANCO + NEGRITA + texto + RESET;
    }
    
    /**
     * Verifica si la terminal soporta colores ANSI
     * (En Windows 10+ y Unix/Linux/Mac normalmente sí)
     */
    public static boolean soportaColores() {
        String os = System.getProperty("os.name").toLowerCase();
        String term = System.getenv("TERM");
        
        // Unix/Linux/Mac siempre soportan
        if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return true;
        }
        
        // Windows 10+ con terminal moderna
        if (os.contains("win")) {
            String version = System.getProperty("os.version");
            return version != null && !version.startsWith("5."); // Win Vista+ (6.0+)
        }
        
        return term != null && !term.equals("dumb");
    }
    
    /**
     * Desactiva colores si la terminal no los soporta
     */
    public static void inicializar() {
        if (!soportaColores()) {
            System.out.println("[INFO] Terminal sin soporte ANSI - Colores desactivados");
        }
    }
}
