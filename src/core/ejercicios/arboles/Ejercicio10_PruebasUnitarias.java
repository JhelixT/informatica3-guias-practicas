package core.ejercicios.arboles;

import core.estructuras.arboles.NodoAVL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Ejercicio 10: Secuencias "estresantes" y pruebas unitarias
 * 
 * a) Genere 3 secuencias de 20 números (creciente, decreciente, pseudoaleatoria con repetidos)
 *    e inserte en un AVL (ignore repetidos si su diseño no los admite).
 * b) Escriba tests que verifiquen tras cada inserción: esAVL == true, alturas correctas
 *    y orden in-order creciente.
 * c) Informe cuántas rotaciones totales se aplicaron en cada secuencia.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio10_PruebasUnitarias {
    
    private static int contadorRotaciones;
    private static List<String> erroresEncontrados;
    
    static class ResultadoTest {
        boolean exito;
        String mensaje;
        NodoAVL<Integer> arbol;
        int rotaciones;
        
        public ResultadoTest(boolean exito, String mensaje, NodoAVL<Integer> arbol, int rotaciones) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.arbol = arbol;
            this.rotaciones = rotaciones;
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 10: PRUEBAS UNITARIAS ═══\n");
        
        int[] secuenciaCreciente = generarSecuenciaCreciente(20);
        int[] secuenciaDecreciente = generarSecuenciaDecreciente(20);
        int[] secuenciaAleatoria = generarSecuenciaAleatoria(20, 15);
        
        System.out.println("Prueba 1 - Secuencia creciente:");
        ResultadoTest resultado1 = probarSecuencia(secuenciaCreciente, "Creciente");
        System.out.println("Resultado: " + (resultado1.exito ? "✅ PASS" : "❌ FAIL"));
        
        System.out.println("\nPrueba 2 - Secuencia decreciente:");
        ResultadoTest resultado2 = probarSecuencia(secuenciaDecreciente, "Decreciente");
        System.out.println("Resultado: " + (resultado2.exito ? "✅ PASS" : "❌ FAIL"));
        
        System.out.println("\nPrueba 3 - Secuencia aleatoria:");
        ResultadoTest resultado3 = probarSecuencia(secuenciaAleatoria, "Pseudoaleatoria");
        System.out.println("Resultado: " + (resultado3.exito ? "✅ PASS" : "❌ FAIL"));
        
        int testsPasados = (resultado1.exito ? 1 : 0) + (resultado2.exito ? 1 : 0) + (resultado3.exito ? 1 : 0);
        System.out.println("\nTests pasados: " + testsPasados + "/3");
    }
    
    private static ResultadoTest probarSecuencia(int[] secuencia, String nombre) {
        NodoAVL<Integer> raiz = null;
        contadorRotaciones = 0;
        erroresEncontrados = new ArrayList<>();
        
        for (int valor : secuencia) {
            raiz = insertar(raiz, valor);
            
            if (!esAVLValido(raiz)) {
                erroresEncontrados.add("Después de insertar " + valor + ": NO es AVL válido");
            }
            if (!esABBValido(raiz, null, null)) {
                erroresEncontrados.add("Después de insertar " + valor + ": NO es ABB válido");
            }
        }
        
        boolean exito = erroresEncontrados.isEmpty();
        return new ResultadoTest(exito, exito ? "OK" : "Falló", raiz, contadorRotaciones);
    }
    

    
    private static boolean esAVLValido(NodoAVL<Integer> nodo) {
        if (nodo == null) return true;
        
        int balance = obtenerBalance(nodo);
        
        if (Math.abs(balance) > 1) {
            return false;
        }
        
        return esAVLValido(nodo.getLeft()) && esAVLValido(nodo.getRight());
    }
    
    private static boolean esABBValido(NodoAVL<Integer> nodo, Integer min, Integer max) {
        if (nodo == null) return true;
        
        if (min != null && nodo.getData() <= min) return false;
        if (max != null && nodo.getData() >= max) return false;
        
        return esABBValido(nodo.getLeft(), min, nodo.getData()) &&
               esABBValido(nodo.getRight(), nodo.getData(), max);
    }
    
    private static int[] generarSecuenciaCreciente(int n) {
        int[] secuencia = new int[n];
        for (int i = 0; i < n; i++) {
            secuencia[i] = i + 1;
        }
        return secuencia;
    }
    
    private static int[] generarSecuenciaDecreciente(int n) {
        int[] secuencia = new int[n];
        for (int i = 0; i < n; i++) {
            secuencia[i] = n - i;
        }
        return secuencia;
    }
    
    private static int[] generarSecuenciaAleatoria(int n, int max) {
        Random random = new Random(42); // Semilla fija para reproducibilidad
        int[] secuencia = new int[n];
        for (int i = 0; i < n; i++) {
            secuencia[i] = random.nextInt(max) + 1;
        }
        return secuencia;
    }
    

    
    private static NodoAVL<Integer> insertar(NodoAVL<Integer> nodo, int valor) {
        if (nodo == null) {
            return new NodoAVL<>(valor);
        }
        
        if (valor < nodo.getData()) {
            nodo.setLeft(insertar(nodo.getLeft(), valor));
        } else if (valor > nodo.getData()) {
            nodo.setRight(insertar(nodo.getRight(), valor));
        } else {
            return nodo; // Duplicado ignorado
        }
        
        actualizarAltura(nodo);
        int balance = obtenerBalance(nodo);
        
        if (balance > 1 && valor < nodo.getLeft().getData()) {
            contadorRotaciones++;
            return rotarDerecha(nodo);
        }
        if (balance < -1 && valor > nodo.getRight().getData()) {
            contadorRotaciones++;
            return rotarIzquierda(nodo);
        }
        if (balance > 1 && valor > nodo.getLeft().getData()) {
            contadorRotaciones += 2;
            nodo.setLeft(rotarIzquierda(nodo.getLeft()));
            return rotarDerecha(nodo);
        }
        if (balance < -1 && valor < nodo.getRight().getData()) {
            contadorRotaciones += 2;
            nodo.setRight(rotarDerecha(nodo.getRight()));
            return rotarIzquierda(nodo);
        }
        
        return nodo;
    }
    
    private static void actualizarAltura(NodoAVL<Integer> nodo) {
        if (nodo != null) {
            int alturaIzq = altura(nodo.getLeft());
            int alturaDer = altura(nodo.getRight());
            nodo.setHeight(1 + Math.max(alturaIzq, alturaDer));
            nodo.setBalance(alturaIzq - alturaDer);
        }
    }
    
    private static int altura(NodoAVL<Integer> nodo) {
        return (nodo == null) ? 0 : nodo.getHeight();
    }
    
    private static int obtenerBalance(NodoAVL<Integer> nodo) {
        return (nodo == null) ? 0 : nodo.getBalance();
    }
    
    private static NodoAVL<Integer> rotarDerecha(NodoAVL<Integer> y) {
        NodoAVL<Integer> x = y.getLeft();
        NodoAVL<Integer> T2 = x.getRight();
        x.setRight(y);
        y.setLeft(T2);
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }
    
    private static NodoAVL<Integer> rotarIzquierda(NodoAVL<Integer> x) {
        NodoAVL<Integer> y = x.getRight();
        NodoAVL<Integer> T2 = y.getLeft();
        y.setLeft(x);
        x.setRight(T2);
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }
}
