import java.util.Vector;

public class Servicio {
    private String nombreServidor;
    private String nombreServicio;
    private Vector listaParametros;
    private String tipoRetorno;

    // Constructor
    public Servicio(String nombreServidor, String nombreServicio, Vector<Object> listaParametros, String tipoRetorno) {
        this.nombreServidor = nombreServidor;
        this.nombreServicio = nombreServicio;
        this.listaParametros = listaParametros;
        this.tipoRetorno = tipoRetorno;
    }

    // Getter y Setter para nombreServidor
    public String obtenerNombreServidor() {
        return nombreServidor;
    }

    public void establecerNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    // Getter y Setter para nombreServicio
    public String obtenerNombreServicio() {
        return nombreServicio;
    }

    public void establecerNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    // Getter y Setter para listaParametros
    public Vector<Object> obtenerListaParametros() {
        return listaParametros;
    }

    public void establecerListaParametros(Vector<Object> listaParametros) {
        this.listaParametros = listaParametros;
    }

    // Getter y Setter para tipoRetorno
    public String obtenerTipoRetorno() {
        return tipoRetorno;
    }

    public void establecerTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }
}
