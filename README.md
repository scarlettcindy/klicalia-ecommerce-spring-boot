# Klicalia Ecommerce — Módulo 6/7: Spring Boot, Login y Roles (Versión Final)

Proyecto final del curso: e-commerce educativo desarrollado en **Spring Boot**, con registro de
usuarios, autenticación con **Spring Security**, control de acceso por roles (**CLIENT** / **ADMIN**),
carrito de compras real conectado a base de datos, y pago en línea integrado con **Webpay (Transbank)**.

🔗 **Repositorio:** https://github.com/scarlettcindy/klicalia-ecommerce-spring-boot

## Tecnologías utilizadas

- Java 21 / Spring Boot 4.1.0
- Spring MVC + JSP/JSTL (vistas)
- Spring Data JPA (Hibernate) + PostgreSQL
- Spring Security (login, logout, roles, BCrypt, protección CSRF)
- Maven (empaquetado en `.war`)
- SDK de Transbank (Webpay Plus, ambiente de integración)

## Requisitos previos

- Java 21 (o superior) instalado
- Maven (o usar el `mvnw` incluido en el proyecto)
- PostgreSQL instalado y corriendo en `localhost:5432`
- Una base de datos creada llamada `ecommerce_Klicalia`

## Configuración

Antes de correr el proyecto, define la variable de entorno `DB_PASSWORD` con tu contraseña real de
PostgreSQL (nunca se escribe la contraseña directamente en el código):

- Desde Eclipse: clic derecho en el proyecto → `Run As` → `Run Configurations` → pestaña `Environment`
  → `New` → nombre `DB_PASSWORD`, valor tu contraseña real.
- Desde terminal: `export DB_PASSWORD=tu_clave_real` (Mac/Linux) o `set DB_PASSWORD=tu_clave_real`
  (Windows), y luego `./mvnw spring-boot:run`.

Las tablas (`categorias`, `productos`, `usuarios`, `items_carrito`, `ordenes`, `detalle_orden`) se crean
automáticamente al arrancar la aplicación gracias a `spring.jpa.hibernate.ddl-auto=update`.

## Cómo ejecutar el proyecto

**Opción 1: Desde Eclipse**
1. Importa el proyecto como Maven existente (`File > Import > Existing Maven Projects`).
2. Clic derecho sobre el proyecto → `Run As > Spring Boot App`.

**Opción 2: Desde la terminal**
```bash
./mvnw spring-boot:run
```

La aplicación queda disponible en: http://localhost:8080

## Rutas principales

| Rol | Ruta | Descripción |
|---|---|---|
| Público | `/` | Página de inicio |
| Público | `/login` | Iniciar sesión |
| Público | `/register` | Crear cuenta (rol CLIENT) |
| CLIENT / ADMIN | `/catalogo` | Ver catálogo, buscar y filtrar por categoría |
| CLIENT / ADMIN | `/carrito` | Ver, agregar, actualizar cantidad y eliminar productos del carrito |
| CLIENT / ADMIN | `/carrito/confirmar` | Confirmar compra y pagar con Webpay |
| ADMIN | `/admin/products` | Listar y buscar productos |
| ADMIN | `/admin/products/new` | Crear producto |
| ADMIN | `/admin/products/edit?id=` | Editar producto |
| ADMIN | `/admin/products/delete` | Eliminar producto |

## Credenciales de prueba

- **Administrador:** email `admin@klicalia.com` / contraseña `admin123`
- **Cliente:** email `clientenuevo@klicalia.com` / contraseña `cliente123`

## Funcionalidades principales

- Registro e inicio de sesión con roles (CLIENT / ADMIN).
- Catálogo con búsqueda por nombre y filtro por categoría.
- Carrito de compras real (agregar, actualizar cantidad, eliminar), disponible también para
  visitantes sin cuenta (se traspasa automáticamente a su cuenta al iniciar sesión).
- Pago en línea con Webpay (Transbank, ambiente de pruebas), con la orden guardada en la base de datos.
- Panel de administración con CRUD completo de productos.

## Autora

Scarlett Monsalve