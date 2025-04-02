# ArqSW-P3 - Implementación de un Broker de Objetos

## Práctica 3 de la asignatura Arquitectura de Software

### Objetivo
Desarrollar un broker de objetos haciendo uso de **Java RMI**.

### Implementación
Se implementarán dos programas servidores:

- Se implementarán las **interfaces remotas** y las clases correspondientes.
- Ofrecerán diferentes **servicios/métodos remotos**.
  - En implementaciones avanzadas, estos servicios tendrán parámetros. Puedes reutilizar, si lo deseas, clases implementadas en las anteriores prácticas.
- Serán capaces de **registrar su ubicación** en el Broker.
- En implementaciones avanzadas, podrán **registrar sus servicios** en el Broker.
- En implementaciones avanzadas, los servidores estarán completamente **desacoplados** entre sí. Es decir:
  - No conforman ninguna interfaz de tipo “servidor”.
  - Están completamente desacoplados del Broker; lo único que conocen del Broker es su **IP** y el **puerto** en el que escucha.

### Broker

Los servicios se ejecutan **dinámicamente**. Si se añade o se quita un servicio, **no es necesario recompilar** el Broker.

#### Métodos a implementar

##### API para los servidores:
```java
// Registrar servidor
void registrar_servidor(String nombre_servidor, String host_remoto_IP_puerto);

// Registrar servicio
void alta_servicio(String nombre_servidor, String nom_servicio,
                   Vector lista_param, String tipo_retorno);

// Dar de baja un servicio
void baja_servicio(String nombre_servidor, String nom_servicio);
```

##### API para los clientes:
```java
// Listar servicios registrados
Servicios lista_servicios();

// Ejecutar servicio síncrono
Respuesta ejecutar_servicio(String nom_servicio, Vector parametros_servicio);

// Ejecutar servicio asíncrono (versión más avanzada)
void ejecutar_servicio_asinc(String nom_servicio, Vector parametros_servicio);

// Obtener la respuesta (caso asíncrono)
Respuesta obtener_respuesta_asinc(String nom_servicio);