package core.ejercicios.rojinegro;

import java.util.ArrayList;
import java.util.List;
import core.estructuras.arboles.ArbolRojinegro;

/**
 * Ejercicio 9: Consulta por rango [a,b]
 * 
 * In-order acotado para devolver claves en el rango [a,b] en orden.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio09_ConsultaRango {
    
    public static List<Integer> consultaRango(ArbolRojinegro<Integer> arbol, int a, int b) {
        List<Integer> resultado = new ArrayList<>();
        List<Integer> todos = arbol.inOrderTraversal();
        
        for (Integer valor : todos) {
            if (valor >= a && valor <= b) {
                resultado.add(valor);
            }
        }
        
        return resultado;
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 9: CONSULTA POR RANGO ═══\n");
        
        ArbolRojinegro<Integer> arbol = new ArbolRojinegro<>();
        int[] valores = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45};
        
        for (int v : valores) {
            arbol.insert(v);
        }
        
        System.out.println("Árbol completo: " + arbol.inOrderTraversal());
        
        System.out.println("\nRango [25, 60]: " + consultaRango(arbol, 25, 60));
        System.out.println("Rango [10, 30]: " + consultaRango(arbol, 10, 30));
        System.out.println("Rango [50, 100]: " + consultaRango(arbol, 50, 100));
    }
}
