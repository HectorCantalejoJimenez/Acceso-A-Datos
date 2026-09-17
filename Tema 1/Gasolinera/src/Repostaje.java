import java.time.LocalDate;

public class Repostaje {
    int id;
    int idClient;
    LocalDate fecha;
    double importe;
    double l;
    String combustible;

    public PagosRepostaje(int id, int idClient, double importe, LocalDate fecha, double l, String combustible) {
        this.id = id;
        this.idClient = idClient;
        this.importe = importe;
        this.fecha = fecha;
        this.l = l;
        this.combustible = combustible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }
}
