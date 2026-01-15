# Inditex Pricing Service

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Descripción

Servicio REST que permite consultar el precio aplicable a un producto de una marca en una fecha específica. El sistema gestiona múltiples tarifas con prioridades y devuelve el precio final de venta (PVP) correspondiente.

**Arquitectura:** Hexagonal (Ports & Adapters) con principios SOLID.

## 🏗️ Arquitectura

El proyecto implementa **Arquitectura Hexagonal** con tres capas claramente definidas:

### Estructura de Capas

```
┌─────────────────────────────────────────────┐
│         INFRASTRUCTURE LAYER                │
│  (REST Controllers, Database Adapters)      │
│                                             │
│  ┌───────────────────────────────────────┐ │
│  │     APPLICATION LAYER                 │ │
│  │   (Use Cases & Services)              │ │
│  │                                       │ │
│  │  ┌─────────────────────────────────┐ │ │
│  │  │      DOMAIN LAYER               │ │ │
│  │  │   (Business Logic & Entities)   │ │ │
│  │  └─────────────────────────────────┘ │ │
│  └───────────────────────────────────────┘ │
└─────────────────────────────────────────────┘
```

**Dominio:** Lógica de negocio independiente de frameworks  
**Aplicación:** Orquestación de casos de uso  
**Infraestructura:** Adaptadores para REST y base de datos

### ✨ Características

- ✅ API REST con validación de parámetros
- ✅ Base de datos H2 en memoria con datos precargados
- ✅ Query optimizada con índices (O(log n))
- ✅ 8 tests de integración (5 requeridos + 3 adicionales)
- ✅ Manejo robusto de errores
- ✅ Documentación completa

## 🚀 Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación
- **Spring Boot 3.2.1**: Framework principal
- **Spring Data JPA**: Persistencia de datos
- **H2 Database**: Base de datos en memoria
- **Maven**: Gestión de dependencias
- **Lombok**: Reducción de código boilerplate
- **REST Assured**: Testing de APIs REST
- **JUnit 5**: Framework de testing

## 📊 Base de Datos

### Tabla PRICES

| Campo | Tipo | Descripción |
|-------|------|-------------|
| ID | BIGINT | Clave primaria |
| BRAND_ID | BIGINT | Identificador de la marca (1 = ZARA) |
| PRODUCT_ID | BIGINT | Identificador del producto |
| PRICE_LIST | BIGINT | Tarifa de precios aplicable |
| START_DATE | TIMESTAMP | Fecha inicio de vigencia |
| END_DATE | TIMESTAMP | Fecha fin de vigencia |
| PRIORITY | INTEGER | Prioridad (mayor valor = mayor prioridad) |
| PRICE | DECIMAL(10,2) | Precio final de venta |
| CURR | VARCHAR(3) | Moneda (ISO 4217) |

### Datos Precargados

El sistema incluye 4 tarifas de ejemplo para el producto 35455 de la marca ZARA (brandId=1):

- **Tarifa 1:** 35.50 EUR (14 Jun - 31 Dic, prioridad 0)
- **Tarifa 2:** 25.45 EUR (14 Jun 15:00-18:30, prioridad 1)
- **Tarifa 3:** 30.50 EUR (15 Jun 00:00-11:00, prioridad 1)
- **Tarifa 4:** 38.95 EUR (15 Jun 16:00 - 31 Dic, prioridad 1)

### Consola H2

Acceder con la aplicación en ejecución:

1. URL: `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:mem:pricingdb`
3. User: `sa` (sin contraseña)

### Optimizaciones

La tabla cuenta con 3 índices para consultas eficientes:
- Índice compuesto en `PRODUCT_ID, BRAND_ID`
- Índice en rango de fechas `START_DATE, END_DATE`
- Índice en `PRIORITY`

Esto garantiza una complejidad **O(log n)** en las búsquedas.

## � Instalación y Ejecución
### 🐳 Opción 1: Docker (Recomendado)

La forma más rápida de ejecutar la aplicación:

```bash
# Con Docker Compose
docker-compose up -d

# Ver logs
docker-compose logs -f
```

**Ventajas:**
- ✅ No requiere instalar Java ni Maven
- ✅ Entorno aislado y reproducible
- ✅ Configuración automática

📖 **Guía completa:** [DOCKER.md](DOCKER.md)

### ⚙️ Opción 2: Ejecución Local
### 1️⃣ Prerrequisitos

- **Java 17** ([Descargar](https://www.oracle.com/java/technologies/downloads/#java17))
- **Maven 3.8+** ([Instrucciones de instalación](.github/INSTRUCTIONS.md))

### 2️⃣ Clonar y Compilar

```bash
git clone https://github.com/tu-usuario/inditex-pricing-service.git
cd inditex-pricing-service
mvn clean install
```

### 3️⃣ Ejecutar

```bash
mvn spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`

### 4️⃣ Ejecutar Tests

```bash
mvn test
```

**Resultado esperado:** 8 tests exitosos (5 requeridos + 3 adicionales)

## 📡 API REST

### Endpoint: Consultar Precio

```http
GET /api/prices?applicationDate={date}&productId={id}&brandId={id}
```

**Parámetros (todos requeridos):**

| Parámetro | Tipo | Ejemplo |
|-----------|------|---------|
| applicationDate | DateTime (ISO 8601) | 2020-06-14T10:00:00 |
| productId | Long | 35455 |
| brandId | Long | 1 |

**Respuesta exitosa (200 OK):**

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14-00.00.00",
  "endDate": "2020-12-31-23.59.59",
  "price": 35.50,
  "currency": "EUR"
}
```

**Códigos de respuesta:**

- `200 OK` - Precio encontrado
- `400 Bad Request` - Parámetros inválidos
- `404 Not Found` - Sin precio aplicable
- `500 Internal Server Error` - Error del servidor

### Ejemplos de Uso

**Con cURL:**

```bash
# Consulta a las 10:00 del 14 de junio
curl "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

**Con PowerShell:**

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

**Con Postman:**

Importar la colección incluida: `postman_collection.json`

## 🧪 Tests Implementados

El proyecto incluye 8 tests de integración que validan todos los escenarios:

| Test | Fecha/Hora | Resultado Esperado |
|------|------------|-------------------|
| ✅ Test 1 | 14-06-2020 10:00 | Price List 1: 35.50 EUR |
| ✅ Test 2 | 14-06-2020 16:00 | Price List 2: 25.45 EUR |
| ✅ Test 3 | 14-06-2020 21:00 | Price List 1: 35.50 EUR |
| ✅ Test 4 | 15-06-2020 10:00 | Price List 3: 30.50 EUR |
| ✅ Test 5 | 16-06-2020 21:00 | Price List 4: 38.95 EUR |
| ✅ Test 6 | Fecha inválida | 400 Bad Request |
| ✅ Test 7 | Producto inexistente | 404 Not Found |
| ✅ Test 8 | Parámetros faltantes | 400 Bad Request |

**Ejecutar:** `mvn test`

## 🎯 Principios de Diseño

El proyecto implementa **principios SOLID** y **arquitectura hexagonal**:

### Arquitectura Hexagonal

- **Dominio:** Lógica de negocio pura, independiente de frameworks
- **Aplicación:** Orquestación de casos de uso
- **Infraestructura:** Adaptadores para tecnologías específicas (REST, JPA)

### Principios SOLID

- **S** - Responsabilidad única: cada clase tiene un propósito específico
- **O** - Abierto/Cerrado: extensible sin modificar código existente
- **L** - Sustitución de Liskov: las implementaciones son intercambiables
- **I** - Segregación de interfaces: interfaces específicas y cohesivas
- **D** - Inversión de dependencias: dependencias hacia abstracciones

### Ventajas

✅ Código mantenible y testeable  
✅ Bajo acoplamiento entre capas  
✅ Alta cohesión en componentes  
✅ Fácil extensión y modificación  
✅ Independencia de frameworks

## � Tecnologías

- **Java 17** - Lenguaje de programación
- **Spring Boot 3.2.1** - Framework principal
- **Spring Data JPA** - Capa de persistencia
- **H2 Database** - Base de datos en memoria
- **Maven** - Gestión de dependencias y construcción
- **Lombok** - Reducción de código repetitivo
- **JUnit 5** - Testing
- **REST Assured** - Testing de APIs REST

## 📖 Documentación Adicional

Para instrucciones detalladas de instalación, configuración y despliegue, consultar:

📄 [INSTRUCTIONS.md](.github/INSTRUCTIONS.md)

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver [LICENSE](LICENSE) para más detalles.

---

**Nota:** Proyecto desarrollado como demostración técnica implementando mejores prácticas de arquitectura empresarial con Spring Boot.
