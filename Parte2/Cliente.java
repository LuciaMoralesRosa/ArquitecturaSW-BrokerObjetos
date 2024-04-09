import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.Scanner;
import java.util.Vector;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: Cliente.java
 * Comentarios: Es el fichero correspondiente al cliente.
 *              Pertenece a la implementacion del cliente
 ******************************************************************************/

public class Cliente {

    private static void mostrarServicios(Class<?> brokerClase) {
        Method metodos[];
        try{
            metodos = brokerClase.getMethods();
            for (Method m : metodos) {
                System.out.println(m.getName());
            }
        } catch (Exception ex) {
            System.out.println("Error al ejecutar el servicio: " + ex.getMessage());
        }
    }
    
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
            
           
            String nombreBroker = "MiBroker";
            Method metodoRemoto;
            Object resultado;
            
            // Carga dinamica de la clase del servidor usando refection
            Class<?> brokerClase = Class.forName(nombreBroker);
            Remote broker = (Remote) brokerClase.getDeclaredConstructor().newInstance();

            // Paso  2 - Invocar remotamente los metodos del objeto servidor:
            // Obtener el metodo remoto mediante reflection
            mostrarServicios(brokerClase);

            parametros.clear();
            parametros.add("obtenerNumeroAlumnos");
            metodoRemoto = brokerClase.getMethod("ejecutar_servicio_sinc", Vector.class);
            int numeroDeAlumnos = (int) metodoRemoto.invoke(broker, parametros);

            parametros.clear();
            parametros.add("obtenerNotasAlumnos");
            metodoRemoto = brokerClase.getMethod("ejecutar_servicio_sinc", Vector.class);
            int[] notasAlumnos = (int[]) metodoRemoto.invoke(broker, parametros);

            System.out.println("Hay " + numeroDeAlumnos + " con las siguientes notas: " + notasAlumnos);
            
            mostrarServicios(brokerClase);

            parametros.clear();
            parametros.add(notasAlumnos);
            parametros.add(numeroDeAlumnos);
            metodoRemoto = brokerClase.getMethod("ejecutar_servicio_sinc", Vector.class);
            int notaMedia = (int) metodoRemoto.invoke(broker, parametros);

            System.out.println("La nota media de los alumnos es: " + notaMedia + "/10");


            String respuesta = "";
            while (!respuesta.equals("S") || !respuesta.equals("s") ||
                    !respuesta.equals("N") || !respuesta.equals("n")) {
                System.out.println("¿Desea volver a ver la lista de servicios? [S]/[N]");
            }
            respuesta = scanner.nextLine();
            if (respuesta.equals("S") || respuesta.equals("s")) {
                
                mostrarServicios(brokerClase);

                System.out.println("Introduzca el nuevo numero de alumnos: ");
                respuesta = scanner.nextLine();
                int nuevoNumeroAlumnos = Integer.parseInt(respuesta);
                
                parametros.clear();
                parametros.add("establecerNumeroAlumnos");
                parametros.add(nuevoNumeroAlumnos);
                metodoRemoto = brokerClase.getMethod("ejecutar_servicio_sinc", Vector.class);
                metodoRemoto.invoke(broker, parametros);
                
                parametros.clear();
                parametros.add("obtenerNumeroAlumnos");
                metodoRemoto = brokerClase.getMethod("ejecutar_servicio_sinc", Vector.class);
                nuevoNumeroAlumnos = (int) metodoRemoto.invoke(broker, parametros);
                
                System.out.println("El numero de alumnos es: " + nuevoNumeroAlumnos);
            }
     
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}