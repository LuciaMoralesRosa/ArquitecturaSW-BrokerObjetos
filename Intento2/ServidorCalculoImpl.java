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
    public int mediaAritmetica(int[] valores) throws RemoteException {
        int suma = 0;
        int numValores = 0;
        for (int v : valores) {
            suma = suma + v;
            numValores++;
        }
        return suma / numValores;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String respuesta;
        String nombreServidor = "ServidorCalculoImpl";
        Vector<Object> parametros = new Vector<Object>();
        
        // Fijar el directorio donde se encuentra el java.policy
        // El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");

        // Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());

        try {
            String host = "155.210.154.";
            System.out.println("Introduzca el final de su ip 155.210.154.XXX:XXXXX: ");
            host = host + scanner.nextLine();

            String hostBroker = "155.210.154.";
            System.out.println("Introduzca el final de la IP del broker 155.210.154.XXX:XXXXX: ");
            hostBroker = hostBroker + scanner.nextLine();

            ServidorCalculoImpl obj = new ServidorCalculoImpl();
            Naming.rebind("//" + host + "/MiServidorCalculo", obj);

            BrokerServ broker = (BrokerServ) Naming.lookup("//" + hostBroker + "/MiBroker");
            
            while (true) {
                System.out.println("Escriba el numero de la instruccion que desea realizar: \n" +
                        "1. Registrarme en el broker\n" + 
                        "2. Dar de alta un servicio\n" + 
                        "3. Dar de baja un servicio");
                respuesta = scanner.nextLine();
                switch (respuesta) {
                    case "1":
                        broker.registrarServidor(nombreServidor, host);
                        System.out.println("El servidor se ha registrado correctamente\n");
                        break;
                    case "2":
                        parametros.add(int[].class);
                        broker.altaServicio(nombreServidor, "mediaAritmetica", parametros, "int");
                        System.out.println("Se ha dado de alta el servicio\n"); 
                        break;

                    case "3":
                        broker.bajaServicio(nombreServidor, "mediaAritmetica");
                        System.out.println("Se ha dado de baja el servicio\n");
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            System.out.println("En la excepcion");
            System.out.println(ex);
        }

    }

    @Override
    public int suma(int num1, int num2) throws RemoteException {
        return num1 + num2;
    }

}