# Klicalia Ecommerce — Módulo 6: Spring Boot, Login y Roles

Proyecto final del curso: migración del e-commerce Klicalia (originalmente en Jakarta EE, Módulo 5)
a **Spring Boot**, agregando registro de usuarios, autenticación con **Spring Security** y control
de acceso por roles (**CLIENT** / **ADMIN**).

🔗 **Repositorio:** https://github.com/scarlettcindy/klicalia-ecommerce-spring-boot

## Tecnologías utilizadas

- Java 21 / Spring Boot 4.1.0
- Spring MVC + JSP/JSTL (vistas)
- Spring Data JPA (Hibernate) + PostgreSQL
- Spring Security (login, logout, roles, BCrypt, protección CSRF)
- Maven (empaquetado en `.war`)
- JUnit 5 + Mockito + Spring Boot Test (pruebas)

## Requisitos previos

- Java 21 (o superior) instalado
- Maven (o usar el `mvnw` incluido en el proyecto)
- PostgreSQL instalado y corriendo en `localhost:5432`
- Una base de datos creada llamada `ecommerce_Klicalia`

## Configuración

Antes de correr el proyecto, edita `src/main/resources/application.properties` con tus propias
credenciales de PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_Klicalia
spring.datasource.username=postgres
spring.datasource.password=TU_CLAVE_AQUI
```

Las tablas (`categorias`, `productos`, `usuarios`) se crean automáticamente al arrancar la
aplicación gracias a `spring.jpa.hibernate.ddl-auto=update`.

## Cómo ejecutar el proyecto

**Opción 1: Desde Eclipse**
1. Importa el proyecto como Maven existente (`File > Import > Existing Maven Projects`)
2. Clic derecho sobre el proyecto → `Run As > Spring Boot App`

**Opción 2: Desde la terminal**
```bash
./mvnw spring-boot:run
```

La aplicación queda disponible en:
http://localhost:8080

## Empaquetado

Para generar el `.war` ejecutable (corre las pruebas automáticamente antes de empaquetar):
```bash
./mvnw clean package
```
El archivo queda en `target/ecommerce-webapp-m6-0.0.1-SNAPSHOT.war`.

## Rutas y control de acceso por rol

| Ruta | Acceso | Descripción |
|---|---|---|
| `/login` | Público | Formulario de inicio de sesión |
| `/register` | Público | Formulario de registro (crea usuario con rol `CLIENT`) |
| `/catalogo` | Autenticado (CLIENT o ADMIN) | Catálogo de productos, solo lectura |
| `/admin/products` | Solo **ADMIN** | Listado de productos (panel de administración) |
| `/admin/products/new` | Solo **ADMIN** | Formulario para crear un producto |
| `/admin/products/edit` | Solo **ADMIN** | Formulario para editar un producto |
| `/admin/products` (POST) | Solo **ADMIN** | Crear producto |
| `/admin/products/update` (POST) | Solo **ADMIN** | Actualizar producto |
| `/admin/products/delete` (POST) | Solo **ADMIN** | Eliminar producto (baja lógica: `activo = false`) |

Al iniciar sesión, cada usuario es redirigido según su rol:
- **CLIENT** → `/catalogo`
- **ADMIN** → `/admin/products`

Un usuario `CLIENT` que intenta acceder a cualquier ruta `/admin/**` (incluso escribiendo la URL
directamente) recibe un error `403 Forbidden`.

## Registro y validaciones

- El registro exige nombre, apellido, email y contraseña.
- El email debe ser único (validado contra la base de datos); si ya existe, se muestra el mensaje
  "Ese email ya está registrado."
- Todo usuario registrado desde `/register` recibe automáticamente el rol `CLIENT` — no es posible
  crear un ADMIN desde el formulario público.
- Las contraseñas se almacenan siempre encriptadas con **BCrypt**, nunca en texto plano.
- El formulario de productos valida nombre obligatorio, categoría obligatoria y precio mayor a 0,
  mostrando mensajes de error claros en la misma vista si algo falla.

## Pruebas

El proyecto incluye 6 pruebas automatizadas:

**`UsuarioServiceTest`** (pruebas unitarias con Mockito)
- Detecta correctamente si un email ya está registrado
- Detecta correctamente si un email está disponible
- Al registrar un cliente, encripta la contraseña y asigna el rol `CLIENT` automáticamente

**`ProductoControllerSecurityTest`** (pruebas de integración con MockMvc)
- Un usuario sin sesión iniciada es redirigido al login al intentar acceder a `/admin/products`
- Un usuario con rol `CLIENT` recibe `403 Forbidden` al intentar acceder a `/admin/products`
- Un usuario con rol `ADMIN` accede correctamente (`200 OK`) a `/admin/products`

Para correrlas:
```bash
./mvnw test
```

## Crear un usuario ADMIN

Como el registro público solo crea usuarios `CLIENT`, un usuario `ADMIN` debe crearse directamente
en la base de datos:

```sql
INSERT INTO usuarios (nombre, apellido, email, password, rol)
VALUES ('Admin', 'Klicalia', 'admin@klicalia.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN');
```
(La contraseña ya encriptada corresponde al texto plano `admin123`.)

## Estructura del proyecto

src/main/java/com/klicalia/ecommerce_webapp_m6/
├── config/         → Configuración de Spring Security
├── controller/      → Controladores Spring MVC (productos, login, registro, catálogo)
├── model/            → Entidades JPA (Producto, Categoria, Usuario)
├── repository/       → Repositorios Spring Data JPA
├── service/          → Lógica de negocio (ProductoService, UsuarioService)
src/main/webapp/WEB-INF/views/ → Vistas JSP
src/test/java/                 → Pruebas unitarias y de integración