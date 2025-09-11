#!/bin/bash

# Скрипт для создания нативного macOS приложения
# Использует jpackage для создания .app bundle

PROJECT_DIR="/Users/eugensolopov/IdeaProjects/4-in-row"
JAVAFX_PATH="/Library/Java/Extensions/javafx-sdk-23.0.1"
OUTPUT_DIR="$PROJECT_DIR/dist"
APP_NAME="Connect4"
JAR_FILE="$PROJECT_DIR/out/artifacts/4_in_row_jar/4_in_row_jar.jar"

echo "Создание нативного macOS приложения..."

# Очищаем предыдущую сборку
rm -rf "$OUTPUT_DIR"
mkdir -p "$OUTPUT_DIR"

# Проверяем существование файлов
if [ ! -f "$JAR_FILE" ]; then
    echo "Ошибка: JAR файл не найден. Соберите проект в IntelliJ IDEA."
    exit 1
fi

if [ ! -d "$JAVAFX_PATH" ]; then
    echo "Ошибка: JavaFX SDK не найден по пути $JAVAFX_PATH"
    exit 1
fi

# Создаем .app bundle
echo "Создание .app bundle..."
jpackage --input "$PROJECT_DIR/out/artifacts/4_in_row_jar" \
         --name "$APP_NAME" \
         --main-jar "4_in_row_jar.jar" \
         --main-class Main \
         --type app-image \
         --dest "$OUTPUT_DIR" \
         --module-path "$JAVAFX_PATH/lib" \
         --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics \
         --java-options "-Dprism.order=sw" \
         --java-options "-Dprism.verbose=true" \
         --java-options "-Djava.awt.headless=false" \
         --app-version "1.0" \
         --description "Игра 4 в ряд с ИИ" \
         --vendor "Eugen Solopov"

if [ $? -eq 0 ]; then
    echo "Готово! Приложение создано в $OUTPUT_DIR/$APP_NAME.app"
    echo "Вы можете запустить его двойным кликом или командой:"
    echo "open \"$OUTPUT_DIR/$APP_NAME.app\""
else
    echo "Ошибка при создании приложения"
fi
