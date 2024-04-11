import java.lang.reflect.Method;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Broker extends UnicastRemoteObject implements BrokerCli, BrokerServ {
    
    // Atributos
    private ArrayList<Servidor> listaServidores = new ArrayList<Servidor>();
    private ArrayList<Servicio> listaServicios = new ArrayList<Servicio>();

    protected Broker() throws RemoteException {
        super();
    }

    // Metodos para el cliente
    @Override
    public Object ejecutar_servicio_sinc(String nombreServicio, Vector parametrosServicio) throws RemoteException {
        String hostServidor = null;
        String nombreServidor = null;
        Servicio servicio;
        for (Servicio s : listaServicios) {
            if (s.obtenerNombreServicio().equals(nombreServicio)) {
                System.out.println("1");
                nombreServidor = s.obtenerNombreServidor();
                servicio = s;
                for (Servidor p : listaServidores) {
                    System.out.println( "Buscando: " + nombreServidor + "; Obtenido: " + p.getNombreServidor());
                        
                    if (p.getNombreServidor().equals(nombreServidor)) {
                        System.out.println("1'5");
                        hostServidor = p.getHostServidor();
                    }
                    break;
                }
                System.out.println("2");
                break;
            }
        }

        //if (hostServidor != null) {
            try {
                System.out.println("3");
                // Carga dinamica de la clase del servidor usando refection
                Class<?> servidorClase = Class.forName(nombreServidor);
                System.out.println("3.5");
                Remote servidor = (Remote) servidorClase.getDeclaredConstructor().newInstance();

                //Metodo 1
                //Method metodoRemoto1 = servidor.getClass().getMethod(nombreServidor, Vector.class);
                //Object ret = metodoRemoto1.invoke(servidor, parametrosServicio);

                System.out.println("4");

                //Metodo2
                // Obtener el metodo remoto mediante reflection
                Method metodoRemoto = servidorClase.getMethod(nombreServicio, Vector.class);
                System.out.println("5");

                // Invocar el método remoto en el servidor seleccionado
                Object resultado = metodoRemoto.invoke(servidor, parametrosServicio);
                System.out.println("6");

                return resultado;

            } catch (Exception ex) {
                System.out.println("Error al ejecutar el servicio: " + ex.getMessage());
            }
        //String respuesta = scanner.nextLine();}
        return null;        
    }

    @Override
    public String mostrarServicios() throws RemoteException {
        String respuesta = null;
        if (!listaServicios.isEmpty()) {
            for (Servicio s : listaServicios) {
                respuesta = ("Metodo: " + s.obtenerNombreServicio() + "; Parametros: " + s.obtenerListaParametros()
                        + ".\n");
            }
        }
        else {
            respuesta = "No hay servicios registrados";
        }
        System.out.println("Se han mostrado los servicios");
        return respuesta;
    }

    // Metodos para los servidores
    @Override
    public void registrarServidor(String nombreServidor, String host) throws RemoteException {
        Servidor servidor = new Servidor(nombreServidor, host);
        if (!listaServidores.isEmpty()) {
            if (!listaServidores.contains(servidor)) {
                listaServidores.add(servidor);
            }
        }
        System.out.println("Se ha registrado un servidor");
    }

    @Override
    public void altaServicio(String nombreServidor, String nombreServicio, Vector<Object> listaParametros, String tipo_retorno)
            throws RemoteException {
        Servicio nuevoServicio = new Servicio(nombreServidor, nombreServicio, listaParametros, tipo_retorno);
        if (!listaServicios.isEmpty()) {
            if (!listaServicios.contains(nuevoServicio)) {
                listaServicios.add(nuevoServicio);
            }
        }
        listaServicios.add(nuevoServicio);
        System.out.println("Se ha dado de alta un servicio");
    }

    @Override
    public void bajaServicio(String nombreServidor, String nombreServicio) throws RemoteException {
        int index = -1;
        for (Servicio s : listaServicios) {
            if (s.obtenerNombreServidor().equals(nombreServicio) && s.obtenerNombreServidor().equals(nombreServidor)) {
                index = listaServicios.indexOf(s);
                break;
            }
        }
        if (index != -1) { // Si el servicio existe lo borramos
            System.out.println("Dando de baja un servicio");
            listaServicios.remove(index);
        }
        System.out.println("Se ha dado de baja un servicio");
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        // Fijar el directorio donde se encuentra el java.policy
        // El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");

        // Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());

        try {
            String host = "155.210.154.";
            System.out.print("Introduzca el final de su ip y el puerto de RMI: ");
            host = host + scanner.nextLine();


            Broker objeto = new Broker();
            Naming.rebind("//" + host + "/MiBroker", objeto);

        } catch (Exception ex) {
            System.out.println(ex);
        }    }


}