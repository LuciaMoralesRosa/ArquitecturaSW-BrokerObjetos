package MiBroker;
import java.lang.reflect.Method;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import javafx.util.Pair;

public class BrokerImpl extends UnicastRemoteObject implements Broker {

    // Atributos
    ArrayList<Pair<String, String>> servidores; //nombre, host
    ArrayList<Servicio> servicios;

    protected BrokerImpl() throws RemoteException {
        super();
        //TODO Auto-generated constructor stub
    }

    public void mostrarListaServicios() {
        for(Servicio s : servicios){
            System.out.println(s.obtenerCabecera());
        }
    }

    @Override
    public void ejecutar_servicio_sinc(String nombreServicio, Vector parametrosServicio) {
        String hostServidor = null;
        String nombreServidor = null;
        for (Servicio s : servicios) {
            if (s.getNombreServicio().equals(nombreServicio)) {
                nombreServidor = s.getNombreServidor();
                for (Pair p : servidores) {
                    if (p.getKey().equals(nombreServidor)) {
                        hostServidor = (String) p.getValue();
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
                
                // Obtener el metodo remoto mediante reflection
                Method metodoRemoto = servidorClase.getMethod(nombreServidor, Vector.class);

                // Invocar el método remoto en el servidor seleccionado
                Object resultado = metodoRemoto.invoke(servidor, parametrosServicio);

                

                // Procesar el resultado, si es necesario
                System.out.println("Resultado del servicio: " + resultado);

            } catch (Exception ex) {
                System.out.println("Error al ejecutar el servicio: " + ex.getMessage());
            }
        }
    }
    
    @Override
    public void registrar_servidor(String nombreServidor, String host) {
        Pair servidor = new Pair<>(nombreServidor, host);
        servidores.add(servidor);
    }

    @Override
    public void alta_servicio(String nombreServidor, String nombreServicio, Vector listaParametros, String tipo_retorno) {
        Servicio nuevoServicio = new Servicio(nombreServidor, nombreServicio, listaParametros, tipo_retorno);
        servicios.add(nuevoServicio);
    }
    
    @Override
    public void baja_servicio(String nombreServidor, String nombreServicio) {
        int index = -1;
        for (Servicio s : servicios) {
            if (s.getNombreServidor().equals(nombreServicio) && s.getNombreServidor().equals(nombreServidor)) {
                index = servicios.indexOf(s);
                break;
            }
        }
        if (index != -1) {   // Si el servicio existe lo borramos 
            servicios.remove(index);
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        // Fijar el directorio donde se encuentra el java.policy
        // El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");

        
        System.out.println("Introduzca la IP del cliente: ");
        String hostnameCliente = scanner.nextLine();

        // Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());

        try{
            BrokerImpl objeto = new BrokerImpl();
            Naming.rebind("//" + hostnameCliente + "/MiBroker", objeto);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }
}
