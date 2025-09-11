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
