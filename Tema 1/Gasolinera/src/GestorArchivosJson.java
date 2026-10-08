import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class GestorArchivosJson implements AlmacenamientoDatos{
    private final Path archivoClientes ;
    private final Path archivoRepostajes;
    private AlmacenamientoDatos almacenamiento;
    private static final String abrirJson = "[\n";
    private static final String cerrarJson = "\n]";

    public GestorArchivosJson(Path carpetaDestino) {
        this.archivoClientes = carpetaDestino.resolve("clientes.json");
        this.archivoRepostajes = carpetaDestino.resolve("repostajes.json");
    }

    @Override
    public boolean existenArchivos() {
        return Files.exists(archivoClientes)&&Files.exists(archivoRepostajes);
    }

    @Override
    public void inicializarArchivos() {
        try {
            if (!Files.exists(archivoClientes)) {
                Files.createDirectories(archivoClientes.getParent());
                Files.createFile(archivoClientes);
            }
            if (!Files.exists(archivoRepostajes)) {
                Files.createDirectories(archivoRepostajes.getParent());
                Files.createFile(archivoRepostajes);
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar los archivos." + e.getMessage());
        }
    }

    @Override
    public List<Cliente> cargarClientes() {
        List<Cliente> lista = new ArrayList<>();
        try {
            if(!Files.exists(archivoClientes)){
                return lista;
            }
            List<String> lineas = Files.readAllLines(archivoClientes, StandardCharsets.UTF_8);
            for (String linea : lineas) {
                if (linea.isBlank()) continue;
                String[] campos = linea.split("\"");
                lista.add(new Cliente(Integer.parseInt(campos[3]), campos[7], campos[11], campos[15]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        List<Cliente> listaCliente=cargarClientes();

        listaCliente.add(cliente);
        String clienteAJson;
        try (BufferedWriter bf = Files.newBufferedWriter(archivoClientes,StandardOpenOption.CREATE)){
            bf.write(abrirJson);
            clienteAJson=listaCliente.stream().map( this:: clienteEnJson).reduce((s1,s2)->s1+",\n"+s2).orElse(" ");
            bf.write(clienteAJson);
            bf.write(cerrarJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String clienteEnJson(Cliente cliente){
        return "{\"Id\":"+cliente.getId()+",\"Nombre\":\""+cliente.getNombre()+"\",\"Telefono\":\""+cliente.getTelefono()+"\",\"Matricula\":\""+cliente.getMatricula()+"\"}";
    }


    public String repostajeEnJson(Repostaje r){
        return "{\"id\":" + r.getId() + ",\"idCliente\":" + r.getIdCliente() + ",\"fecha\":\"" + r.getFecha() + "\"" + ",\"importe\":" + r.getImporte() + ",\"litros\":" + r.getLitros() + ",\"combustible\":\"" + r.getCombustible() + "\"}";
    }

    @Override
    public void guardarRepostaje(Repostaje repostaje) {
        List<Repostaje>listaRepostajes = cargarRepostajes();

        listaRepostajes.add(repostaje);
        String repostajeAJson;
        try (BufferedWriter bf = Files.newBufferedWriter(archivoRepostajes,StandardOpenOption.CREATE)){
            bf.write(abrirJson);
            repostajeAJson=listaRepostajes.stream().map(this::repostajeEnJson).reduce((s1, s2)->s1+",\n"+s2).orElse(" ");
            bf.write(repostajeAJson);
            bf.write(cerrarJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Repostaje> cargarRepostajes() {
        List<Repostaje> lista = new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(archivoRepostajes, StandardCharsets.UTF_8);
            for (String linea : lineas) {
                if (linea.isBlank()) continue;
                String[] campos = linea.split("\"");
                if (campos.length != 6) throw new IllegalArgumentException("Fichero de repostajes corrupto.");

                int id = Integer.parseInt(campos[3]);
                int idCliente = Integer.parseInt(campos[7]);
                LocalDate fecha = LocalDate.parse(campos[11]);
                double importe = Double.parseDouble(campos[15]);
                double litros = Double.parseDouble(campos[19]);
                String combustible = campos[23];

                lista.add(new Repostaje(id, idCliente,fecha,importe, litros, combustible));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

}
