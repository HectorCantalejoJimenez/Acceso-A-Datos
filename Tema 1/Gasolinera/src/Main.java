import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Path carpetaDatosJson = Path.of("Tema 1","datosJson");
        Path carpetaDatosCsv = Path.of("Tema 1","datosCsv");
        AlmacenamientoDatos almacenamientoJson = new GestorArchivosJson(carpetaDatosJson);
        AlmacenamientoDatos almacenamientoCsv = new GestorArchivosCsv(carpetaDatosCsv);

        if (!almacenamientoJson.existenArchivos()) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Deseas pasar los archivos de CSV a JSON?? (S/N)");
            String respuesta = sc.nextLine().toUpperCase(Locale.ROOT).trim();

            while (!respuesta.equals("S") && !respuesta.equals("N")) {
                System.out.println("Por favor, introduzca S o N");
                respuesta = sc.nextLine().toUpperCase(Locale.ROOT).trim();
            }

            if (respuesta.equals("S")) {
                try {
                    TraductorCsvAJson traductor = new TraductorCsvAJson(carpetaDatosCsv, carpetaDatosJson);
                    traductor.traductor();
                    almacenamientoJson.inicializarArchivos();
                    new ConsolaPrincipal(almacenamientoJson).iniciarConsola();
                } catch (Exception e) {
                    System.out.println("Traduccion fallida. " + e.getMessage());
                }
            } else if (respuesta.equals("N")) {
                almacenamientoJson.inicializarArchivos();
                new ConsolaPrincipal(almacenamientoJson).iniciarConsola();
            }
        } else {
            almacenamientoJson.inicializarArchivos();
            new ConsolaPrincipal(almacenamientoJson).iniciarConsola();
        }
    }
}

