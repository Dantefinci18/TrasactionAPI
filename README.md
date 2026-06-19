# Challenge Mendel
## Autor
- **Dante Finci**
- **Github:** Dantefinci18 
- **Email:** datalefi@gmail.com

## Requisitos
- Java 21
- Docker >= 29.5.2
- Docker compose >= 5.1.4

## Compilar el proyecto
```bash
./mvnw clean package
```

## Ejecutar los tests
```bash
  ./mvnw test
  ```

## Ejecucion del Proyecto
### 1. Levantar Docker
```bash
 docker compose up -d
```

### 2. Ejecutar la API
```bash
./mvnw spring-boot:run
```

### 3. Enlace al Swagger
   http://localhost:8080/swagger-ui/index.html

### 4. Detener servicios Docker
```bash
docker compose down
```