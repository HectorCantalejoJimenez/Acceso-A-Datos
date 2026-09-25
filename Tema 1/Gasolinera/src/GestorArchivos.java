import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class GestorArchivos {
    private final Path archivoClientes = Path.of("datos", "clientes.csv");
    private final Path archivoRepostajes = Path.of("datos", "repostajes.csv");

    public GestorArchivos() {
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
            System.out.println("Error al inicializar los archivos: " + e.getMessage());
        }
    }


    public void guardarCliente(Cliente cliente) {
        try {
            String linea = cliente.toString() + System.lineSeparator();
            Files.writeString(archivoClientes, linea, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

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


    public void guardarRepostaje(Repostaje repostaje) {
        try {
            String linea = repostaje.toString() + System.lineSeparator();
            Files.writeString(archivoRepostajes, linea, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

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

                lista.add(new Repostaje(id, idCliente, importe, fecha, litros, combustible));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }
}

