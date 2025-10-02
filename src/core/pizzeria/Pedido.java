package core.pizzeria;

public class Pedido {
    private double tiempoPrepMinutos;
    private double precioTotal;
    private String nombreCliente;
    
    public Pedido(double tiempoPrepMinutos, double precioTotal, String nombreCliente) {
        this.tiempoPrepMinutos = tiempoPrepMinutos;
        this.precioTotal = precioTotal;
        this.nombreCliente = nombreCliente;
    }
    
    public double getTiempoPrepMinutos() {
        return tiempoPrepMinutos;
    }
    
    public double getPrecioTotal() {
        return precioTotal;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setTiempoPrepMinutos(double tiempoPrepMinutos) {
        this.tiempoPrepMinutos = tiempoPrepMinutos;
    }
    
    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    @Override
    public String toString() {
        return String.format("Cliente: %s | Precio: $%.2f | Tiempo: %.1f min", 
                           nombreCliente, precioTotal, tiempoPrepMinutos);
    }
}