#!/usr/bin/env python3
"""
Connect4 Game Launcher
Кроссплатформенный запускатель игры с автоматическим поиском Java и JavaFX
"""

import os
import sys
import subprocess
import platform
import shutil
from pathlib import Path

class Connect4Launcher:
    def __init__(self):
        self.project_dir = Path(__file__).parent.absolute()
        self.jar_file = self.project_dir / "out" / "artifacts" / "4_in_row_jar" / "4_in_row_jar.jar"
        self.system = platform.system()
        
    def print_header(self):
        print("🎮 Connect4 Game Launcher")
        print("=" * 25)
        print()
        
    def check_jar_file(self):
        """Проверяет наличие JAR файла"""
        if not self.jar_file.exists():
            print(f"❌ JAR файл не найден: {self.jar_file}")
            print("   Сначала соберите проект в IntelliJ IDEA")
            return False
        print("✅ JAR файл найден")
        return True
        
    def check_java(self):
        """Проверяет наличие Java"""
        java_cmd = shutil.which("java")
        if not java_cmd:
            print("❌ Java не найдена в PATH")
            print("   Установите Java 11+ с https://adoptium.net/")
            return False
            
        # Проверяем версию Java
        try:
            result = subprocess.run(["java", "-version"], capture_output=True, text=True)
            version_line = result.stderr.split('\n')[0]
            version = version_line.split('"')[1]
            major_version = int(version.split('.')[0])
            
            if major_version < 11:
                print(f"⚠️  Найдена Java версии {version}, рекомендуется Java 11+")
            else:
                print(f"✅ Java версии {version}")
                
        except Exception as e:
            print(f"⚠️  Не удалось определить версию Java: {e}")
            
        return True
        
    def find_javafx(self):
        """Ищет JavaFX в стандартных местах"""
        javafx_paths = []
        
        if self.system == "Darwin":  # macOS
            javafx_paths = [
                Path("/Library/Java/Extensions/javafx-sdk-23.0.1/lib"),
                Path.home() / "javafx-sdk-23.0.1" / "lib",
                self.project_dir / "javafx-sdk-23.0.1" / "lib",
                Path("/opt/javafx/lib"),
            ]
        elif self.system == "Linux":
            javafx_paths = [
                Path("/usr/share/openjfx/lib"),
                Path("/usr/lib/jvm/javafx-sdk/lib"),
                Path("/opt/javafx/lib"),
                Path.home() / "javafx-sdk-23.0.1" / "lib",
                self.project_dir / "javafx-sdk-23.0.1" / "lib",
            ]
        elif self.system == "Windows":
            javafx_paths = [
                Path("C:/Program Files/JavaFX/javafx-sdk-23.0.1/lib"),
                Path("C:/javafx-sdk-23.0.1/lib"),
                self.project_dir / "javafx-sdk-23.0.1" / "lib",
                Path.home() / "javafx-sdk-23.0.1" / "lib",
                Path("D:/javafx-sdk-23.0.1/lib"),
            ]
            
        # Ищем JavaFX
        for path in javafx_paths:
            if path.exists() and (path / "javafx.base.jar").exists():
                print(f"✅ JavaFX найден: {path}")
                return str(path)
                
        print("❌ JavaFX не найден в стандартных местах")
        self.show_javafx_install_help()
        return None
        
    def show_javafx_install_help(self):
        """Показывает помощь по установке JavaFX"""
        print()
        print("📥 Варианты установки JavaFX:")
        print()
        
        if self.system == "Darwin":  # macOS
            print("🍎 macOS:")
            print("   brew install openjfx")
            print("   или скачайте с https://openjfx.io/ и распакуйте в:")
            print("   /Library/Java/Extensions/javafx-sdk-23.0.1/")
        elif self.system == "Linux":
            print("🐧 Linux:")
            print("   sudo apt install openjfx  # Ubuntu/Debian")
            print("   sudo dnf install openjfx  # Fedora")
            print("   или скачайте с https://openjfx.io/")
        elif self.system == "Windows":
            print("🪟 Windows:")
            print("   Запустите INSTALL_JAVAFX.bat для автоматической установки")
            print("   или скачайте с https://openjfx.io/")
            print("   и распакуйте в C:\\javafx-sdk-23.0.1\\")
            
        print()
        print("📁 Или поместите JavaFX в папку проекта:")
        print(f"   {self.project_dir / 'javafx-sdk-23.0.1'}/")
        
    def get_jvm_args(self, javafx_path):
        """Возвращает JVM аргументы для запуска"""
        args = [
            "java",
            "--module-path", javafx_path,
            "--add-modules", "javafx.controls,javafx.fxml",
            "-Dprism.order=sw",
            "-Dprism.verbose=false",
            "-Djava.awt.headless=false",
        ]
        
        # Дополнительные аргументы для Apple Silicon
        if self.system == "Darwin" and platform.machine() == "arm64":
            args.append("-Dprism.allowhidpi=false")
            
        args.extend(["-jar", str(self.jar_file)])
        return args
        
    def launch_game(self, javafx_path):
        """Запускает игру"""
        print("🚀 Запуск игры...")
        print()
        
        args = self.get_jvm_args(javafx_path)
        
        try:
            result = subprocess.run(args, cwd=self.project_dir)
            exit_code = result.returncode
            
            print()
            if exit_code == 0:
                print("✅ Игра завершилась успешно")
            else:
                print(f"❌ Игра завершилась с ошибкой (код: {exit_code})")
                print()
                print("🔧 Возможные решения:")
                print("   - Обновите Java до последней версии")
                print("   - Обновите JavaFX до последней версии")
                print("   - Проверьте права доступа к файлам")
                
            return exit_code
            
        except Exception as e:
            print(f"❌ Ошибка запуска: {e}")
            return 1
            
    def run(self):
        """Основная функция запуска"""
        self.print_header()
        
        # Проверяем JAR файл
        if not self.check_jar_file():
            return 1
            
        # Проверяем Java
        if not self.check_java():
            return 1
            
        # Ищем JavaFX
        javafx_path = self.find_javafx()
        if not javafx_path:
            return 1
            
        # Запускаем игру
        return self.launch_game(javafx_path)

def main():
    launcher = Connect4Launcher()
    exit_code = launcher.run()
    
    if exit_code != 0:
        input("Нажмите Enter для выхода...")
    
    sys.exit(exit_code)

if __name__ == "__main__":
    main()
