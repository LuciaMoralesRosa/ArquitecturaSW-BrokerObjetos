package MiBroker;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Broker extends Remote {

    // Metodos de la interface
    //void mostrarListaServicios() throws RemoteException;

    Object ejecutar_servicio_sinc(String nom_servicio, Vector parametros_servicio) throws RemoteException;

    void registrar_servidor(String nombre_servidor, String host_remoto_IP_puerto) throws RemoteException;

    void alta_servicio(String nombre_servidor, String nom_servicio, Vector lista_param, String tipo_retorno)
            throws RemoteException;
    
    void baja_servicio(String nombre_servidor, String nom_servicio) throws RemoteException;
}


/*
    //Ejecutar servicio as´ıncrono (versi´on m´as avanzada):
    void ejecutar_servicio_asinc(String nom_servicio, Vector parametros_servicio){}
    //Obtener la respuesta (caso as´ıncrono):
    Respuesta obtener_respuesta_asinc(String nom_servicio){}
 */