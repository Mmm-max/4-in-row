@echo off
title Connect4 Game Launcher
chcp 65001 >nul 2>&1

echo.
echo 🎮 Connect4 Game Launcher
echo =========================
echo.

REM Определяем путь к проекту
set "PROJECT_DIR=%~dp0"
set "JAR_FILE=%PROJECT_DIR%out\artifacts\4_in_row_jar\4_in_row_jar.jar"

REM Проверяем наличие JAR файла
if not exist "%JAR_FILE%" (
    echo ❌ JAR файл не найден: %JAR_FILE%
    echo    Сначала соберите проект в IntelliJ IDEA
    echo.
    pause
    exit /b 1
)

echo ✅ JAR файл найден

REM Проверяем Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java не найдена в PATH
    echo    Установите Java 11+ с https://adoptium.net/
    echo.
    pause
    exit /b 1
)

echo ✅ Java найдена

REM Список возможных путей к JavaFX
set JAVAFX_PATHS[0]=C:\Program Files\JavaFX\javafx-sdk-23.0.1\lib
set JAVAFX_PATHS[1]=C:\javafx-sdk-23.0.1\lib
set JAVAFX_PATHS[2]=%PROJECT_DIR%javafx-sdk-23.0.1\lib
set JAVAFX_PATHS[3]=%USERPROFILE%\javafx-sdk-23.0.1\lib
set JAVAFX_PATHS[4]=D:\javafx-sdk-23.0.1\lib

REM Ищем JavaFX
set "JAVAFX_PATH="
for /l %%i in (0,1,4) do (
    call set "CURRENT_PATH=%%JAVAFX_PATHS[%%i]%%"
    if exist "!CURRENT_PATH!\javafx.base.jar" (
        set "JAVAFX_PATH=!CURRENT_PATH!"
        echo ✅ JavaFX найден: !JAVAFX_PATH!
        goto :found_javafx
    )
)

REM JavaFX не найден
echo ❌ JavaFX не найден в стандартных местах
echo.
echo 📥 Варианты установки JavaFX:
echo.
echo 1. Скачайте JavaFX SDK с https://openjfx.io/
echo 2. Распакуйте в одну из папок:
echo    - C:\javafx-sdk-23.0.1\
echo    - %PROJECT_DIR%javafx-sdk-23.0.1\
echo    - %USERPROFILE%\javafx-sdk-23.0.1\
echo.
echo 🔧 Или запустите автоматический установщик:
echo    INSTALL_JAVAFX.bat
echo.
pause
exit /b 1

:found_javafx

REM Включаем поддержку переменных окружения
setlocal EnableDelayedExpansion

echo 🚀 Запуск игры...
echo.

REM Запускаем приложение с необходимыми аргументами
java --module-path "%JAVAFX_PATH%" ^
     --add-modules javafx.controls,javafx.fxml ^
     -Dprism.order=sw ^
     -Dprism.verbose=false ^
     -Djava.awt.headless=false ^
     -jar "%JAR_FILE%"

REM Проверяем результат
set EXIT_CODE=%errorlevel%
echo.

if %EXIT_CODE% equ 0 (
    echo ✅ Игра завершилась успешно
) else (
    echo ❌ Игра завершилась с ошибкой ^(код: %EXIT_CODE%^)
    echo.
    echo 🔧 Возможные решения:
    echo    - Обновите Java до последней версии
    echo    - Обновите JavaFX до последней версии
    echo    - Запустите от имени администратора
    echo    - Проверьте антивирус
    echo.
    pause
)

exit /b %EXIT_CODE%
