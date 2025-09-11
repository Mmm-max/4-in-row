#!/bin/bash

# Скрипт для создания готового пакета игры Connect4
# Включает все необходимые файлы для пользователей

PROJECT_DIR="/Users/eugensolopov/IdeaProjects/4-in-row"
PACKAGE_DIR="$PROJECT_DIR/Connect4-Game-Package"
VERSION="1.0"

echo "📦 Создание готового пакета Connect4 Game v$VERSION"
echo "=================================================="
echo

# Очищаем предыдущую сборку
rm -rf "$PACKAGE_DIR"
mkdir -p "$PACKAGE_DIR"

echo "📁 Создание структуры пакета..."

# Проверяем наличие JAR файла
JAR_SOURCE="$PROJECT_DIR/out/artifacts/4_in_row_jar/4_in_row_jar.jar"
if [ ! -f "$JAR_SOURCE" ]; then
    echo "❌ JAR файл не найден: $JAR_SOURCE"
    echo "   Сначала соберите проект в IntelliJ IDEA"
    exit 1
fi

echo "✅ JAR файл найден"

# Копируем JAR файл
cp "$JAR_SOURCE" "$PACKAGE_DIR/4_in_row_jar.jar"
echo "📄 JAR файл скопирован"

# Копируем скрипты запуска
cp "$PROJECT_DIR/launch_game.sh" "$PACKAGE_DIR/"
cp "$PROJECT_DIR/launch_game.bat" "$PACKAGE_DIR/"
cp "$PROJECT_DIR/launch_game.py" "$PACKAGE_DIR/"
cp "$PROJECT_DIR/INSTALL_JAVAFX.bat" "$PACKAGE_DIR/"

echo "🛠️ Скрипты запуска скопированы"

# Делаем скрипты исполняемыми
chmod +x "$PACKAGE_DIR/launch_game.sh"
chmod +x "$PACKAGE_DIR/launch_game.py"

# Обновляем Windows скрипты для работы с локальным JAR
sed -i '' 's|%PROJECT_DIR%out\\artifacts\\4_in_row_jar\\4_in_row_jar.jar|%PROJECT_DIR%4_in_row_jar.jar|g' "$PACKAGE_DIR/launch_game.bat"

# Обновляем Python скрипт для работы с локальным JAR
sed -i '' 's|"out" \/ "artifacts" \/ "4_in_row_jar" \/ "4_in_row_jar.jar"|"4_in_row_jar.jar"|g' "$PACKAGE_DIR/launch_game.py"

# Создаем главный README
cat > "$PACKAGE_DIR/README.md" << 'EOF'
# 🎮 Connect4 Game - Готовый пакет

Игра "4 в ряд" с искусственным интеллектом на Java + JavaFX

## 🚀 Быстрый старт

### macOS/Linux:
```bash
./launch_game.sh
```

### Windows:
```cmd
launch_game.bat
```

### Python (любая ОС):
```bash
python3 launch_game.py
```

## 📋 Что в пакете

- `4_in_row_jar.jar` - основная игра
- `launch_game.sh` - запускатель для macOS/Linux
- `launch_game.bat` - запускатель для Windows
- `launch_game.py` - кроссплатформенный Python запускатель
- `INSTALL_JAVAFX.bat` - автоустановщик JavaFX для Windows
- `README.md` - этот файл

## ⚙️ Системные требования

- Java 11 или новее
- JavaFX SDK (инструкции по установке ниже)
- 512MB RAM
- Разрешение экрана 800x600

## 📥 Установка Java и JavaFX

### Java
Скачайте с [adoptium.net](https://adoptium.net/) и установите

### JavaFX

**Windows (автоматически):**
```cmd
INSTALL_JAVAFX.bat
```

**macOS:**
```bash
brew install openjfx
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt install openjfx
```

**Вручную для любой ОС:**
1. Скачайте JavaFX SDK с [openjfx.io](https://openjfx.io/)
2. Распакуйте в папку `javafx-sdk-23.0.1` рядом с игрой

## 🎮 Об игре

Классическая игра "4 в ряд" с возможностью игры:
- Человек против человека
- Человек против ИИ (несколько уровней сложности)

ИИ использует алгоритм minimax с альфа-бета отсечением.

## 🔧 Устранение проблем

### "JAR файл не найден"
Убедитесь, что файл `4_in_row_jar.jar` находится в той же папке, что и скрипт запуска.

### "Java не найдена"
Установите Java 11+ с [adoptium.net](https://adoptium.net/)

### "JavaFX не найден"
- Windows: запустите `INSTALL_JAVAFX.bat`
- macOS: `brew install openjfx`
- Linux: `sudo apt install openjfx`
- Или скачайте с [openjfx.io](https://openjfx.io/) и распакуйте в `javafx-sdk-23.0.1/`

### Игра не запускается
1. Проверьте версии Java и JavaFX
2. Попробуйте запустить через Python: `python3 launch_game.py`
3. Убедитесь в правах доступа к файлам

## 👨‍💻 Разработчик

Eugen Solopov

---

Версия: 1.0  
Размер пакета: ~3MB
EOF

# Создаем быстрый старт для Windows
cat > "$PACKAGE_DIR/QUICK_START_WINDOWS.txt" << 'EOF'
=== БЫСТРЫЙ СТАРТ ДЛЯ WINDOWS ===

1. Установите Java:
   - Перейдите на https://adoptium.net/
   - Скачайте и установите Java 11 или новее

2. Установите JavaFX (автоматически):
   - Дважды щелкните на INSTALL_JAVAFX.bat
   - Дождитесь завершения установки

3. Запустите игру:
   - Дважды щелкните на launch_game.bat

Готово! Игра должна запуститься.

ЕСЛИ ЧТО-ТО НЕ РАБОТАЕТ:
- Перезагрузите компьютер после установки Java
- Проверьте антивирус (может блокировать скачивание JavaFX)
- Попробуйте запустить от имени администратора
EOF

# Создаем быстрый старт для macOS
cat > "$PACKAGE_DIR/QUICK_START_MACOS.txt" << 'EOF'
=== БЫСТРЫЙ СТАРТ ДЛЯ MACOS ===

1. Установите Java (если не установлена):
   - Откройте Терминал
   - Выполните: brew install openjdk
   - Или скачайте с https://adoptium.net/

2. Установите JavaFX:
   - В Терминале выполните: brew install openjfx

3. Запустите игру:
   - Дважды щелкните на launch_game.sh
   - Или в Терминале: ./launch_game.sh

Готово!

ЕСЛИ ЧТО-ТО НЕ РАБОТАЕТ:
- Установите Homebrew: /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
- Или используйте Python версию: python3 launch_game.py
EOF

# Создаем информацию о файлах
cat > "$PACKAGE_DIR/FILES_INFO.txt" << 'EOF'
=== ИНФОРМАЦИЯ О ФАЙЛАХ ===

ОСНОВНЫЕ:
- 4_in_row_jar.jar       - Игра (JAR файл)
- README.md              - Основная документация

ЗАПУСКАТЕЛИ:
- launch_game.sh         - Для macOS/Linux
- launch_game.bat        - Для Windows  
- launch_game.py         - Кроссплатформенный (Python)

УСТАНОВЩИКИ:
- INSTALL_JAVAFX.bat     - Автоустановщик JavaFX (Windows)

СПРАВКА:
- QUICK_START_WINDOWS.txt - Быстрый старт для Windows
- QUICK_START_MACOS.txt   - Быстрый старт для macOS
- FILES_INFO.txt          - Этот файл

РАЗМЕРЫ:
- Основная игра: ~3MB
- Весь пакет: ~3.1MB
- С JavaFX: ~43MB (после автоустановки)

КАК ИСПОЛЬЗОВАТЬ:
1. Скачайте всю папку
2. Установите Java + JavaFX (см. инструкции)
3. Запустите подходящий скрипт для вашей ОС
EOF

echo "📝 Документация создана"

# Вычисляем размеры
JAR_SIZE=$(du -sh "$PACKAGE_DIR/4_in_row_jar.jar" | cut -f1)
TOTAL_SIZE=$(du -sh "$PACKAGE_DIR" | cut -f1)

echo
echo "🎉 Пакет создан успешно!"
echo "📁 Расположение: $PACKAGE_DIR"
echo "📊 Размер JAR: $JAR_SIZE"
echo "📊 Общий размер: $TOTAL_SIZE"
echo
echo "📋 Содержимое пакета:"
ls -1 "$PACKAGE_DIR/" | sed 's/^/  ✓ /'
echo
echo "🚀 Готово к распространению!"
echo
echo "💡 Что делать дальше:"
echo "  1. Заархивируйте папку Connect4-Game-Package"
echo "  2. Отправьте архив пользователям"
echo "  3. Пользователи распаковывают и запускают игру"

# Создаем ZIP архив
cd "$PROJECT_DIR"
if command -v zip &> /dev/null; then
    ZIP_NAME="Connect4-Game-v$VERSION.zip"
    zip -r "$ZIP_NAME" "$(basename "$PACKAGE_DIR")" > /dev/null 2>&1
    if [ -f "$ZIP_NAME" ]; then
        ZIP_SIZE=$(du -sh "$ZIP_NAME" | cut -f1)
        echo
        echo "📦 Создан архив: $ZIP_NAME ($ZIP_SIZE)"
    fi
fi

echo
echo "🎊 Пакет готов! Пользователям нужно только:"
echo "  1. Скачать и распаковать"
echo "  2. Установить Java"  
echo "  3. Запустить launch_game.*"
