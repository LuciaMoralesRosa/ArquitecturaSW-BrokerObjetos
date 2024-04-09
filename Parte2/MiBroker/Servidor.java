package MiBroker;
public class Servidor {
    private String nombreServidor;
    private String hostServidor;

    Servidor(String nombreServidor, String hostServidor) {
        this.nombreServidor = nombreServidor;
        this.hostServidor = hostServidor;
    }

    // Getter para nombreServidor
    public String getNombreServidor() {
        return nombreServidor;
    }

    // Setter para nombreServidor
    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    // Getter para hostServidor
    public String getHostServidor() {
        return hostServidor;
    }

    // Setter para hostServidor
    public void setHostServidor(String hostServidor) {
        this.hostServidor = hostServidor;
    }

}
