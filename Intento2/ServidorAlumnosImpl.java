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
    
    // Metodos privados
    private void generadorNotasAleatorias() {
        for (int i = 0; i < numeroAlumnos; i++) {
            notasAlumnos[i] = random.nextInt(11);
        }
    }

    protected ServidorAlumnosImpl() throws RemoteException {
        super();
        numeroAlumnos = random.nextInt(5) + 1;
        notasAlumnos = new int[numeroAlumnos];
        generadorNotasAleatorias();
    }

    

    // Metodos ofrecidos
    @Override
    public int obtenerNumeroDeAlumnos() throws RemoteException {
        return numeroAlumnos;
    }

    @Override
    public void establecerNumeroDeAlumnos(int numero) throws RemoteException {
        numeroAlumnos = numero;
        notasAlumnos = new int[numeroAlumnos];
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

            ServidorAlumnosImpl obj = new ServidorAlumnosImpl();
            System.out.println("Despues de crear el objeto ");

            Naming.rebind("//" + host + "/MiServidorAlumnos", obj);
            System.out.println("Tras rebind ");

            BrokerServ broker = (BrokerServ) Naming.lookup("//" + hostBroker + "/MiBroker");
            System.out.println("tras broker ");

            while (true) {
                System.out.println("Escriba el numero de la instruccion que desea realizar: \n" +
                        "1. Registrarme en el broker\n" +
                        "2. Dar de alta un servicio\n" +
                        "3. Dar de baja un servicio");
                respuesta = scanner.nextLine();
                switch (respuesta) {
                    case "1":
                        broker.registrarServidor("//" + host + "/MiServidorAlumnos", host);
                        System.out.println("El servidor se ha registrado correctamente\n");
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
                                System.out.println("Se ha dado de alta el servicio\n");
                                break;
                            case "2":
                                parametros.clear();
                                parametros.add(int.class);
                                broker.altaServicio(nombreServidor, "establecerNumeroDeAlumnos", parametros, null);
                                System.out.println("Se ha dado de alta el servicio\n");
                                break;

                            case "3":
                                parametros.clear();
                                broker.altaServicio(nombreServidor, "obtenerNotas", parametros, "int[]");
                                System.out.println("Se ha dado de alta el servicio\n");
                                break;
                            default:
                                System.out.println("El servicio especificado no existe.");
                                break;
                        }
                        break;

                    case "3":
                        System.out.println("Escriba el numero del servicio que desea dar de baja: \n" +
                                "1. obtenerNumeroDeAlumnos\n" +
                                "2. establecerNumeroDeAlumnos\n" +
                                "3. obtenerNotas");
                        respuesta = scanner.nextLine();

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
                        break;
                }

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
    
}