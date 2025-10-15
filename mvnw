#!/bin/sh
# Maven Wrapper script for Unix/Mac

set -euf
[ "${MVNW_VERBOSE-}" != debug ] || set -x

# Определяем путь до java
if [ -z "${JAVA_HOME-}" ]; then
  JAVA_CMD="$(command -v java || true)"
else
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

if [ ! -x "${JAVA_CMD-}" ]; then
  echo "Error: JAVA_HOME is not set and no 'java' command found in PATH." >&2
  exit 1
fi

# Определяем путь до Maven
WRAPPER_DIR="$(dirname "$0")/.mvn/wrapper"
PROPERTIES_FILE="$WRAPPER_DIR/maven-wrapper.properties"

distributionUrl=$(grep -E "^distributionUrl=" "$PROPERTIES_FILE" | cut -d'=' -f2)
MAVEN_HOME="$HOME/.m2/wrapper/dists/apache-maven-3.9.11"

# Скачиваем Maven, если он ещё не установлен
if [ ! -d "$MAVEN_HOME" ]; then
  echo "Downloading Maven 3.9.11..."
  mkdir -p "$MAVEN_HOME"
  curl -fsSL "$distributionUrl" -o "$MAVEN_HOME/maven.zip"
  unzip -q "$MAVEN_HOME/maven.zip" -d "$MAVEN_HOME"
  rm "$MAVEN_HOME/maven.zip"
fi

# Запуск Maven
exec "$MAVEN_HOME"/apache-maven-3.9.11/bin/mvn "$@"
