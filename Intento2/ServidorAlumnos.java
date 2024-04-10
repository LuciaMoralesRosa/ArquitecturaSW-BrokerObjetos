import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorAlumnos extends Remote {

    // Metodos de la interface
    int obtenerNumeroDeAlumnos() throws RemoteException;

    void establecerNumeroDeAlumnos(int numero) throws RemoteException;

    int[] obtenerNotas() throws RemoteException;
    
}