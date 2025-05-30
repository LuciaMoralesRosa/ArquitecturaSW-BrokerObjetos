import java.util.ArrayList;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: Servidor.java
 * Comentarios: Es el fichero correspondiente a la representacion de un servidor.
 ******************************************************************************/

/**
 * Clase que representa un servidor.
 */
public class Servidor {

    // Atributos
    private String nombreServidor; // Nombre del servidor en RMI
    private String hostServidor; // Dirección del servidor IP:puerto
    private ArrayList<Servicio> listaServicios = new ArrayList<Servicio>();

    /**
     * Constructor para inicializar un objeto Servidor con nombre y host
     * especificados.
     * 
     * @param nombreServidor Nombre del servidor.
     * @param hostServidor   Dirección del servidor.
     */
    public Servidor(String nombreServidor, String hostServidor) {
        this.nombreServidor = nombreServidor;
        this.hostServidor = hostServidor;
    }

    /**
     * Obtiene el nombre del servidor.
     * 
     * @return El nombre del servidor.
     */
    public String getNombreServidor() {
        return nombreServidor;
    }

    /**
     * Establece el nombre del servidor.
     * 
     * @param nombreServidor El nuevo nombre del servidor.
     */
    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    /**
     * Obtiene la dirección del servidor.
     * 
     * @return La dirección del servidor.
     */
    public String getHostServidor() {
        return hostServidor;
    }

    /**
     * Establece la dirección del servidor.
     * 
     * @param hostServidor La nueva dirección del servidor.
     */
    public void setHostServidor(String hostServidor) {
        this.hostServidor = hostServidor;
    }
}
