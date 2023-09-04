package uniandes.dpoo.t2.modelo;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.File;


public class Pedido {
    private List<ProductoMenu> productosPedido; // Lista de productos en el pedido
    private List<Combo> combos;

    private static int numeroPedidos = 0; // Este es un contador global para todos los pedidos
    private int idPedido;
    private String nombreCliente;
    private String direccionCliente;
    private int precioNeto;
    private double IVA = 0.19; // Suponiendo un IVA del 19%
    private boolean cerrado = false;  // Indica si el pedido está cerrado
    private List<ProductoAjustado> productosAjustados;



    public Pedido(String nombreCliente, String direccionCliente) {
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.precioNeto = 0; // Se inicializa en 0
        this.idPedido = ++numeroPedidos; // Se incrementa el número total de pedidos y se asigna como ID
        this.productosPedido = new ArrayList<>(); 
        this.combos = new ArrayList<>();
        this.productosAjustados = new ArrayList<>();



    }
    

    
    public boolean estaCerrado() {
        return cerrado;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getPrecioNetoPedido() {
        return precioNeto;
    }

    public int getPrecioTotalPedido() {
        return (int) (precioNeto + getPrecioIVAPedido()); // Precio neto + IVA
    }

    public int getPrecioIVAPedido() {
        return (int) (precioNeto * IVA); // Suponiendo que IVA es un valor entre 0 y 1, como 0.19 para 19%
    }
    
    public void agregarProducto(ProductoMenu producto) {
        if (cerrado) {
            System.out.println("No puedes añadir productos a un pedido cerrado.");
            return;
        }
        productosPedido.add(producto);
        precioNeto += producto.getPrecio();
    }
    
    public void agregarProductoAjustado(ProductoAjustado productoAjustado) {
        if (cerrado) {
            System.out.println("No puedes añadir productos a un pedido cerrado.");
            return;
        }
        productosAjustados.add(productoAjustado);
        precioNeto += productoAjustado.getPrecioTotal();
    }

    public void agregarCombo(Combo combo) {
        if (cerrado) {
            System.out.println("No puedes añadir combos a un pedido cerrado.");
            return;
        }
        this.combos.add(combo);
        precioNeto += combo.getPrecio();
    }

    private String generarTextoFactura() {
        StringBuilder factura = new StringBuilder();
        factura.append("Factura Pedido: ").append(this.getIdPedido()).append("\n");
        factura.append("Cliente: ").append(this.getNombreCliente()).append("\n");
        factura.append("Dirección: ").append(this.getDireccionCliente()).append("\n");
        factura.append("Productos:\n");
        for (ProductoMenu producto : this.getProductos()) {
            factura.append(producto.generarTextoFactura()).append("\n");
        }
        // ... similar para combos y otros elementos
        factura.append("Total: $").append(this.getPrecioTotalPedido());
        return factura.toString();
    }
    
    
    

    public List<ProductoMenu> getProductos() {
        return productosPedido;
    }

	public String getNombreCliente() {
		// TODO Auto-generated method stub
		return nombreCliente;
	}

	public String getDireccionCliente() {
		// TODO Auto-generated method stub
		return direccionCliente;
	}
	
    public List<ProductoAjustado> getProductosAjustados() {
        return productosAjustados;
    }
    
    
    
    public void guardarFactura(File archivo) {
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(generarTextoFactura());
        } catch (IOException e) {
            System.out.println("Error guardando la factura: " + e.getMessage());
        }
    }
    
    public void cerrar() {
        this.cerrado = true;
        File facturaArchivo = new File("factura_" + this.getIdPedido() + ".txt");
        guardarFactura(facturaArchivo);
    }

}


