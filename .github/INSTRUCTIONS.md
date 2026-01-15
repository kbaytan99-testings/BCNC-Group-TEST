# 📋 Instrucciones de Instalación y Ejecución

## 🎯 Propósito del Proyecto

Servicio REST para consultas de precios con **arquitectura hexagonal**, desarrollado siguiendo principios **SOLID** y mejores prácticas de desarrollo empresarial.

---

## 🚀 Instalación y Ejecución

### 1️⃣ Prerrequisitos

**Instalar Java 17:**
- Descargar desde: https://www.oracle.com/java/technologies/downloads/#java17
- Verificar: `java -version`

**Instalar Maven:**

Opción A - Descarga Manual:
1. Descargar Maven desde: https://maven.apache.org/download.cgi
2. Extraer a: `C:\Program Files\Apache\maven`
3. Añadir al PATH del sistema:
   - Panel de Control → Sistema → Configuración avanzada del sistema
   - Variables de entorno → Path → Nuevo
   - Añadir: `C:\Program Files\Apache\maven\bin`
4. Verificar: `mvn -version`

Opción B - Chocolatey (si está instalado):
```powershell
choco install maven
```

### 2️⃣ Clonar el Repositorio

```bash
git clone https://github.com/[usuario]/inditex-pricing-service.git
cd inditex-pricing-service
```

### 3️⃣ Compilar el Proyecto

```bash
mvn clean install
```

**Resultado esperado:**
```
[INFO] BUILD SUCCESS
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
```

### 4️⃣ Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

**Salida esperada:**
```
Started PricingApplication in 3.5 seconds (process running on 8080)
```

### 5️⃣ Probar el API

**Windows PowerShell:**
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

**Con curl (si está instalado):**
```bash
curl "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

**Respuesta esperada:**
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

---

## 🧪 Ejecutar Tests

```bash
# Todos los tests
mvn test

# Ver resultados detallados
mvn test -X
```

**Resultados esperados - 8 tests:**
1. ✅ Test 1: 14 Jun 10:00 → 35.50 EUR (Price List 1)
2. ✅ Test 2: 14 Jun 16:00 → 25.45 EUR (Price List 2)
3. ✅ Test 3: 14 Jun 21:00 → 35.50 EUR (Price List 1)
4. ✅ Test 4: 15 Jun 10:00 → 30.50 EUR (Price List 3)
5. ✅ Test 5: 16 Jun 21:00 → 38.95 EUR (Price List 4)
6. ✅ Test adicional: Fecha inválida → 400
7. ✅ Test adicional: Producto no existe → 404
8. ✅ Test adicional: Parámetros faltantes → 400

---

## 📊 Explorar Base de Datos H2

Con la aplicación ejecutándose:

1. Abrir navegador: `http://localhost:8080/h2-console`
2. Configurar conexión:
   - **JDBC URL**: `jdbc:h2:mem:pricingdb`
   - **User**: `sa`
   - **Password**: _(dejar vacío)_
3. Click "Connect"
4. Ejecutar query de ejemplo:

```sql
SELECT * FROM PRICES ORDER BY PRIORITY DESC;
```

---

## 📡 API Endpoint

### GET /api/prices

**Parámetros (todos requeridos):**

| Parámetro | Tipo | Formato | Ejemplo |
|-----------|------|---------|---------|
| applicationDate | DateTime | ISO 8601 | 2020-06-14T10:00:00 |
| productId | Long | Numérico | 35455 |
| brandId | Long | Numérico | 1 |

**Respuestas:**

| Código | Descripción |
|--------|-------------|
| 200 | OK - Precio encontrado |
| 400 | Bad Request - Parámetros inválidos |
| 404 | Not Found - Sin precio aplicable |
| 500 | Internal Server Error |

---

## 🏗️ Arquitectura del Proyecto

### Estructura Hexagonal (Ports & Adapters)

```
src/main/java/com/inditex/pricing/
│
├── domain/                     🔵 DOMINIO (Independiente)
│   ├── model/                  - Entidades de negocio
│   ├── port/input/             - Casos de uso (interfaces)
│   ├── port/output/            - Repositorios (interfaces)
│   └── exception/              - Excepciones de negocio
│
├── application/                🟢 APLICACIÓN
│   └── service/                - Implementación de casos de uso
│
└── infrastructure/             🟡 INFRAESTRUCTURA
    └── adapter/
        ├── input/rest/         - API REST (Controllers, DTOs)
        └── output/persistence/ - Base de datos (JPA, Entities)
```

### Flujo de una Petición

```
Cliente → REST Controller → Use Case → Service → Repository → Database
        (Infrastructure)   (Domain)   (App)      (Infrastructure)
```

---

## ✅ Checklist de Verificación

### Requisitos Implementados

- [x] Endpoint REST GET para consulta de precios
- [x] Parámetros: applicationDate, productId, brandId
- [x] Respuesta: productId, brandId, priceList, fechas, precio
- [x] Base de datos H2 en memoria
- [x] Datos precargados al iniciar
- [x] 5 tests de integración requeridos
- [x] 3 tests adicionales de casos de error

### Arquitectura y Calidad

- [x] Arquitectura Hexagonal completa
- [x] Principios SOLID aplicados
- [x] Clean Code y JavaDoc
- [x] Query optimizada con índices
- [x] Manejo de errores robusto
- [x] Código limpio y mantenible

---

## 🎯 Tecnologías Utilizadas

- **Java 17** - Lenguaje de programación
- **Spring Boot 3.2.1** - Framework principal
- **Spring Data JPA** - Persistencia
- **H2 Database** - Base de datos en memoria
- **Maven** - Gestión de dependencias
- **JUnit 5 + REST Assured** - Testing

---

## 📞 Soporte

Para más información consultar el [README.md](../README.md) principal del proyecto.

---

## 📝 Actualización del README

**Importante:** El README.md debe mantenerse actualizado y claro para el cliente:

### Qué incluir:
- ✅ Descripción funcional clara
- ✅ Tecnologías utilizadas
- ✅ Instrucciones de instalación
- ✅ Ejemplos de uso del API
- ✅ Información de arquitectura
- ✅ Tests implementados

### Qué evitar:
- ❌ Jerga técnica innecesaria
- ❌ Detalles de implementación interna
- ❌ Información redundante
- ❌ Documentación excesivamente larga

### Formato:
- Usar lenguaje profesional pero accesible
- Incluir ejemplos prácticos
- Estructura clara con secciones bien definidas
- Badges para indicar tecnologías
- Diagramas simples cuando sea necesario

---

*Última actualización: Enero 2026*
- **Git**

Verificar versiones:

```bash
java -version
mvn -version
git --version
```

### 2️⃣ Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/inditex-pricing-service.git
cd inditex-pricing-service
```

### 3️⃣ Compilar el Proyecto

```bash
mvn clean install
```

Este comando:
- Limpia compilaciones previas
- Descarga dependencias
- Compila el código
- Ejecuta los tests
- Genera el JAR

### 4️⃣ Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### 5️⃣ Verificar que Funciona

Abre otra terminal y ejecuta:

```bash
curl -X GET "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

Deberías recibir:

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

---

## 🧪 Ejecutar Tests

### Todos los Tests

```bash
mvn test
```

### Solo Tests de Integración

```bash
mvn test -Dtest=PriceControllerIntegrationTest
```

### Tests con Reporte Detallado

```bash
mvn test -X
```

### Resultados Esperados

```
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
```

Los 5 tests principales validan los escenarios requeridos, y 3 adicionales cubren casos de error.

---

## 📊 Explorar la Base de Datos

### Acceder a la Consola H2

1. Arrancar la aplicación: `mvn spring-boot:run`
2. Abrir navegador: `http://localhost:8080/h2-console`
3. Configurar conexión:
   - **JDBC URL**: `jdbc:h2:mem:pricingdb`
   - **User**: `sa`
   - **Password**: _(dejar vacío)_
4. Click en "Connect"

### Consultas Útiles

```sql
-- Ver todos los precios
SELECT * FROM PRICES;

-- Ver precio aplicable para un caso específico
SELECT * FROM PRICES 
WHERE PRODUCT_ID = 35455 
  AND BRAND_ID = 1 
  AND '2020-06-14 10:00:00' BETWEEN START_DATE AND END_DATE
ORDER BY PRIORITY DESC
LIMIT 1;
```

---

## 🔍 Pruebas Manuales de la API

### Escenario 1: Test a las 10:00 del día 14

```bash
curl -X GET "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

**Resultado esperado**: Price List 1, Precio 35.50 EUR

### Escenario 2: Test a las 16:00 del día 14

```bash
curl -X GET "http://localhost:8080/api/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1"
```

**Resultado esperado**: Price List 2, Precio 25.45 EUR

### Escenario 3: Test a las 21:00 del día 14

```bash
curl -X GET "http://localhost:8080/api/prices?applicationDate=2020-06-14T21:00:00&productId=35455&brandId=1"
```

**Resultado esperado**: Price List 1, Precio 35.50 EUR

### Escenario 4: Test a las 10:00 del día 15

```bash
curl -X GET "http://localhost:8080/api/prices?applicationDate=2020-06-15T10:00:00&productId=35455&brandId=1"
```

**Resultado esperado**: Price List 3, Precio 30.50 EUR

### Escenario 5: Test a las 21:00 del día 16

```bash
curl -X GET "http://localhost:8080/api/prices?applicationDate=2020-06-16T21:00:00&productId=35455&brandId=1"
```

**Resultado esperado**: Price List 4, Precio 38.95 EUR

### Caso de Error: Producto No Existente

```bash
curl -X GET "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=99999&brandId=1"
```

**Resultado esperado**: HTTP 404 con mensaje de error

---

## 🏗️ Arquitectura Detallada

### Flujo de una Petición

```
1. Cliente HTTP
   ↓
2. PriceController (Infrastructure - Input Adapter)
   ↓
3. GetPriceUseCase (Domain - Input Port)
   ↓
4. PriceService (Application)
   ↓
5. PriceRepositoryPort (Domain - Output Port)
   ↓
6. PriceRepositoryAdapter (Infrastructure - Output Adapter)
   ↓
7. JpaPriceRepository (Infrastructure - Spring Data)
   ↓
8. H2 Database
```

### Capas y Responsabilidades

#### 🔵 Domain (Núcleo de Negocio)

- **Modelos**: `Price` (entidad de dominio inmutable)
- **Puertos de Entrada**: `GetPriceUseCase` (caso de uso)
- **Puertos de Salida**: `PriceRepositoryPort` (abstracción de persistencia)
- **Excepciones**: `PriceNotFoundException`

**Características**:
- Sin dependencias externas
- No conoce Spring, JPA, ni HTTP
- Contiene solo lógica de negocio

#### 🟢 Application (Orquestación)

- **Servicios**: `PriceService` (implementa casos de uso)

**Características**:
- Coordina entre dominio e infraestructura
- Implementa puertos de entrada
- Usa puertos de salida

#### 🟡 Infrastructure (Detalles Técnicos)

**Input Adapters** (REST):
- `PriceController`: Expone endpoints HTTP
- DTOs: `PriceResponse`, `ErrorResponse`
- `GlobalExceptionHandler`: Manejo de errores

**Output Adapters** (Persistencia):
- `PriceRepositoryAdapter`: Implementa puerto de repositorio
- `PriceEntity`: Entidad JPA
- `JpaPriceRepository`: Spring Data Repository
- Mappers: Conversión entre capas

---

## 🎯 Decisiones de Diseño

### ¿Por qué Arquitectura Hexagonal?

1. **Independencia del Dominio**: El núcleo de negocio no depende de frameworks
2. **Testabilidad**: Fácil crear tests unitarios del dominio
3. **Mantenibilidad**: Cambios en infraestructura no afectan al dominio
4. **Escalabilidad**: Fácil añadir nuevos adaptadores

### ¿Por qué un Único Query?

La query utiliza:

```sql
SELECT p FROM PriceEntity p 
WHERE p.productId = :productId 
  AND p.brandId = :brandId 
  AND :applicationDate BETWEEN p.startDate AND p.endDate 
ORDER BY p.priority DESC 
LIMIT 1
```

**Ventajas**:
- ✅ **Eficiente**: Una sola consulta a BD
- ✅ **Optimizada**: Usa índices
- ✅ **Escalable**: O(log n) con índices
- ✅ **Simple**: Lógica en BD, no en código

### ¿Por qué Modelo de Dominio Inmutable?

```java
public class Price {
    private final Long id;
    private final Long brandId;
    // ... solo getters, sin setters
}
```

**Ventajas**:
- ✅ Thread-safe
- ✅ Previene modificaciones accidentales
- ✅ Código más predecible

---

## 📚 Recursos Adicionales

### Documentación Relevante

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)
- [REST Best Practices](https://restfulapi.net/)

### Libros Recomendados

- "Clean Architecture" - Robert C. Martin
- "Domain-Driven Design" - Eric Evans
- "Clean Code" - Robert C. Martin

---

## 🐛 Troubleshooting

### Problema: Puerto 8080 ya en uso

**Solución**: Cambiar el puerto en `application.yml`:

```yaml
server:
  port: 8081
```

### Problema: Tests fallan

**Solución**: Verificar que Java 17 esté instalado:

```bash
java -version
```

### Problema: No se inicializan los datos

**Solución**: Verificar los logs. Los datos se cargan al inicio:

```
Executing SQL script from URL [file:.../data.sql]
```

---

## 📞 Contacto y Soporte

Para cualquier duda o problema:

1. Revisar este documento
2. Consultar el README.md principal
3. Abrir un issue en el repositorio

---

## ✨ Características Destacadas

### 1. Código Limpio

- Nombres descriptivos
- Métodos pequeños (< 20 líneas)
- JavaDoc completo
- Sin código duplicado

### 2. Tests Completos

- 8 tests de integración
- Cobertura de casos exitosos y errores
- Tests legibles con REST Assured

### 3. Manejo de Errores Robusto

- Excepciones específicas de dominio
- GlobalExceptionHandler
- Mensajes de error descriptivos
- Códigos HTTP apropiados

### 4. Optimización de Base de Datos

- Índices estratégicos
- Query eficiente con LIMIT 1
- No carga datos innecesarios

### 5. Separación de Responsabilidades

- Cada clase una responsabilidad
- Capas bien definidas
- Sin acoplamiento

---

**¡Proyecto listo para evaluación!** ✅

Todos los requisitos han sido implementados siguiendo las mejores prácticas de la industria.
