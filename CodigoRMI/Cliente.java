import java.rmi.Naming;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 *          Lucia Morales Rosa, 816906
 * 
 * Fichero: Cliente.j
 * Comentarios: Es el fichero correspondiente al cliente.
 *              Pertenece a la implementacion del cliente
 ******************************************************************************/

public class Cliente {
	public static void main(String[] args) {
		System.setProperty("java.security.policy", "./java.policy");

		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            // Paso 1 - Obtener una referencia al objeto servidor creado anteriormente
            // Nombre del host servidor o su IP. Es donde se buscara al objeto remoto
            String hostname = "155.210.154.199:32001"; // se puede usar "IP:puerto"
            Collection server = (Collection) Naming.lookup("//" + hostname + "/MyCollection");

            // Paso 2 - Invocar remotamente los metodos del objeto servidor:
            String nombreColeccion = server.name_of_collection();
            int numeroDeLibros = server.number_of_books();
            System.out.println("Hay " + numeroDeLibros + " en la coleccion " + nombreColeccion);

            server.name_of_collection("NuevoNombre");
            String nuevoNombreColeccion = server.name_of_collection();
            if (nuevoNombreColeccion.equals(nombreColeccion)) {
                System.out.println("Se ha producido un error al cambiar el nombre de la coleccion");
            }
            else {
                System.out.println("Has cambiado el nombre de la coleccion con exito");
            }
                
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
