# 🎮 Connect4 Game - Простые скрипты запуска

Создано несколько удобных скриптов для запуска игры на любой системе.

## 🚀 Быстрый запуск

### На macOS/Linux:
```bash
./launch_game.sh
```

### На Windows:
```cmd
launch_game.bat
```

### Через Python (любая ОС):
```bash
python3 launch_game.py
```

## 📋 Что делают скрипты

1. **Автоматически находят JavaFX** в стандартных местах
2. **Проверяют версию Java** (рекомендуется 11+)
3. **Настраивают правильные JVM аргументы** для стабильной работы
4. **Запускают игру** с правильными параметрами

## 📁 Файлы

| Файл | Платформа | Описание |
|------|-----------|-----------|
| `launch_game.sh` | macOS, Linux | Bash скрипт с автопоиском JavaFX |
| `launch_game.bat` | Windows | Batch файл с автопоиском JavaFX |
| `launch_game.py` | Любая | Python скрипт (кроссплатформенный) |
| `INSTALL_JAVAFX.bat` | Windows | Автоустановщик JavaFX |

## ⚙️ Автоматическая установка JavaFX

### Windows:
```cmd
INSTALL_JAVAFX.bat
```
Скрипт автоматически скачает и установит JavaFX в папку проекта.

### macOS:
```bash
brew install openjfx
```

### Linux (Ubuntu/Debian):
```bash
sudo apt install openjfx
```

## 📦 Места поиска JavaFX

Скрипты ищут JavaFX в следующих местах:

### macOS:
- `/Library/Java/Extensions/javafx-sdk-23.0.1/lib`
- `~/javafx-sdk-23.0.1/lib`
- `./javafx-sdk-23.0.1/lib` (в папке проекта)

### Linux:
- `/usr/share/openjfx/lib`
- `/usr/lib/jvm/javafx-sdk/lib`
- `/opt/javafx/lib`
- `~/javafx-sdk-23.0.1/lib`
- `./javafx-sdk-23.0.1/lib`

### Windows:
- `C:\Program Files\JavaFX\javafx-sdk-23.0.1\lib`
- `C:\javafx-sdk-23.0.1\lib`
- `.\javafx-sdk-23.0.1\lib` (в папке проекта)
- `%USERPROFILE%\javafx-sdk-23.0.1\lib`

## 🔧 Устранение проблем

### "JAR файл не найден"
Соберите проект в IntelliJ IDEA:
`Build → Build Artifacts → 4_in_row:jar → Build`

### "Java не найдена"
Установите Java 11+ с [adoptium.net](https://adoptium.net/)

### "JavaFX не найден"
1. Используйте автоустановщик (Windows): `INSTALL_JAVAFX.bat`
2. Установите через пакетный менеджер (macOS/Linux)
3. Скачайте с [openjfx.io](https://openjfx.io/) и распакуйте

### Игра не запускается на Apple Silicon
Скрипты автоматически применяют исправления для Apple Silicon Mac.

## 💡 Преимущества

✅ **Автоматический поиск** - находит Java и JavaFX сам  
✅ **Кроссплатформенность** - работает на всех ОС  
✅ **Простота** - просто двойной клик или одна команда  
✅ **Надежность** - правильные JVM аргументы для стабильной работы  
✅ **Помощь** - подсказки по установке недостающих компонентов  

## 🎉 Готово!

Теперь любой пользователь может легко запустить игру, даже без технических знаний!
