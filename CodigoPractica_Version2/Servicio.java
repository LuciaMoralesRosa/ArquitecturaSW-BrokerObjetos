import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: Servicio.java
 * Comentarios: Es el fichero correspondiente a la representacion de un servicio.
 ******************************************************************************/

/**
 * Clase que representa un servicio.
 */
public class Servicio implements Serializable {

    // Atributos
    private String nombreServidor;
    private String nombreServicio;
    private List listaParametros;
    private String tipoRetorno;

    /**
     * Constructor de la clase Servicio.
     * 
     * @param nombreServidor  Nombre del servidor al que pertenece el servicio.
     * @param nombreServicio  Nombre del servicio.
     * @param listaParametros Lista de parámetros del servicio.
     * @param tipoRetorno     Tipo de retorno del servicio.
     */
    public Servicio(String nombreServidor, String nombreServicio, List<Object> listaParametros, String tipoRetorno) {
        this.nombreServidor = nombreServidor;
        this.nombreServicio = nombreServicio;
        this.listaParametros = listaParametros;
        this.tipoRetorno = tipoRetorno;
    }

    /**
     * Método getter para obtener el nombre del servidor.
     * 
     * @return Nombre del servidor.
     */
    public String obtenerNombreServidor() {
        return nombreServidor;
    }

    /**
     * Método setter para establecer el nombre del servidor.
     * 
     * @param nombreServidor Nombre del servidor.
     */
    public void establecerNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    /**
     * Método getter para obtener el nombre del servicio.
     * 
     * @return Nombre del servicio.
     */
    public String obtenerNombreServicio() {
        return nombreServicio;
    }

    /**
     * Método setter para establecer el nombre del servicio.
     * 
     * @param nombreServicio Nombre del servicio.
     */
    public void establecerNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    /**
     * Método getter para obtener la lista de parámetros del servicio.
     * 
     * @return Lista de parámetros del servicio.
     */
    public List<Object> obtenerListaParametros() {
        return listaParametros;
    }

    /**
     * Método setter para establecer la lista de parámetros del servicio.
     * 
     * @param listaParametros Lista de parámetros del servicio.
     */
    public void establecerListaParametros(List<Object> listaParametros) {
        this.listaParametros = listaParametros;
    }

    /**
     * Método getter para obtener el tipo de retorno del servicio.
     * 
     * @return Tipo de retorno del servicio.
     */
    public String obtenerTipoRetorno() {
        return tipoRetorno;
    }

    /**
     * Método setter para establecer el tipo de retorno del servicio.
     * 
     * @param tipoRetorno Tipo de retorno del servicio.
     */
    public void establecerTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }
}