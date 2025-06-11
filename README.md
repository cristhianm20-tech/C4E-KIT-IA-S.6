# Proyecto Backend con Malas Prácticas

Este proyecto es una demostración intencional de malas prácticas en el desarrollo de software. Está diseñado para ser utilizado como base para una demostración de refactorización asistida por IA.

## Tecnologías Utilizadas

- Java 11
- Spring Boot 2.7.0
- H2 Database 1.4.200
- Gradle
- JUnit 4
- SonarQube 3.0

## Malas Prácticas Implementadas

### 1. Estructura y Configuración
- [ ] Convenciones de paquetes inconsistentes
- [ ] Configuración insegura de H2 (credenciales débiles)
- [ ] Consola H2 habilitada en producción
- [ ] Sin perfiles de Spring

### 2. Seguridad
- [ ] Contraseñas en texto plano
- [ ] Endpoints sin autorización
- [ ] CORS configurado con wildcard
- [ ] Sin validación de entradas
- [ ] Exposición de datos sensibles en logs

### 3. Diseño y Arquitectura
- [ ] God classes con múltiples responsabilidades
- [ ] Variables globales estáticas
- [ ] Lógica de negocio en capas incorrectas
- [ ] Acoplamiento fuerte entre componentes
- [ ] Nombres de variables genéricos

### 4. Rendimiento
- [ ] Retardos intencionales (Thread.sleep)
- [ ] Consultas SQL ineficientes
- [ ] Sin paginación en endpoints
- [ ] Configuración de servidor no optimizada

### 5. Testing
- [ ] Tests sin aserciones
- [ ] Dependencias entre tests
- [ ] Sin limpieza de datos
- [ ] Uso de JUnit 4 en lugar de JUnit 5
- [ ] Tests con retardos

## Próximos Pasos de Refactorización

1. **Seguridad**
   - Implementar Spring Security
   - Hashear contraseñas
   - Configurar CORS adecuadamente
   - Implementar validación de entradas

2. **Arquitectura**
   - Reorganizar paquetes
   - Implementar DTOs
   - Separar responsabilidades
   - Eliminar variables globales

3. **Rendimiento**
   - Optimizar consultas SQL
   - Implementar caché
   - Eliminar retardos
   - Configurar conexiones

4. **Testing**
   - Migrar a JUnit 5
   - Implementar mocks
   - Añadir aserciones
   - Limpiar datos entre tests

5. **Configuración**
   - Implementar perfiles
   - Mejorar configuración de H2
   - Actualizar dependencias
   - Configurar logging

## Vulnerabilidades OWASP

1. A1 - Inyección SQL
2. A2 - Autenticación rota
3. A3 - Exposición de datos sensibles
4. A5 - Control de acceso roto
5. A7 - Configuración de seguridad incorrecta
6. A8 - CSRF
7. A9 - Componentes desactualizados

## Ejecución del Proyecto

```bash
./gradlew bootRun
```

## Ejecución de Tests

```bash
./gradlew test
```

## Análisis de Código

```bash
./gradlew sonarqube
```

## Benchmark

```bash
./gradlew benchmark
``` 