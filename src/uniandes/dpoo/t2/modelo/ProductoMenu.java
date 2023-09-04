package uniandes.dpoo.t2.modelo;

public class ProductoMenu {
    private String nombre;
    private int precio; // Cambiado a int y renombrado

    public ProductoMenu(String nombre, int precioBase) {
        this.nombre = nombre;
        this.precio = precioBase;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() { // Cambiado a int y renombrado
        return precio;
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio; // Cambiado a precioBase
    }
    
    public String generarTextoFactura() {
        return this.getNombre() + " - $" + this.getPrecio();
    }
}
