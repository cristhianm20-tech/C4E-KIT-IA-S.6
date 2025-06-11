#!/bin/bash

# Colores para mensajes
VERDE='\033[0;32m'
ROJO='\033[0;31m'
AMARILLO='\033[1;33m'
NC='\033[0m'

echo -e "${AMARILLO}Iniciando análisis con SonarQube...${NC}"

# Verificar que SonarQube esté corriendo
if ! curl -s https://integracioncontinua.personalsoft.com > /dev/null; then
    echo -e "${ROJO}Error: SonarQube no está corriendo en http://localhost:9000${NC}"
    echo "Por favor, inicia SonarQube primero con: docker-compose up -d"
    exit 1
fi

# Limpiar y compilar el proyecto
echo -e "${AMARILLO}Limpiando y compilando el proyecto...${NC}"
./gradlew clean build

# Ejecutar el análisis de SonarQube
echo -e "${AMARILLO}Ejecutando análisis de SonarQube...${NC}"
./gradlew sonarqube \
    -Dsonar.host.url=https://integracioncontinua.personalsoft.com \
    -Dsonar.login=d7526b284077b90cce0345f4966c2d4502324ae9 \
    -Dsonar.projectKey=bad-practices-demo-IA \
    -Dsonar.scm.provider=git \
    -Dsonar.coverage.jacoco.xmlReportPaths=build/test-results/test/TEST-com.proyecto.UsuarioServiceTest.xml

# Verificar el resultado
if [ $? -eq 0 ]; then
    echo -e "${VERDE}Análisis completado exitosamente${NC}"
    echo -e "${AMARILLO}Puedes ver los resultados en: http://localhost:9000/dashboard?id=backend-java-8-bad-practice${NC}"
else
    echo -e "${ROJO}Error al ejecutar el análisis${NC}"
    exit 1
fi 