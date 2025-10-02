package core.pizzeria;

import java.util.ArrayList;
import java.util.List;

public class Pizzeria {
    private List<Pedido> pedidos;

    public Pizzeria() {
        this.pedidos = new ArrayList<>();
    }
    
    public void agregarPedido(double tiempoPrepMinutos, double precioTotal, String nombreCliente) {
        if (tiempoPrepMinutos < 0) {
            throw new IllegalArgumentException("El tiempo de preparación no puede ser menor a 0");
        }
        if (precioTotal < 0) {
            throw new IllegalArgumentException("El precio total no puede ser menor a 0");
        }
        if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede ser vacío");
        }
        
        pedidos.add(new Pedido(tiempoPrepMinutos, precioTotal, nombreCliente.trim()));
    }

    public void agregarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }
        pedidos.add(pedido);
    }
    
    public void eliminarPedido(int index) {
        if (index < 0 || index >= pedidos.size()) {
            throw new IndexOutOfBoundsException("El índice del pedido no es válido");
        }
        pedidos.remove(index);
    }

    public void editarTiempoPedido(int index, double nuevoTiempo) {
        if (index < 0 || index >= pedidos.size()) {
            throw new IndexOutOfBoundsException("El índice del pedido no es válido");
        }
        if (nuevoTiempo < 0) {
            throw new IllegalArgumentException("El tiempo no puede ser negativo");
        }
        pedidos.get(index).setTiempoPrepMinutos(nuevoTiempo);
    }
    
    public void editarPrecioPedido(int index, double nuevoPrecio) {
        if (index < 0 || index >= pedidos.size()) {
            throw new IndexOutOfBoundsException("El índice del pedido no es válido");
        }
        if (nuevoPrecio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        pedidos.get(index).setPrecioTotal(nuevoPrecio);
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos); // Retorna una copia para encapsulación
    }
    
    public int getCantidadPedidos() {
        return pedidos.size();
    }
    
    public boolean tienePedidos() {
        return !pedidos.isEmpty();
    }
    
    public Pedido getPedido(int index) {
        if (index < 0 || index >= pedidos.size()) {
            throw new IndexOutOfBoundsException("El índice del pedido no es válido");
        }
        return pedidos.get(index);
    }
}