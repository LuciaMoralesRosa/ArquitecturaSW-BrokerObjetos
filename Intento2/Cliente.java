import java.rmi.Naming;
import java.rmi.RemoteException;
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

/**
 * Esta clase representa un cliente que interactúa con un broker para ejecutar
 * servicios remotos.
 */
public class Cliente {

    // Atributos
    // Último valor almacenado como resultado de un servicio.
    private static Object ultimoValor = new Object();

    // Métodos privados

    /**
     * Método para obtener los parámetros de entrada para un servicio.
     * 
     * @param scanner Scanner utilizado para la entrada de datos.
     * @return Vector de objetos que representan los parámetros del servicio.
     */
    private static Vector<Object> obtenerParametros(Scanner scanner) {
        Vector<Object> parametros = new Vector<Object>();
        Vector<Object> parametroMultiple = new Vector<Object>();

        String atributosDadosString[];
        String parteDeAtributo[];
        System.out.println("Introduzca los parametros del servicio que" +
                "desea ejecutar (separados con ',' y con ';' " +
                "para separar elementos de un unico atrubuto):");
        atributosDadosString = scanner.nextLine().split(",");

        for (String s : atributosDadosString) {
            parteDeAtributo = s.split(";");
            if (parteDeAtributo.length == 1) {
                parametros.add((Object) parteDeAtributo);
            } else {
                for (int i = 0; i < parteDeAtributo.length; i++) {
                    parametroMultiple.add((Object) parteDeAtributo[i]);
                }
                parametros.add(parametroMultiple);
            }
        }
        return parametros;
    }

    /**
     * Método para ejecutar un servicio proporcionado por un BrokerCli.
     * 
     * @param broker Instancia del broker remoto.
     * @param scanner Scanner utilizado para la entrada de datos.
     * @throws RemoteException Si ocurre un error durante la ejecución remota.
     */
    private static void ejecutarServicio(BrokerCli broker, Scanner scanner)
            throws RemoteException {
        Vector<Object> elementosParametro = new Vector<Object>();

        System.out.println("¿Que servicio desea ejecutar?: ");
        String servicio = scanner.nextLine();

        System.out.println("¿Desea usar el ultimo resultado como parametro? [S]");
        String usarRes = scanner.nextLine();

        if (usarRes.equals("S") || usarRes.equals("s")) {
            if (ultimoValor == null) {
                System.out.println("No hay ningun valor almacenado");
                elementosParametro = obtenerParametros(scanner);
            } else {
                elementosParametro.add(ultimoValor);
            }
        } else {
            elementosParametro = obtenerParametros(scanner);
        }

        // Ejecutar el servicio
        Object resultado = broker.ejecutar_servicio_sinc(servicio, elementosParametro);
        ultimoValor = resultado;
        if (resultado == null) {
            System.out.println("Su servicio no ha proporcionado resultados\n");
        } else {
            System.out.println("Resultado del servicio: " + resultado + "\n");
        }
    }

    /**
     * Método principal que inicia la interacción del cliente con el broker
     * 
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ejecutar = "S";

        System.setProperty("java.security.policy", "./java.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            // Obtener IP del broker
            String hostBroker = "155.210.154.";
            System.out.println("Complete la IP del broker 155.210.154.XXX:XXXXX: ");
            hostBroker = hostBroker + scanner.nextLine();

            // Busqueda del broker en RMI registry
            BrokerCli broker = (BrokerCli) Naming.lookup("//" + hostBroker + "/MiBroker");

            while (true) {
                // Mostrar servicios a ejecutar
                System.out.println("Estos son los servicios ofrecidos por los servidores: ");
                System.out.println(broker.mostrarServicios());
                
                System.out.println("\n¿Desea ejecutar un servicio ahora? [S]: ");
                ejecutar = scanner.nextLine();

                //Ejecutar un serrvicio
                if (ejecutar.equals("S") || ejecutar.equals("s")) {
                    ejecutarServicio(broker, scanner);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
