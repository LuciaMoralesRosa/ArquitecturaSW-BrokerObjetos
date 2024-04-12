import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

// Definición de la interfaz remota BrokerServ
public interface BrokerServ extends Remote {

    /**
     * Registra un servidor en el sistema Broker.
     * @param nombre_servidor Nombre del servidor a registrar.
     * @param host_remoto_IP_puerto Dirección IP y puerto remoto del servidor.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    void registrarServidor(String nombre_servidor, String host_remoto_IP_puerto) throws RemoteException;

    /**
     * Da de alta un nuevo servicio en el servidor especificado.
     * @param nombre_servidor Nombre del servidor donde se dará de alta el servicio.
     * @param nom_servicio Nombre del servicio a dar de alta.
     * @param listaParametros Lista de parámetros requeridos por el servicio.
     * @param tipo_retorno Tipo de dato que retorna el servicio.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    void altaServicio(String nombre_servidor, String nom_servicio, Vector<Object> listaParametros, String tipo_retorno)
            throws RemoteException;

    /**
     * Da de baja un servicio del servidor especificado.
     * @param nombre_servidor Nombre del servidor donde se dará de baja el servicio.
     * @param nom_servicio Nombre del servicio a dar de baja.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    void bajaServicio(String nombre_servidor, String nom_servicio) throws RemoteException;

}
