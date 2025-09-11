#!/bin/bash

# Скрипт для запуска игры 4-в-ряд
# Требует установленный JavaFX SDK

JAVAFX_PATH="/Library/Java/Extensions/javafx-sdk-23.0.1/lib"
JAR_PATH="out/artifacts/4_in_row_jar/4_in_row_jar.jar"

# Проверяем существование JavaFX
if [ ! -d "$JAVAFX_PATH" ]; then
    echo "Ошибка: JavaFX SDK не найден по пути $JAVAFX_PATH"
    echo "Убедитесь, что JavaFX SDK установлен"
    exit 1
fi

# Проверяем существование JAR файла
if [ ! -f "$JAR_PATH" ]; then
    echo "Ошибка: JAR файл не найден по пути $JAR_PATH"
    echo "Выполните сборку проекта в IntelliJ IDEA"
    exit 1
fi

echo "Запуск игры 4-в-ряд..."
java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -Dprism.order=sw \
     -Dprism.verbose=true \
     -Djava.awt.headless=false \
     -jar "$JAR_PATH"
