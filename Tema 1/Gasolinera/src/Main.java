import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        GestorArchivos gestor = new GestorArchivos();
        Gasolinera gasolinera = new Gasolinera(new ArrayList<>(),new ArrayList<>(),gestor);

        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Porfavor,inserte un numero."+e.getMessage());
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> {
                    String nombre =comprobarVacio("Nombre:");
                    String telefono=comprobarVacio("Telefono:");
                    String matricula=comprobarVacio("Matricula");
                    gasolinera.agregarCliente(nombre,telefono,matricula);
                }
                case 2 ->{
                    System.out.println("LISTADO DE CLIENTES");
                    gasolinera.listarClientes();
                }
                case 3 ->{
                    String busqueda = comprobarVacio("Inserte el cliente que quiera buscar(Nombre,tlf,matricula):");
                    gasolinera.buscarClientes(busqueda.toLowerCase());
                }
                case 4 -> {


                }
                case 5 ->{
                    System.out.println("LISTA DE PAGOS");
                    gasolinera.consultarPagos();
                }

            }
        } while (opcion != 0);
    }

    public static void mostrarMenu() {
        System.out.println();
        System.out.println("=== GESTIÓN DE GASOLINERA ===");
        System.out.println("1. Dar de alta un cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Buscar clientes");
        System.out.println("4. Procesar un pago de repostaje");
        System.out.println("5. Consultar pagos");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    public static String comprobarVacio(String mensaje) {
        String texto;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(mensaje);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El texto no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }


}

