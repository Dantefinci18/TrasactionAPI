# Challenge Mendel
## Autor
- **Dante Finci**
- **Github:** Dantefinci18 
- **Email:** datalefi@gmail.com

## Requisitos
- Java 21
- Docker
- Docker compose v2+

## Compilación del proyecto
```bash
./mvnw clean package
```

## Ejecución de los tests
```bash
./mvnw test
```

## Ejecución del Proyecto
### 1. Levantar Docker
```bash
docker compose up -d
```

### 2. Ejecutar la API
```bash
./mvnw spring-boot:run
```

### 3. Enlace al Swagger
[Swagger UI](http://localhost:8080/swagger-ui/index.html)

### 4. Detener servicios Docker
```bash
docker compose down
```