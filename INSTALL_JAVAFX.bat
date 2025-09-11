@echo off
title JavaFX Auto Installer
chcp 65001 >nul 2>&1

echo.
echo 📥 JavaFX Auto Installer
echo ========================
echo.

REM Определяем папку проекта
set "PROJECT_DIR=%~dp0"
set "JAVAFX_DIR=%PROJECT_DIR%javafx-sdk-23.0.1"

REM Проверяем, не установлен ли уже JavaFX
if exist "%JAVAFX_DIR%\lib\javafx.base.jar" (
    echo ✅ JavaFX уже установлен в: %JAVAFX_DIR%
    echo.
    echo Хотите переустановить? ^(y/N^)
    set /p REINSTALL=
    if /i not "%REINSTALL%"=="y" (
        echo Установка отменена
        pause
        exit /b 0
    )
    echo.
    echo 🗑️ Удаление старой версии...
    rmdir /s /q "%JAVAFX_DIR%" 2>nul
)

REM Проверяем интернет соединение
echo 🌐 Проверка интернет соединения...
ping -n 1 google.com >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Нет подключения к интернету
    echo    Скачайте JavaFX вручную с https://openjfx.io/
    pause
    exit /b 1
)

echo ✅ Интернет подключен

REM Проверяем PowerShell
echo 🔧 Проверка PowerShell...
powershell -Command "Get-Host" >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ PowerShell недоступен
    echo    Скачайте JavaFX вручную с https://openjfx.io/
    pause
    exit /b 1
)

echo ✅ PowerShell доступен

REM Создаем временную папку
set "TEMP_DIR=%PROJECT_DIR%temp_download"
mkdir "%TEMP_DIR%" 2>nul

echo.
echo 📦 Скачивание JavaFX SDK...
echo    Размер: ~40MB, может занять несколько минут
echo.

REM Скачиваем JavaFX через PowerShell
powershell -Command "& { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $ProgressPreference = 'SilentlyContinue'; Write-Host '⏬ Скачивание...'; try { Invoke-WebRequest -Uri 'https://download2.gluonhq.com/openjfx/23.0.1/openjfx-23.0.1_windows-x64_bin-sdk.zip' -OutFile '%TEMP_DIR%\javafx.zip' -TimeoutSec 300; Write-Host '✅ Скачивание завершено' } catch { Write-Host '❌ Ошибка скачивания:' $_.Exception.Message; exit 1 } }"

if not exist "%TEMP_DIR%\javafx.zip" (
    echo ❌ Ошибка скачивания JavaFX
    echo.
    echo 🔧 Попробуйте:
    echo    1. Проверить интернет соединение
    echo    2. Отключить антивирус временно
    echo    3. Скачать вручную с https://openjfx.io/
    echo.
    rmdir /s /q "%TEMP_DIR%" 2>nul
    pause
    exit /b 1
)

echo.
echo 📂 Распаковка JavaFX...

REM Распаковываем архив
powershell -Command "& { $ProgressPreference = 'SilentlyContinue'; Write-Host '📂 Распаковка...'; try { Expand-Archive -Path '%TEMP_DIR%\javafx.zip' -DestinationPath '%PROJECT_DIR%' -Force; Write-Host '✅ Распаковка завершена' } catch { Write-Host '❌ Ошибка распаковки:' $_.Exception.Message; exit 1 } }"

REM Проверяем успешность распаковки
if not exist "%JAVAFX_DIR%\lib\javafx.base.jar" (
    echo ❌ Ошибка распаковки JavaFX
    echo    Попробуйте распаковать вручную
    rmdir /s /q "%TEMP_DIR%" 2>nul
    pause
    exit /b 1
)

REM Очищаем временные файлы
echo.
echo 🧹 Очистка временных файлов...
rmdir /s /q "%TEMP_DIR%" 2>nul

echo.
echo 🎉 JavaFX успешно установлен!
echo.
echo 📁 Расположение: %JAVAFX_DIR%
echo 📊 Размер: 
for /f %%A in ('dir "%JAVAFX_DIR%" /s /-c ^| find "bytes"') do echo    %%A

echo.
echo ✅ Теперь можно запускать игру:
echo    launch_game.bat
echo.

REM Предлагаем сразу запустить игру
echo Запустить игру сейчас? ^(Y/n^)
set /p LAUNCH_NOW=
if /i not "%LAUNCH_NOW%"=="n" (
    echo.
    echo 🎮 Запуск игры...
    call "%PROJECT_DIR%launch_game.bat"
) else (
    pause
)
