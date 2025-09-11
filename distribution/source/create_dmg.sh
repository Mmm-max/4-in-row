#!/bin/bash

# Скрипт для создания красивого DMG файла для распространения

PROJECT_DIR="/Users/eugensolopov/IdeaProjects/4-in-row"
APP_NAME="Connect4"
DMG_NAME="Connect4-v1.0"
SOURCE_DIR="$PROJECT_DIR/dist_manual"
OUTPUT_DIR="$PROJECT_DIR/dist"
TEMP_DMG="temp.dmg"
FINAL_DMG="$DMG_NAME.dmg"

echo "Создание DMG файла для распространения..."

# Очищаем предыдущие файлы
mkdir -p "$OUTPUT_DIR"

# Убеждаемся, что приложение создано
if [ ! -d "$SOURCE_DIR/$APP_NAME.app" ]; then
    echo "Создаем приложение..."
    cd "$PROJECT_DIR"
    ./create_manual_app.sh
fi

# Создаем временную папку для DMG
TEMP_DIR=$(mktemp -d)
cp -R "$SOURCE_DIR/$APP_NAME.app" "$TEMP_DIR/"

# Создаем ссылку на Applications
ln -s /Applications "$TEMP_DIR/Applications"

# Создаем README для пользователей
cat > "$TEMP_DIR/README.txt" << 'EOF'
=== Connect 4 Game ===

Игра "4 в ряд" с искусственным интеллектом

УСТАНОВКА:
1. Перетащите Connect4.app в папку Applications
2. Запустите игру двойным кликом

СИСТЕМНЫЕ ТРЕБОВАНИЯ:
- macOS 10.15 или новее
- Разрешение экрана: минимум 800x600

УСТРАНЕНИЕ ПРОБЛЕМ:
Если приложение не запускается:
1. Щелкните правой кнопкой на Connect4.app
2. Выберите "Открыть"
3. Нажмите "Открыть" в диалоге безопасности

Разработчик: Eugen Solopov
Версия: 1.0
EOF

# Вычисляем размер
SIZE=$(du -sm "$TEMP_DIR" | cut -f1)
SIZE=$((SIZE + 10)) # Добавляем немного места

echo "Создание DMG..."
hdiutil create -srcfolder "$TEMP_DIR" -volname "$APP_NAME" -fs HFS+ \
    -fsargs "-c c=64,a=16,e=16" -format UDRW -size ${SIZE}m "$OUTPUT_DIR/$TEMP_DMG"

# Монтируем DMG для настройки
DEVICE=$(hdiutil attach -readwrite -noverify -noautoopen "$OUTPUT_DIR/$TEMP_DMG" | \
         egrep '^/dev/' | sed 1q | awk '{print $1}')

sleep 2

# Настраиваем внешний вид окна
MOUNT_DIR="/Volumes/$APP_NAME"

# Устанавливаем позиции иконок и размер окна
echo '
   tell application "Finder"
     tell disk "'$APP_NAME'"
           open
           set current view of container window to icon view
           set toolbar visible of container window to false
           set statusbar visible of container window to false
           set the bounds of container window to {400, 100, 920, 440}
           set viewOptions to the icon view options of container window
           set arrangement of viewOptions to not arranged
           set icon size of viewOptions to 72
           set position of item "'$APP_NAME'.app" of container window to {130, 120}
           set position of item "Applications" of container window to {390, 120}
           set position of item "README.txt" of container window to {260, 250}
           close
           open
           update without registering applications
           delay 2
     end tell
   end tell
' | osascript

sleep 3

# Синхронизируем и размонтируем
sync
hdiutil detach "$DEVICE"

# Конвертируем в финальный сжатый DMG
echo "Сжатие DMG..."
hdiutil convert "$OUTPUT_DIR/$TEMP_DMG" -format UDZO -imagekey zlib-level=9 \
    -o "$OUTPUT_DIR/$FINAL_DMG"

# Удаляем временные файлы
rm -f "$OUTPUT_DIR/$TEMP_DMG"
rm -rf "$TEMP_DIR"

echo "Готово! DMG файл создан: $OUTPUT_DIR/$FINAL_DMG"
echo "Размер файла: $(du -sh "$OUTPUT_DIR/$FINAL_DMG" | cut -f1)"
