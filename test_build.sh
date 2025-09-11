#!/bin/bash

# Скрипт для быстрого тестирования всех способов сборки

PROJECT_DIR="/Users/eugensolopov/IdeaProjects/4-in-row"
cd "$PROJECT_DIR"

echo "=== Тестирование сборки игры 4-в-ряд ==="
echo

# Проверяем существование JAR файла
JAR_FILE="out/artifacts/4_in_row_jar/4_in_row_jar.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "❌ JAR файл не найден: $JAR_FILE"
    echo "   Выполните сборку в IntelliJ IDEA (Build → Build Artifacts → 4_in_row:jar → Build)"
    exit 1
fi
echo "✅ JAR файл найден"

# Проверяем JavaFX
JAVAFX_PATH="/Library/Java/Extensions/javafx-sdk-23.0.1/lib"
if [ ! -d "$JAVAFX_PATH" ]; then
    echo "❌ JavaFX SDK не найден: $JAVAFX_PATH"
    exit 1
fi
echo "✅ JavaFX SDK найден"

# Тестируем простой запуск
echo
echo "🧪 Тестирование простого запуска..."
timeout 5s java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -Dprism.order=sw \
     -Djava.awt.headless=false \
     -jar "$JAR_FILE" &>/dev/null &
PID=$!
sleep 2
if kill -0 $PID 2>/dev/null; then
    echo "✅ Приложение успешно запускается"
    kill $PID 2>/dev/null
else
    echo "❌ Проблема с запуском приложения"
fi

# Создаем и тестируем ручное приложение
echo
echo "🧪 Создание ручного .app приложения..."
if ./create_manual_app.sh > /dev/null 2>&1; then
    echo "✅ Ручное приложение создано: dist_manual/Connect4.app"
    
    # Проверяем размер
    SIZE=$(du -sh dist_manual/Connect4.app | cut -f1)
    echo "   Размер: $SIZE"
    
    # Тестируем запуск
    echo "🧪 Тестирование ручного приложения..."
    timeout 5s dist_manual/Connect4.app/Contents/MacOS/Connect4 &>/dev/null &
    PID=$!
    sleep 2
    if kill -0 $PID 2>/dev/null; then
        echo "✅ Ручное приложение работает"
        kill $PID 2>/dev/null
    else
        echo "❌ Проблема с ручным приложением"
    fi
else
    echo "❌ Ошибка создания ручного приложения"
fi

echo
echo "=== Резюме ==="
echo "✅ Рекомендуемый способ: ./create_manual_app.sh"
echo "✅ Быстрый запуск: ./run.sh"
echo "✅ Готовое приложение: dist_manual/Connect4.app"
echo
echo "Для запуска выполните:"
echo "  open dist_manual/Connect4.app"
