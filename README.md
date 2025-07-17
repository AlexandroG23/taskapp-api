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
