import java.util.List;

public class DatosServ {
    private String nombreServicio;
    private List<Object> parametros;

    DatosServ(String nombreServicio, List<Object> parametros) {
        this.nombreServicio = nombreServicio;
        this.parametros = parametros;
    }

    public String obtenerNombreServicio() {
        return nombreServicio;
    }

    public List<Object> obtenerParametros() {
        return parametros;
    }
}
