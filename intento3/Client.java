import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Client {

    private static void mostrarServicios(List<Servicio> listaServicios) {
        System.out.println("0) Terminar ejecución");

        int i = 1;
        for (Servicio s : listaServicios) {
            System.out.println(i + ") " + s.obtenerNombreServicio());
        }
    }

    private static Object ejecutarServicio(Servicio servicio, BrokerCli broker) {
        // Argumentos del servicio
        List<Object> argumentos = new ArrayList<>();
        Vector<Object> parametros = servicio.obtenerListaParametros();

        if (!parametros.isEmpty()) {
            // Pedir parametros al usuario
            System.out.println("El servicio necesita los siguientes parametros: ");
            System.out.println(parametros);
            System.out.println("Escriba un parametro en cada línea:");
            for (int i = 0; i <= parametros.size(); i++) {
                argumentos.add(System.console().readLine());
                //i++;
            }
        }
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
                String seleccion = System.console().readLine();
                if (Integer.parseInt(seleccion) == 0) {
                    break;
                }
                Servicio servicio = listaServicios.get(Integer.parseInt(seleccion) - 1);

                Object resultado = null;
                resultado = ejecutarServicio(servicio, broker);
                
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