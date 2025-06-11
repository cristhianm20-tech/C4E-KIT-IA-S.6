#!/bin/bash

# Instala SDKMAN si no está instalado
test -d "$HOME/.sdkman" || (curl -s "https://get.sdkman.io" | bash)
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Instala Gradle 6.8.3 si no está instalado
sdk install gradle 6.8.3 || sdk use gradle 6.8.3

# Usa Gradle 6.8.3 en la sesión actual
sdk use gradle 6.8.3

echo "Versión de Gradle activa:"
gradle -v

# Genera el wrapper con la versión correcta
gradle wrapper --gradle-version 6.8.3

echo "Listo. Ahora puedes ejecutar ./gradlew build" 