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
