import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class ServidorAlumnosImpl extends UnicastRemoteObject implements ServidorAlumnos {

    // Atributos
    private int numeroAlumnos;
    private int notasAlumnos[];
    private Random random = new Random();
    private static String miNombre;

    
    // Metodos privados empleados por el constructor

    /**
     * Genera notas aleatorias para los alumnos.
     */
    private void generadorNotasAleatorias() {
        for (int i = 0; i < numeroAlumnos; i++) {
            notasAlumnos[i] = random.nextInt(11);
        }
    }

    /**
     * Constructor de la clase ServidorAlumnosImpl.
     * Inicializa el servidor con un número aleatorio de alumnos y genera
     * notas aleatorias para los alumnos.
     * 
     * @throws RemoteException Si ocurre un error durante la inicialización remota.
     */
    protected ServidorAlumnosImpl() throws RemoteException {
        super();
        numeroAlumnos = random.nextInt(49) + 1;
        notasAlumnos = new int[numeroAlumnos];
        generadorNotasAleatorias();
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

        broker.registrarServidor("/MiServidorAlumnos", host);
        System.out.println("El servidor se ha registrado correctamente\n");
    }

    /**
     * Da de alta un servicio en el broker especificado según la elección del
     * usuario.
     * 
     * @param scanner        Scanner para leer la entrada del usuario.
     * @param broker         Referencia al broker donde se dará de alta el servicio.
     * @param nombreServidor Nombre del servidor donde se dará de alta el servicio.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    private static void darDeAlta(Scanner scanner, BrokerServ broker,
            String nombreServidor) throws RemoteException {
        Vector<Object> parametros = new Vector<Object>();
        System.out.println("Escriba el numero de la instruccion que desea dar" +
                " de alta: \n1. obtenerNumeroDeAlumnos\n" +
                "2. establecerNumeroDeAlumnos\n" +
                "3. obtenerNotas");
        String respuesta = scanner.nextLine();

        switch (respuesta) {
            case "1":
                broker.altaServicio(
                        nombreServidor, "obtenerNumeroDeAlumnos",
                        null, "int");
                System.out.println("Se ha dado de alta el servicio\n");
                break;
            case "2":
                parametros.add(int.class);
                broker.altaServicio(
                        nombreServidor, "establecerNumeroDeAlumnos",
                        parametros, null);
                System.out.println("Se ha dado de alta el servicio\n");
                break;

            case "3":
                broker.altaServicio(
                        nombreServidor, "obtenerNotas",
                        parametros, "int[]");
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
                " baja: \n1. obtenerNumeroDeAlumnos\n" +
                "2. establecerNumeroDeAlumnos\n" +
                "3. obtenerNotas");
        String respuesta = scanner.nextLine();

        switch (respuesta) {
            case "1":
                broker.bajaServicio(nombreServidor, "obtenerNumeroDeAlumnos");
                System.out.println("Se ha dado de baja el servicio\n");
                break;
            case "2":
                broker.bajaServicio(nombreServidor, "establecerNumeroDeAlumnos");
                System.out.println("Se ha dado de baja el servicio\n");
                break;
            case "3":
                broker.bajaServicio(nombreServidor, "obtenerNotas");
                System.out.println("Se ha dado de baja el servicio\n");
                break;
            default:
                System.out.println("El servicio especificado no existe.");
                break;
        }
    }

    // Métodos ofrecidos por la interfaz ServidorAlumnos

    /**
     * Obtiene el número de alumnos registrados.
     * 
     * @return Número de alumnos.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    @Override
    public int obtenerNumeroDeAlumnos() throws RemoteException {
        return numeroAlumnos;
    }

    /**
     * Establece el número de alumnos y genera notas aleatorias para cada uno.
     * 
     * @param numero Número de alumnos a establecer.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    @Override
    public void establecerNumeroDeAlumnos(int numero) throws RemoteException {
        numeroAlumnos = numero;
        notasAlumnos = new int[numeroAlumnos];
        generadorNotasAleatorias();
    }

    /**
     * Obtiene las notas de los alumnos registrados.
     * 
     * @return Arreglo de enteros que representa las notas de los alumnos.
     * @throws RemoteException Si ocurre un error durante la comunicación remota.
     */
    @Override
    public int[] obtenerNotas() throws RemoteException {
        return notasAlumnos;
    }

    /**
     * Método principal que inicia y ejecuta el servidor de alumnos.
     * 
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nombreServidor = "/MiServidorAlumnos";
        String respuesta;
        // Fijar el directorio donde se encuentra el java.policy
        // El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");

        // Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());

        try {
            // Obtener IP del servidor
            String host = "155.210.154.";
            System.out.println("Complete su IP 155.210.154.XXX:XXXXX: ");
            //host = host + scanner.nextLine();
            host = host + "201:32008";

            miNombre = "//" + host + "/MiServidorAlumnos";

            // Obtener IP del broker
            String hostBroker = "155.210.154.";
            System.out.println("Complete la IP del broker 155.210.154.XXX:XXXXX: ");
            //hostBroker = hostBroker + scanner.nextLine();
            hostBroker = hostBroker + "200:32009";

            // Crear una instancia del serivdor
            ServidorAlumnosImpl obj = new ServidorAlumnosImpl();
            // Registro de la instancia en RMI registry
            Naming.rebind(miNombre, obj);
            System.out.println("[ * ] Se ha registrado el objeto en RMI");

            // Busqueda del broker en RMI registry
            BrokerServ broker = (BrokerServ) Naming.lookup("//" + hostBroker + "/MiBroker");
            System.out.println("[ * ] Se ha establecido la conexion con el broker");

            // Ejecucion de un bucle infinito para realizar las instrucciones
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
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
