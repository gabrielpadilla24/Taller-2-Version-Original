package uniandes.dpoo.t2.modelo;

import java.util.ArrayList;
import java.util.List;

public class ProductoAjustado {
    private ProductoMenu productoBase;
    private List<Ingrediente> ingredientesAdicionales;

    public ProductoAjustado(ProductoMenu productoBase) {
        this.productoBase = productoBase;
        this.ingredientesAdicionales = new ArrayList<>();
    }

    public void agregarIngrediente(Ingrediente ingrediente) {
        ingredientesAdicionales.add(ingrediente);
    }

    public void eliminarIngrediente(Ingrediente ingrediente) {
        ingredientesAdicionales.remove(ingrediente);
    }

    public ProductoMenu getProductoBase() {
        return productoBase;
    }

    public List<Ingrediente> getIngredientesAdicionales() {
        return ingredientesAdicionales;
    }

    public int getPrecioTotal() {
        int precioTotal = productoBase.getPrecio();
        for (Ingrediente ingrediente : ingredientesAdicionales) {
            precioTotal += ingrediente.getPrecio();
        }
        return precioTotal;
    }
    public String getNombre() {
        return productoBase.getNombre();
    }

    public int getPrecioTotalConIngredientes() {
        int precioBase = productoBase.getPrecio();
        int precioIngredientes = 0;

        for (Ingrediente ingrediente : ingredientesAdicionales) {
            precioIngredientes += ingrediente.getPrecio();
        }

        return precioBase + precioIngredientes;
    }
    
    public String generarTextoFactura() {
        // Suponiendo que tenga un método para calcular el precio total con ingredientes
        return this.getNombre() + " (ajustado) - $" + this.getPrecioTotalConIngredientes();
    }

    @Override
    public String toString() {
        StringBuilder descripcion = new StringBuilder(productoBase.toString());
        for (Ingrediente ingrediente : ingredientesAdicionales) {
            descripcion.append("\n+ ").append(ingrediente.toString());
        }
        return descripcion.toString();
    }
}
