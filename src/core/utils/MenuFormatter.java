package core.utils;

public class MenuFormatter {
    
    public static void mostrarTituloPrincipal(String titulo) {
        String linea = "=".repeat(60);
        System.out.println("\n" + linea);
        System.out.println("  " + titulo.toUpperCase());
        System.out.println(linea);
    }
    
    public static void mostrarTituloSecundario(String titulo) {
        String linea = "-".repeat(40);
        System.out.println("\n" + linea);
        System.out.println("  " + titulo);
        System.out.println(linea);
    }
    
    public static void mostrarOpcion(int numero, String descripcion) {
        System.out.printf("  %d. %s%n", numero, descripcion);
    }
    
    public static void mostrarOpcionSalir(int numero) {
        System.out.printf("  %d. 🚪 Volver al menú anterior%n", numero);
    }
    
    public static void mostrarOpcionSalir() {
        mostrarOpcionSalir(0);
    }
    
    public static void mostrarSeparador() {
        System.out.println("  " + "-".repeat(35));
    }
    
    public static void mostrarMensajeExito(String mensaje) {
        System.out.println("✅ " + mensaje);
    }
    
    public static void mostrarMensajeError(String mensaje) {
        System.out.println("❌ " + mensaje);
    }
    
    public static void mostrarMensajeInfo(String mensaje) {
        System.out.println("ℹ️  " + mensaje);
    }
    
    public static void mostrarMensajeAdvertencia(String mensaje) {
        System.out.println("⚠️  " + mensaje);
    }
    
    public static void limpiarPantalla() {
        // Simula limpiar pantalla
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }
    
    public static void mostrarDespedida() {
        System.out.println("\n🔰 ¡Gracias por usar las Guías Prácticas de Informática 3!");
        System.out.println("🎓 ¡Sigue practicando y mejorando tus habilidades!");
    }
}