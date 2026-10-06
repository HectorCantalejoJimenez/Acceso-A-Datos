public class Cliente implements Comparable<Cliente> {

    private final int id;
    private String nombre;
    private String telefono;
    private String matricula;

    public Cliente(int id, String nombre, String telefono, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.matricula = matricula;

    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\",\"nombre\":\"" + nombre + "\",\"telefono\":\"" + telefono + "\",\"matricula\":\"" + matricula + "\"}";
    }


    @Override
    public int compareTo(Cliente c) {
        int compararNombre=this.nombre.compareTo(c.getNombre());
        if(compararNombre!= 0){
            return compararNombre;
        }
        return Integer.compare(this.id,c.getId());
    }
}
