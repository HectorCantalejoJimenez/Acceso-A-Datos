import java.nio.file.Path;
import java.util.List;

public class TraductorCsvAJson {

    private final Path carpetaOrigen;
    private final Path carpetaDestino;

    public TraductorCsvAJson(Path carpetaOrigen, Path carpetaDestino) {
        this.carpetaOrigen = carpetaOrigen;
        this.carpetaDestino = carpetaDestino;
    }

    public void traductor(){
        GestorArchivosCsv csv = new GestorArchivosCsv(carpetaOrigen);
        GestorArchivosJson json = new GestorArchivosJson(carpetaDestino);

        json.inicializarArchivos();

        for(Cliente c : csv.cargarClientes()){
            json.guardarCliente(c);
        }

        for (Repostaje r : csv.cargarRepostajes()){
            json.guardarRepostaje(r);
        }
    }
}
