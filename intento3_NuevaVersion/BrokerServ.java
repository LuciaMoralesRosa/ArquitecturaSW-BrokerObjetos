import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: BrokerServ.java
 * Comentarios: Es el fichero correspondiente a la interfaz para servidores del
 * broker.
 ******************************************************************************/


/**
 * Definición de la interfaz remota BrokerServ
 */
public interface BrokerServ extends Remote {

    // Métodos de la interfaz remota

    /**
     * Registra un servidor en el sistema Broker.
     * 
     * @param nombreServidor Nombre del servidor a registrar.
     * @param hostRemoto     Dirección IP y puerto remoto del servidor.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    void registrarServidor(String nombreServidor, String hostRemoto)
                           throws RemoteException;

    /**
     * Da de alta un nuevo servicio en el servidor especificado.
     * 
     * @param nombreServidor  Nombre del servidor donde se dará de alta el servicio.
     * @param nombreServicio    Nombre del servicio a dar de alta.
     * @param listaParametros Lista de parámetros requeridos por el servicio.
     * @param tipoRetorno    Tipo de dato que retorna el servicio.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    void altaServicio(String nombreServidor, String nombreServicio,
                      List<Object> listaParametros, String tipoRetorno)
                      throws RemoteException;

    /**
     * Da de baja un servicio del servidor especificado.
     * 
     * @param nombre_servidor Nombre del servidor donde se dará de baja el servicio.
     * @param nombreServicio Nombre del servicio a dar de baja.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    void bajaServicio(String nombreServidor, String nombreServicio)
                      throws RemoteException;

}
