import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class GestorArchivosCsv implements AlmacenamientoDatos{
    private final Path archivoClientes = Path.of("datos", "clientes.csv");
    private final Path archivoRepostajes = Path.of("datos", "repostajes.csv");
    private AlmacenamientoDatos almacenamiento;



    public GestorArchivosCsv() {
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
            List<String> lineas = Files.readAllLines(archivoClientes, StandardCharsets.UTF_8);
            for (String linea : lineas) {
                if (linea.isBlank()) continue;
                String[] campos = linea.split(";");
                if (campos.length != 4) throw new IllegalArgumentException("Fichero de clientes corrupto.");
                lista.add(new Cliente(Integer.parseInt(campos[0]), campos[1], campos[2], campos[3]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public String clienteEnCsv(Cliente cliente){
        return cliente.getId()+";"+cliente.getNombre()+";"+cliente.getTelefono()+";"+cliente.getMatricula();
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        List<Cliente> listaCliente=cargarClientes();

        listaCliente.add(cliente);
        String clienteACsv;
        try (BufferedWriter bf = Files.newBufferedWriter(archivoClientes,StandardOpenOption.CREATE)){
            clienteACsv=listaCliente.stream().map(c -> clienteEnCsv(c)).reduce((s1,s2)->s1+",\n"+s2).orElse(" ");
            bf.write(clienteACsv);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String repostajeEnCsv(Repostaje r){
        return r.getId()+";"+r.getIdCliente()+";"+r.getFecha()+";"+r.getImporte()+";"+r.getLitros()+";"+r.getCombustible();
    }

    @Override
    public void guardarRepostaje(Repostaje repostaje) {
        List<Repostaje>listaRepostajes = cargarRepostajes();

        listaRepostajes.add(repostaje);
        String repostajeACsv;
        try (BufferedWriter bf = Files.newBufferedWriter(archivoRepostajes,StandardOpenOption.CREATE)){
            repostajeACsv=listaRepostajes.stream().map(r -> repostajeEnCsv(r)).reduce((s1,s2)->s1+",\n"+s2).orElse(" ");
            bf.write(repostajeACsv);
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
                String[] campos = linea.split(";");
                if (campos.length != 6) throw new IllegalArgumentException("Fichero de repostajes corrupto.");

                int id = Integer.parseInt(campos[0]);
                int idCliente = Integer.parseInt(campos[1]);
                LocalDate fecha = LocalDate.parse(campos[2]);
                double importe = Double.parseDouble(campos[3]);
                double litros = Double.parseDouble(campos[4]);
                String combustible = campos[5];

                lista.add(new Repostaje(id, idCliente,fecha,importe, litros, combustible));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }
}

