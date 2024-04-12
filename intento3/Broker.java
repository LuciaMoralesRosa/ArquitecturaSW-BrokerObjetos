import java.lang.reflect.Method;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Vector;

public class Broker extends UnicastRemoteObject implements BrokerCli, BrokerServ {

    private final Map<String, String> listaServidores = new HashMap<>();
    private final List<Servicio> listaServicios;


    protected Broker() throws RemoteException {
        super();
        this.listaServicios = new ArrayList<>();
    }
    
    public static Servicio obtenerServicioPorNombre(List<Servicio> listaServicios, String nombreServicio) {
        return listaServicios.stream()
                .filter(s -> s.obtenerNombreServicio().equals(nombreServicio))
                .findFirst().orElse(null);
    }
    
    @Override
    public void registrarServidor(String nombreServidor, String hostRemoto) throws RemoteException {
        // Si un servicio ya estaba registrado borramos los servicios que tenia 
        // dados de alta anteriormente
        if (!listaServidores.isEmpty() && listaServidores.containsKey(nombreServidor)) {
            for (Servicio s : listaServicios) {
                if (Objects.equals(s.obtenerNombreServidor(), nombreServidor)) {
                    listaServicios.remove(s);
                }
            }
        }
        else {
            listaServidores.put(nombreServidor, hostRemoto);
            System.out.println("[ + ] Se ha registrado el servidor " +
                               nombreServidor);
        }
    }

    @Override
    public void altaServicio(String nombreServidor, String nombreServicio, Vector<Object> listaParametros,
            String tipoRetorno) throws RemoteException {
        Boolean yaExiste = false;
        for (Servicio s : listaServicios) {
            if (Objects.equals(s.obtenerNombreServicio(), nombreServicio) &&
                    Objects.equals(s.obtenerNombreServidor(), nombreServidor)) {
                yaExiste = true;
                break;
            }
        }
        if (!yaExiste) {   
            Servicio servicio = new Servicio(nombreServidor, nombreServicio, listaParametros, tipoRetorno);
            this.listaServicios.add(servicio);
            System.out.println("[ + ] El servidor" + nombreServidor +
                               " ha dado de alta el servicio " + nombreServicio);
        }
    }

    @Override
    public void bajaServicio(String nombreServidor, String nombreServicio) throws RemoteException {
        for (Servicio s : listaServicios) {
            if (Objects.equals(s.obtenerNombreServicio(), nombreServicio) &&
                    Objects.equals(s.obtenerNombreServidor(), nombreServidor)) {
                listaServicios.remove(s);
                System.out.println("[ - ] El servidor" + nombreServidor +
                        " ha dado de baja el servicio " +
                        nombreServicio);
                break;
            }
        }
        System.out.println("[ * ] No se ha encontrado el servicio " +
                           nombreServicio);
    }

    @Override
    public Object ejecutar_servicio_sinc(String nombreServicio, List<?> parametros) throws RemoteException {
        Servicio s = obtenerServicioPorNombre(listaServicios, nombreServicio);
        String nombreServidor = s.obtenerNombreServidor();
        String hostServidor = listaServidores.get(nombreServidor);
        
        try{

            Object servidor = Naming.lookup("//" + hostServidor + nombreServidor);
            Method metodo;
            if (parametros.isEmpty()) {
                metodo = servidor.getClass().getMethod(nombreServicio);
                return metodo.invoke(servidor);
            } else {
                metodo = servidor.getClass().getMethod(nombreServicio, parametros.getClass());
                return metodo.invoke(servidor, parametros);
            }
        } catch(Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public List<Servicio> obtenerServicios() throws RemoteException {
        System.out.println("[ * ] Se ha devuelto la lista de servicios");
        return this.listaServicios;
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
            System.out.print("Complete su IP 155.210.154.XXX:xxxxx: ");
            //host = host + scanner.nextLine();
            host = host + "200:32009";

            Broker objeto = new Broker();
            Naming.rebind("//" + host + "/MiBroker", objeto);
            System.out.println("[ + ] Se ha registrado el objeto remoto");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}
