# ArqSW-P3 - Implementacion de un broker de objetos

## Practica 3 de la asignatura Arquitectura Software
Objetivo: Desarrollar un broker de objetos haciendo uso de java RMI. 
Se implementaran dos programas servidores:
- Se implementaran las interfaces remotas y las clases correspondientes.
- Ofreceran diferentes servicios/metodos-remotos.
    En implementaciones avanzadas estos servicios tendran parametros. Puedes reutilizar,
    si lo deseas, clases implementadas en las anteriores practicas.
- Son capaces de registrar su “ubicacion” en el Broker.
- En implementaciones avanzadas son capaces de registrar sus servicios en el Broker.
- En implementaciones avanzadas los servidores estan completamente “desacoplados”
    entre sı. Es decir, no conforman con ninguna interfaz de tipo “servidor”.
    Ademas, estan completamente “desacoplados” del Broker, lo unico que conocen 
    del Broker es la IP y el puerto en el que escucha.
Broker:
- Los servicios se ejecutan “dinamicamente”. Si se añade o se quita un servicio,
    no hay que recompilar el Broker. Metodos a implementar:
        API para los servidores:
            //Registrar servidor: 
            void registrar_servidor(String nombre_servidor, String host_remoto_IP_puerto)
            //Registrar servicio:
            void alta_servicio(String nombre_servidor, String nom_servicio,
                Vector lista_param, String tipo_retorno)
            //Dar de baja servicio:
            void baja_servicio(String nombre_servidor, String nom_servicio)    
        API para los clientes:
            //Listar servicios registrados:
            Servicios lista_servicios()
            //Ejecutar servicio sıncrono:
            Respuesta ejecutar_servicio(String nom_servicio, Vector parametros_servicio)
            //Ejecutar servicio asıncrono (version mas avanzada):
            void ejecutar_servicio_asinc(String nom_servicio, Vector parametros_servicio)
            //Obtener la respuesta (caso asıncrono):
            Respuesta obtener_respuesta_asinc(String nom_servicio)
