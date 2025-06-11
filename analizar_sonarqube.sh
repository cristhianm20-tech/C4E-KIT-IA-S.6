#!/bin/bash

# Colores para mensajes
VERDE='\033[0;32m'
ROJO='\033[0;31m'
AMARILLO='\033[1;33m'
NC='\033[0m'

echo -e "${AMARILLO}Iniciando análisis con SonarQube...${NC}"

# Verificar que SonarQube esté corriendo
if ! curl -s https://integracioncontinua.personalsoft.com > /dev/null; then
    echo -e "${ROJO}Error: No se puede acceder a SonarQube${NC}"
    exit 1
fi

# Limpiar el proyecto
echo -e "${AMARILLO}Limpiando el proyecto...${NC}"
./gradlew clean

# Compilar y ejecutar tests
echo -e "${AMARILLO}Compilando y ejecutando tests...${NC}"
./gradlew build

# Generar reporte de JaCoCo
echo -e "${AMARILLO}Generando reporte de cobertura...${NC}"
./gradlew jacocoTestReport

# Verificar que el reporte de JaCoCo existe
if [ ! -f "build/reports/jacoco/test/jacocoTestReport.xml" ]; then
    echo -e "${ROJO}Error: No se pudo generar el reporte de JaCoCo${NC}"
    exit 1
fi

# Ejecutar el análisis de SonarQube
echo -e "${AMARILLO}Ejecutando análisis de SonarQube...${NC}"
./gradlew sonarqube --info

# Verificar el resultado
if [ $? -eq 0 ]; then
    echo -e "${VERDE}Análisis completado exitosamente${NC}"
    echo -e "${AMARILLO}Puedes ver los resultados en: https://integracioncontinua.personalsoft.com/dashboard?id=bad-practices-demo-IA${NC}"
else
    echo -e "${ROJO}Error al ejecutar el análisis${NC}"
    exit 1
fi 