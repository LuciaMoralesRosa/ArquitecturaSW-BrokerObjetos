import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface BrokerCli extends Remote {

    // Metodos de la interface
    // void mostrarListaServicios() throws RemoteException;

    Object ejecutar_servicio_sinc(String nom_servicio, Vector parametros_servicio) throws RemoteException;

    String mostrarServicios() throws RemoteException;

}
