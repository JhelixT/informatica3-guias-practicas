package core.ejercicios.arboles;

import core.estructuras.arboles.NodoAVL;

/**
 * Ejercicio 5: Comprobador de AVL
 * 
 * Implemente un método esAVL(Nodo r) que:
 * - Devuelva (esAVL, altura) en una sola pasada recursiva.
 * - Verifique que para todo nodo |altura(izq) - altura(der)| ≤ 1 y que
 *   además respete la propiedad de ABB.
 * Pruebe con árboles válidos e inválidos pequeños.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio05_ComprobadorAVL {
    
    // Clase auxiliar para retornar múltiples valores
    static class ResultadoAVL {
        boolean esAVL;
        boolean esABB;
        int altura;
        Integer minimo;
        Integer maximo;
        String razon;
        
        public ResultadoAVL(boolean esAVL, boolean esABB, int altura, Integer min, Integer max, String razon) {
            this.esAVL = esAVL;
            this.esABB = esABB;
            this.altura = altura;
            this.minimo = min;
            this.maximo = max;
            this.razon = razon;
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║  EJERCICIO 5: COMPROBADOR DE AVL                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝\n");
        
        // Caso 1: AVL válido
        System.out.println("Caso 1: AVL válido");
        NodoAVL<Integer> avlValido = construirAVLValido();
        ResultadoAVL r1 = esAVL(avlValido);
        System.out.println("Es AVL: " + r1.esAVL + ", Altura: " + r1.altura);
        
        // Caso 2: ABB desbalanceado
        System.out.println("\nCaso 2: ABB desbalanceado");
        NodoAVL<Integer> abbDesbalanceado = construirABBDesbalanceado();
        ResultadoAVL r2 = esAVL(abbDesbalanceado);
        System.out.println("Es AVL: " + r2.esAVL + " - " + r2.razon);
        
        // Caso 3: No es ABB
        System.out.println("\nCaso 3: No es ABB");
        NodoAVL<Integer> noABB = construirNoABB();
        ResultadoAVL r3 = esAVL(noABB);
        System.out.println("Es AVL: " + r3.esAVL + " - " + r3.razon);
        
        // Conclusiones
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("CONCLUSIONES");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        System.out.println("💡 Condiciones para que un árbol sea AVL:");
        System.out.println("   1. Debe ser un Árbol Binario de Búsqueda (ABB)");
        System.out.println("      • Todo nodo izquierdo < padre < todo nodo derecho");
        System.out.println("   2. Debe estar balanceado en TODOS los nodos");
        System.out.println("      • |altura(izq) - altura(der)| ≤ 1 para cada nodo");
        System.out.println("   3. Los subárboles izquierdo y derecho también deben ser AVL");
        
        System.out.println("\n🔍 Método de verificación:");
        System.out.println("   • Una sola pasada recursiva (O(n))");
        System.out.println("   • Verifica propiedad ABB usando min/max de subárboles");
        System.out.println("   • Calcula alturas y factores de equilibrio simultáneamente");
        System.out.println("   • Detección temprana: falla al primer problema encontrado");
    }
    
    /**
     * Verifica si un árbol es AVL válido en una sola pasada
     * @param nodo Raíz del árbol a verificar
     * @return Resultado con información completa de la verificación
     */
    private static ResultadoAVL esAVL(NodoAVL<Integer> nodo) {
        // Caso base: árbol vacío es AVL válido
        if (nodo == null) {
            return new ResultadoAVL(true, true, 0, null, null, "Árbol vacío (válido por definición)");
        }
        
        // Verificar recursivamente subárbol izquierdo
        ResultadoAVL izq = esAVL(nodo.getLeft());
        if (!izq.esAVL || !izq.esABB) {
            return new ResultadoAVL(false, false, 0, null, null, 
                "Subárbol izquierdo no es AVL: " + izq.razon);
        }
        
        // Verificar recursivamente subárbol derecho
        ResultadoAVL der = esAVL(nodo.getRight());
        if (!der.esAVL || !der.esABB) {
            return new ResultadoAVL(false, false, 0, null, null, 
                "Subárbol derecho no es AVL: " + der.razon);
        }
        
        // Verificar propiedad ABB
        if (izq.maximo != null && izq.maximo >= nodo.getData()) {
            return new ResultadoAVL(false, false, 0, null, null, 
                String.format("Viola ABB: nodo %d tiene hijo izquierdo %d mayor o igual", 
                    nodo.getData(), izq.maximo));
        }
        
        if (der.minimo != null && der.minimo <= nodo.getData()) {
            return new ResultadoAVL(false, false, 0, null, null, 
                String.format("Viola ABB: nodo %d tiene hijo derecho %d menor o igual", 
                    nodo.getData(), der.minimo));
        }
        
        // Calcular altura y factor de equilibrio
        int altura = 1 + Math.max(izq.altura, der.altura);
        int balance = izq.altura - der.altura;
        
        // Verificar balance AVL
        if (Math.abs(balance) > 1) {
            return new ResultadoAVL(false, true, altura, null, null, 
                String.format("Nodo %d desbalanceado: FE = %d (debe ser -1, 0 o 1)", 
                    nodo.getData(), balance));
        }
        
        // Calcular min y max del subárbol actual
        Integer min = (izq.minimo != null) ? izq.minimo : nodo.getData();
        Integer max = (der.maximo != null) ? der.maximo : nodo.getData();
        
        return new ResultadoAVL(true, true, altura, min, max, 
            String.format("AVL válido (altura=%d, FE=%d)", altura, balance));
    }
    
    private static NodoAVL<Integer> construirAVLValido() {
        //         20
        //        /  \
        //      10    30
        //     /  \   /  \
        //    5   15 25  35
        
        NodoAVL<Integer> raiz = new NodoAVL<>(20);
        raiz.setLeft(new NodoAVL<>(10));
        raiz.setRight(new NodoAVL<>(30));
        raiz.getLeft().setLeft(new NodoAVL<>(5));
        raiz.getLeft().setRight(new NodoAVL<>(15));
        raiz.getRight().setLeft(new NodoAVL<>(25));
        raiz.getRight().setRight(new NodoAVL<>(35));
        
        actualizarAlturas(raiz);
        return raiz;
    }
    
    private static NodoAVL<Integer> construirABBDesbalanceado() {
        //     10
        //    /  \
        //   5   20
        //      /  \
        //     15  30
        //            \
        //            40
        //              \
        //              50  <- Causa desbalance en nodo 20
        
        NodoAVL<Integer> raiz = new NodoAVL<>(10);
        raiz.setLeft(new NodoAVL<>(5));
        raiz.setRight(new NodoAVL<>(20));
        raiz.getRight().setLeft(new NodoAVL<>(15));
        raiz.getRight().setRight(new NodoAVL<>(30));
        raiz.getRight().getRight().setRight(new NodoAVL<>(40));
        raiz.getRight().getRight().getRight().setRight(new NodoAVL<>(50));
        
        actualizarAlturas(raiz);
        return raiz;
    }
    
    private static NodoAVL<Integer> construirNoABB() {
        //     20
        //    /  \
        //   30   10  <- Viola ABB: 30 > 20 pero está a la izquierda
        //  /  \
        // 5   35
        
        NodoAVL<Integer> raiz = new NodoAVL<>(20);
        raiz.setLeft(new NodoAVL<>(30));  // Incorrecto: mayor que raíz
        raiz.setRight(new NodoAVL<>(10)); // Incorrecto: menor que raíz
        raiz.getLeft().setLeft(new NodoAVL<>(5));
        raiz.getLeft().setRight(new NodoAVL<>(35));
        
        actualizarAlturas(raiz);
        return raiz;
    }
    
    private static void actualizarAlturas(NodoAVL<Integer> nodo) {
        if (nodo == null) return;
        
        actualizarAlturas(nodo.getLeft());
        actualizarAlturas(nodo.getRight());
        
        int alturaIzq = (nodo.getLeft() == null) ? 0 : nodo.getLeft().getHeight();
        int alturaDer = (nodo.getRight() == null) ? 0 : nodo.getRight().getHeight();
        nodo.setHeight(1 + Math.max(alturaIzq, alturaDer));
        nodo.setBalance(alturaIzq - alturaDer);
    }
}
