package MisServidores;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

import MiBroker.BrokerImpl;

public class Servidor01_Impl extends UnicastRemoteObject implements Servidor01 {
    // Atributos
    private int nAlumnos;
    private int notasAlumnos[];
    private Random random = new Random();

    private int puertoBroker;
    private String ipBroker;

    // Metodos privados
    private void generadorNotasAleatorias() {
        for (int i = 0; i < nAlumnos; i++) {
            notasAlumnos[i] = random.nextInt(11);
        }
    }

    // Constructor
    Servidor01_Impl() throws RemoteException {
        super();
        nAlumnos = random.nextInt(50);
        generadorNotasAleatorias();
    }

    // Metodos ofrecidos
    @Override
    public int obtenerNumeroDeAlumnos() throws RemoteException {
        return nAlumnos;
    }
    
    @Override
    public void establecerNumeroDeAlumnos(int numero) throws RemoteException {
        nAlumnos = numero;
        generadorNotasAleatorias();
    }

    @Override
    public int[] obtenerNotas() throws RemoteException {
        return notasAlumnos;
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

        try {

            Servidor01_Impl objeto = new Servidor01_Impl();
            Naming.rebind("//" + hostnameBroker + "/MiBroker", objeto);

            BrokerServ broker 
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
