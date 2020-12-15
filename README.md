# Proyecto-BBSitter
INDICE

1. Idea del proyecto	3
2. Objetivos	3
3. Arquitectura de la app	4
4. Tecnologías empleadas	6
4.1 IDE y lenguaje de programación	6
4.2 Base de datos	7
4.3 Diseño y prototipo	7
4.4 Control de versiones Git y GitHub	8
4.5 Google Maps Platform	8
5. Fases del proyecto	9
6. Base de datos (Firebase)	10
6.1 Implementación Firebase en app Android	10
6.2 Servicios de Firebase utilizados	17
6.2.1 Firebase Authentication	17
6.2.2 Firebase Storage	19
6.2.3 Firebase Cloud Firestore	21
6.2.4 Consultas en la base de datos Firebase Firestore	24
6.2.5 Modelo de datos	25
7. Casos de Uso	31
8. Diseño de la app	35
8.1. Carpeta res	35
8.2 Material Design	39
8.2.1 ¿Qué es Material Design?	39
8.2.2 Implementación de Material Design en nuestra aplicación.	39
8.2.3 Componentes de Material Design utilizados en BBSitter	40
8.3 Animaciones Lottie	42
8.3.1 ¿Qué es Lottie?	42
8.3.2 ¿Cómo trabaja Lottie?	42
8.3.3 Banco de recursos lottiefiles.com	42
8.3.4 Implementación de recursos Lottie	43
8.4 Identidad visual de BBSitter	45
8.4.1 Logo	45
8.4.2 Tipografía	45
8.4.3 Selección de color	46
8.4.4 Imágenes, iconos….	46
9. Google Maps Platform	47
9.1 Credencial clave API de Google Maps Platform	47
9.2 Obtener clave API para nuestro proyecto	48
9.3 Implementar Google Maps y Google Places en nuestro proyecto	49
10. GitHub	52
11. Usabilidad de nuestra app	56
12. Mejoras de la aplicación	57
13. Bibliografía	58

 
1. Idea del proyecto

Nuestra aplicación, basada en Android, intenta hacer la vida más fácil a los padres que necesitan un cuidador/a para sus hijos. Hay muchos padres que, debido sobre todo al trabajo, no disponen de todo el tiempo que quisieran para poder cuidar a sus hijos y es una tarea muy complicada poder encontrar a alguien de confianza para que los cuide.
De ahí nace nuestra aplicación, donde los padres pueden ver los anuncios de los cuidadores/as y disponer de toda la transparencia y confianza ya que implementamos valoraciones y chat para que los padres estén seguros de elegir el mejor candidato y los niños estén en las mejores manos.

2. Objetivos

Uno de los objetivos primordiales de nuestra aplicación es la confianza. Por ello implementamos valoraciones para que las familias puedan calificar a los cuidadores y así sea más fácil poder elegir.
Otro objetivo es la comunicación segura y eficaz entre familias y cuidadores/as, por ello implementamos un chat para que la comunicación sea fluida y rápida. (Mejora)
Por último, un objetivo muy importante es hacer la vida más fácil a nuestros clientes. Encontrar a un cuidador/a de confianza puede ser muy difícil y por ello creamos esta plataforma para que padres y cuidadores/as puedan comunicarse de manera sencilla y transparente.
El usuario se registra en nuestra aplicación mediante un correo electrónico el cual verificamos desde Firebase. Una vez verificado el usuario tiene que crearse un perfil de familia o de canguro rellenando todos sus datos. Cuando entramos en la aplicación en el caso de ser una familia el usuario podrá ver todos los canguros que hay cerca de ella, los más valorados y los más baratos aparte de poder consultar en el mapa los canguros que tiene por la zona en la que esté en ese momento ya que accedemos a su ubicación.
En el usuario familia también puede crear un anuncio si lo desea, para que los canguros puedan contactar con ella mediante el menú desplegable introduciendo todos los datos para que el canguro sepa que necesidades tiene la familia.
El objetivo es que tanto las familias como los canguros tengan una experiencia de uso plena y gratificante, que puedan comunicarse entre familias y canguros con la mayor transparencia posible. 
3. Arquitectura de la app

La arquitectura de aplicación describe los patrones y las técnicas que se utilizan para diseñar y desarrollar una aplicación. La arquitectura le proporciona un plan y las prácticas recomendadas que debe seguir al momento de diseñar una aplicación, de modo que obtenga una aplicación bien estructurada.
En una arquitectura de aplicaciones habrá servicios de frontend y de backend. El desarrollo de frontend se refiere a la experiencia del usuario con la aplicación, mientras que el desarrollo de backend implica proporcionar acceso a los datos, los servicios y otros sistemas actuales que permiten el funcionamiento de la aplicación.
La arquitectura de BBSitter comienza con los lenguajes empleado que vamos a utilizar que será java, como hemos empleado durante todo el curso, XML (utilizado en el frontend de nuestra app) y Json en caso de escribir en la base de datos de Firebase. 
Estos lenguajes los utilizaremos usando un IDE para la creación de la App, el cuál será Android Studio. Ya que estamos familiarizados con este IDE desde el comienzo de nuestro 2º curso de Desarrollo de Aplicaciones Multiplataforma, debíamos utilizarlo para desarrollar nuestro proyecto.
Para poder ir guardando todo el proyecto y no perder nada de el, hemos elegido la manera más extendida y profesional que existe en estos momentos para el control de versiones y ese es Git. 
Git es un sistema de control de versiones. Un sistema de control de versiones nos va a servir para trabajar en equipo de una manera mucho más simple y optima cuando estamos desarrollando software. Cuando acabamos de desarrollar nuestro código, utilizamos Git para mezclar los cambios con los otros compañeros. 
Unido a Git, usaremos GitHub. Un repositorio en la nube donde guardar nuestros proyectos y una a la vez una red social del mundo de la programación. 
Una arquitectura de aplicación no podría existir si no existiese un Backend, donde poder guardar los datos y acceder a ellos. En nuestro caso usaremos Firebase, desarrollado por Google. El cual nos facilitará el posible quebradero de cabeza de configurar un servidor, ya que esto lo hará Firebase por nosotros. Firebase tiene herramientas que nos facilitará el registro de usuarios (Firebase Authentication), el grabar y acceder a datos con Cloud Firestore y el almacenamiento de imágenes con Firebase Storage.
Dentro de una arquitectura de aplicación también podemos tener otros servicios de terceros que nos ayudarán a implementar algunas funcionalidades, como en nuestro caso puede ser Google Maps Platform y Google Places para poder implementar un mapa donde colocar los marcadores de los canguros.







 
4. Tecnologías empleadas

4.1 IDE y lenguaje de programación

Android Studio
Android Studio es el entorno de desarrollo integrado oficial para la plataforma Android. Fue anunciado el 16 de mayo de 2013 y reemplazó a Eclipse como el IDE oficial para el desarrollo de aplicaciones para Android. La primera versión estable fue publicada en diciembre de 2014.
Desde el 7 de mayo de 2019, Kotlin es el lenguaje preferido de Google para el desarrollo de aplicaciones de Android. 3 aun así, Android Studio admite otros lenguajes de programación, como Java y C ++.

Java
Java es un lenguaje de programación y una plataforma informática que fue comercializada por primera vez en 1995 por Sun Microsystems. Hay muchas aplicaciones y sitios web que no funcionarán, probablemente, a menos que tengan Java instalado y cada día se crean más. 
Java es rápido, seguro y fiable. Desde ordenadores portátiles hasta centros de datos, desde consolas para juegos hasta computadoras avanzadas, desde teléfonos móviles hasta Internet, Java está en todas partes, si es ejecutado en una plataforma no tiene que ser recompilado para correr en otra. 
Java es, a partir de 2012, uno de los lenguajes de programación más populares en uso, particularmente para aplicaciones de cliente-servidor de web, con unos diez millones de usuarios reportados.
	
JSON
JSON (acrónimo de JavaScript Object Notation) es un formato de texto sencillo para el intercambio de datos. Se trata de un subconjunto de la notación literal de objetos de JavaScript, aunque, debido a su amplia adopción como alternativa a XML, se considera un formato independiente del lenguaje.

 
4.2 Base de datos

Firebase
Firebase es una plataforma para el desarrollo de aplicaciones web y aplicaciones móviles desarrollada por Google en 2014
Es una plataforma ubicada en la nube, integrada con Google Could Platform, que usa 	un conjunto de herramientas para la creación y sincronización de proyectos que serán 	dotados de alta calidad, haciendo posible el crecimiento del número de usuarios y 	dando resultado también a la obtención de una mayor monetización. 

Firebase Firestore
Cloud Firestore es la base de datos más reciente de Firebase para el desarrollo de apps para dispositivos móviles. Es una solución eficiente y de baja latencia destinada a las apps para dispositivos móviles que necesitan estados sincronizados entre los clientes en tiempo real.

Firebase Authentication
Firebase Authentication nos permite implementar fácilmente la autenticación en cualquier proyecto sea web o app. Firebase ofrece la posibilidad de autenticar al usuario a través de diferentes proveedores más allá de email o password.

Firebase Storage
Cloud Storage para Firebase es un servicio de almacenamiento de objetos potente, simple y rentable construido para la escala de Google. Los SDK de Firebase para Cloud Storage agregan la seguridad de Google a las operaciones de carga y descarga de archivos para tus apps de Firebase, sin importar la calidad de la red.

4.3 Diseño y prototipo

Adobe XD
Adobe XD es un editor de gráficos vectoriales desarrollado y publicado por Adobe Inc para diseñar y crear un prototipo de la experiencia del usuario para páginas web y aplicaciones móviles.
         
Adobe Photoshop
Adobe Photoshop es un editor de fotografías desarrollado por Adobe Systems Incorporated. Usado principalmente para el retoque de fotografías y gráficos.
Lo hemos utilizado para la creación de logos, imágenes de Login, de bienvenida y el menú desplegable 

Material Desing
Material Design es una normativa de diseño enfocado en la visualización del sistema operativo Android, además en la web y en cualquier plataforma. Fue desarrollado por Google.

Lottie
Lottie es una biblioteca para Android, iOS, Web y Windows que analiza animaciones de Adobe After Effects exportadas como json con Bodymovin y las representa de forma nativa en el móvil y en la web
Más tarde lo explicamos con más detalle.

AirDroid
AirDroid es una aplicación que hace de necesario puente entre el PC y tu móvil Android para que puedas gestionarlo desde la comodidad del PC: ver tus imágenes, leer tus mensajes o incluso manejar tu teléfono de forma remota.
La utilizamos para poder controlar el móvil sin necesidad de cables ni máquinas virtuales

4.4 Control de versiones Git y GitHub

Git
Git es un sistema de control de versiones libre y gratuito. Un sistema de control de versiones nos va a servir para trabajar en equipo de una manera mucho más simple y optima cuando estamos desarrollando software. Cuando acabamos de desarrollar nuestro código, utilizamos Git para mezclar los cambios con los otros compañeros. 

GitHub
Unido a Git, usaremos GitHub. Un repositorio en la nube donde guardar nuestros proyectos y a la vez una red social del mundo de la programación. Donde poder colaborar con otros proyectos y compartir los tuyos para que alguien contribuya en su realización.

4.5 Google Maps Platform 

Google Maps Platform
Google Maps Platform es una plataforma de servicio gratuito de Google de mapas a través de la Web. Ofrece imágenes de mapas desplazables, así como fotos de satélite del mundo entero y de ciudades, e incluso la ruta entre diferentes ubicaciones con especificación del detalle del recorrido. 
Gracias a las APIs que ofrecen podemos implementar más funcionalidades a nuestra aplicación, como en nuestro caso Google Places. 
5. Fases del proyecto


•	Planificación de requisitos: Antes de empezar con el desarrollo de la aplicación debíamos tener claro que requisitos mínimos tendría que tener nuestra App, y entre ellos fundamentalmente seria la base de datos e implementar un mapa. Más adelante nos irían saliendo más, pero estos teníamos que tenerlos claros para comenzar a desarrollar.

•	Diseño de la arquitectura de software: Una vez teníamos claros que requisitos mínimos tendría que tener nuestra aplicación, debíamos investigar cómo llevarlos a cabo y que herramientas existían para poder desarrollarlos. Para la base de datos elegimos la plataforma que ofrece Firebase sobre todo por la facilidad de integrarlo en nuestro proyecto y en nuestro IDE Android Studio, tanto como el no tener que configurar un servidor por nosotros mismos.

•	Diseño de la base de datos Firebase: Una vez elegido Firebase como base de datos de nuestro proyecto, era la hora de buscar información y entender el majeo de Firebase.

•	Diseño de la aplicación Android: Para el diseño de la App nos hemos apoyado mucho en la guía de diseño y componentes que nos ofrece Material Design.

•	Desarrollo de la aplicación Android: Una vez tenido claro todo lo anterior, era hora de ponerse a desarrollar la App, luchando con todas las dificultades de que se nos pusieron por delante, sobre todo la espinita que se nos queda de no hacer conseguido implementar un chat por la falta de tiempo que teníamos.

•	Lanzamiento y testeo de la aplicación: Finalmente y después de tantas horas utilizadas para el desarrollo tocaba el testearla y ver que fallos tenía nuestra app y como poder eliminarlos 
 
6. Base de datos (Firebase)

Uno de los grandes retos que hemos tenido que superar ha sido la base de datos, ya que elegimos una base de datos noSQL, llamada Firebase.
Esta plataforma fue creada por Google en 2014 para el desarrollo de aplicaciones móviles y aplicaciones web. Es una plataforma ubicada en la nube, integrada con Google Cloud Platform, que usa un conjunto de herramientas para la creación y sincronización de proyectos que serán dotados de alta calidad, haciendo posible el crecimiento del número de usuarios y dando resultado también a la obtención de una mayor monetización.
Firebase dota a sus usuarios de una gran documentación para crear aplicaciones usando esta plataforma. Aparte de esto, ofrece soporte gratuito mediante correo electrónico para todos sus usuarios, y además sus desarrolladores participan en plataformas como GitHub, como más tarde explicaremos.

6.1 Implementación Firebase en app Android

Para implementar Firebase en la aplicación hay que seguir unos pasos que los explicamos a continuación. Los requisitos previos son los siguientes:
•	Instalación de Android Studio o actualizar a su versión más reciente.
•	Se orienta al nivel de API 16 (Jelly Bean) o una versión posterior.
•	Usa Gradle 4.1 o una versión posterior.
•	Implementación de com.android.tools.build:gradle 3.2.1 o una versión posterior
•	compileSdkVersion 28 o una versión posterior
•	Configura un dispositivo físico o utiliza un emulador para ejecutar tu app.
Los emuladores deben usar una imagen que cuente con Google Play.
•	Accede a Firebase con tu Cuenta de Google.
 

Una vez completados los requisitos previos debemos conectar la aplicación con nuestro proyecto de Firebase. Para ello lo primeros que debemos hacer es crear un proyecto de Firebase.
Nos iremos a Firebase (google.com), para crear un nuevo proyecto tenemos que presionar el botón Comenzar.

 

Debemos ponerle un nombre al proyecto y aceptar los términos de Firebase

 
 
Activamos Google Analytics para Firebase ya que proporciona informes ilimitados y gratuitos de hasta 500 eventos diferentes. El SDK captura de manera automática ciertos eventos clave y propiedades del usuario; además, puedes definir tus propios eventos personalizados a fin de medir factores particularmente importantes.














Y configuramos Google Analytics para nuestro país.

Una vez creado el proyecto nos llevará a la pantalla principal de Firebase donde podremos encontrar todos los servicios de la plataforma y donde podremos enlazar el proyecto con nuestra app en Android Studio haciendo clic en el icono de Android situado en el centro de la pantalla.

 
Para ello nos pedirán dos datos obligatorios: el nombre del paquete Android y un apodo.
Para saber el nombre del paquete de Android tenemos que acceder al build.gradle de nuestra app y cogeremos el dato “applicationId” para utilizarlo en Firebase.










Y ponemos los datos que hemos recogido en Firebase, hacemos clic en Registrar aplicación 

 













Nos pedirá que descarguemos el archivo google-services.json y que lo implementemos en la carpeta app de nuestro proyecto






El siguiente paso es verificar que nuestra aplicación tiene implementados los servicios de Google. Para ello vamos otra vez a build.gradle a nivel de proyecto de nuestra aplicación y tiene que tener estas líneas















Y en nuestro build.gradle a nivel de aplicación debemos poner estas líneas 









Por último tenemos que sincronizar en build.gradle a nivel de proyecto para que ejecute los cambios

 
6.2 Servicios de Firebase utilizados

Firebase tiene muchos servicios disponibles, pero en nuestro caso hemos utilizado 3 servicios: Firebase Authentication, Firebase Storage y Firebase Cloud Firestore.

6.2.1 Firebase Authentication 

La mayoría de las apps necesitan identificar a los usuarios. Conocer la identidad de un usuario permite que una app guarde sus datos en la nube de forma segura y proporcione la misma experiencia personalizada en todos los dispositivos del usuario.
Firebase Authentication proporciona servicios de backend, SDK fáciles de usar y bibliotecas de IU ya elaboradas (en GitHub) para autenticar a los usuarios en tu app. Admite la autenticación mediante contraseñas, números de teléfono, proveedores de identidad federada populares, como Google, Facebook y Twitter, y mucho más, pero en nuestro caso implementamos únicamente registro mediante email y contraseña y Google.
Para empezar, tenemos que habilitar la opción de autentificación por correo en Firebase, para ello vamos a ir al servicio Authentication y habilitamos las opciones que vayamos a utilizar para registrarnos.

 
Una vez habilitados tendremos que implementar en nuestro build.gradle a nivel de proyecto la dependencia de Firebase Auth.






Existen múltiples métodos para usar el servicio Authentication, los más usados sirven para crear usuarios, registrar usuarios o para cerrar sesión.

Crear usuario


Registrar usuario
 

Cerrar sesión










6.2.2 Firebase Storage

Cloud Storage para Firebase es un servicio de almacenamiento de objetos potente, simple y rentable construido para la escala de Google. Los SDK de Firebase para Cloud Storage agregan la seguridad de Google a las operaciones de carga y descarga de archivos para tus apps de Firebase, sin importar la calidad de la red. Puedes usar nuestros SDK para almacenar imágenes, audio, video y otros tipos de contenido generado por el usuario. En el servidor, puedes usar Google Cloud Storage para acceder a los mismos archivos.
En este servicio guardamos imágenes de logos de BBSitter y las imágenes de nuestros usuarios para que sea mucho más intuitiva y eficaz la recogida de datos al estar todo dentro de Firebase.
Para comenzar a cargar imágenes en Firebase Storage tenemos de implementar su dependencia.
 




 
En nuestra aplicación la utilizamos con el método putFile() para que suba la imagen a nuestro Storage. Lo hacemos de esta forma:


Al guarda la imagen de esta forma se crea una referencia y una URL de esa imagen
 
Con Firebase Cloud Firestore accederemos a esa URL para poder utilizarla dentro de la aplicación.
 
6.2.3 Firebase Cloud Firestore

Cloud Firestore es una base de datos flexible y escalable para la programación en servidores, dispositivos móviles y la Web desde Firebase y Google Cloud Platform. 
Al igual que Firebase Realtime Database (versión anterior de base de datos de Firestore), mantiene tus datos sincronizados entre apps cliente a través de agentes de escucha en tiempo real y ofrece asistencia sin conexión para dispositivos móviles y la Web, por lo que puedes compilar apps con capacidad de respuesta que funcionan sin importar la latencia de la red ni la conectividad a Internet.
Las funciones clave más importantes de Firebase Cloud Firestore son las siguientes:

•	Flexibilidad
El modelo de datos de Cloud Firestore admite estructuras de datos flexibles y jerárquicas. Almacena tus datos en documentos, organizados en colecciones. Los documentos pueden contener objetos anidados complejos, además de subcolecciones.

•	Consultas expresivas
En Cloud Firestore, puedes usar consultas para recuperar documentos individuales específicos o para recuperar todos los documentos de una colección que coinciden con los parámetros de la consulta. 
Las consultas pueden incluir varios filtros en cadena y combinar los filtros con criterios de orden. También se indexan de forma predeterminada, por lo que el rendimiento de las consultas es proporcional al tamaño de tu conjunto de resultados, no del conjunto de datos.

•	Actualizaciones en tiempo real
Al igual que Realtime Database, Cloud Firestore usa la sincronización de datos para actualizar los datos de cualquier dispositivo conectado. 
Sin embargo, también está diseñado para ejecutar consultas de recuperación únicas y sencillas de manera más eficiente que en Realtime Database.

•	Diseñado para ajustarse a la escala de la aplicación
Cloud Firestore te ofrece lo mejor de la poderosa infraestructura de Google Cloud Platform: replicación automática de datos multirregión, garantías de coherencia sólida, operaciones atómicas por lotes y asistencia real sobre transacciones.
Cloud Firestore sirve para controlar las cargas de trabajo de las bases de datos más complejas de las apps más grandes del mundo. Que no es nuestro caso porque nuestra aplicación es pequeña, pero podría ser más grande en un futuro.
 
Para poder empezar a utilizar esta potente base de datos NoSQL debemos implementar su dependencia en el build.gradle de nuestro proyecto
 




Una vez implementada la dependencia podemos escribir datos en Firestore desde nuestra aplicación creando un Mapa de datos y utilizando el método .add() de esta manera:













También podemos leer datos de Firestore desde nuestra aplicación. Para ello utilizaremos el método .get()
 

 
6.2.4 Consultas en la base de datos Firebase Firestore

Cloud Firestore proporciona una potente función de consulta para especificar qué documentos deseas recuperar de una colección o de un grupo de colecciones. Estas consultas también se pueden usar con get() o addSnapshotListener().
Para empezar, tenemos que llamar a la clase CollectionReference.






Esta consulta es lo más simple que podemos hacer, accede a la colección de la base de datos llamada “usuarios” y nos saca todos los documentos que tenga dentro.

Podemos hacer consultas más complejas como estas:



También tenemos la opción de crear consultas compuestas, como si de un AND se tratara.

 
6.2.5 Modelo de datos

Colección Usuarios
 

Colección Familias

Colección hijos












 

Colección Anuncios
Colección Canguro


Como podemos observar el modelo de datos es no relacional. Cada colección es una clase en nuestro proyecto.

Clase Usuario








Clase Familia









Clase Anuncio
 

 

Clase Canguro
 
 
7. Casos de Uso

Tenemos en cuenta unos requisitos claves para la utilización de nuestra aplicación. Queremos que el usuario pueda tener una experiencia lo más rápida y eficiente posible, para ello el usuario debería poder hacer estos pasos:

•	Usuario
o	Verificación de email
o	Creación de perfil
o	Cambiar contraseña

•	Familia
o	Crear un anuncio
o	Visualizar los canguros disponibles filtrados
o	Visualizar mapa con canguros
o	Llamar y mandar un email a los canguros
o	Añadir Hijos
o	Contactar con Soporte
o	Eliminar Anuncios
o	Eliminar Hijos
o	Eliminar su perfil
o	Editar su perfil
o	Cerrar sesión

•	Canguro
o	Creación de perfil con sus datos
o	Visualizar anuncios de las familias
o	Editar su perfil
o	Eliminar su perfil
o	Contactar con Soporte
o	Cerrar sesión 

Una vez establecido los requisitos que queremos que tenga nuestra aplicación procedemos a hacer los casos de uso.

 
En esta pantalla podemos hacer tres cosas: Identificación, Registro y cambio de contraseña.
Cuando te identificas puede ser que te equivoques de contraseña, por eso tenemos un control de validación de campos, tanto para errores, para existencia de usuarios en Firebase como si el email esta verificado o no (Esto lo explicamos en el apartado de usabilidad).

 

 


Una vez creado el perfil de familia tenemos diversas opciones dentro de la aplicación.
Podemos ver a todos los canguros tanto en una lista como en un mapa con sus marcadores y dentro de ellos podemos ver su perfil y contactar con ellos tanto por email como por teléfono.
En mi perfil podemos editar nuestro perfil, añadir hijos y eliminar nuestro perfil
En Acerca de nosotros podrá ver nuestros datos y nuestro GitHub

 

 

En el perfil de canguro podemos ver los anuncios de las familias en forma de lista. Cuando nos metemos en el detalle del anuncio podemos contactar con la familia por email y podemos ver su perfil para ver sus datos y sus hijos
En Mi perfil podemos modificar nuestro perfil por si tenemos más pluses o más idiomas y podemos eliminar nuestro perfil
En soporte podemos contactar con el soporte de nuestra aplicación por su tuviera algún problema
En Acerca de nosotros podrá ver nuestros datos y nuestro GitHub





8. Diseño de la app

En Android para diseñar las interfaces de usuario de nuestras aplicaciones tenemos, por un lado, las propias actividades para crear cada una de las diferentes pantallas, y por otro lado las vistas para crear el contenido visual de cada actividad. Para este contenido visual utilizamos archivos .xml que serán los encargados de dar el aspecto visual de las pantallas y están conectados con los archivos .java que serán los encargados de hacer el trabajo lógico de la aplicación.
Casi todos los elementos gráficos de nuestro proyecto irán en la carpeta res del proyecto, desde los layout mencionados anteriormente hasta las imágenes, videos, animaciones, menús, colores… A continuación, os mostramos una imagen de la carpeta res de nuestro proyecto y más adelante detallaremos un poco cada carpeta que contiene y para qué sirve.

8.1. Carpeta res                                                          
Como hemos mencionado anteriormente es la carpeta que contiene los recursos usados por la aplicación. Las carpetas que contiene en su interior serán las que contengan los diferentes recursos en la mayoría referidos al tema gráfico.
Drawable
En esta carpeta se almacenan los ficheros de imágenes (JPG o PNG) y descriptores de imágenes en XML. 
Mipmap
En una carpeta guardaremos el icono de la aplicación y también aquellas imágenes en las que queramos que s densidad gráfica sea la correcta dependiendo el dispositivo en el que sea utilizada la aplicación. En nuestro caso no hemos utilizado esta carpeta dejando nuestro logo guardado en la carpeta Drawable.
Menu
Ficheros XML con los menús de cada actividad. En esta carpeta tendremos los menús utilizados del Navigation Drawer de nuestra aplicación, tanto el de perfil Familia como el de perfil Canguro y que será el encargado de vincular el ítem del menú pulsado con el fragment correspondiente a dicho ítem.
Además, tenemos otro menú a la derecha de la toolbar que nos servirá para que el usuario puede cerrar sesión.

Values
También utilizaremos ficheros XML para indicar valores usados en la aplicación, de esta manera podremos cambiarlos desde estos ficheros sin necesidad de ir al código fuente. 
En colors.xml hemos definido los tres colores primarios de la aplicación, además de otros colores que hemos necesitado para ello. Esto facilita el tener que poner o saber el color que quieras utilizar en RGB.
En dimens.xml, se pueden definir dimensiones como el margen por defecto o el ancho de los botones. 
En el fichero strings.xml, aquí se todas las cadenas de caracteres de la aplicación. Creando recursos alternativos resultará muy sencillo traducir una aplicación a otro idioma. Nosotros en esta carpeta no hemos usado strings para traducir simplemente Android Studio ha creado strings automáticos que ha ido necesitando cuando hemos introducido algún elemento a la app.
Finalmente, en styles.xml, podrás definir los estilos y temas de tu aplicación. En nuestro caso hemos tenido que definir el theme necesario para poder usar elementos de Material Design, además de tener que modificar los colores en el alert Dialog de Material, ya que no mostraba colores oscuros que no queríamos tener.










Anim
Contiene ficheros XML con animaciones de vistas (Tween). En nuestro caso desarrollamos un par de animaciones, pero finalmente optamos por poner animaciones que tiene Android propiamente y que sólo con mencionarlas nos valdría y no tendríamos que desarrollarlas. Estas animaciones las usamos cuando una familia pincha en un ítem de la lista canguros y le aparece el detalle del perfil de canguro.
Raw
En esta carpeta metemos aquellos ficheros adicionales que no se encuentran en formato XML. Como son animaciones Lottie y videos, en nuestro caso.








Navigation
Esta carpeta la crea Android al crear una activity Navigation Drawer y es la encargada de alojar los archivos .xml, los cuales serán los encargados de alojar los diferentes fragments de los que se constituye un Navigation Drawer relacionado con el menu del propio Navigation Drawer. El código siguiente es el encargado del Navigation de nuestro perfil canguro:

 
Layout 
Por último, no nos podíamos olvidar de esta carpeta, ya que podría ser la carpeta más importante en cuanto a diseño, y la encargada de colocar los elementos visuales de nuestra aplicación.
Contiene ficheros XML con vistas de la aplicación. Las vistas nos permitirán configurar las diferentes pantallas que compondrán la interfaz de usuario de la aplicación. Se utiliza un formato similar al dom HTML usado para diseñar páginas web. En la imagen siguiente os mostramos algunos de los layout utilizados en BBSitter.














Un Layout es el contenedor principal que define el orden y secuencia en que se organizarán los widgets en nuestra actividad. Existen varios tipos de Layout (LinearLayout, RelativeLayout, ConstraintLayout, FrameLayout…)
Básicamente en nuestra aplicación utilizamos dos tipos de layout como son los LinearLayout (maquetación en línea de las vistas ya sean verticalmente u horizontalmente), ConstraintLayout y los RelativeLayout (similares a los LinearLayout pero nos permite más flexibilidad a la hora de colocar los objetos con la posibilidad de simular diferentes capas en las que están situadas las vistas). 
Dentro de estos Layouts colocaremos todos aquellos componentes que queremos que aparezcan en nuestras activities o fragments. A estos componentes se les llama vistas (views) y existen muchísimos tipos de vistas como Buttom, TextView, EditText, ListView, ImageView… Todos estos componentes se pueden personalizar dándole el aspecto visual que queramos como tamaños, colores, etc  y para ello el conocimiento en aspectos gráficos, visuales y maquetación son esenciales para darle un aspecto profesional a cualquier diseño de aplicación. Ante esto no todos los desarrolladores tienen tales conocimientos y puede que la aplicación pueda quedar un poco sobria o con aspecto desfasado a las tendencias de diseño que existen en el momento de desarrollo. Para esto apareció Material Design.



8.2 Material Design

8.2.1 ¿Qué es Material Design?             

Históricamente Android ha tenido diseño Holo, a partir de ahora el nuevo estilo y la tendencia en Android es Material Design. Material Design es un concepto, una filosofía, unas pautas enfocadas al diseño utilizado en Android, pero también en la web y en cualquier plataforma. 

Material Design es un sistema de diseño creado por Google con el fin de ser capaz de adaptarse a múltiples dispositivos y plataformas.
La idea de Google es la de incorporar este sistema de coherencia estética y funcional de forma progresiva a todos sus productos, incluyendo las aplicaciones web y móviles, con la finalidad de crear una experiencia similar en todas sus plataformas.
Los componentes de material son bloques de construcción interactivos para crear una interfaz de usuario e incluyen un sistema de estados integrado para comunicar los estados de enfoque, selección, activación, error, desplazamiento, presión, arrastre y deshabilitación. Todo esto no sólo favorece el tener un aspecto visual tremendamente profesional, sino que también facilita el controlar la usabilidad para que el usuario sepa que está ocurriendo en todo momento.

Material Design tiene bibliotecas de componentes que están disponibles tanto para Android, iOS, Flutter como para web. No todos los componentes de Material Design están disponibles para cada una de estas plataformas, nosotros obviamente hemos usado la documentación y componentes disponibles para Android.



8.2.2 Implementación de Material Design en nuestra aplicación.

Para usar Material Design en Android tendremos que añadir la dependencia de Material en el archivo build.gradle de nuestro proyecto:






Para poder usar los componentes de Material tuvimos que cambiar el theme de nuestra app a un theme de Material:



8.2.3 Componentes de Material Design utilizados en BBSitter

Os vamos a mostrar algunos de los componentes de Material que hemos usado de BBSitter, obviamente existe una variedad muy grande de componentes que no hemos utilizado, pero los usados favorecen mucho
el diseño de la app y también la usabilidad del usuario. 




























 
8.3 Animaciones Lottie

No es ninguna sorpresa si os decimos que las micro interacciones son tendencia. Desde hace años las pequeñas animaciones copan las aplicaciones y las webs con el fin de mejorar su interacción, su usabilidad y, sobre todo, alegrarnos la vida haciendo mucho más agradable la experiencia de usuario.
Desde BBSitter valoramos mucho el introducir estos componentes ya que hacen nuestra aplicación mucho más llamativa e interesante para el usuario y gracias a su sencilla implementación nos permite tener componentes en nuestra app, muy interesantes.

8.3.1 ¿Qué es Lottie?

Lottie es una herramienta muy potente creada por AirBnb con la que implementar animaciones y microinteraciones para nuestras webs o apps. 
Lottie es una librería capaz de reproducir animaciones en tiempo real y de forma nativa en web, Android e iOS. De esta forma, Lottie facilita el desarrollo e implementación de animaciones para diferentes plataformas, integrándose en Android e iOS permitiendo cargar animaciones como si fueran cualquier otro contenido estático. Además, Lottie las hace escalables y fáciles de adaptar a cualquier dispositivo y capaces de reaccionar a los eventos provocados por el usuario.
Lottie dispone incluso de su propia app en la que podrás comprobar cómo se reproducen tus animaciones sin necesidad de crear un proyecto desde cero.

8.3.2 ¿Cómo trabaja Lottie?

Lottie trabaja con animaciones de After Effects exportadas gracias al plugging Bodymovin, capaz de convertirlas a formato JSON. De esta forma, una animación creada en el potentísimo programa de Adobe puede presentarse de forma nativa en cualquier dispositivo Android e iOS.
Si eres capaz de desarrollar una animación solo hay que instalar el plugging Bodymovin en nuestro After Effects, crear la animación y exportarla en el formato adecuado según sea para web, Android o iOS. After Effects nos exportará un JSON con el que trabajaremos en el entorno en el que vayamos a implementar la animación.


8.3.3 Banco de recursos lottiefiles.com

Si no te ves capaz de crear una animación como hemos mencionado antes, lo más fácil es acceder a la página de recursos lottie lottiefiles.com. Donde podemos descargar numerosas animaciones de manera gratuita y por supuesto también de pago. 


8.3.4 Implementación de recursos Lottie

En caso de que quieras incluir animaciones con Lottie en un proyecto Android, estos son los pasos que tendrás que tener en cuenta:

1.  Agregar al build.gradle la librería Lottie:














2.  Introducir el archivo JSON de la animación en la carpeta raw del proyecto. Hay que tener en cuenta que tendrás que crear la carpeta raw si no la has creado, ya que no viene creada por defecto.



3.  Introducimos el componente en nuestro layout de la siguiente forma: 





















Cómo veis, trabajamos con el componente com.airbnb.lottie.LottieAnimationView al que le añadimos: 
1.	En lottie_fileName el nombre del archivo JSON para Lottie. 
2.	En lottie_loop si habilitamos (true) o no (false) la animación en bucle, si no ponemos nada por defecto será false.
3.	En lottie_autoPlay si habilitamos (true) o no (false) la reproducción automática de la animación. 

En la documentación de Lottie podréis encontrar más atributos para controlar tu animación. 

4.  Por último, configuramos nuestro código en el activity o fragment dentro del onCreateView para poder mostrar la animación utilizando los diferentes métodos para ello: 







 








8.4 Identidad visual de BBSitter

Toda marca necesita una identidad y BBSitter no iba a ser menos. El conjunto de elementos gráficos que ayudan a la diferenciación, en general se compone de logotipo, tipografía, cromática… en definitiva, los aspectos visuales o expresión gráfica de una marca. La identidad visual es el conjunto de elementos que tangibilidad la propuesta de valor y la personalidad de una marca. 

8.4.1 Logo
Sin duda, uno de los elementos más reconocibles en las grandes marcas es su logo, y adquiere vida propia conforme se posiciona en el público. 



8.4.2 Tipografía

Elegir una fuente distintiva (o un grupo de fuentes) que realmente marquen la diferencia y que transmitan la personalidad de tu marca, es esencial para diferenciarse del resto. En nuestro caso optamos por la tipografía que nos usa Android por defecto, Robocop.












8.4.3 Selección de color

Los tonos que utilices en tu sitio web, en tu publicidad y hasta en tus empaques deben estar orientados a tu audiencia y a las emociones que quieres despertar en ella. 


8.4.4 Imágenes, iconos….

Decidir cuál es el enfoque que prefieres en las imágenes que aparezcan en tu app o que estén relacionadas con tu marca hará que el usuario la reconozca y se sienta reflejado en ella. Para BBSitter no hemos utilizado grandes librerías de iconos, ni imágenes… simplemente hemos convertido a vector (ic_nombre.xml) los iconos que nos ofrece Android, y alguna descarga de icons icons, retocados con Photoshop.



 
 
9. Google Maps Platform

Desde un principio la idea principal de BBSitter era poder ver en un mapa todos aquellos canguros que estaban cercanos a una familia que estaba en búsqueda de canguro y que, de una manera rápida, pudiese ver a los más cercanos de un vistazo. Para después poder pinchar en aquel que eligiese y poder ver la información más esencial de dicho canguro, incluso pudiendo pinchar en el para ver sus detalles. 
Para poder implementar esta funcionalidad, obviamente teníamos que echar mano de Google Maps y la mejor manera de utilizarlo para poder desarrollar algo así, era con las APIs que ofrece Google Maps Platform.

9.1 Credencial clave API de Google Maps Platform

Google actualizó Google Maps para implementarlo en sitios web de cara a mejorar su rendimiento en dispositivos móviles. Permitiendo una carga mucho más rápida y adaptando sus funcionalidades según las características de los smartphones actuales. 
Para emplear esta nueva API se introdujeron cambios en el servicio para desarrolladores, ya que estas novedades traen consigo limitaciones en su versión gratuita. Una de las principales es la introducción de distintos tipos de clave necesarios en tu App para que la aplicación funcione correctamente. 
Para poder disfrutar de las APIs que ofrece Google Maps Platform, tendremos que tener una clave API (es un tipo de autentificación para el uso de aplicaciones con Google Maps JavaScript API) que debes solicitar, vincular e insertar en tu proyecto de App.  
9.2 Obtener clave API para nuestro proyecto

Desde una cuenta de Google, accederemos a la console de Google Maps Platform y en el apartado de Credenciales, pincharemos en Nueva Clave API. 
Tendremos o nos pedirá que nos creemos una cuenta gratuita y meter datos de una tarjeta de crédito para poder hace futuros cargos en caso de que el proyecto siga adelante en un futuro, sino la cuenta gratuita durará 3 meses ( a nosotros a día de hoy no nos han cobrado y queda todavía un mes de vigencia de la cuenta gratuita, ya veremos en un futuro, aunque con dar de baja al servicio ya valdría).
Crearemos una clave API que ira asociada a nuestro proyecto, la cual tendremos que incluir en nuestro proyecto de Android una vez creemos un Map Fragment. 



Una vez tengamos nuestra clave generada tendremos que activar las APIs que necesitemos de aquellas que nos ofrece Google Platform. En nuestro caso nosotros tenemos dadas de alta las siguientes, pudiendo ver las métricas de uso de dichas APIs:

9.3 Implementar Google Maps y Google Places en nuestro proyecto

Una vez obtenida la clave y activadas las APIs necesarias, iremos a nuestro proyecto a implementar Google Maps y Google Places en nuestro  build.gradle.




Deberemos también poner esto en nuestro manifest para poder hacer uso de la geolocalización de nuestro dispositivo así como decirle cual es nuestra clave Api de Google Maps.





 
Para mostrar el mapa de Google Maps donde colocaremos los marcadores de los canguros tendremos que crear un Google Maps Fragment, una vez hecho esto nos creará un archivo xml con este nombre google_maps_api.xml, en el que nos explica brevemente como obtener la clave Api para poder usar Google Maps.




Google Maps Fragment
Android nos frece un fragment Maps de Google en el cual desarrollaremos la lógica para obtener nuestra ubicación y colocar nuestro marcador. También haremos lo mismo recorriendo nuestra base de datos para obtener las ubicaciones de todos los canguros y poder colocar su marcador en el Mapa.



Google Places
Google Places es una Api que debemos activar en nuestra cuenta de Google Platform y que nosotros hemos utilizado en nuestro proyecto para poder obtener un autocompletado de direcciones cuando el canguro o la familia escriben su dirección para poder registrarla.

Mientras el usuario va escribiendo su dirección, la actividad de autocompletado va generando direcciones acordes al texto escrito por el usuario, el cual podrá elegir aquella que necesita en cuanto salga en la lista de direcciones.













 
10. GitHub

GitHub es un sistema de gestión de proyectos y control de versiones de código, así como una plataforma de red social diseñada para desarrolladores que permite trabajar en colaboración con otras personas de todo el mundo, planificar proyectos y realizar un seguimiento del trabajo.
GitHub es también uno de los repositorios online más grandes de trabajo colaborativo en todo el mundo.
¿Qué es un control de versiones? Cuando los desarrolladores hacen un nuevo proyecto, siempre continúan haciéndole modificaciones al código. Incluso después de la puesta en marcha de los proyectos, todavía necesitan actualizar las versiones, corregir errores, agregar nuevas funciones, etc.
El sistema de control de versiones ayuda a registrar los cambios realizados al código. Aún más, registra quién realizó los cambios y puede restaurar el código borrado o modificado.
No hay códigos sobrescritos ya que Git guarda varias copias en el repositorio.
Esta parte del proyecto es la que más nos ha costado entender ya que esto no lo vimos en clase y además es un poco lioso. Finalmente entendimos el proceso de enviar datos a GitHub y de recoger datos después de vernos innumerables videos tutoriales.
Su funcionamiento es el siguiente:

Paso 1: Crear un repositorio en GitHub.	
Para ello debemos registrarnos en GitHub.com, buscar la pestaña “Repositories” y hacer clic en “New”

 

Rellenamos el nombre de nuestro repositorio, le ponemos una descripción y le damos a “Create repository” y nos saldrá en nuestra pantalla principal un repositorio vacío donde debemos rellenar con los datos de nuestra app.


 
Paso 2. Clonar repositorio en nuestro pc
En este paso es necesario descargarse Git mediante este enlace.
Una vez instalado en nuestro pc nos vamos a cualquier carpeta y presionamos el botón derecho para ver las opciones y seleccionamos “Git Bash Here”










Se nos abrirá la consola de Git y es el momento de clonar nuestro repositorio. Para ello necesitamos la URL de nuestro repositorio que lo encontraremos en nuestro repositorio de GitHub

 

En la consola de Git debemos poner el siguiente comando






Esto nos permitirá tener todo lo que haya en GitHub en nuestro pc y viceversa. Con lo cual el siguiente paso es crear nuestro proyecto de Android Studio dentro de esa carpeta para poder subirlo.
Una vez creado el proyecto de Android para poder subir el proyecto a GitHub tendrá que ser el siguiente comando











En cambio, si lo que queremos es recoger los datos de GitHub tendremos que hacer lo siguiente














11. Usabilidad de nuestra app

La usabilidad de una App es un tema muy importante que hay que tratar con detalle para hacer de la experiencia del usuario algo cómodo, sencillo e intuitivo. Para ello hay numerosas maneras de hacer que un usuario no deje de usar los servicios de nuestra app solo porque no sabe que está pasando en algunos momentos cuando ha hecho clic en algún botón, cargar una imagen, etc.

Ahora os detallaremos algunas acciones que hemos implementado y creemos que son buenas para que el usuario tenga una buena usabilidad:

•	Menos pasos a la hora de registrarse y facilidad de cambiar contraseña

La mayoría de las personas no quieren teclear demasiado en su celular. Sobre todo si existe la opción de llenar datos usando Facebook, Google + , Twitter u otra forma. También hay momentos que para resetear la contraseña se hace muy pesado el poder hacerlo de una manera sencilla.
•	Una sola activity, pocos campos y frases sencillas para crear perfil
Entre menos pasos, páginas, botones y campos tenga que ir el usuario, más contento estará.

•	Mostrar Toast, Snackbars o comentarios en EditText para errores y acciones
Mostraremos mensaje de diferentes tipos para avisar al usuario tanto para cuando se equivoca o hay un error, como cuando realiza una acción que conlleve algún cambio.

•	Mostrar CardView de canguro a la hora de hacer clic en un marcador del mapa
Mostraremos mensaje de diferentes tipos para avisar al usuario tanto para cuando se equivoca o hay un error, como cuando realiza una acción que conlleve algún cambio.

•	PageView de bienvenida una vez creado el perfil
Una vez creado el perfil, ya sea canguro o familia, hemos creado un pageView que el usuario podrá deslizar y que nos informará de lo que el usuario puede hacer con la app.

•	Animaciones de carga de datos, cambios o creación
Cuando nosotros como usuarios pinchamos en algún botón de cualquier aplicación de las que usamos, esperamos saber que está ocurriendo o que ha pasado cuando hicimos clic en él. Para esto hemos creado tanto ProgressBar, como Toast, como animaciones que nos dirán que ha ocurrido.

12. Mejoras de la aplicación


Nuestra aplicación la pensamos con idea de que en un futuro pudiera ampliarse para que fuera más completa. Las mejoras que tenemos en mente son:
•	Usuario

o	Inicio de sesión con otros proveedores (Google, Facebook,…)
o	Cambiar correo electrónico 


•	Perfiles

o	Crear chat 
o	Calificaciones a los canguros
o	Comentarios a los canguros
o	Filtros personalizables
o	Filtro por distancia en perfil de familia
o	Calificación a las familias
o	Comentarios a las familias


•	Base de datos

o	Mejorar la eficiencia de la base de datos sin repetir datos
o	Seguridad en las contraseñas, teléfonos, emails…
o	Compresión de las imágenes para cargas más rápidas 

 
13. Bibliografía

Android Studio 
Firebase 
Adobe Photoshop 
Java (lenguaje de programación) 
Documentación Firebase 
Firebase Authentication 
Autentica con Firebase mediante un vínculo de correo electrónico en Android 
Autentica mediante el Acceso con Google en Android 
APIs de geolocalización  |  Google Maps Platform  |  Google Cloud
Mapas personalizados  |  Google Maps Platform  |  Google Cloud
Places  |  Google Maps Platform  |  Google Cloud
Kotlin Programming Language (kotlinlang.org)
Cloud Firestore  
Primeros pasos con Cloud Firestore 
Modelo de datos de Cloud Firestore
Elige una base de datos: Cloud Firestore o Realtime Database
SDK y bibliotecas cliente
Elige una estructura de datos 
Agrega datos a Cloud Firestore
Transacciones y escrituras en lotes
Borra datos de Cloud Firestore 
Administra Cloud Firestore con Firebase console
Obtén datos con Cloud Firestore
Obtén actualizaciones en tiempo real con Cloud Firestore 
Realiza consultas simples y compuestas en Cloud Firestore
Cloud Storage 
Comienza a usar Cloud Storage en Android
Crea una referencia de Storage en Android
Sube archivos en Android
Descarga archivos en Android
Borra archivos en Android
Material Design
Design - Material Design
Components - Material Design
Develop - Material Design
LottieFiles 
Iconos
Carbon | Codigo
Download Android Studio and SDK tools  |  Android Developers
Android Studio release notes  |  Android Developers
SDK Tools release notes  |  Android Developers
Emulator release notes  |  Android Developers
Java 8+ APIs available through desugaring  |  Android Developers
Stack Overflow en español
GitHub - jesusgm94/Proyecto-BBSitter
Git - gittutorial Documentation (git-scm.com)
Instant Chat Messenger with Cloud Firestore | Eric Decanini
This Person Does Not Exist
MoureDev by Brais Moure - YouTube
Firebase - YouTube
Fireship - YouTube
FalconMasters - YouTube



