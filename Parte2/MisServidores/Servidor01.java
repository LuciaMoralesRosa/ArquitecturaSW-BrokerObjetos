package MisServidores;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Servidor01 extends Remote {

    // Metodos de la interface
    int obtenerNumeroDeAlumnos() throws RemoteException;

    void establecerNumeroDeAlumnos(int numero) throws RemoteException;

    int[] obtenerNotas() throws RemoteException;
}