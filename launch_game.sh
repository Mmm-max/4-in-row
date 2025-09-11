#!/bin/bash

# Простой скрипт для запуска Connect4 с автоматическим поиском JavaFX
# Работает на macOS, Linux и Windows (через Git Bash)

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAR_FILE="$PROJECT_DIR/out/artifacts/4_in_row_jar/4_in_row_jar.jar"

echo "🎮 Connect4 Game Launcher"
echo "========================="
echo

# Проверяем наличие JAR файла
if [ ! -f "$JAR_FILE" ]; then
    echo "❌ JAR файл не найден: $JAR_FILE"
    echo "   Сначала соберите проект в IntelliJ IDEA"
    exit 1
fi

echo "✅ JAR файл найден"

# Проверяем Java
if ! command -v java &> /dev/null; then
    echo "❌ Java не найдена в PATH"
    echo "   Установите Java 11+ с https://adoptium.net/"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 11 ] 2>/dev/null; then
    echo "⚠️  Найдена Java версии $JAVA_VERSION, рекомендуется Java 11+"
else
    echo "✅ Java версии $JAVA_VERSION"
fi

# Список возможных путей к JavaFX (в порядке приоритета)
JAVAFX_PATHS=(
    "/Library/Java/Extensions/javafx-sdk-23.0.1/lib"                    # macOS стандартное расположение
    "/usr/share/openjfx/lib"                                           # Linux (Ubuntu/Debian)
    "/usr/lib/jvm/javafx-sdk/lib"                                      # Linux альтернативное
    "$HOME/javafx-sdk-23.0.1/lib"                                     # Пользовательская установка
    "$PROJECT_DIR/javafx-sdk-23.0.1/lib"                              # Локальная установка
    "$PROJECT_DIR/../javafx-sdk-23.0.1/lib"                           # Соседняя папка
    "/opt/javafx/lib"                                                  # Linux /opt
    "C:/Program Files/JavaFX/javafx-sdk-23.0.1/lib"                   # Windows Program Files
    "C:/javafx-sdk-23.0.1/lib"                                        # Windows корень C:
)

# Ищем JavaFX
JAVAFX_PATH=""
for path in "${JAVAFX_PATHS[@]}"; do
    if [ -d "$path" ] && [ -f "$path/javafx.base.jar" ]; then
        JAVAFX_PATH="$path"
        echo "✅ JavaFX найден: $JAVAFX_PATH"
        break
    fi
done

# Если не найден, предлагаем установку
if [ -z "$JAVAFX_PATH" ]; then
    echo "❌ JavaFX не найден в стандартных местах"
    echo
    echo "📥 Варианты установки JavaFX:"
    echo
    echo "🍎 macOS:"
    echo "   brew install openjfx"
    echo "   или скачайте с https://openjfx.io/ и распакуйте в:"
    echo "   /Library/Java/Extensions/javafx-sdk-23.0.1/"
    echo
    echo "🐧 Linux (Ubuntu/Debian):"
    echo "   sudo apt install openjfx"
    echo
    echo "🪟 Windows:"
    echo "   Скачайте с https://openjfx.io/"
    echo "   Распакуйте в C:\\javafx-sdk-23.0.1\\"
    echo
    echo "📁 Или поместите JavaFX в папку проекта:"
    echo "   $PROJECT_DIR/javafx-sdk-23.0.1/"
    echo
    exit 1
fi

# Определяем JVM аргументы для стабильной работы
JVM_ARGS=(
    "--module-path" "$JAVAFX_PATH"
    "--add-modules" "javafx.controls,javafx.fxml"
    "-Dprism.order=sw"                    # Принудительно использовать программный рендеринг
    "-Dprism.verbose=false"               # Отключить подробный вывод рендерера
    "-Djava.awt.headless=false"           # GUI режим
)

# Дополнительные аргументы для Apple Silicon
if [[ "$(uname -m)" == "arm64" ]] && [[ "$(uname)" == "Darwin" ]]; then
    JVM_ARGS+=("-Dprism.allowhidpi=false")  # Отключить HiDPI для стабильности
fi

echo "🚀 Запуск игры..."
echo

# Запускаем приложение
java "${JVM_ARGS[@]}" -jar "$JAR_FILE"

# Проверяем результат
EXIT_CODE=$?
echo

if [ $EXIT_CODE -eq 0 ]; then
    echo "✅ Игра завершилась успешно"
else
    echo "❌ Игра завершилась с ошибкой (код: $EXIT_CODE)"
    echo
    echo "🔧 Возможные решения:"
    echo "   - Обновите Java до последней версии"
    echo "   - Обновите JavaFX до последней версии"
    echo "   - Проверьте права доступа к файлам"
fi

exit $EXIT_CODE
