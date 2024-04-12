import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

// Definición de la interfaz remota BrokerCli
public interface BrokerCli extends Remote {

    // Métodos de la interfaz remota
    
    /**
     * Ejecuta un servicio de manera síncrona.
     * @param nom_servicio Nombre del servicio a ejecutar.
     * @param parametros_servicio Parámetros necesarios para ejecutar el servicio.
     * @return Objeto que representa el resultado de la ejecución del servicio.
     * @throws RemoteException Si ocurre un error durante la ejecución remota.
     */
    Object ejecutar_servicio_sinc(String nom_servicio, Vector parametros_servicio) throws RemoteException;

    /**
     * Muestra los servicios registrados.
     * @return Cadena que representa los servicios registrados o un mensaje indicando que no hay servicios.
     * @throws RemoteException Si ocurre un error durante la ejecución remota.
     */
    String mostrarServicios() throws RemoteException;

}