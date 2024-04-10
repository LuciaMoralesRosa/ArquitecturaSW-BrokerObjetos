import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface BrokerServ extends Remote {

    void registrarServidor(String nombre_servidor, String host_remoto_IP_puerto) throws RemoteException;

    void altaServicio(String nombre_servidor, String nom_servicio, Vector<Object> listaParametros, String tipo_retorno)
            throws RemoteException;

    void bajaServicio(String nombre_servidor, String nom_servicio) throws RemoteException;

}
