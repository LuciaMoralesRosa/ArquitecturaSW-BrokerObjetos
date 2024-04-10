import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorCalculo extends Remote {

    // Metodos de la interface
    int mediaAritmetica(int[] valores, int numValores) throws RemoteException;
}