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

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: Broker.java
 * Comentarios: Es el fichero correspondiente al Broker.
 ******************************************************************************/

/**
 * Clase que representa un Broker.
 */
public class Broker extends UnicastRemoteObject implements BrokerCli, BrokerServ {

    // Atributos

    private final Map<String, String> listaServidores = new HashMap<>();
    private final List<Servicio> listaServicios;

    /**
     * Constructor para el Broker
     */
    protected Broker() throws RemoteException {
        super();
        this.listaServicios = new ArrayList<>();
    }
    
    // Metodos auxiliares

    /**
     * Devuelve el servidor con el nombre dado de la lista de servicios
     * 
     * @param listaServicios Lista de los servicios registrados
     * @param nombreServicio Nombre del servicio a buscar
     * @return Servicio con el nombre especificado
     */

    public static Servicio obtenerServicioPorNombre(List<Servicio> listaServicios, String nombreServicio) {
        return listaServicios.stream()
                .filter(s -> s.obtenerNombreServicio().equals(nombreServicio))
                .findFirst().orElse(null);
    }

    // Metodos para los servidores

    /*
     * Crea un servidor con los campos dados y lo añade a la lista de servidores
     * 
     * @param nombreServidor Nombre del servidor que se quiere registrar
     * 
     * @param host Dirección host del servidor que se quiere registrar
     */
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
        } else {
            listaServidores.put(nombreServidor, hostRemoto);
            System.out.println("[ + ] Se ha registrado el servidor " +
                    nombreServidor);
        }
    }

    /**
     * Crea un servicio con los campos dados, verifica que no exista ya en la
     * lista de servicios y lo añade a la lista
     * 
     * @param nombreServidor  Nombre del servidor al que pertenece el servicio
     *                        que se quiere dar de alta
     * @param nombreServicio  Nombre del servicio que se quiere dar de alta
     * @param listaParametros Lista de los parámetros que requiere el servicio
     * @param tipoRetorno     Tipo del dato que retorna el método.
     * 
     */
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

    /*
     * Verifica que existe un servicio con los valores especificados y lo
     * elimina de la lista de servicios
     * 
     * @param nombreServidor Nombre del servidor donde se encuentra el servicio
     * que se quiere dar de baja
     * 
     * @param nombreServicio Nombre del servicio que se quiere dar de baja
     */
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

    // Metodos para el cliente

    /*
     * Ejecuta un metodo remoto especificado en el servidor correspondiente
     * a través de RMI
     * 
     * @param nombreServicio Indica el nombre del servicio que se quiere ejecutar
     * 
     * @param parametrosServicio Indica los parametros que empleará el servicio
     * a ejecutar
     * 
     * @return El objeto devuelto por el servicio ejecutado
     */
    @Override
    public Object ejecutar_servicio_sinc(String nombreServicio, List<?> parametros) throws RemoteException {
        Servicio s = obtenerServicioPorNombre(listaServicios, nombreServicio);
        String nombreServidor = s.obtenerNombreServidor();
        String hostServidor = listaServidores.get(nombreServidor);

        try {

            Object servidor = Naming.lookup("//" + hostServidor + nombreServidor);
            Method metodo;
            if (parametros.isEmpty()) {
                metodo = servidor.getClass().getMethod(nombreServicio);
                return metodo.invoke(servidor);
            } else {
                metodo = servidor.getClass().getMethod(nombreServicio, parametros.getClass());
                return metodo.invoke(servidor, parametros);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    /**
     * Verifica si hay servicios registrados y en caso de haberlos, devuelve un
     * String con su nombre y parametros necesarios
     * 
     * @return Si hay servicios devuelve una cadena con los servicios registrados
     *         y sus respectivos parametros. Si no hay, devuelve un mensaje
     *         avisando de que no hay servicios registrados
     */
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
