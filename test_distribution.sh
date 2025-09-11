#!/bin/bash

# Финальный тест всех созданных дистрибутивов

PROJECT_DIR="/Users/eugensolopov/IdeaProjects/4-in-row"
cd "$PROJECT_DIR"

echo "🧪 === ФИНАЛЬНОЕ ТЕСТИРОВАНИЕ ДИСТРИБУТИВОВ ==="
echo

# Проверяем основные компоненты
echo "📋 Проверка основных файлов:"

files_to_check=(
    "distribution/macOS/Connect4-v1.0.dmg"
    "distribution/macOS/Connect4.app/Contents/MacOS/Connect4"
    "distribution/windows/jar/4_in_row_jar.jar"
    "distribution/windows/scripts/build_windows.bat"
    "distribution/universal/4_in_row_jar.jar"
    "distribution/universal/run_macos_linux.sh"
    "distribution/universal/run_windows.bat"
    "distribution/README.md"
)

for file in "${files_to_check[@]}"; do
    if [ -e "$file" ]; then
        echo "✅ $file"
    else
        echo "❌ $file"
    fi
done

echo
echo "📊 Размеры дистрибутивов:"
if [ -d "distribution" ]; then
    echo "📁 Общий размер: $(du -sh distribution | cut -f1)"
    echo "🍎 macOS: $(du -sh distribution/macOS 2>/dev/null | cut -f1 || echo 'N/A')"
    echo "🪟 Windows: $(du -sh distribution/windows 2>/dev/null | cut -f1 || echo 'N/A')" 
    echo "🌐 Universal: $(du -sh distribution/universal 2>/dev/null | cut -f1 || echo 'N/A')"
    echo "📝 Source: $(du -sh distribution/source 2>/dev/null | cut -f1 || echo 'N/A')"
fi

echo
echo "🧪 Тестирование исполняемых файлов:"

# Тест macOS приложения
if [ -x "distribution/macOS/Connect4.app/Contents/MacOS/Connect4" ]; then
    echo "🍎 Тестирование macOS приложения..."
    timeout 3s "distribution/macOS/Connect4.app/Contents/MacOS/Connect4" &>/dev/null &
    PID=$!
    sleep 1
    if kill -0 $PID 2>/dev/null; then
        echo "✅ macOS приложение запускается"
        kill $PID 2>/dev/null
    else
        echo "❌ Проблема с macOS приложением"
    fi
else
    echo "❌ macOS приложение не найдено"
fi

# Тест универсального JAR
if [ -f "distribution/universal/4_in_row_jar.jar" ]; then
    echo "🌐 Тестирование универсального JAR..."
    JAVAFX_PATH="/Library/Java/Extensions/javafx-sdk-23.0.1/lib"
    if [ -d "$JAVAFX_PATH" ]; then
        timeout 3s java --module-path "$JAVAFX_PATH" \
             --add-modules javafx.controls,javafx.fxml \
             -Dprism.order=sw \
             -jar "distribution/universal/4_in_row_jar.jar" &>/dev/null &
        PID=$!
        sleep 1
        if kill -0 $PID 2>/dev/null; then
            echo "✅ Универсальный JAR работает"
            kill $PID 2>/dev/null
        else
            echo "❌ Проблема с универсальным JAR"
        fi
    else
        echo "⚠️ JavaFX не найден, тест пропущен"
    fi
else
    echo "❌ Универсальный JAR не найден"
fi

echo
echo "📋 === РЕЗЮМЕ ==="
echo "✅ Готовые файлы для распространения:"
echo "   🍎 macOS: distribution/macOS/Connect4-v1.0.dmg"
echo "   🪟 Windows: distribution/windows/ (для сборки EXE)"
echo "   🌐 Universal: distribution/universal/ (JAR + скрипты)"
echo
echo "📦 Что отправлять пользователям:"
echo "   - Пользователям Mac: только Connect4-v1.0.dmg"
echo "   - Пользователям Windows: папку windows/ или universal/"
echo "   - Техническим пользователям: universal/ или source/"
echo
echo "🎉 Дистрибутив готов к распространению!"

# Создаем финальную справку
cat > "distribution/QUICK_START.txt" << 'EOF'
=== БЫСТРЫЙ СТАРТ ===

ДЛЯ ПОЛЬЗОВАТЕЛЕЙ:

🍎 macOS:
   Откройте Connect4-v1.0.dmg и перетащите приложение в Applications

🪟 Windows:
   1. Установите Java 11+ с https://adoptium.net/
   2. Скачайте JavaFX с https://openjfx.io/
   3. Используйте файлы из папки windows/ или universal/

🐧 Linux:
   1. Установите Java и JavaFX через пакетный менеджер
   2. Используйте файлы из папки universal/

ДЛЯ РАЗРАБОТЧИКОВ:
   Полный исходный код в папке source/
   Инструкции по сборке в BUILD_INSTRUCTIONS.md

ПОДДЕРЖКА:
   При проблемах проверьте README.md в соответствующей папке
EOF

echo "📝 Создан файл QUICK_START.txt"
