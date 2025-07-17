# 🧠 Task App - Backend

API RESTful desarrollada con **Spring Boot** para la gestión de tareas personales. Proporciona autenticación con **JWT**, control de usuarios con **roles (USER / ADMIN)** y funcionalidades administrativas como activación, desactivación y promoción de usuarios.

## 🚀 Características

- Registro y login con JWT
- CRUD de tareas privadas por usuario
- Protección de rutas con Spring Security
- Panel administrativo:
  - Ver usuarios activos / inactivos
  - Promover a rol ADMIN
  - Desactivar / reactivar usuarios
- Estructura modular con DTOs, Services, Controllers y Security
- CORS habilitado para conexión con frontend Astro

---

## 🛠️ Tecnologías usadas

- Java 17
- Spring Boot 3
- Spring Security
- JWT (jjwt)
- MySQL (con JPA/Hibernate)
- Maven

---

## 📦 Instalación

### 1. Clonar repositorio

```bash
git clone https://github.com/TU_USUARIO/taskapp-backend.git
cd taskapp-backend
```

### 2. Configurar base de datos
Crea una base de datos MySQL llamada taskapp_db (o la que prefieras) y configura tu conexión en `application.properties`:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/taskapp_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Ejecutar la app

```bash
./mvnw spring-boot:run
```
> **Nota:** El **backend** se ejecutará por defecto en `http://localhost:8090`

## 🔐 Autenticación
Se utiliza JWT para proteger rutas.
Al hacer login, obtienes un token que debes incluir en cada petición:

```bash
Authorization: Bearer tu_token_jwt
```

## 📚 Endpoints principales

### 🔐 Autenticación

|Método |Endpoint          |Descripción         |
|-------|------------------|--------------------|
|POST   |`/auth/register`  |Registro de usuario |
|POST   |`/auth/login`     |Login y JWT token.  |

### ✅ Tareas

|Método |Endpoint        |Descripción               |
|-------|----------------|--------------------------|
|GET    |`/tasks`        |Listar tareas del usuario |
|POST   |`/tasks`        |Crear nueva tarea         |
|PUT    |`/tasks/{id}`   |Editar tarea              |
|DELETE |`/tasks/{id}`   |Eliminar tarea            |
> 🔒 Todos protegidos con **JWT**

### 👑 Panel Admin

|Método |Endpoint                    |Descripción               |
|-------|----------------------------|--------------------------|
|GET    |`/admin/usuarios`           |Ver todos los usuarios    |
|POST   |`/admin/usuarios/inactivos` |Ver usuarios inactivos    |
|PUT    |`/admin/promote/{email}`    |Promover a ADMIN          |
|DELETE |`/admin/deactivate/{email}` |Desactivar cuenta         |
|PUT.   |`/admin/reactivate/{email}` |Reactivar cuenta          |
> 🔒 Acceso solo para `ROLE_ADMIN`

## 🧪 Datos de prueba
Puedes crear usuarios normales y luego promoverlos vía endpoint admin.
O insertar manualmente en la base de datos:

```bash
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@email.com';
```

