#!/bin/bash

# Скрипт для создания Windows EXE через jpackage
# Примечание: Требует запуска на Windows или использования виртуальной машины

PROJECT_DIR="/Users/eugensolopov/IdeaProjects/4-in-row"
APP_NAME="Connect4"
OUTPUT_DIR="$PROJECT_DIR/dist_windows"
JAR_FILE="$PROJECT_DIR/out/artifacts/4_in_row_jar/4_in_row_jar.jar"

echo "=== Создание Windows EXE ==="
echo "ВНИМАНИЕ: Этот скрипт предназначен для запуска на Windows"
echo "На macOS он создаст файлы для последующей сборки на Windows"
echo

# Очищаем предыдущую сборку
rm -rf "$OUTPUT_DIR"
mkdir -p "$OUTPUT_DIR"

# Проверяем JAR файл
if [ ! -f "$JAR_FILE" ]; then
    echo "❌ JAR файл не найден: $JAR_FILE"
    echo "   Выполните сборку в IntelliJ IDEA"
    exit 1
fi

# Создаем папку для Windows сборки
mkdir -p "$OUTPUT_DIR/jar"
mkdir -p "$OUTPUT_DIR/javafx"
mkdir -p "$OUTPUT_DIR/scripts"

# Копируем JAR файл
cp "$JAR_FILE" "$OUTPUT_DIR/jar/"

# Создаем инструкции для Windows
cat > "$OUTPUT_DIR/README_WINDOWS.txt" << 'EOF'
=== Инструкции для создания Windows EXE ===

ТРЕБОВАНИЯ НА WINDOWS:
1. Java 11+ (с jpackage)
2. JavaFX SDK 23.0.1 для Windows
3. WiX Toolset (для создания .msi установщика)

ШАГИ:
1. Скачайте JavaFX SDK для Windows:
   https://openjfx.io/

2. Распакуйте JavaFX в папку, например:
   C:\javafx-sdk-23.0.1

3. Установите WiX Toolset:
   https://wixtoolset.org/

4. Откройте Command Prompt или PowerShell

5. Выполните команду (измените пути под ваши):
   jpackage --input jar ^
            --name Connect4 ^
            --main-jar 4_in_row_jar.jar ^
            --main-class Main ^
            --type exe ^
            --dest . ^
            --module-path "C:\javafx-sdk-23.0.1\lib" ^
            --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics ^
            --java-options "-Dprism.order=sw" ^
            --java-options "-Djava.awt.headless=false" ^
            --app-version "1.0" ^
            --description "Игра 4 в ряд с ИИ" ^
            --vendor "Eugen Solopov" ^
            --win-console

АЛЬТЕРНАТИВНО - создание .msi установщика:
   jpackage --input jar ^
            --name Connect4 ^
            --main-jar 4_in_row_jar.jar ^
            --main-class Main ^
            --type msi ^
            --dest . ^
            --module-path "C:\javafx-sdk-23.0.1\lib" ^
            --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics ^
            --java-options "-Dprism.order=sw" ^
            --java-options "-Djava.awt.headless=false" ^
            --app-version "1.0" ^
            --description "Игра 4 в ряд с ИИ" ^
            --vendor "Eugen Solopov"

РЕЗУЛЬТАТ:
- Connect4-1.0.exe (исполняемый установщик)
- или Connect4-1.0.msi (установщик MSI)
EOF

# Создаем пакетный файл для Windows
cat > "$OUTPUT_DIR/scripts/build_windows.bat" << 'EOF'
@echo off
echo === Сборка Windows EXE ===

REM Установите правильный путь к JavaFX
set JAVAFX_PATH=C:\javafx-sdk-23.0.1\lib

REM Проверяем существование JavaFX
if not exist "%JAVAFX_PATH%" (
    echo Ошибка: JavaFX не найден по пути %JAVAFX_PATH%
    echo Скачайте JavaFX SDK и обновите путь в этом файле
    pause
    exit /b 1
)

echo Создание EXE файла...
jpackage --input ../jar ^
         --name Connect4 ^
         --main-jar 4_in_row_jar.jar ^
         --main-class Main ^
         --type exe ^
         --dest ../output ^
         --module-path "%JAVAFX_PATH%" ^
         --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics ^
         --java-options "-Dprism.order=sw" ^
         --java-options "-Djava.awt.headless=false" ^
         --app-version "1.0" ^
         --description "Игра 4 в ряд с ИИ" ^
         --vendor "Eugen Solopov" ^
         --win-console

if %ERRORLEVEL% == 0 (
    echo Готово! EXE файл создан в папке output
    echo Файл можно распространять пользователям Windows
) else (
    echo Ошибка при создании EXE файла
)

pause
EOF

# Создаем PowerShell скрипт
cat > "$OUTPUT_DIR/scripts/build_windows.ps1" << 'EOF'
# PowerShell скрипт для создания Windows EXE

Write-Host "=== Сборка Windows EXE ===" -ForegroundColor Green

# Установите правильный путь к JavaFX
$JavaFXPath = "C:\javafx-sdk-23.0.1\lib"

# Проверяем существование JavaFX
if (-not (Test-Path $JavaFXPath)) {
    Write-Host "Ошибка: JavaFX не найден по пути $JavaFXPath" -ForegroundColor Red
    Write-Host "Скачайте JavaFX SDK и обновите путь в этом файле" -ForegroundColor Yellow
    Read-Host "Нажмите Enter для выхода"
    exit 1
}

Write-Host "Создание EXE файла..." -ForegroundColor Yellow

$jpackageArgs = @(
    "--input", "../jar",
    "--name", "Connect4",
    "--main-jar", "4_in_row_jar.jar", 
    "--main-class", "Main",
    "--type", "exe",
    "--dest", "../output",
    "--module-path", $JavaFXPath,
    "--add-modules", "javafx.controls,javafx.fxml,javafx.base,javafx.graphics",
    "--java-options", "-Dprism.order=sw",
    "--java-options", "-Djava.awt.headless=false",
    "--app-version", "1.0",
    "--description", "Игра 4 в ряд с ИИ",
    "--vendor", "Eugen Solopov",
    "--win-console"
)

try {
    & jpackage @jpackageArgs
    Write-Host "Готово! EXE файл создан в папке output" -ForegroundColor Green
    Write-Host "Файл можно распространять пользователям Windows" -ForegroundColor Green
} catch {
    Write-Host "Ошибка при создании EXE файла: $_" -ForegroundColor Red
}

Read-Host "Нажмите Enter для выхода"
EOF

echo "✅ Файлы для Windows сборки созданы в: $OUTPUT_DIR"
echo
echo "📁 Структура:"
echo "  - jar/4_in_row_jar.jar (ваше приложение)"
echo "  - scripts/build_windows.bat (пакетный файл для Windows)"
echo "  - scripts/build_windows.ps1 (PowerShell скрипт)"
echo "  - README_WINDOWS.txt (подробные инструкции)"
echo
echo "🔧 Для создания EXE на Windows:"
echo "  1. Скопируйте папку dist_windows на компьютер с Windows"
echo "  2. Установите Java и JavaFX SDK"
echo "  3. Запустите scripts/build_windows.bat"
echo
echo "📦 Альтернативно можно использовать GitHub Actions или Docker"
