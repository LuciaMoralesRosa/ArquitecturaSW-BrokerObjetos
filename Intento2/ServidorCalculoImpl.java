import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Vector;

public class ServidorCalculoImpl extends UnicastRemoteObject implements ServidorCalculo {

    protected ServidorCalculoImpl() throws RemoteException {
        super();
    }

    @Override
    public int mediaAritmetica(int[] valores, int numValores) throws RemoteException {
        int suma = 0;
        for (int v : valores) {
            suma = suma + v;
        }
        return suma / numValores;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String respuesta;
        String nombreServidor = "ServidorCalculoImpl";
        Vector<Object> parametros = null;
        
        // Fijar el directorio donde se encuentra el java.policy
        // El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");

        String hostnameBroker = "155.210.154";
        System.out.println("Introduzca la IP del broker: ");
        hostnameBroker = hostnameBroker + scanner.nextLine();

        // Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());

        try {
            String host = InetAddress.getLocalHost().toString();
            BrokerServ broker = (BrokerServ) Naming.lookup("//" + hostnameBroker + "/MiBroker");
            boolean primerRegistro = true;
            while (true) {
                
                System.out.println("Escriba el numero de la instruccion que desea realizar: \n" +
                        "1. Registrarme en el broker\n" + 
                        "2. Dar de alta un servicio\n" + 
                        "3. Dar de baja un servicio");
                respuesta = scanner.nextLine();
                switch (respuesta) {
                    case "1":
                        if (primerRegistro) {
                            primerRegistro = false;
                            
                            broker.registrarServidor(nombreServidor, host);
                        }
                        else {
                            System.out.println("Ya estas registrado en el broker");
                        }
                        break;
                    case "2":
                        parametros.add(int.class);
                        parametros.add(int[].class);
                        broker.altaServicio(nombreServidor, "mediaAritmetica", parametros, "int");
                        break;

                    case "3":
                        broker.bajaServicio(nombreServidor, "mediaAritmetica");
                        break;
                    default:
                        break;
                }
   
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

}