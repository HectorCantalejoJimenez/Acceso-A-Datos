import java.time.LocalDate;

public class Repostaje implements Comparable<Repostaje>{
    int id;
    int idCliente;
    LocalDate fecha;
    double importe;
    double litros;
    String combustible;



    public Repostaje(int id, int idCliente,LocalDate fecha,double importe, double litros, String combustible) {
        this.id = id;
        this.idCliente = idCliente;
        this.importe = importe;
        this.fecha = fecha;
        this.litros = litros;
        this.combustible = combustible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {

        this.combustible = combustible;
    }


    @Override
    public int compareTo(Repostaje r) {
        int compararFecha=r.fecha.compareTo(this.fecha);
        if(compararFecha!= 0){
            return compararFecha;
        }
        return Integer.compare(r.getId(),this.id);
    }
}
