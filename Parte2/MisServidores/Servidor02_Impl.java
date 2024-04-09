package MisServidores;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Servidor02_Impl extends UnicastRemoteObject implements Servidor02 {
    
    //Atributos
    private int puertoBroker;
    private String ipBroker;
    
    // Constructor
    Servidor02_Impl() throws RemoteException {
        super();
    }

    // Metodos ofrecidos
    @Override
    public int mediaAritmetica(int[] valores, int numValores) throws RemoteException{
        int suma = 0;
        for (int v : valores) {
            suma = suma + v;
        }
        return suma / numValores;
    }
    
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        
        // Fijar el directorio donde se encuentra el java.policy
        // El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");

        
        System.out.println("Introduzca la IP del broker: ");
        String hostnameBroker = scanner.nextLine();

        // Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());

        try{
            Servidor02_Impl objeto = new Servidor02_Impl();
            Naming.rebind("//" + hostnameBroker + "/MiBroker", objeto);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
