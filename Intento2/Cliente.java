import java.rmi.Naming;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.setProperty("java.security.policy", "./java.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            String hostBroker = "155.210.154.201:32008";

            BrokerCli broker = (BrokerCli) Naming.lookup("//" + hostBroker + "/MiBroker");

            while (true) {
                System.out.println("Servicios: \n" + broker.mostrarServicios());
                System.out.println("Introduzca el nombre del servicio que desea ejecutar: ");
                //String respuesta = scanner.nextLine();
                String respuesta = "obtenerNumeroDeAlumnos";
                Object resultado = broker.ejecutar_servicio_sinc(respuesta, null);

                System.out.println("Su servicio ha proporcionado el siguiente resultado: " + resultado);
                String pausa = scanner.nextLine();
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
