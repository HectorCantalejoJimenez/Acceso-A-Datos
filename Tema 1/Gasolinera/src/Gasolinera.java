import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Gasolinera {

    private List<Cliente> clientes;
    private List<Repostaje> repostajes;
    private GestorArchivos archivos;

    public Gasolinera(List<Cliente> clientes, List<Repostaje> repostajes, GestorArchivos archivos) {
        this.archivos = archivos;
        try {
            this.repostajes = archivos.cargarRepostajes();
            this.clientes = archivos.cargarClientes();
        } catch (Exception e) {
            System.out.println("ERROR!!!No se han podido cargar los ficheros"+e.getMessage());
        }
    }

    public boolean existeMatricula(String matricula) {
        for (Cliente c : clientes) {
            if (c.getMatricula().equalsIgnoreCase(matricula.trim())) {
                return true;
            }
        }
        return false;
    }

    public int crearIdCli() {
        int idMax = 0;
        for (Cliente c : clientes) {
            if (c.getId() > idMax) {
                idMax = c.getId();
            }
        }
        return idMax + 1;
    }

    public int crearIdRep() {
        int idMax = 0;
        for (Repostaje r : repostajes) {
            if (r.getId() > idMax) {
                idMax = r.getId();
            }
        }
        return idMax + 1;
    }

    public void agregarCliente(String nombre, String telefono, String matricula) {
        if (existeMatricula(matricula)) {
            System.out.println("Esa matricula ya esta registrada.No se ha creado el perfil");
        }

        int id = crearIdCli();
        Cliente newCliente = new Cliente(id, nombre, telefono, matricula);
        archivos.guardarCliente(newCliente);
        clientes.add(newCliente);
        System.out.println("Se ha añadido el cliente.Su id es: " + id);
    }

    public void listarClientes() {
        List<Cliente> listaClientes = new ArrayList<>(clientes);
        if (clientes.isEmpty()) {
            System.out.println("La lista Clientes esta vacia");
            return;
        } else {
            listaClientes.sort(null);
            System.out.println("LISTADO DE CLIENTES");
            System.out.println("ID " + "NOMBRE " + "TELEFONO " + "MATRICULA ");
            for (Cliente c : listaClientes) {
                System.out.println(c.getId() + ";" + c.getNombre() + ";" + c.getTelefono() + ";" + c.getMatricula());
            }
        }
    }

    public void buscarClientes(String busqueda) {

        List<Cliente> encontrados = new ArrayList<>();

        for (Cliente c : clientes) {
            if (c.getNombre().toLowerCase().contains(busqueda) ||
                    c.getTelefono().contains(busqueda) ||
                    c.getMatricula().toLowerCase().contains(busqueda)) {
                encontrados.add(c);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("No se han encontrado clientes.");
        }else {
            encontrados.sort(null);
            System.out.println("ID " + "NOMBRE " + "TELÉFONO " + "MATRÍCULA ");
            for (Cliente c : encontrados) {
                System.out.println(c.getId() + ";" + c.getNombre() + ";" + c.getTelefono() + ";" + c.getMatricula());
            }
        }
    }


    public void procesarPago(Scanner sc) {
        if(clientes.isEmpty()){
            System.out.println("No hay clientes registrados todavia.Registrese antes de repostar");
            return;
        }
        System.out.println("=== LISTADO DE CLIENTES ===");
        listarClientes();
        System.out.println("Seleccione el ID del cliente: ");
        int idCli = 0;
        try {
            idCli = Integer.parseInt(sc.nextLine().trim());
        }catch (NumberFormatException e){
            System.out.println("El ID debe ser un numero entero y positivo."+e.getMessage());
        }

        Cliente clienteActual = null;
        for (Cliente c : clientes){
            if(c.getId()==idCli){
                clienteActual = c;
            }
        }
        if (clienteActual == null){
            System.out.println("Todavia no hay registro de ese cliente.Registrese antes de realizar un repostaje");
            return;
        }
        int id=crearIdRep();

        LocalDate fechaRepostaje = null;
        DateTimeFormatter estructuraFecha = DateTimeFormatter.ofPattern("d/M/yyyy");

        while(fechaRepostaje == null){
            System.out.println("Fecha (dd/MM/aaaa);vacio para hoy: ");
            String entradaFecha=sc.nextLine().trim();
            if(entradaFecha.isEmpty()){
                fechaRepostaje = LocalDate.now();
            }else{
                try {
                    fechaRepostaje = LocalDate.parse(entradaFecha,estructuraFecha);
                }catch(DateTimeParseException e){
                    System.out.println("Use el formato dd/MM/aaaa para que la fecha sea correcta."+e.getMessage());
                }
            }
        }

        double importe = 0;
        while(importe <= 0){
            try {
                String scannerImporte =Utilidades.comprobarVacio("Introduzca el importe: ");
                importe=Double.parseDouble(scannerImporte.replace(",","."));
            }catch(NumberFormatException e){
                System.out.println("Escriba un importe mayor a 0");
            }
        }

        double litros = 0;
        while (litros <= 0){
            try{
                String scannerLitros =Utilidades.comprobarVacio("Introduzca los litros: ");
                litros =Double.parseDouble(scannerLitros.replace(",","."));
            }catch(NumberFormatException e){
                litros = 0;
                System.out.println("Introduce una cantidad positiva mayor a cero."+e.getMessage());
            }
        }

        System.out.println("Elija si quiere Diesel o Gasolina 95");
        String combustible = tipoCombustible();

        Repostaje newRepostaje = new Repostaje(id,idCli,fechaRepostaje,importe,litros,combustible);
        archivos.guardarRepostaje(newRepostaje);
        repostajes.add(newRepostaje);
        System.out.println("Se ha añadido el repostaje.Su id es: " + id);


    }

    public void consultarPagos() {
        if (repostajes.isEmpty()) {
            System.out.println("No hay pagos registrados.");
        }else{

            List<Repostaje> listaPagos = new ArrayList<>(repostajes);
            listaPagos.sort(null);

            System.out.println("LISTA DE PAGOS");

            System.out.println("ID " + "CLIENTE " + "FECHA " + "IMPORTE " + "LITROS " + "COMBUSTIBLE");

            DateTimeFormatter formateador=DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Repostaje p : listaPagos) {
                String nombreCli = "Desconocido";
                for (Cliente c : clientes) {
                    if (c.getId() == p.getIdCliente()) {
                        nombreCli = c.getNombre();
                    }
                }
                String fechaFormateada=p.getFecha().format(formateador);
                System.out.println(p.getId() + ";" + nombreCli + ";" + fechaFormateada + ";" + p.getImporte() + ";" + p.getLitros() + ";" + p.getCombustible());
            }
        }
    }
    public static String tipoCombustible(){
        String combustible = "";
        Scanner sc = new Scanner(System.in);
        boolean eleccion = false;
        do {
            System.out.println("Seleccione un tipo de combustible(1 para Diesel/2 para Gasolina 95):");
            int tipo = sc.nextInt();
            if (tipo == 1) {
                combustible = "Diesel";
                eleccion = true;
            }else if(tipo == 2){
                combustible = "Gasolina 95";
                eleccion = true;
            }else{
                System.out.println("ERROR!!!Solo se puede elejir entre 1 y 2");
            }
        }while(!eleccion);
        return combustible;
    }

}