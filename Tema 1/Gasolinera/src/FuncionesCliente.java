import java.util.List;

public interface FuncionesCliente {
    void altaCliente(String nombre,String telefono,String matricula);
    void listarClientes();
    List<Cliente> buscarClientes(String texto);
}
