# Сборка и запуск игры "4 в ряд"

Это руководство поможет вам создать исполняемое приложение из вашего проекта JavaFX.

## Предварительные требования

1. Java 11+ (у вас установлена Java 21 ✅)
2. JavaFX SDK (установлен в `/Library/Java/Extensions/javafx-sdk-23.0.1/` ✅)

## Способы запуска

### 1. Быстрый запуск через терминал
```bash
./run.sh
```

### 2. Нативное macOS приложение (рекомендуется)
```bash
./create_manual_app.sh
```
После выполнения скрипта в папке `dist_manual/` появится файл `Connect4.app`, который можно запускать двойным кликом.

### 3. Альтернативное приложение через jpackage
```bash
./create_app.sh
```

### 4. Автономное приложение с runtime
```bash
./create_standalone.sh
```
Создает полностью автономную версию с встроенной Java runtime.

### 5. Ручной запуск JAR файла
```bash
java --module-path /Library/Java/Extensions/javafx-sdk-23.0.1/lib \
     --add-modules javafx.controls,javafx.fxml \
     -Dprism.order=sw \
     -Djava.awt.headless=false \
     -jar out/artifacts/4_in_row_jar/4_in_row_jar.jar
```

## Устранение проблем

### "JavaFX runtime components are missing"
Это означает, что JavaFX не найден в classpath. Используйте любой из предложенных способов выше.

### "Error initializing QuantumRenderer: no suitable pipeline found"
Эта ошибка связана с графическими драйверами на macOS. Решение:
- Используйте приложение, созданное через `create_manual_app.sh`
- Или добавьте JVM аргумент `-Dprism.order=sw` для принудительного использования программного рендеринга

### Приложение не запускается
1. Убедитесь, что проект собран в IntelliJ IDEA (Build → Build Artifacts → 4_in_row:jar → Build)
2. Проверьте путь к JavaFX SDK
3. Убедитесь, что все скрипты имеют права на выполнение (`chmod +x script_name.sh`)

## Структура проекта

- `src/` - исходный код
- `out/artifacts/4_in_row_jar/` - собранный JAR файл
- `dist/` - готовые к распространению приложения
- `run.sh` - скрипт быстрого запуска
- `create_app.sh` - создание нативного macOS приложения
- `create_standalone.sh` - создание автономного приложения

## Распространение

Для распространения приложения рекомендуется использовать:
1. **Connect4.app** - для пользователей macOS (создается через `create_app.sh`)
2. **Папка dist из create_standalone.sh** - для любых систем (включает Java runtime)

Нативное приложение `.app` можно просто скопировать в папку Applications или передать другим пользователям macOS.
