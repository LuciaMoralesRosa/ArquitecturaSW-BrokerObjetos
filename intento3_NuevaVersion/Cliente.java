import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

public class Cliente {

    private static void mostrarServicios(List<String> listaNombres) {
        System.out.println("0) Terminar ejecución");

        int i = 1;
        for (String nombre : listaNombres) {
            System.out.println(i + ") " + nombre);
        }
        
    }

    private static Object ejecutarServicio(String servicio, BrokerCli broker,
        HashMap<String, List<Object>> listaServicios) throws RemoteException {
        // Argumentos del servicio
        List<Object> argumentos = new ArrayList<>();
        List<Object> parametros = new ArrayList<Object>();

        parametros = listaServicios.get(servicio);

        //parametros = servicio.obtenerListaParametros();

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

        return broker.ejecutar_servicio_sinc(servicio, argumentos);
        
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
                HashMap<String, List<Object>> listaServicios = broker.obtenerServicios();
                List<String> listaNombres = new ArrayList<String>();
                
                for (String key : listaServicios.keySet()) {
                    listaNombres.add(key);
                }

                mostrarServicios(listaNombres);
                
                // Seleccionar servicio
                String seleccionString = System.console().readLine();
                Integer seleccion = Integer.parseInt(seleccionString);
                if (seleccion == 0) {
                    break;
                }
                seleccion--;

                //Servicio servicio = listaServicios.get(seleccion);

                String nombreServicio = listaNombres.get(seleccion);

                Object resultado = null;
                

                resultado = ejecutarServicio(nombreServicio, broker, listaServicios);
                
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