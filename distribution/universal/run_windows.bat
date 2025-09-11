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
