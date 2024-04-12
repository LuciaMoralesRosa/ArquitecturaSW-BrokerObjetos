import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Vector;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: ServidorCalculoImpl.java
 * Comentarios: Es el fichero correspondiente a implementacion de los servicios 
 * ofrecidos por el servidor de calculo.
 ******************************************************************************/


/**
 * Implementación de un servidor de calculo que ofrece servicios remotos
 * mediante RMI
 */
public class ServidorCalculoImpl extends UnicastRemoteObject implements ServidorCalculo {

    /**
     * Constructor de la clase ServidorCalculoImpl.
     * 
     * @throws RemoteException Si ocurre un error durante la inicialización remota.
     */
    protected ServidorCalculoImpl() throws RemoteException {
        super();
    }

    // Métodos privados de gestión de instrucciones

    /**
     * Muestra las opciones de instrucciones disponibles y espera la elección del
     * usuario.
     * 
     * @param scanner Scanner para leer la entrada del usuario.
     * @return Cadena que representa la opción elegida por el usuario.
     */
    private static String intruccionAEjecutar(Scanner scanner) {
        System.out.println("Escriba el numero de la instruccion que desea realizar: \n" +
                "1. Registrarme en el broker\n" +
                "2. Dar de alta un servicio\n" +
                "3. Dar de baja un servicio");
        return scanner.nextLine();
    }

    /**
     * Registra este servidor en el broker especificado.
     * 
     * @param broker Referencia al broker donde se registrará este servidor.
     * @param host   Dirección IP y puerto del servidor.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    private static void registrar(BrokerServ broker,
            String host) throws RemoteException {
        // Opcion 1:
        // broker.registrarServidor("ServidorCalculoImpl", host);
        // Opcion 2:
        broker.registrarServidor("//" + host + "/MiServidorCalculo", host);
        System.out.println("El servidor se ha registrado correctamente\n");
    }

    /**
     * Da de alta un servicio en el broker especificado según la elección del
     * usuario.
     * 
     * @param scanner Scanner para leer la entrada del usuario.
     * @param broker Referencia al broker donde se dará de alta el servicio.
     * @param nombreServidor Nombre del servidor donde se dará de alta el servicio.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    private static void darDeAlta(Scanner scanner, BrokerServ broker,
                                  String nombreServidor) throws RemoteException {

        Vector<Object> parametros = new Vector<Object>();
        System.out.println("Escriba el numero de la instruccion que desea dar" +
                " de alta: \n 1. obtenerNumeroDeAlumnos\n" +
                "2. establecerNumeroDeAlumnos\n" +
                "3. obtenerNotas");
        String respuesta = scanner.nextLine();

        switch (respuesta) {
            case "1":
                parametros.add(int[].class);
                broker.altaServicio(nombreServidor, "mediaAritmetica",
                                    parametros, "int");
                System.out.println("Se ha dado de alta el servicio\n");
                break;
            case "2":
                parametros.add(int.class);
                parametros.add(int.class);
                broker.altaServicio(nombreServidor, "add",
                        parametros, "int");
                System.out.println("Se ha dado de alta el servicio\n");
                break;
            default:
                System.out.println("El servicio especificado no existe.");
                break;
        }
    }

    /**
     * Da de baja un servicio en el broker especificado según la elección del
     * usuario.
     * 
     * @param scanner Scanner para leer la entrada del usuario.
     * @param broker Referencia al broker donde se dará de alta el servicio.
     * @param nombreServidor Nombre del servidor donde se dará de alta el servicio.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    private static void darDeBaja(Scanner scanner, BrokerServ broker,
            String nombreServidor) throws RemoteException {
        System.out.println("Escriba el numero del servicio que desea dar de" +
                " baja: \n1. mediaAritmetica\n2. suma");
        String respuesta = scanner.nextLine();

        switch (respuesta) {
            case "1":
                broker.bajaServicio(nombreServidor, "mediaAritmetica");
                System.out.println("Se ha dado de baja el servicio\n");
                break;
            case "2":
                broker.bajaServicio(nombreServidor, "suma");
                System.out.println("Se ha dado de baja el servicio\n");
                break;
            default:
                System.out.println("El servicio especificado no existe.");
                break;
        }
    }

    /**
     * Calcula la media aritmética de un arreglo de valores enteros.
     * 
     * @param valores Arreglo de valores enteros sobre los cuales calcular la media.
     * @return Media aritmética de los valores.
     * @throws RemoteException Si ocurre un error durante la invocación remota.
     */
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

    /**
     * Calcula la suma de dos números enteros.
     * 
     * @param num1 Primer número entero.
     * @param num2 Segundo número entero.
     * @return Suma de num1 y num2.
     * @throws RemoteException Si ocurre un error durante la invocación remota.
     */
    @Override
    public int suma(int num1, int num2) throws RemoteException {
        return num1 + num2;
    }

    /**
     * Método principal que inicia y ejecuta el servidor de calculo.
     * 
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
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
            // Obtener IP del servidor
            String host = "155.210.154.";
            System.out.println("Complete su IP 155.210.154.XXX:XXXXX: ");
            host = host + scanner.nextLine();

            // Obtener IP del broker
            String hostBroker = "155.210.154.";
            System.out.println("Complete la IP del broker 155.210.154.XXX:XXXXX: ");
            hostBroker = hostBroker + scanner.nextLine();

            // Crear una instancia del serivdor
            ServidorCalculoImpl obj = new ServidorCalculoImpl();
            // Registro de la instancia en RMI registry
            Naming.rebind("//" + host + "/MiServidorCalculo", obj);
            // Busqueda del broker en RMI registry
            BrokerServ broker = (BrokerServ) Naming.lookup("//" + hostBroker + "/MiBroker");
            
            while (true) {
                respuesta = intruccionAEjecutar(scanner);
                switch (respuesta) {
                    case "1":
                        registrar(broker, host);
                        break;
                    case "2":
                        darDeAlta(scanner, broker, nombreServidor);
                        break;

                    case "3":
                        darDeBaja(scanner, broker, nombreServidor);
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

}