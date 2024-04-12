import java.rmi.Remote;
import java.rmi.RemoteException;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: ServidorAlumnos.java
 * Comentarios: Es el fichero correspondiente a interfaz de los servicios 
 * ofrecidos por el servidor de alumnos.
 ******************************************************************************/



/**
 * Interfaz remota para el servicio de gestión de alumnos.
 * Esta interfaz define los métodos remotos para obtener y establecer
 * información relacionada con los alumnos.
 */
public interface ServidorAlumnos extends Remote {

    /**
     * Obtiene el número total de alumnos.
     * 
     * @return El número total de alumnos.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    int obtenerNumeroDeAlumnos() throws RemoteException;

    /**
     * Establece el número total de alumnos.
     * 
     * @param numero El número total de alumnos a establecer.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    void establecerNumeroDeAlumnos(int numero) throws RemoteException;

    /**
     * Obtiene un arreglo de notas de los alumnos.
     * 
     * @return Un arreglo de notas de los alumnos.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    int[] obtenerNotas() throws RemoteException;

}
