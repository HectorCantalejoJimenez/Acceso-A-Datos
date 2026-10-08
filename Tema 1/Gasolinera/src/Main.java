import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
            Path carpetaDatosJson = Path.of("datosJson");
            Path carpetaDatosCsv = Path.of("datosCsv");
            AlmacenamientoDatos almacenamientoJson = new GestorArchivosJson(carpetaDatosJson);
            AlmacenamientoDatos almacenamientoCsv = new GestorArchivosCsv(carpetaDatosCsv);

            if(!almacenamientoJson.existenArchivos()){
                Scanner sc = new Scanner(System.in);
                System.out.println("Deseas pasar los archivos de CSV a JSON??(S/N)");
                String respuesta = sc.nextLine().toUpperCase(Locale.ROOT).trim();
                while (respuesta != "S" || respuesta != "N"){
                    System.out.println("Porfavor,introduzca S o N");
                    respuesta = sc.nextLine().toUpperCase(Locale.ROOT).trim();
                    almacenamientoJson.inicializarArchivos();
                    new ConsolaPrincipal(almacenamientoJson).iniciarConsola();
                }
                if(respuesta == "S"){
                    try {
                        TraductorCsvAJson traductor = new TraductorCsvAJson(carpetaDatosCsv,carpetaDatosJson);
                    }catch (Exception e){
                        System.out.println("Traduccion fallida."+e.getMessage());
                    }
                }else if (respuesta == "N"){
                    almacenamientoJson.inicializarArchivos();
                    new ConsolaPrincipal(almacenamientoJson).iniciarConsola();
                }
            }else{
                almacenamientoJson.inicializarArchivos();
                new ConsolaPrincipal(almacenamientoJson).iniciarConsola();
            }





    }
}

