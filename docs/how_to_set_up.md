# HOW TO SET UP ALL OF THIS !?

En esta pequena guia explicare, para los futuros devs y para mi "yo" del futuro, como se levanto este proyecto.

## DOCKER MYSQL

## MYSQL

Vamos a crear la base de datos y poblarla. Para hacerlo, conviene conectarse desde un cliente gráfico. Por ejemplo, con MySQL Workbench.

1. Abrimos MySQL workbench y añadimos una nueva conexión con los siguientes parámetros:
    * Connection name: mysql_container
    * Hostname: localhost (127.0.0.1)
    * Port: 13306 (el que hemos establecido antes al crear el contenedor!)
    * username: mysql_admin (el que hemos creado)
    * para la contraseña, hacemos click en *store in vault* y ponemos la contraseña creada antes (en mi caso admin1234).

    Podemos hacer un *test connection* para ver que todo va bien. Luego clickeamos en 'ok'

2. Ahora necesitamos crear la arquitectura de la base de datos. Para ello, vamos a copiar y pegar el contenido del fichero **CREAR_DB.sql** y lo ejecutamos. Con eso tendremos la base de datos montada.

3. Ahora hay que poblarla (opcional, sólo porque Txema lo pidio para esta inception) utilizando el script de python que he creado. La documentación está subida en Teams!

## SPRINGBOOT

Ahora toca el momento de ponerse las *botas* (badum tss) y hacer el pequeño backend de nuestra aplicación. El backend se va a encargar de hablar con la base de datos y, obviamente, será su propio contenedor.

Para esta parte estoy siguiendo, parcialemente,  <a href="https://www.youtube.com/watch?v=g_zoy9m0KMs"> este </a> tutorial 

1. Crear un proyecto de springboot. Necesitamos hacer, básicamente, un proyecto de Maven (que no es más que un builder de java que nos va a ayudar a compilar y ejecutar nuestro proyecto) con todas las dependencias de springboot. Para ello vamos a esta <a href="https://start.spring.io"> pagina </a> y ponemos las siguientes opciones:
    * Project: Maven
    * Lenguage: Java
    * Spring Boot: La que sea más nueva que NO sea snapshot
    * Group: El nombre con el que quieras llamar al directorio "raiz" del proyecto" (ejemplo: com.epac)
    * Artifact: El nombre del proyecto (ejemplo: vertical_inception)
    * Description: Descripción del proyecto
    * Packaging: Jar
    * Java: La versión de java que quieras
    * Dependencias (Con Ctrl+B o click en 'add dependencies'): Spring Boot DevTools, Spring Web, Spring Data JPA, MySQL Drive (MySQL JDBC driver).

    Finalmente, hacemos click en generar. Eso nos vas a generar un zip. Lo descomprimimos y esa es la carpeta de nuestro proyecto.

2. Abrimos nuestro editor de código favorito. En mi caso es Visual Studio Code. Simplemente con abrir el editor en la carpeta descomprimida ya el editor va a interpretar que es un proyecto Maven (siempre que tengamos las extensiones correctas instaladas...)

3. Ahora tenemos que acceder al fichero **src>main>resources>application.properties** para poder configurar la conexión con la BBDD. Ahí vamos a copiar (después de la primera línea que viene por defecto, en caso de haberla) lo siguiente: 
    ```
    spring.datasource.url=jdbc:mysql://localhost:13306/pac_db
    spring.datasource.username=mysql_admin
    spring.datasource.password=admin1234
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    ## spring.jpa.hibernate.ddl-auto = validate
    spring.jpa.show-sql=true
    server.port=8082
    ```
    Está claro que los valores deben ser "tuneados" para cada proyecto particular. Yo he puesto los que estoy usando. Feel free de modificar el número de puerto en el que se lanza la API.

    Mucho ojo con el tema del "dialect". Yo he puesto MySQL8Dialect en vez de MySQL5Dialect porque (si miramos un par de pasos atras) he forzado a que la versión de mysql que use el contenedor sea la 8.0... Si tú montaste el contenedor con la 5, utiliza MySQL5Dialect!

    Podemos chequear que todo va 'ok' simplemente ejecutando el único fichero que hay en src\main\java\com\epac\vertical_inception\VerticalInceptionApplication.java (los nombres son los que yo he puesto, pero la ruta es casi identica en cualquier caso). En VSCode es simplemente darle al 'play' teniendo ese fichero abierto. Si todo va bien, debería salir una terminal con muchísimo texto y mantenerse ejecutando. Eso significa que la API está esperando peticiones. Para cerrar, un Ctrl + C basta.

    Una última consideración, ```spring.jpa.hibernate.ddl-auto = validate``` viene comentado porque es DEMASIADO ESTRICTO. Esta opcion lo que hace es que Hibernate (proveedor de JPA) verifique que la arquitectura de la BBDD coincida con la planteada en las entidades. Si se comenta, el valor por defecto es ```none```, y se asume que la integridad la maneja el programador desde el backend. Recomiendo dejarla comentada...

4.  Toca ahora ponerse a programar! Esta parte no la voy a explicar en detalle porque el <a href="https://www.youtube.com/watch?v=g_zoy9m0KMs"> tutorial </a> referenciado al comienzo de esta sección lo hace mucho mejor que yo. Si embargo, si voy a dar unas consideraciones clave:
    * El flujo de trabajo con spring boot es: Crear una entidad (que es la clase que modela un elemento de una tabla en la BBBD) en el paquete **models**, crear un repositorio en el paquete **repositories**, crear un servicio (que es una interfaz y una clase que implementa dicha interfaz) en el paquete **services**. Los servicios son realmente todas las funcionalidades que van a tener nuestros endpoints, y estos utilizan funcionalidades de los repositorios (que a su vez implementan funcionalidades básicas como CRUD -Create, Read, Update, Delete-). Por último, se crea un controlador en el paquete *controllers*. En los controladores se programan los endpoints de nuestra API.

    * Trabajar con enums: Esto me dio muchísimo dolor de cabeza así que lo explico aquí. Para trabajar con un atributo en una tabla de mysql que sea de tipo enum, lo que debemos es hacer nuestro propio enumerado en java (en el paquete models). Luego, ese enumerado es un tanto especial si el enum de sql no es traducible trivialmente a java (por ejemplo, si tiene un "-"), pues tiene que ser paramétrico. Esto quiere decir que debe incluir un método toString y un fromValue. Estos métodos transforman un string en un enum y viceversa.
    Una vez hecho el enumerado (ejemplo en el codigo. La clase Rol.java) debemos hacer un convertidor (también en el paquete models). Este patrón es soportado por spring boot y se trata de una clase que tiene dos métodos: convertToDatabaseColumn para pasar de enum a columna de sql (que si la columna es de tipo enum, sql la interpreta como un string) y convertToEntityAttribute para pasar de un string de una columna de sql a enum, que es el tipo de dato que usa nuestra entidad en java. Con esta clase hecha, solo quedaría añadir las anotaciones sobre el atributo de tipo enum de nuestra entidad: ```@Convert(converter = RolConverter.class)```

    * Trabajar con relaciones muchos a muchos (n:m): Si tenemos una relación muchos a muchos entre dos entidades, tendremos que indicarlo en el código... Pero, ¿Cómo se hace esto? 
    Primero, seleccionamos una entidad de la relación como propietaria de dicha relación (elección arbitraria. En las relaciones n:m se puede escoger cualquiera). Luego, creamos un atributo ```Set<Unidad> unidadesALasQuePertenece =  new HashSet<>();```, donde ```Unidad``` es la entidad **no propietaria**. Añadimos sobre ese atributo las anotaciones: 
        ```
        @ManyToMany
        @JoinTable(
            name = "usuario_pertenece_a_unidad", 
            joinColumns = @JoinColumn(name = "ID_usuario"), 
            inverseJoinColumns = @JoinColumn(name = "ID_unidad"))
        ``` 
        Donde ```name``` es el nombre de la tabla intermedia que genera la relación (**OJO!!! SI EN ESTE PUNTO TIENES EN LAS OPCIONES ```spring.jpa.hibernate.ddl-auto = validate```, ENTONCES HIBERNATE VA A INTENTAR ALTERAR LA TABLA INTERMEDIA, LO CUAL ES MUY MOLESTO. PONER A ```none``` O A ```validate```**). Luego, ```joinColumns``` tiene en ```name``` el nombre de la primary key de la entidad "propietaria" y ```inverseJoinColumns``` tiene en ```name``` el nombre de la primary key de la entidad "no-propietaria"

## DOCKER SPRINGBOOT

## FRONTEND

* DEPENDENCIAS: 
    * Material UI: ```npm install @mui/material @emotion/react @emotion/styled```

    * Material UI Icons: ```npm install @mui/icons-material```

    * BUEN TUTO REACT https://www.youtube.com/watch?v=O_XL9oQ1_To

## DOCKER FRONTEND