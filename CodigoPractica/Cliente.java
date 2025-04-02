import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Cliente {

    private static void mostrarServicios(List<Servicio> listaServicios) {
        System.out.println("0) Terminar ejecución");

        int i = 1;
        for (Servicio s : listaServicios) {
            System.out.println(i + ") " + s.obtenerNombreServicio());
        }
    }

    private static Object ejecutarServicio(Servicio servicio, BrokerCli broker) throws RemoteException {
        // Argumentos del servicio
        List<Object> argumentos = new ArrayList<>();
        Vector<Object> parametros = new Vector<Object>();
        parametros = servicio.obtenerListaParametros();
        System.out.println("Depurando: antes del if ");

        if (parametros != null) {
            // Pedir parametros al usuario
            System.out.println("El servicio necesita los siguientes parametros: ");
            System.out.println(parametros);
            System.out.println("Escriba un parametro en cada línea:");
            for (int i = 0; i < parametros.size(); i++) {
                argumentos.add(System.console().readLine());
                //i++;
            }
        }
        System.out.println("Depurando: despues del if ");

        return broker.ejecutar_servicio_sinc(servicio.obtenerNombreServicio(), argumentos);
        
    }

    public static void main(String args[]) {
        System.setProperty("java.security.policy", "./java.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try{
            // Obtener IP del broker
            String hostBroker = "155.210.154.";
            System.out.println("Complete la IP del broker 155.210.154.XXX:XXXXX: ");
            //hostBroker = hostBroker + scanner.nextLine();
            hostBroker = hostBroker + "200:32009"; //Borrar

            // Busqueda del broker en RMI registry
            BrokerCli broker = (BrokerCli) Naming.lookup("//" + hostBroker + "/MiBroker");

            while(true){
                List<Servicio> listaServicios = broker.obtenerServicios();
                
                mostrarServicios(listaServicios);
                
                // Seleccionar servicio
                String seleccionString = System.console().readLine();
                Integer seleccion = Integer.parseInt(seleccionString);
                if (seleccion == 0) {
                    break;
                }
                seleccion--;
                Servicio servicio = listaServicios.get(seleccion);

                Object resultado = null;
                
                System.out.println("Depurando: antes de ejecutar - ");

                resultado = ejecutarServicio(servicio, broker);
                System.out.println("Depurando: despues de ejecutar");
                
                //Mostrar resultado
                if(resultado == null){
                    System.out.println("El servicio no ha devuelto un resultado");
                }
                else{
                    System.out.println("El servicio ha devuelto:" + resultado);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}