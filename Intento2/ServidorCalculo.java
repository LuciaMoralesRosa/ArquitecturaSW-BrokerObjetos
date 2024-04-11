import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;

public interface ServidorCalculo extends Remote {

    // Metodos de la interface
    int mediaAritmetica(int[] valores) throws RemoteException;

    int suma(int num1, int num2) throws RemoteException;
}