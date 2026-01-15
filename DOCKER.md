# 🐳 Docker - Guía de Ejecución

Esta guía explica cómo ejecutar el servicio de precios Inditex usando Docker.

## 📋 Prerrequisitos

- **Docker** instalado ([Descargar Docker Desktop](https://www.docker.com/products/docker-desktop))
- **Docker Compose** (incluido con Docker Desktop)

## 🚀 Opción 1: Docker Compose (Recomendado)

La forma más sencilla de ejecutar la aplicación:

### Iniciar el servicio

```bash
# Construir y ejecutar en segundo plano
docker-compose up -d

# Ver logs en tiempo real
docker-compose logs -f pricing-service

# Verificar estado
docker-compose ps
```

### Probar la aplicación

```bash
# PowerShell
Invoke-WebRequest -Uri "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"

# cURL
curl "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

### Detener el servicio

```bash
# Detener contenedores
docker-compose stop

# Detener y eliminar contenedores
docker-compose down

# Detener y eliminar contenedores + volúmenes
docker-compose down -v
```

## 🔧 Opción 2: Docker Manual

Si prefieres ejecutar comandos Docker directamente:

### 1. Construir la imagen

```bash
docker build -t inditex-pricing-service:1.0.0 .
```

### 2. Ejecutar el contenedor

```bash
docker run -d \
  --name pricing-service \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xmx512m -Xms256m" \
  inditex-pricing-service:1.0.0
```

En Windows PowerShell:

```powershell
docker run -d `
  --name pricing-service `
  -p 8080:8080 `
  -e JAVA_OPTS="-Xmx512m -Xms256m" `
  inditex-pricing-service:1.0.0
```

### 3. Ver logs

```bash
docker logs -f pricing-service
```

### 4. Detener y eliminar

```bash
docker stop pricing-service
docker rm pricing-service
```

## 🏥 Health Check

Verificar el estado de salud de la aplicación:

```bash
# Endpoint de health check
curl http://localhost:8080/actuator/health

# Información del servicio
curl http://localhost:8080/actuator/info
```

Respuesta esperada:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "H2",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP"
    },
    "ping": {
      "status": "UP"
    }
  }
}
```

## 📊 Acceso a la Consola H2

La consola H2 está disponible cuando el contenedor está ejecutándose:

```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:pricingdb
User: sa
Password: (vacío)
```

## 🔍 Comandos Útiles de Docker

### Ver contenedores en ejecución
```bash
docker ps
```

### Ver todas las imágenes
```bash
docker images
```

### Entrar al contenedor (shell)
```bash
docker exec -it pricing-service sh
```

### Ver uso de recursos
```bash
docker stats pricing-service
```

### Ver logs desde un momento específico
```bash
docker logs --since 30m pricing-service
```

### Limpiar todo (contenedores, imágenes, redes)
```bash
docker system prune -a
```

## 🎯 Características del Dockerfile

### Multi-stage Build
- **Stage 1 (build)**: Usa Maven + JDK para compilar la aplicación
- **Stage 2 (runtime)**: Usa solo JRE para ejecutar (imagen más ligera)

### Optimizaciones
- ✅ Cache de dependencias Maven
- ✅ Imagen Alpine Linux (pequeña y segura)
- ✅ Usuario no-root para seguridad
- ✅ Health check automático
- ✅ Variables de entorno configurables
- ✅ Memoria JVM optimizada para contenedores

### Tamaños de Imagen
- Imagen de build: ~800 MB (temporal)
- Imagen final: ~250 MB (runtime)

## 🧪 Ejecutar Tests en Docker

Para ejecutar los tests dentro de un contenedor:

```bash
docker build --target build -t pricing-test .
docker run --rm pricing-test mvn test
```

## 🌐 Variables de Entorno

Puedes configurar la aplicación mediante variables de entorno:

```bash
docker run -d \
  --name pricing-service \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e JAVA_OPTS="-Xmx1g -Xms512m" \
  -e SERVER_PORT=8080 \
  inditex-pricing-service:1.0.0
```

## 📦 Publicar en Docker Hub (opcional)

```bash
# Etiquetar la imagen
docker tag inditex-pricing-service:1.0.0 tu-usuario/inditex-pricing-service:1.0.0

# Hacer login
docker login

# Publicar
docker push tu-usuario/inditex-pricing-service:1.0.0
```

## 🆘 Solución de Problemas

### El contenedor no inicia
```bash
# Ver logs completos
docker logs pricing-service

# Verificar que el puerto 8080 esté libre
netstat -ano | findstr :8080  # Windows
lsof -i :8080                  # Linux/Mac
```

### Error de memoria
```bash
# Aumentar límites de memoria
docker run -d \
  --name pricing-service \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xmx1g -Xms512m" \
  --memory="1g" \
  inditex-pricing-service:1.0.0
```

### Reconstruir imagen desde cero
```bash
docker-compose build --no-cache
```

## 📝 Notas

- La base de datos H2 es **en memoria**, los datos se pierden al reiniciar el contenedor
- Para persistencia, considera usar H2 en modo archivo o una base de datos externa
- El health check verifica cada 30 segundos que la aplicación responda
- Los logs se pueden ver con `docker logs` o `docker-compose logs`

---

Para más información sobre la aplicación, consulta el [README.md](README.md) principal.
