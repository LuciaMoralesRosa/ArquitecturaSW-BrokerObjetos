import java.lang.reflect.Method;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
    private ArrayList<Servidor> listaServidores = new ArrayList<Servidor>();
    private ArrayList<Servicio> listaServicios = new ArrayList<Servicio>();

    /**
     * Constructor para el Broker 
     */
    protected Broker() throws RemoteException {
        super();
    }

    // Metodos para el cliente

    /* 
     * Ejecuta un metodo remoto especificado en el servidor correspondiente
     * a través de RMI
     * 
     * @param nombreServicio Indica el nombre del servicio que se quiere ejecutar
     * @param parametrosServicio Indica los parametros que empleará el servicio
     *          a ejecutar
     * @return El objeto devuelto por el servicio ejecutado
     */
    @Override
    public Object ejecutar_servicio_sinc(String nombreServicio,
                                         Vector parametrosServicio)
                                         throws RemoteException {
        System.out.println("Depuracion: 0");
        String hostServidor = null;
        String nombreServidor = null;
        Servicio servicio;
        for (Servicio s : listaServicios) {
            if (s.obtenerNombreServicio().equals(nombreServicio)) {
                System.out.println("Depuracion: 1");
                nombreServidor = s.obtenerNombreServidor();
                servicio = s;
                for (Servidor p : listaServidores) {
                    System.out.println("Buscando: " + nombreServidor +
                                    "; Obtenido: " + p.getNombreServidor());
                        
                    if (p.getNombreServidor().equals(nombreServidor)) {
                        System.out.println("Depuracion: 1'5");
                        hostServidor = p.getHostServidor();
                    }
                    break;
                }
                System.out.println("Depuracion: 2");
                break;
            }
        }

        //if (hostServidor != null) {
            try {
                System.out.println("Depuracion: 3; Nombre servidor y host " + nombreServidor + ", "+ hostServidor);
                Object servidorClase = (Object) Naming.lookup(nombreServidor);
                
                System.out.println("Depuracion: 3.5");
                Method metodoRemoto = servidorClase.getClass().getMethod(nombreServicio,
                        Vector.class);
                System.out.println("Depuracion: 4");

                // Carga dinamica de la clase del servidor usando refection
                // Class<?> servidorClase = Class.forName(nombreServidor);
                //Remote servidor = (Remote) servidorClase.getDeclaredConstructor().newInstance();
                //Method metodoRemoto = servidorClase.getMethod(nombreServicio, Vector.class);

                //Metodo alternativo
                //Method metodoRemoto1 = servidor.getClass().getMethod(nombreServidor, Vector.class);
                //Object ret = metodoRemoto1.invoke(servidor, parametrosServicio);


                //Metodo2
                // Obtener el metodo remoto mediante reflection
                
                System.out.println("Depuracion: 5");

                // Invocar el método remoto en el servidor seleccionado
                Object resultado = metodoRemoto.invoke(servidorClase.getClass(), parametrosServicio);
                System.out.println("Depuracion: 6");

                return resultado;

            } catch (Exception ex) {
                System.out.println("Error al ejecutar el servicio: " + ex.getMessage());
            }
        //String respuesta = scanner.nextLine();}
        return null;        
    }

    @Override
    /**
     * Verifica si hay servicios registrados y en caso de haberlos, devuelve un
     * String con su nombre y parametros necesarios
     * 
     * @return Si hay servicios devuelve una cadena con los servicios registrados
     *          y sus respectivos parametros. Si no hay, devuelve un mensaje
     *          avisando de que no hay servicios registrados
     */
    public String mostrarServicios() throws RemoteException {
        String respuesta = null;
        if (!listaServicios.isEmpty()) {
            for (Servicio s : listaServicios) {
                respuesta = ("Metodo: " + s.obtenerNombreServicio() +
                        "; Parametros: " + s.obtenerListaParametros() + ".\n");
            }
        }
        else {
            respuesta = "No hay servicios registrados";
        }
        System.out.println("Se han mostrado los servicios");
        return respuesta;
    }

    // Metodos para los servidores

    /*
     * Crea un servidor con los campos dados y lo añade a la lista de servidores
     * @param nombreServidor Nombre del servidor que se quiere registrar
     * @param host Dirección host del servidor que se quiere registrar
     */
    @Override
    public void registrarServidor(String nombreServidor, String host)
                                  throws RemoteException {
        System.out.println("nombre del servidor a registrar y host" + nombreServidor + ", " + host);
        Servidor servidor = new Servidor(nombreServidor, nombreServidor);
        if (!listaServidores.isEmpty()) {
            if (!listaServidores.contains(servidor)) {
                listaServidores.add(servidor);
            }
        }
        System.out.println("Se ha registrado un servidor");
    }


    /**
     * Crea un servicio con los campos dados, verifica que no exista ya en la
     * lista de servicios y lo añade a la lista
     * 
     * @param nombreServidor Nombre del servidor al que pertenece el servicio
     *          que se quiere dar de alta
     * @param nombreServicio Nombre del servicio que se quiere dar de alta
     * @param listaParametros Lista de los parámetros que requiere el servicio
     * @param tipoRetorno Tipo del dato que retorna el método.
     * 
     */
    @Override
    public void altaServicio(String nombreServidor, String nombreServicio,
                             Vector<Object> listaParametros, String tipoRetorno)
                             throws RemoteException {
        Servicio nuevoServicio = new Servicio(nombreServidor, nombreServicio,
                                              listaParametros, tipoRetorno);
        if (!listaServicios.isEmpty()) {
            if (!listaServicios.contains(nuevoServicio)) {
                listaServicios.add(nuevoServicio);
            }
        }
        listaServicios.add(nuevoServicio);
        System.out.println("Se ha dado de alta un servicio");
    }
    
    
    /* 
     * Verifica que existe un servicio con los valores especificados y lo
     * elimina de la lista de servicios
     * @param nombreServidor Nombre del servidor donde se encuentra el servicio
     *          que se quiere dar de baja
     * @param nombreServicio Nombre del servicio que se quiere dar de baja
     */
    @Override
    public void bajaServicio(String nombreServidor, String nombreServicio)
                             throws RemoteException {
        int index = -1;
        for (Servicio s : listaServicios) {
            if (s.obtenerNombreServidor().equals(nombreServicio) &&
                s.obtenerNombreServidor().equals(nombreServidor)) {
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


    /**
     * Método principal que inicia y ejecuta el borker.
     * 
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
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
            //host = host + scanner.nextLine();
            host = host + "200:32009";

            Broker objeto = new Broker();
            Naming.rebind("//" + host + "/MiBroker", objeto);

        } catch (Exception ex) {
            System.out.println(ex);
        }    }


}