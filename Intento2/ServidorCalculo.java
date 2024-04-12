import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;

/**
 * Interfaz remota para el servicio de gestión de operaciones.
 * Esta interfaz define los métodos remotos para realizar operaciones
 * matematicas
 */
public interface ServidorCalculo extends Remote {

    // Metodos de la interfaz

    /**
     * Calcula la media aritmética de un arreglo de valores enteros.
     * 
     * @param valores Arreglo de valores enteros sobre los cuales calcular la media.
     * @return Media aritmética de los valores.
     * @throws RemoteException Si ocurre un error durante la invocación remota.
     */
    int mediaAritmetica(int[] valores) throws RemoteException;

    /**
     * Calcula la suma de dos números enteros.
     * 
     * @param num1 Primer número entero.
     * @param num2 Segundo número entero.
     * @return Suma de num1 y num2.
     * @throws RemoteException Si ocurre un error durante la invocación remota.
     */
    int suma(int num1, int num2) throws RemoteException;
}