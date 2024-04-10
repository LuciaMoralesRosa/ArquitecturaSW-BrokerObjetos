import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class ServidorAlumnosImpl extends UnicastRemoteObject implements ServidorAlumnos {

    // Atributos
    private int numeroAlumnos;
    private int notasAlumnos[];
    private Random random = new Random();

    protected ServidorAlumnosImpl() throws RemoteException {
        super();
        numeroAlumnos = random.nextInt(50);
        generadorNotasAleatorias();
    }

    // Metodos privados
    private void generadorNotasAleatorias() {
        for (int i = 0; i < numeroAlumnos; i++) {
            notasAlumnos[i] = random.nextInt(11);
        }
    }

    // Metodos ofrecidos
    @Override
    public int obtenerNumeroDeAlumnos() throws RemoteException {
        return numeroAlumnos;
    }

    @Override
    public void establecerNumeroDeAlumnos(int numero) throws RemoteException {
        numeroAlumnos = numero;
        generadorNotasAleatorias();
    }

    @Override
    public int[] obtenerNotas() throws RemoteException {
        return notasAlumnos;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String respuesta = null;
        String nombreServidor = "ServidorAlumnosImpl";
        Vector parametros = null;

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
                        } else {
                            System.out.println("Ya estas registrado en el broker");
                        }
                        break;
                    case "2":
                        System.out.println("Escriba el numero de la instruccion que desea dar de alta: \n" +
                                "1. obtenerNumeroDeAlumnos\n" +
                                "2. establecerNumeroDeAlumnos\n" +
                                "3. obtenerNotas");
                        respuesta = scanner.nextLine();

                        switch (respuesta) {
                            case "1":
                                parametros.clear();
                                broker.altaServicio(nombreServidor, "obtenerNumeroDeAlumnos", parametros, "int");
                                break;
                            case "2":
                                parametros.clear();
                                parametros.add(int.class);
                                broker.altaServicio(nombreServidor, "establecerNumeroDeAlumnos", parametros, null);
                                break;

                            case "3":
                                parametros.clear();
                                broker.altaServicio(nombreServidor, "obtenerNotas", parametros, "int[]");
                                break;
                            default:
                            System.out.println("El servicio especificado no existe.");
                                break;
                        }
                        break;

                    case "3":
                        System.out.println("Escriba el numero de la instruccion que desea dar de baja: \n" +
                                "1. obtenerNumeroDeAlumnos\n" +
                                "2. establecerNumeroDeAlumnos\n" +
                                "3. obtenerNotas");
                        respuesta = scanner.nextLine();

                        switch (respuesta) {
                            case "1":
                                broker.bajaServicio(nombreServidor, "obtenerNumeroDeAlumnos");
                                break;
                            case "2":
                                broker.bajaServicio(nombreServidor, "establecerNumeroDeAlumnos");
                                break;
                            case "3":
                                broker.bajaServicio(nombreServidor, "obtenerNotas");
                                break;
                            default:
                                System.out.println("El servicio especificado no existe.");
                                break;
                        }
                        break;
                }

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
    
}