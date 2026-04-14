# Alarmas

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.6-6DB33F)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248)
![Kafka](https://img.shields.io/badge/Apache_Kafka-black)
![JWT](https://img.shields.io/badge/Auth-JWT-blue)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36)

Microservicio REST desarrollado con **Spring Boot** para la gestión de alarmas.

Permite crear, consultar, actualizar y eliminar alarmas, protege los endpoints mediante **JWT**, persiste los datos en **MongoDB**, consume eventos desde **Kafka** procedentes de notas y tareas, y publica eventos cuando se crea una nueva alarma.

## Enfoque del proyecto

> Este proyecto nace con un doble objetivo: funcional y formativo. Más allá de su implementación técnica, ha sido una oportunidad para aprender y consolidar tecnologías backend que no había trabajado previamente en profundidad durante el grado superior. Su desarrollo me ha permitido practicar integración entre microservicios, seguridad con JWT, persistencia con MongoDB, comunicación asíncrona con Kafka y despliegue con Docker. Además, he utilizado herramientas de apoyo como la documentación oficial, recursos técnicos y asistencia puntual con IA, siempre con el objetivo de revisar, adaptar y comprender cada solución aplicada.

---

## Funcionalidades

- CRUD completo de alarmas
- Protección de endpoints con JWT
- Persistencia en MongoDB
- Consumo de eventos Kafka desde `notas.eventos` y `tareas.eventos`
- Publicación de eventos en Kafka al crear alarmas
- Logging estructurado
- Tests básicos de controlador, servicio, modelo, eventos y consumidores Kafka

---

## Stack tecnológico

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Web**
- **Spring Data MongoDB**
- **Spring Security**
- **JWT**
- **Apache Kafka**
- **Lombok**
- **Logback**
- **JUnit 5**
- **Maven**

---

## Estructura del proyecto

```text
src/main/java/com/asistente/alarmas
├── config          # Configuración de Kafka, filtros y seguridad
├── controlador     # Endpoints REST
├── eventos         # Eventos y publicación en Kafka
├── kafka           # Consumidores de eventos de notas y tareas
├── logs            # Logging y manejo global de errores
├── modelo          # Entidad Alarma
├── repositorio     # Acceso a datos
├── seguridad       # Filtro JWT y utilidades
└── servicio        # Lógica de negocio
```

---

## Configuración

El microservicio utiliza `application.yml` con dos perfiles:

- **local**
  - MongoDB: `mongodb://localhost:27017/asistente_diario`
  - Kafka: `127.0.0.1:29092`
- **docker**
  - MongoDB: `mongodb://mongo:27017/asistente_diario`
  - Kafka: `kafka:9092`

Puerto por defecto:

- **8084**

Colección Mongo utilizada:

- **alarmas**

Topics Kafka relevantes:

- **Entrada**: `notas.eventos`, `tareas.eventos`
- **Salida**: `alarmas.programadas`

---

## Ejecución

### Arrancar en local

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
mvnw.cmd spring-boot:run
```

### Ejecutar tests

Los tests están desactivados por defecto en Maven. Para ejecutarlos:

```bash
./mvnw -Pwith-tests test
```

En Windows:

```powershell
mvnw.cmd -Pwith-tests test
```

---

## Ejemplo de evento consumido desde notas

```json
{
  "id": "nota-123",
  "titulo": "Llamar al médico",
  "contenido": "Pedir cita para revisión",
  "fecha": "2026-03-18T09:30:00"
}
```

## Ejemplo de evento consumido desde tareas

```json
{
  "id": "tarea-456",
  "titulo": "Entregar trabajo",
  "fecha": "2026-03-19T18:00:00",
  "completada": false,
  "prioridad": "ALTA"
}
```

---

## Notas técnicas

- El controlador delega la lógica principal en el servicio.
- La creación de alarmas publica un `EventoAlarmaCreada` en Kafka.
- Los consumidores Kafka transforman eventos de notas y tareas en alarmas persistidas en MongoDB.
- El perfil `with-tests` evita el arranque automático de listeners Kafka durante los tests.
