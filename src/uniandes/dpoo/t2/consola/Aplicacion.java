package uniandes.dpoo.t2.consola;

import java.util.List;
import java.util.Scanner;

import uniandes.dpoo.t2.modelo.Restaurante;
import uniandes.dpoo.t2.modelo.Combo;
import uniandes.dpoo.t2.modelo.Ingrediente;
import uniandes.dpoo.t2.modelo.Pedido;
import uniandes.dpoo.t2.modelo.ProductoAjustado;
import uniandes.dpoo.t2.modelo.ProductoMenu;

public class Aplicacion {
    private Restaurante restaurante;
    private Scanner scanner = new Scanner(System.in);

    public Aplicacion() {
        this.restaurante = new Restaurante();
    }

    public void ejecutarAplicacion() {
        System.out.println("Sistema de Pedidos - Tienda de Hamburguesas\n");

        boolean continuar = true;
        while (continuar) {
            try {
                mostrarMenu();
                int opcionSeleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
                ejecutarOpcion(opcionSeleccionada);
                if (opcionSeleccionada == 6) {
                    continuar = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe seleccionar uno de los números de las opciones.");
            }
        }
    }

    public void ejecutarOpcion(int opcionSeleccionada) {
        switch (opcionSeleccionada) {
            case 1:
                mostrarMenuProductos();
                mostrarMenuIngredientes();
                mostrarMenuCombos();
                break;
            case 2:
                iniciarNuevoPedido();
                break;
            case 3:
                agregarElementoAPedido();
                break;
            case 4:
                cerrarPedido();
                break;
            case 5:
                consultarPedido();
                break;
            case 6:
                System.out.println("Saliendo de la aplicación ...");
                break;
            default:
                System.out.println("Por favor seleccione una opción válida.");
        }
    }

    public void mostrarMenu() {
        System.out.println("\nOpciones de la aplicación\n");
        System.out.println("1. Mostrar menú de productos");
        System.out.println("2. Iniciar un nuevo pedido");
        System.out.println("3. Agregar un elemento a un pedido");
        System.out.println("4. Cerrar un pedido y guardar la factura");
        System.out.println("5. Consultar la información de un pedido dado su id");
        System.out.println("6. Salir de la aplicación\n");
    }
    
    private String input(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    private void mostrarMenuProductos() {
        System.out.println("\nMenú de Productos:\n");
        List<ProductoMenu> menu = restaurante.getMenuBase();
        int indice = 1;
        for (ProductoMenu producto : menu) {
            System.out.println(indice + ". " + producto.toString());  // Usamos el método toString de ProductoMenu
            indice++;
        }
    }
    
    private void mostrarMenuIngredientes() {
        System.out.println("\nMenú de Ingredientes:\n");
        List<Ingrediente> menu = restaurante.getIngredientes();
        int indice = 1;
        for (Ingrediente producto : menu) {
            System.out.println(indice + ". " + producto.toString());  // Usamos el método toString de ProductoMenu
            indice++;
        }
    }
    
    private void mostrarMenuCombos() {
    	System.out.println("\nMenú de Combos:\n");
        List<Combo> combo = restaurante.getCombos();
        int indice = 1;
        for (Combo producto : combo) {
            System.out.println(indice + ". " + producto.toString());  // Usamos el método toString de ProductoMenu
            indice++;
        }
    }
    
    private void iniciarNuevoPedido() {
        System.out.println("\nIniciando un nuevo pedido...\n");
        
        // Solicitar información al cliente
        String nombreCliente = input("Ingrese su nombre");
        String direccionCliente = input("Ingrese su dirección");

        // Usar el método iniciarPedido de Restaurante para crear el pedido
        int idPedido = restaurante.iniciarPedido(nombreCliente, direccionCliente);

        System.out.println("Pedido creado con éxito. Su ID de pedido es: " + idPedido);
    }
    
    private void consultarPedido() {
        System.out.println("\nConsultar información de un pedido\n");

        int idPedido = Integer.parseInt(input("Ingrese el ID del pedido"));
        Pedido pedido = restaurante.getPedidoPorId(idPedido);

        if (pedido != null) {
            System.out.println("Nombre del cliente: " + pedido.getNombreCliente());
            System.out.println("Dirección del cliente: " + pedido.getDireccionCliente());
            
            for (ProductoMenu producto : pedido.getProductos()) {
                System.out.println("Producto: " + producto.getNombre() + 
                                   ", Precio sin IVA: " + producto.getPrecio() + 
                                   ", IVA: 19%" );
            }
            
            System.out.println("Precio neto del pedido: " + pedido.getPrecioNetoPedido());
            System.out.println("Precio total del pedido (con IVA): " + pedido.getPrecioTotalPedido());

        } else {
            System.out.println("No se encontró el pedido con el ID: " + idPedido);
        }
    }

    private void cerrarPedido() {
        System.out.println("\nCerrando un pedido...\n");
        
        // Solicitar ID del pedido que se desea cerrar
        int idPedido = Integer.parseInt(input("Ingrese el ID del pedido que desea cerrar"));
        
        // Llamar al método cerrarYGuardarPedido para cerrar el pedido
        restaurante.cerrarYGuardarPedido(idPedido);
    }

    public void agregarElementoAPedido() {
        System.out.println("\nAgregando un elemento a un pedido...\n");

        int idPedido = Integer.parseInt(input("Ingrese el ID del pedido al que quiere agregar un producto o combo"));
        Pedido pedido = restaurante.getPedidoPorId(idPedido);

        if (pedido != null && !pedido.estaCerrado()) {
            System.out.println("1. Agregar Producto");
            System.out.println("2. Agregar Combo");
            int eleccion = Integer.parseInt(input("Elija una opción (1 o 2):"));
            
            if (eleccion == 1) {
                mostrarMenuProductos();
                int opcionProducto = Integer.parseInt(input("Seleccione un producto del menú para agregar al pedido"));
                ProductoMenu producto = restaurante.getMenuBase().get(opcionProducto - 1);

                ProductoAjustado productoAjustado = new ProductoAjustado(producto);

                char opcionIngrediente;
                do {
                    mostrarMenuIngredientes();
                    int opcionIng = Integer.parseInt(input("Seleccione un ingrediente para agregar o '0' para continuar"));
                    if (opcionIng != 0) {
                        Ingrediente ingrediente = restaurante.getIngredientes().get(opcionIng - 1);
                        productoAjustado.agregarIngrediente(ingrediente);
                        System.out.println("Ingrediente añadido con éxito.");
                    }

                    opcionIngrediente = input("¿Desea agregar otro ingrediente? (s/n)").charAt(0);
                } while (opcionIngrediente == 's' || opcionIngrediente == 'S');
                
                pedido.agregarProductoAjustado(productoAjustado); // Asumo que agregarás este método en la clase Pedido
                System.out.println("Producto con ingredientes añadidos con éxito al pedido.");
            
                
            } else if (eleccion == 2) {
                mostrarMenuCombos();
                int opcionCombo = Integer.parseInt(input("Seleccione un combo del menú para agregar al pedido"));
                Combo combo = restaurante.getCombos().get(opcionCombo - 1);
                pedido.agregarCombo(combo);
                System.out.println("Combo añadido con éxito al pedido.");
            } else {
                System.out.println("Opción no válida.");
            }

        } else {
            System.out.println("El pedido no existe o ya está cerrado.");
        }
    }


    

    public static void main(String[] args) {
        Aplicacion consola = new Aplicacion();
        consola.ejecutarAplicacion();
    }
}

