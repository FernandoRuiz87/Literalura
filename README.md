﻿# 📚 Literalura

Una aplicación de consola desarrollada en Java con Spring Boot que permite construir un catálogo interactivo de libros consumiendo la API de Gutendex.

## 🚀 Descripción

LiterAlura es una aplicación de consola que permite:

- Buscar libros por título a través de la API de Gutendex
- Gestionar un catálogo personal de libros y autores
- Consultar información almacenada en base de datos PostgreSQL
- Filtrar libros por idioma
- Buscar autores que estuvieron vivos en un año específico

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **PostgreSQL** como base de datos
- **Jackson** para el manejo de JSON
- **Docker & Docker Compose** para el entorno de base de datos
- **Maven** como gestor de dependencias

## 🏗️ Arquitectura del Proyecto

```
src/main/java/com/fernandoruiz87/literalura/
├── LiteraluraApplication.java          # Clase principal de la aplicación
├── dto/                                # Data Transfer Objects
│   ├── AutorDTO.java                   # DTO para autores
│   ├── LibroDTO.java                   # DTO para libros
│   └── RespuestaApiDTO.java            # DTO para respuestas de la API
├── model/                              # Entidades JPA
│   ├── Autor.java                      # Entidad Autor
│   └── Libro.java                      # Entidad Libro
├── principal/                          # Lógica principal de la aplicación
│   └── Principal.java                  # Menú y control de flujo
├── repository/                         # Repositorios JPA
│   ├── AutorRepository.java            # Repositorio de autores
│   └── LibroRepository.java            # Repositorio de libros
└── service/                           # Servicios
    ├── APIservice.java                # Servicio para consumir APIs
    ├── Conversor.java                 # Conversión de datos JSON
```

## 📋 Prerrequisitos

- **Java 17** o superior
- **Docker** y **Docker Compose**
- **Maven** (incluido en el proyecto con Maven Wrapper)

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/FernandoRuiz87/Literalura
cd literalura
```

### 2. Configurar la base de datos con Docker

```bash
docker-compose up -d
```

Esto iniciará un contenedor PostgreSQL con:

- Puerto: `16254`
- Base de datos: `literalura`
- Usuario: `postgres`
- Contraseña: `password`

### 3. Verificar la configuración

El archivo `application.properties` está configurado para conectarse automáticamente a la base de datos:

```properties
spring.datasource.url=jdbc:postgresql://127.0.0.1:16254/literalura
spring.datasource.username=postgres
spring.datasource.password=password
```

### 4. Ejecutar la aplicación

#### Opción A: Usando Maven Wrapper (Recomendado)

```bash
# En Windows
.\mvnw.cmd spring-boot:run

# En Linux/Mac
./mvnw spring-boot:run
```

#### Opción B: Usando Maven instalado

```bash
mvn spring-boot:run
```

## 💻 Uso de la Aplicación

Al ejecutar la aplicación, se mostrará un menú interactivo con las siguientes opciones:

```
¡Bienvenido a literalura!
Para continuar elija una opción:
    1 - Buscar libro por titulo
    2 - Mostrar libros registrados
    3 - Mostrar autores registrados
    4 - Mostrar autores vivos en un año determinado
    5 - Mostrar libros por idioma
    0 - Salir
```

### 🔍 Funcionalidades Detalladas

#### 1. Buscar libro por título

- Busca libros en la API de Gutendx
- Guarda automáticamente el libro y sus autores en la base de datos
- Evita duplicados de autores

#### 2. Mostrar libros registrados

- Lista todos los libros almacenados en la base de datos local
- Muestra título, autores, idiomas y número de descargas

#### 3. Mostrar autores registrados

- Lista todos los autores almacenados
- Muestra nombre, fechas de nacimiento y fallecimiento

#### 4. Mostrar autores vivos en un año determinado

- Permite ingresar un año específico
- Filtra autores que estuvieron vivos en ese año

#### 5. Mostrar libros por idioma

- Filtra libros por idioma
- Idiomas disponibles: Español (es), Inglés (en)

## 🗄️ Modelo de Datos

### Entidad Libro

```java
- id: Long (PK)
- titulo: String
- cantidadDescargas: Double
- idiomas: List<String>
- autores: List<Autor> (ManyToMany)
```

### Entidad Autor

```java
- id: Long (PK)
- nombre: String
- fechaNacimiento: Integer
- fechaFallecimiento: Integer
- libros: List<Libro> (ManyToMany)
```

## 🌐 API Externa

La aplicación consume la API de **Gutendx** (https://gutendx.com/), que proporciona:

- Catálogo gratuito de libros del Proyecto Gutenberg
- Búsqueda por título, autor, idioma
- Metadatos completos de libros y autores

## 🐳 Docker

El proyecto incluye un `docker-compose.yaml` que configura:

- **PostgreSQL 15** como base de datos
- **Puerto personalizado 16254** para evitar conflictos
- **Volumen persistente** para los datos
- **Variables de entorno** preconfiguradas

Para gestionar la base de datos:

```bash
# Iniciar la base de datos
docker-compose up -d

# Detener la base de datos
docker-compose down

# Reiniciar la base de datos
docker-compose restart

# Ver logs de la base de datos
docker-compose logs db
```

## 🧪 Testing

Para ejecutar las pruebas:

```bash
.\mvnw.cmd test
```

## 📝 Notas Técnicas

- **Gestión de duplicados**: La aplicación verifica si un autor ya existe antes de crearlo
- **Manejo de errores**: Validación de entrada de usuario y manejo de excepciones
- **Pool de conexiones**: Configurado con HikariCP para optimizar las conexiones a la DB
- **Conversión JSON**: Utiliza Jackson para mapear respuestas de la API a DTOs


Este proyecto está bajo la Licencia MIT - ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

Fernando Ruiz - [@fernandoruiz87](https://github.com/fernandoruiz87)

---

⭐ ¡Si te gustó este proyecto, no olvides darle una estrella!
