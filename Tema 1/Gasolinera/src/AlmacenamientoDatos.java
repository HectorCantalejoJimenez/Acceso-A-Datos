import java.util.List;

public interface AlmacenamientoDatos {
    List<Cliente> cargarClientes();
    void guardarCliente(Cliente cliente);
    List <Repostaje>cargarRepostajes();
    void guardarRepostaje(Repostaje repostaje);

}
