package MiCliente;
import java.rmi.Naming;
import java.util.Scanner;
import java.util.Vector;

import MiBroker.Broker;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: Cliente.java
 * Comentarios: Es el fichero correspondiente al cliente.
 *              Pertenece a la implementacion del cliente
 ******************************************************************************/

public class Cliente {
    
    // Pide al Broker la lista de servicios
    // El cliente pide al Broker la ejecución de ese método y muestra el resultado por pantalla.
    //Vuelve a mostrar la lista de servicios.

    public static void main(String[] args) {
        // Creamos una instancia de la clase Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);
        Vector parametros = new Vector<>();

		System.setProperty("java.security.policy", "./java.policy");

		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            // Paso 1 - Obtener una referencia al objeto Broker
            // Nombre del host servidor o su IP. Es donde se buscara al objeto remoto
            System.out.println("Introduzca la IP del broker (Ejemplo: 155.210.154.199:32001): ");

            String hostname = scanner.nextLine();
            Broker broker = (Broker) Naming.lookup("//" + hostname + "/MiBroker");

            // Paso 2 - Invocar remotamente los metodos del objeto servidor:
            broker.mostrarListaServicios();
            
            int numeroDeAlumnos;
            broker.ejecutar_servicio_sinc("obtenerNumeroAlumnos", parametros, numeroDeAlumnos);
            
            int[] notasAlumnos = broker.obtenerNotasAlumnos();

            System.out.println("Hay " + numeroDeAlumnos + " con las siguientes notas: " + notasAlumnos);

            broker.mostrarListaServicios();
            int notaMedia = broker.calcularMediaAritmetica(notasAlumnos, numeroDeAlumnos);

            System.out.println("La nota media de los alumnos es: " + notaMedia + "/10");

            String respuesta = "";
            while(!respuesta.equals("S") ||
                  !respuesta.equals("s") ||
                  !respuesta.equals("N") ||
                  !respuesta.equals("n")) {
                    System.out.println("¿Desea volver a ver la lista de servicios? [S]/[N]");
            }
            respuesta = scanner.nextLine();
            if(respuesta.equals("S") ||
               respuesta.equals("s")){
                broker.mostrarListaServicios();
                
                System.out.println("Introduzca el nuevo numero de alumnos: ");
                int nuevoNumeroAlumnos = scanner.nextLine();
                broker.establecerNumeroAlumnos(nuevoNumeroAlumnos);
                System.out.println("El numero de alumnos es: " + broker.obtenerNumeroAlumnos());
            }

                
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}