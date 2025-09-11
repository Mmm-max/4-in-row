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
