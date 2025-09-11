#!/bin/bash

# Альтернативный способ создания приложения для macOS
# Создаем .app bundle вручную

PROJECT_DIR="/Users/eugensolopov/IdeaProjects/4-in-row"
APP_NAME="Connect4"
OUTPUT_DIR="$PROJECT_DIR/dist_manual"
JAVAFX_PATH="/Library/Java/Extensions/javafx-sdk-23.0.1"

echo "Создание .app bundle вручную..."

# Очищаем предыдущую сборку
rm -rf "$OUTPUT_DIR"
mkdir -p "$OUTPUT_DIR"

# Создаем структуру .app
APP_PATH="$OUTPUT_DIR/$APP_NAME.app"
mkdir -p "$APP_PATH/Contents/MacOS"
mkdir -p "$APP_PATH/Contents/Resources"
mkdir -p "$APP_PATH/Contents/Java"

# Копируем JAR файл
cp "$PROJECT_DIR/out/artifacts/4_in_row_jar/4_in_row_jar.jar" "$APP_PATH/Contents/Java/"

# Создаем исполняемый скрипт
cat > "$APP_PATH/Contents/MacOS/$APP_NAME" << 'EOF'
#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
JAVA_HOME=$(/usr/libexec/java_home)

# Находим JavaFX
JAVAFX_PATH="/Library/Java/Extensions/javafx-sdk-23.0.1/lib"

if [ ! -d "$JAVAFX_PATH" ]; then
    echo "JavaFX SDK не найден!"
    exit 1
fi

# Запускаем приложение
"$JAVA_HOME/bin/java" \
  --module-path "$JAVAFX_PATH" \
  --add-modules javafx.controls,javafx.fxml \
  -Dprism.order=sw \
  -Dprism.verbose=true \
  -Djava.awt.headless=false \
  -jar "$DIR/../Java/4_in_row_jar.jar"
EOF

chmod +x "$APP_PATH/Contents/MacOS/$APP_NAME"

# Создаем Info.plist
cat > "$APP_PATH/Contents/Info.plist" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    <key>CFBundleDevelopmentRegion</key>
    <string>en</string>
    <key>CFBundleExecutable</key>
    <string>$APP_NAME</string>
    <key>CFBundleIdentifier</key>
    <string>com.eugensolopov.connect4</string>
    <key>CFBundleInfoDictionaryVersion</key>
    <string>6.0</string>
    <key>CFBundleName</key>
    <string>$APP_NAME</string>
    <key>CFBundlePackageType</key>
    <string>APPL</string>
    <key>CFBundleShortVersionString</key>
    <string>1.0</string>
    <key>CFBundleVersion</key>
    <string>1</string>
    <key>LSMinimumSystemVersion</key>
    <string>10.15</string>
    <key>NSHighResolutionCapable</key>
    <true/>
</dict>
</plist>
EOF

echo "Готово! Приложение создано в $APP_PATH"
echo "Для запуска выполните: open \"$APP_PATH\""
