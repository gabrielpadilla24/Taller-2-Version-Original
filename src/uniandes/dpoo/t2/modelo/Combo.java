package uniandes.dpoo.t2.modelo;

public class Combo {
    private String nombreCombo;
    private int precio;


    public Combo(String nombreCombo, double descuento, int precio) {
        this.nombreCombo = nombreCombo;
        this.precio = precio;
    }


    public int getPrecio() {
    	return precio;
    }

    public String getNombre() {
        return nombreCombo;
    }
    
    public String toString() {
        return nombreCombo + " - $" + getPrecio();
    }
    public String generarTextoFactura() {
        return "Combo: " + this.getNombre() + " - $" + this.getPrecio();
    }
}
