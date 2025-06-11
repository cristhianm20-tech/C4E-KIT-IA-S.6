#!/bin/bash

# Instala SDKMAN si no está instalado
test -d "$HOME/.sdkman" || (curl -s "https://get.sdkman.io" | bash)
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Instala y usa JDK 11
echo "Instalando Java 11..."
sdk install java 11.0.26-amzn
sdk use java 11.0.26-amzn

# Verifica la versión de Java
echo "Verificando versión de Java..."
java -version

# Instala y usa Gradle 6.8.3
echo "Instalando Gradle 6.8.3..."
sdk install gradle 6.8.3
sdk use gradle 6.8.3

echo "Verificando versión de Gradle..."
gradle -v

# Genera el wrapper con la versión correcta
echo "Generando wrapper de Gradle..."
gradle wrapper --gradle-version 6.8.3

# Limpia el proyecto
echo "Limpiando proyecto..."
./gradlew clean

# Compila y ejecuta los tests
echo "Compilando y ejecutando tests..."
./gradlew build --info

#echo "Ejecutando la SUPER APLICACION..."
#./gradlew bootRun

# Si la compilación falla, muestra los logs de error
if [ $? -ne 0 ]; then
    echo "La compilación falló. Mostrando logs de error..."
    ./gradlew test --info
fi 