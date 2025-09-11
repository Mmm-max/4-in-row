#!/bin/bash

# Скрипт для создания автономного приложения 4-в-ряд
# Использует jlink для создания custom runtime

PROJECT_DIR="/Users/eugensolopov/IdeaProjects/4-in-row"
JAVAFX_PATH="/Library/Java/Extensions/javafx-sdk-23.0.1"
OUTPUT_DIR="$PROJECT_DIR/dist"
APP_NAME="ConnectFour"

echo "Создание автономного приложения..."

# Очищаем предыдущую сборку
rm -rf "$OUTPUT_DIR"
mkdir -p "$OUTPUT_DIR"

# Создаем custom runtime с JavaFX
echo "Создание custom runtime..."
jlink --module-path "$JAVAFX_PATH/lib" \
      --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics \
      --output "$OUTPUT_DIR/runtime" \
      --compress=2 \
      --no-header-files \
      --no-man-pages

# Копируем JAR
cp "$PROJECT_DIR/out/artifacts/4_in_row_jar/4_in_row_jar.jar" "$OUTPUT_DIR/"

# Создаем скрипт запуска
cat > "$OUTPUT_DIR/run_game.sh" << 'EOF'
#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
"$DIR/runtime/bin/java" -jar "$DIR/4_in_row_jar.jar"
EOF

chmod +x "$OUTPUT_DIR/run_game.sh"

echo "Готово! Приложение создано в $OUTPUT_DIR"
echo "Для запуска выполните: $OUTPUT_DIR/run_game.sh"
