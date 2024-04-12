import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: BrokerCli.java
 * Comentarios: Es el fichero correspondiente a la interfaz para clientes del 
 * broker.
 ******************************************************************************/


/**
 * Definición de la interfaz remota BrokerCli
 */
public interface BrokerCli extends Remote {

    // Métodos de la interfaz remota
    
    /**
     * Ejecuta un servicio de manera síncrona.
     * 
     * @param nombreServicio Nombre del servicio a ejecutar.
     * @param parametrosServicio Parámetros necesarios para ejecutar el servicio.
     * @return Objeto que representa el resultado de la ejecución del servicio.
     * @throws RemoteException Si ocurre un error durante la ejecución remota.
     */
    Object ejecutar_servicio_sinc(String nombreServicio, List<?> parametrosServicio)
                                  throws RemoteException;

    /**
     * Muestra los servicios registrados.
     * 
     * @return Cadena que representa los servicios registrados o un mensaje
     *          indicando que no hay servicios.
     * @throws RemoteException Si ocurre un error durante la ejecución remota.
     */
    List<Servicio> obtenerServicios() throws RemoteException;

}