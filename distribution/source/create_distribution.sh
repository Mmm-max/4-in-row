#!/bin/bash

# Скрипт для создания полного дистрибутива для всех платформ

PROJECT_DIR="/Users/eugensolopov/IdeaProjects/4-in-row"
DIST_DIR="$PROJECT_DIR/distribution"
VERSION="1.0"

echo "=== Создание полного дистрибутива Connect4 v$VERSION ==="
echo

# Очищаем и создаем папку дистрибутива
rm -rf "$DIST_DIR"
mkdir -p "$DIST_DIR"

echo "📁 Создание структуры дистрибутива..."

# Создаем структуру папок
mkdir -p "$DIST_DIR/macOS"
mkdir -p "$DIST_DIR/windows"
mkdir -p "$DIST_DIR/universal"
mkdir -p "$DIST_DIR/source"

echo "🍎 Создание macOS версии..."

# Создаем macOS приложение
cd "$PROJECT_DIR"
./create_manual_app.sh > /dev/null 2>&1

# Копируем macOS приложение
if [ -d "dist_manual/Connect4.app" ]; then
    cp -R "dist_manual/Connect4.app" "$DIST_DIR/macOS/"
    echo "✅ macOS приложение скопировано"
else
    echo "❌ Ошибка создания macOS приложения"
fi

echo "💿 Создание DMG для macOS..."

# Создаем DMG
./create_dmg.sh > /dev/null 2>&1

if [ -f "dist/Connect4-v1.0.dmg" ]; then
    cp "dist/Connect4-v1.0.dmg" "$DIST_DIR/macOS/"
    echo "✅ DMG файл создан"
else
    echo "❌ Ошибка создания DMG"
fi

echo "🪟 Подготовка Windows версии..."

# Создаем Windows файлы
./create_windows_exe.sh > /dev/null 2>&1

if [ -d "dist_windows" ]; then
    cp -R "dist_windows"/* "$DIST_DIR/windows/"
    echo "✅ Windows файлы подготовлены"
else
    echo "❌ Ошибка подготовки Windows файлов"
fi

echo "🌐 Создание универсальной версии..."

# Создаем универсальную версию (JAR + скрипты)
JAR_FILE="out/artifacts/4_in_row_jar/4_in_row_jar.jar"
if [ -f "$JAR_FILE" ]; then
    cp "$JAR_FILE" "$DIST_DIR/universal/"
    
    # Копируем скрипты запуска
    cp "run.sh" "$DIST_DIR/universal/run_macos_linux.sh"
    
    # Создаем Windows batch файл
    cat > "$DIST_DIR/universal/run_windows.bat" << 'EOF'
@echo off
echo Запуск Connect4...
echo.
echo ТРЕБОВАНИЯ:
echo - Java 11+
echo - JavaFX SDK
echo.

REM Измените путь к JavaFX под ваш
set JAVAFX_PATH=C:\javafx-sdk-23.0.1\lib

java --module-path "%JAVAFX_PATH%" ^
     --add-modules javafx.controls,javafx.fxml ^
     -Dprism.order=sw ^
     -Djava.awt.headless=false ^
     -jar 4_in_row_jar.jar

if %ERRORLEVEL% neq 0 (
    echo.
    echo Ошибка запуска! Проверьте:
    echo 1. Установлена ли Java
    echo 2. Правильно ли указан путь к JavaFX
    echo 3. Скачан ли JavaFX SDK
    pause
)
EOF
    
    echo "✅ Универсальная версия создана"
else
    echo "❌ JAR файл не найден"
fi

echo "📝 Создание исходного кода..."

# Копируем исходный код (без служебных файлов)
rsync -av --exclude='.git' --exclude='out' --exclude='dist*' --exclude='.idea' \
      "$PROJECT_DIR/" "$DIST_DIR/source/"

echo "✅ Исходный код скопирован"

echo "📋 Создание документации..."

# Создаем главный README
cat > "$DIST_DIR/README.md" << 'EOF'
# Connect 4 Game - Дистрибутив v1.0

Игра "4 в ряд" с искусственным интеллектом на Java + JavaFX

## 🎮 Описание игры

Классическая игра "4 в ряд" (Connect Four) с возможностью игры:
- Человек против человека
- Человек против ИИ (несколько уровней сложности)

Игра реализована с использованием:
- Java (основная логика)
- JavaFX (графический интерфейс)
- Алгоритм minimax с альфа-бета отсечением (ИИ)

## 📦 Что в дистрибутиве

### 🍎 macOS/
- `Connect4.app` - готовое приложение для macOS
- `Connect4-v1.0.dmg` - установочный образ для macOS

### 🪟 windows/
- Файлы и инструкции для создания EXE на Windows
- `scripts/build_windows.bat` - автоматическая сборка
- `README_WINDOWS.txt` - подробные инструкции

### 🌐 universal/
- `4_in_row_jar.jar` - JAR файл для любой ОС
- `run_macos_linux.sh` - скрипт запуска для macOS/Linux
- `run_windows.bat` - скрипт запуска для Windows

### 📁 source/
- Полный исходный код проекта
- Скрипты сборки
- Документация

## 🚀 Быстрый старт

### macOS
1. Откройте `macOS/Connect4-v1.0.dmg`
2. Перетащите приложение в Applications
3. Запустите двойным кликом

### Windows
1. Перейдите в папку `windows/`
2. Следуйте инструкциям в `README_WINDOWS.txt`
3. Или используйте готовый JAR из папки `universal/`

### Linux / Другие ОС
1. Перейдите в папку `universal/`
2. Установите Java 11+ и JavaFX
3. Запустите `./run_macos_linux.sh`

## 🔧 Системные требования

- Java 11 или новее
- JavaFX SDK (входит в дистрибутив для macOS)
- Минимум 512MB RAM
- Разрешение экрана 800x600

## 👨‍💻 Разработчик

Eugen Solopov

## 📄 Лицензия

Проект создан в образовательных целях.
EOF

# Создаем инструкции для каждой платформы
cat > "$DIST_DIR/macOS/INSTALL.txt" << 'EOF'
=== Установка на macOS ===

ПРОСТАЯ УСТАНОВКА:
1. Откройте Connect4-v1.0.dmg
2. Перетащите Connect4.app в папку Applications
3. Запустите игру из Launchpad или Finder

ЕСЛИ ПРИЛОЖЕНИЕ НЕ ЗАПУСКАЕТСЯ:
1. Щелкните правой кнопкой на Connect4.app
2. Выберите "Открыть"
3. В диалоге безопасности нажмите "Открыть"

АЛЬТЕРНАТИВНЫЙ СПОСОБ:
1. Откройте Терминал
2. Выполните: xattr -cr /Applications/Connect4.app
3. Запустите приложение

СИСТЕМНЫЕ ТРЕБОВАНИЯ:
- macOS 10.15 или новее
- 512MB свободной памяти

Приложение включает все необходимые компоненты.
Дополнительная установка Java или JavaFX не требуется.
EOF

cat > "$DIST_DIR/universal/INSTALL.txt" << 'EOF'
=== Универсальная версия ===

ТРЕБОВАНИЯ:
1. Java 11 или новее
2. JavaFX SDK

УСТАНОВКА JAVA:
- Windows: https://adoptium.net/
- macOS: brew install openjdk
- Linux: sudo apt install openjdk-11-jdk

УСТАНОВКА JAVAFX:
1. Скачайте с https://openjfx.io/
2. Распакуйте в любую папку
3. Обновите пути в скриптах запуска

ЗАПУСК:
- macOS/Linux: ./run_macos_linux.sh
- Windows: run_windows.bat

РУЧНОЙ ЗАПУСК:
java --module-path /path/to/javafx/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar 4_in_row_jar.jar
EOF

echo "📊 Создание отчета..."

# Вычисляем размеры
MACOS_SIZE=$(du -sh "$DIST_DIR/macOS" 2>/dev/null | cut -f1 || echo "N/A")
WINDOWS_SIZE=$(du -sh "$DIST_DIR/windows" 2>/dev/null | cut -f1 || echo "N/A")
UNIVERSAL_SIZE=$(du -sh "$DIST_DIR/universal" 2>/dev/null | cut -f1 || echo "N/A")
SOURCE_SIZE=$(du -sh "$DIST_DIR/source" 2>/dev/null | cut -f1 || echo "N/A")
TOTAL_SIZE=$(du -sh "$DIST_DIR" 2>/dev/null | cut -f1 || echo "N/A")

cat > "$DIST_DIR/DISTRIBUTION_INFO.txt" << EOF
=== Connect4 Distribution v$VERSION ===
Дата создания: $(date)
Система сборки: $(uname -a)

РАЗМЕРЫ:
- macOS версия: $MACOS_SIZE
- Windows файлы: $WINDOWS_SIZE  
- Универсальная версия: $UNIVERSAL_SIZE
- Исходный код: $SOURCE_SIZE
- Общий размер: $TOTAL_SIZE

СОДЕРЖИМОЕ:
macOS/
  ├── Connect4.app (нативное приложение)
  ├── Connect4-v1.0.dmg (установочный образ)
  └── INSTALL.txt

windows/
  ├── jar/ (JAR файл)
  ├── scripts/ (скрипты сборки)
  └── README_WINDOWS.txt

universal/
  ├── 4_in_row_jar.jar
  ├── run_macos_linux.sh
  ├── run_windows.bat
  └── INSTALL.txt

source/
  └── [полный исходный код]

КОНТРОЛЬНЫЕ СУММЫ:
EOF

# Добавляем контрольные суммы
if command -v shasum >/dev/null 2>&1; then
    echo "SHA256:" >> "$DIST_DIR/DISTRIBUTION_INFO.txt"
    find "$DIST_DIR" -type f -name "*.jar" -o -name "*.dmg" -o -name "*.app" | while read file; do
        shasum -a 256 "$file" >> "$DIST_DIR/DISTRIBUTION_INFO.txt"
    done
fi

echo
echo "🎉 Дистрибутив создан успешно!"
echo "📁 Расположение: $DIST_DIR"
echo "📊 Общий размер: $TOTAL_SIZE"
echo
echo "📋 Что можно распространять:"
echo "  🍎 macOS пользователям: macOS/Connect4-v1.0.dmg"
echo "  🪟 Windows пользователям: windows/ (для сборки EXE)"
echo "  🌐 Всем остальным: universal/ (требует Java+JavaFX)"
echo "  👨‍💻 Разработчикам: source/"
echo
echo "✨ Готово к распространению!"
