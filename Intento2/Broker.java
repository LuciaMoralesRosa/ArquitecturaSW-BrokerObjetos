import java.lang.reflect.Method;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Broker extends UnicastRemoteObject implements BrokerCli, BrokerServ {
    
    // Atributos
    ArrayList<Servidor> listaServidores;
    ArrayList<Servicio> listaServicios;

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
                nombreServidor = s.obtenerNombreServidor();
                servicio = s;
                for (Servidor p : listaServidores) {
                    if (p.getNombreServidor().equals(nombreServidor)) {
                        hostServidor = (String) p.getHostServidor();
                    }
                    break;
                }
                break;
            }
        }

        if (hostServidor != null) {
            try {
                // Carga dinamica de la clase del servidor usando refection
                Class<?> servidorClase = Class.forName(nombreServidor);
                Remote servidor = (Remote) servidorClase.getDeclaredConstructor().newInstance();

                //Metodo 1
                Method metodoRemoto1 = servidor.getClass().getMethod(nombreServidor, Vector.class);
                Object ret = metodoRemoto1.invoke(servidor, parametrosServicio);


                //Metodo2
                // Obtener el metodo remoto mediante reflection
                Method metodoRemoto = servidorClase.getMethod(nombreServicio, Vector.class);

                // Invocar el método remoto en el servidor seleccionado
                Object resultado = metodoRemoto.invoke(servidor, parametrosServicio);
                return resultado;

            } catch (Exception ex) {
                System.out.println("Error al ejecutar el servicio: " + ex.getMessage());
            }
        }
        return null;        
    }

    @Override
    public String mostrarServicios() throws RemoteException {
        String respuesta = null;
        for (Servicio s : listaServicios) {
            respuesta = ("Metodo: " + s.obtenerNombreServicio() + "; Parametros: " + s.obtenerListaParametros() + ".\n");
        }
        return respuesta;
    }

    // Metodos para los servidores
    @Override
    public void registrarServidor(String nombreServidor, String host) throws RemoteException {
        Servidor servidor = new Servidor(nombreServidor, host);
        listaServidores.add(servidor);
    }

    @Override
    public void altaServicio(String nombreServidor, String nombreServicio, Vector<Object> listaParametros, String tipo_retorno)
            throws RemoteException {
        Servicio nuevoServicio = new Servicio(nombreServidor, nombreServicio, listaParametros, tipo_retorno);
        listaServicios.add(nuevoServicio);
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
            listaServicios.remove(index);
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        // Fijar el directorio donde se encuentra el java.policy
        // El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");

        String hostnameCliente = "155.210.154";
        System.out.println("Introduzca la IP del cliente: ");
        hostnameCliente = hostnameCliente + scanner.nextLine();

        // Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());

        try {
            Broker objeto = new Broker();
            Naming.rebind("//" + hostnameCliente + "/MiBroker", objeto);

        } catch (Exception ex) {
            System.out.println(ex);
        }    }


}