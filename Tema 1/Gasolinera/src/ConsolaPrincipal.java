import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsolaPrincipal {

    private AlmacenamientoDatos almacenamiento;
    public ConsolaPrincipal(AlmacenamientoDatos almacenamiento){
        this.almacenamiento = almacenamiento;
    }
    public void iniciarConsola(){
        Gasolinera gasolinera = new Gasolinera(new ArrayList<>(),new ArrayList<>(),almacenamiento);
        int opcion;
        Scanner sc = new Scanner(System.in);
        do{
            mostrarMenu();
            try {
                opcion = Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Porfavor,inserte un numero."+e.getMessage());
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> {
                    String nombre= Utilidades.comprobarVacio("Nombre: ");
                    while(!nombre.matches("[a-zA-Z ]+")){
                        System.out.println("No se pueden poner numeros en el nombre.");
                        nombre=Utilidades.comprobarVacio("Nombre: ");
                    }

                    String telefono=Utilidades.comprobarVacio("Telefono: ");
                    while(telefono.length() >= 13){
                        System.out.println("ERROR!!!Escriba un telefono no mayor a 12 Digitos con el + y el prefijo telefonico");
                        telefono =Utilidades.comprobarVacio("Telefono: ");
                    }
                    String matricula=Utilidades.comprobarVacio("Matricula: ");
                    while(matricula.length() >= 8){
                        System.out.println("ERROR!!!Escriba una matricula no mayor a 7 Digitos");
                        matricula =Utilidades.comprobarVacio("Matricula: ").toUpperCase();
                    }
                    gasolinera.agregarCliente(nombre,telefono,matricula);
                }
                case 2 ->
                        gasolinera.listarClientes();

                case 3 ->{
                    String busqueda = Utilidades.comprobarVacio("Inserte el cliente que quiera buscar(Nombre,tlf,matricula):");
                    gasolinera.buscarClientes(busqueda.toLowerCase());
                }
                case 4 -> {
                    System.out.println("PROCESAR REPOSTAJE");
                    gasolinera.procesarPago(sc);
                }
                case 5 ->
                        gasolinera.consultarPagos();


            }
        }while(opcion != 0);
    }
    public static void mostrarMenu() {
    System.out.println();
    System.out.println("=== GESTION DE GASOLINERA ===");
    System.out.println("1. Dar de alta un cliente");
    System.out.println("2. Listar clientes");
    System.out.println("3. Buscar clientes");
    System.out.println("4. Procesar un pago de repostaje");
    System.out.println("5. Consultar pagos");
    System.out.println("0. Salir");
    System.out.print("Opcion: ");
    }
}
