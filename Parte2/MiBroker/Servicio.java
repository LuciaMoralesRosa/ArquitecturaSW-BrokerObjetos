package MiBroker;

import java.util.Vector;

public class Servicio {
    private String nombreServidor;
    private String nombreServicio;
    private Vector listaParametros;
    private String tipoRetorno;

    public Servicio(String nombreServidor, String nombreServicio, Vector listaParametros, String tipoRetorno) {
        this.nombreServidor = nombreServidor;
        this.nombreServicio = nombreServicio;
        this.listaParametros = listaParametros;
        this.tipoRetorno = tipoRetorno;
    }

    /*
    public String obtenerCabecera() {
        String cabecera = tipoRetorno + " " + nombreServicio + "(";
        int i;
        for (i = 0; i < listaParametros.size() - 1; i++) {
            cabecera = cabecera + listaParametros.elementAt(i) + ", ";
        }
        cabecera = cabecera + listaParametros.elementAt(i + 1) + ")";
        
        return cabecera;
    }
    */

    // Getter y Setter para nombreServidor
    public String getNombreServidor() {
        return nombreServidor;
    }

    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    // Getter y Setter para nombreServicio
    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    // Getter y Setter para listaParametros
    public Vector getListaParametros() {
        return listaParametros;
    }

    public void setListaParametros(Vector listaParametros) {
        this.listaParametros = listaParametros;
    }

    // Getter y Setter para tipoRetorno
    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

}
