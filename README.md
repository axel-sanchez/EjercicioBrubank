EjercicioBrubankApp
Éste es el proyecto prueba de Brubank, con esta app se puede ver un listado de peliculas o series y acceder para ver sus detalles.

Experiencia de usuario
Este proyecto contiene las siguientes características:

La pantalla principal donde se ve un listado de peliculas o series populares o las que desee buscar el usuario.
Una vista con una película específica con su descipción completa.
Capturas de pantalla
 ![WhatsApp Image 2022-02-18 at 6 15 41 PM (1)](https://user-images.githubusercontent.com/51034538/154771702-00a8154b-4d99-4752-bc20-28b9922ac360.jpeg)

![WhatsApp Image 2022-02-18 at 6 15 41 PM](https://user-images.githubusercontent.com/51034538/154771706-62057939-ebda-471c-ad87-e7d113f6c975.jpeg)

Guía de implementación
Trabajo con una API externa que me devuelve las películas, la url de la documentación es https://developers.themoviedb.org/3/getting-started/introduction
Arquitectura
Este proyecto implementa el patrón de arquitectura MVVM y sigue buenas prácticas de Clean Architecture para hacer un código más independiente, mantenible y sencillo.

Capas
Presentación: Viewmodels, Fragments y Activities
Data: contiene los modelos, la implementación del repositorio y los sources donde se conecta con la api y a room
Domain: contiene los casos de uso y la definición del repositorio
Este proyecto usa ViewModel para almacenar y manejar datos, así como comunicar cambios hacia la vista.

Administrador de solicitudes: Retrofit
Este proyecto utiliza Retrofit para mostrar los productos desde una API.

Inyección de dependencia - Koin
Este proyecto utiliza Koin para gestionar la inyección de dependencia.

Guía de instalación
En caso de no tener instalado Android Studio, descargue la última versión estable. Una vez que tenemos el programa instalado vamos a Get from Version Control y vamos a pegar https://github.com/axel-sanchez/EjercicioBrubank.git Una vez hecho eso se va a clonar el proyecto, lo que resta sería conectar un celular y darle al botón verde de Run 'app'
