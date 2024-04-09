package MisServidores;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Servidor02 extends Remote {

    // Metodos de la interface
    int mediaAritmetica(int[] valores, int numValores) throws RemoteException;
}