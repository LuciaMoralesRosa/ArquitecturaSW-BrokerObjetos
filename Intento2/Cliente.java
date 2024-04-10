import java.lang.reflect.Method;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.Scanner;
import java.util.Vector;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: Cliente.java
 * Comentarios: Es el fichero correspondiente al cliente.
 * Pertenece a la implementacion del cliente
 ******************************************************************************/

public class Cliente {

    // Pide al Broker la lista de servicios
    // El cliente pide al Broker la ejecución de ese método y muestra el resultado
    // por pantalla.
    // Vuelve a mostrar la lista de servicios.

    public static void main(String[] args) {
        // Creamos una instancia de la clase Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);
        Vector parametros = new Vector<>();
        Object atributos[];
        String servicio, respuesta = "S";

        System.setProperty("java.security.policy", "./java.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            // Paso 1 - Obtener una referencia al objeto Broker
            // Nombre del host servidor o su IP. Es donde se buscara al objeto remoto
            String hostname = "155.210.154";
            System.out.println("Introduzca la IP del broker (Ejemplo: 155.210.154.199:32001): ");
            hostname = hostname + scanner.nextLine();

            BrokerCli broker = (BrokerCli) Naming.lookup("//" + hostname + "/MiBroker");

            while (respuesta.equals("S") || respuesta.equals("s")) {
                System.out.println("Estos son los servicios ofrecidos por los servidores: ");
                broker.mostrarServicios();
                
                System.out.println("Introduzca el nombre del servicio que desea ejecutar: ");
                servicio = scanner.nextLine();

                System.out.println("Introduzca los parametros del servicio que desea ejecutar (separados con ','): ");
                atributos = scanner.nextLine().split(",");

                parametros = null;
                for(Object a : atributos){
                    parametros.addElement(a);
                }

                //Ejecutar el servicio
                Object resultado = broker.ejecutar_servicio_sinc(servicio, parametros);
                if (resultado == null) {
                    System.out.println("Su servicio no ha proporcionado resultados.");
                }
                else {
                    System.out.println("Su servicio ha proporcionado el siguiente resultado: " + resultado);
                }

                System.out.println("¿Desea ejecutar otro servicio? [S]/[N]: ");
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
