import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 *          Lucia Morales Rosa, 816906
 * 
 * Fichero: CollectionImpl.java
 * Comentarios: Es el fichero correspondiente a la implementacion de la interfaz
 *              remota.
 *              Pertenece a la implementacion del servidor
 ******************************************************************************/

/*
 * Caracteristicas:
 * -  Especifica la(s) interface(s) remota(s) que implementa.
 * -  Define el constructor para el objeto remoto.
 * -  Implementa los m´etodos que pueden ser invocados de forma remota.
 * -  Crea e instala un gestor de seguridad (Security Manager).
 * -  Creacion del objeto remoto.
 * -  Registra al menos uno de los objetos remotos mediante el registro de RMI.
 */

public class CollectionImpl extends UnicastRemoteObject implements Collection {
    
    // Variables privadas
    private int m_number_of_books;
    private String m_name_of_collection;

    // Constructor
    public CollectionImpl() throws RemoteException {
        super(); // Llama al constructor de UnicastRemoteObject
        m_number_of_books = 10;
        m_name_of_collection = "Coleccion_L";
    }

    @Override
    public String name_of_collection() throws RemoteException {
        return m_name_of_collection;
    }

    @Override
    public void name_of_collection(String _new_value) throws RemoteException {
        m_name_of_collection = _new_value;
    }

    @Override
    public int number_of_books() throws RemoteException {
        return m_number_of_books;
    }

    public static void main(String args[]) {
        
        //Fijar el directorio donde se encuentra el java.policy
        //El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");

        //Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());
        
        //nombre o IP del host donde reside el objeto servidor
        String hostName = "155.210.154.199:32001"; //se puede usar "IPhostremoto:puerto"
        
        //Por defecto RMI usa el puerto 1099
        try {
            // Crear objeto remoto
            CollectionImpl obj = new CollectionImpl();
            System.out.println("Creado!");
            // Registrar el objeto remoto
            Naming.rebind("//" + hostName + "/MyCollection", obj);
            System.out.println("Estoy registrado!");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
