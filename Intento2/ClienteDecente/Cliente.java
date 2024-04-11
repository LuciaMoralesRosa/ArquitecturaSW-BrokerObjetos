import java.rmi.Naming;
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

        Object ultimoResultado = null;
        Scanner scanner = new Scanner(System.in);
        Vector<Object> parametros = new Vector<Object>();
        Vector<Vector<Object>> elementosParametro= new Vector<Vector<Object>>();
        Vector<Object> vectoAuxiliar = new Vector<Object>();
        String auxiliar[];
        String atributosDadosString[];
        String servicio, respuesta = "S";
        String respuesta2 = "S";

        System.setProperty("java.security.policy", "./java.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            // Paso 1 - Obtener una referencia al objeto Broker
            // Nombre del host servidor o su IP. Es donde se buscara al objeto remoto
            String hostname = "155.210.154.";
            System.out.println("Introduzca la IP del broker (Ejemplo: 199:32001): ");
            hostname = hostname + scanner.nextLine();

            BrokerCli broker = (BrokerCli) Naming.lookup("//" + hostname + "/MiBroker");

            while (respuesta.equals("S") || respuesta.equals("s")) {
                System.out.println("Estos son los servicios ofrecidos por los servidores: ");
                System.out.println(broker.mostrarServicios());
                
                System.out.println("¿Desea ejecutar un servicio? [S]/[N]: ");
                respuesta2 = scanner.nextLine();

                if (respuesta2.equals("S") || respuesta2.equals("s")) {
                    System.out.println("Introduzca el nombre del servicio que desea ejecutar: ");
                    servicio = scanner.nextLine();

                    parametros.clear();

                    System.out.println("¿Desea usar el ultimo resultado obtenido como parametro? [S]/[N] ");
                    String usarRes = scanner.nextLine();

                    if (usarRes.equals("S") || usarRes.equals("s")) {
                        elementosParametro.clear();
                        vectoAuxiliar.clear();
                        vectoAuxiliar.add(ultimoResultado);
                        elementosParametro.add(vectoAuxiliar);
                    }
                    else if (usarRes.equals("N") || usarRes.equals("n")) {
                        elementosParametro.clear();
                        System.out.println("Introduzca los parametros del servicio que desea ejecutar (separados con ',' y con ';' para separar elementos de un unico atrubuto): ");
                        atributosDadosString = scanner.nextLine().split(",");
                    
                        int i = 0;
                        for (String aux : atributosDadosString) {
                            auxiliar = aux.split(";");
                            vectoAuxiliar.clear();
                            for (String a : auxiliar) {
                                vectoAuxiliar.add(a);

                            }
                            elementosParametro.add(vectoAuxiliar);
                        }
                    }
                    
                    
                    // Ejecutar el servicio
                    Object resultado = broker.ejecutar_servicio_sinc(servicio, elementosParametro);
                    ultimoResultado = resultado;
                    if (resultado == null) {
                        System.out.println("Su servicio no ha proporcionado resultados.");
                    } else {
                        System.out.println("Su servicio ha proporcionado el siguiente resultado: " + resultado);
                    }
                }
                System.out.println("¿Desea ejecutar otro servicio? [S]/[N]: ");
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
