import java.util.Scanner;

public class Utilidades {
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
