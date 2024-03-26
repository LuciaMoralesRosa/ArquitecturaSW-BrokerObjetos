/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 *          Lucia Morales Rosa, 816906
 * 
 * Fichero: Collection.java
 * Comentarios: Es el fichero correspondiente a la interfaz remota
 *              Pertenece a la implementacion del servidor
 ******************************************************************************/

/*
 * Caracteristicas:
 * - La interface remota debe ser p´ublica. De otro modo se producir´ıa un error
 *   cuando el cliente tratara de cargar un objeto remoto que implementa la
 *   interface remota.
 * - La interface remota debe descender de java.rmi.Remote
 * - Cada m´etodo debe declarar una excepci´on java.rmi.RemoteException en
 *   su cl´ausula throws, adem´as de excepciones espec´ıficas del m´etodo si las
 *   tuviera.
 * - Un objeto remoto pasado como argumento o valor de retorno debe ser
 *   declarado como interface remota, no como clase de implementaci´on (objeto
 *   remoto)
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Collection extends Remote {

    // Metodos de la interface
    int number_of_books() throws RemoteException;

    String name_of_collection() throws RemoteException;

    void name_of_collection(String _new_value) throws RemoteException;
}