# Multi-stage Dockerfile for Inditex Pricing Service
# Stage 1: Build con Maven
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar archivos de configuración de Maven primero (para aprovechar cache de Docker)
COPY pom.xml .

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar y empaquetar la aplicación (saltando tests para build más rápido)
RUN mvn clean package -DskipTests

# Stage 2: Runtime con JRE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Copiar JAR desde el stage de build
COPY --from=build /app/target/pricing-1.0.0.jar app.jar

# Cambiar propietario del JAR
RUN chown spring:spring app.jar

# Cambiar a usuario no-root
USER spring:spring

# Exponer puerto 8080
EXPOSE 8080

# Configurar opciones de JVM para contenedores
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Healthcheck para verificar que la aplicación está corriendo
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Punto de entrada
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
