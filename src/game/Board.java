package game;
import java.util.List;
import java.util.ArrayList;

public class Board implements CellClickListener, Cloneable {
    int[][] board;
    int height; // количество строк
    int width;  // количество столбцов
    int player = 1;
    List<BoardChangeListener> listeners = new ArrayList<>();

    public int getCurrPlayer() {
        return player;
    }
    public void switchPlayer() {
        player = player == 1 ? 2 : 1;
    }

    // Конструктор с параметрами: columns (width), rows (height)
    public Board(int columns, int rows) {
        board = new int[rows][columns]; // [строки][столбцы]
        height = rows;
        width = columns;
    }

    // Конструктор по умолчанию: 7 столбцов, 6 строк (стандарт Connect Four)
    public Board() {
        board = new int[6][7]; // 6 строк, 7 столбцов
        height = 6;
        width = 7;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int isFull(int moves) {
        if (moves == height * width) {
            return 1;
        }
        return 0;
    }

    public void printBoard() {
        for (int y = 0; y < height; y++) { // строки
            for (int x = 0; x < width; x++) { // столбцы
                System.out.print(board[y][x] + " ");
            }
            System.out.println();
        }
    }

    private int getCell(int x, int y) {
        return board[y][x]; // board[строка][столбец]
    }

    public int isLegalMove(int x) {
        if (x < 0 || x >= width) {
            return 0;
        }
        if (getCell(x, 0) == 0) {
            return 1;
        }
        return 0;
    }

    public int makeMove(int x, int player) {
        if (isLegalMove(x) == 0) {
            return -1;
        }
        int y = get_y_coord(x);
        board[y][x] = player;
        notifyListeners(x, y, player);
        return isWinningMove(x, player);
    }

    private int get_y_coord(int x) {
        for (int i = height - 1; i >= 0; i--) { // ищем снизу вверх
            if (board[i][x] == 0) {
                return i;
            }
        }
        return -1;
    }

    public int isWinningMove(int x, int player) {
        int y = get_y_coord(x) + 1;
        if (checkLine(x, y, 1, 0, player)) return 1;    // Горизонталь
        if (checkLine(x, y, 0, 1, player)) return 1;    // Вертикаль
        if (checkLine(x, y, 1, 1, player)) return 1;    // Правая диагональ
        if (checkLine(x, y, -1, 1, player)) return 1;   // Левая диагональ
        return 0;
    }

    public boolean checkWin(int player) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board[y][x] == player) {
                    if (checkLine(x, y, 1, 0, player) ||      // Горизонталь
                        checkLine(x, y, 0, 1, player) ||      // Вертикаль
                        checkLine(x, y, 1, 1, player) ||      // Правая диагональ
                        checkLine(x, y, -1, 1, player)) {     // Левая диагональ
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Проверяет линию из 4 подряд в любом направлении
    private boolean checkLine(int x, int y, int dx, int dy, int player) {
        int count = 0;
        for (int i = -3; i <= 3; i++) {
            int currentX = x + i * dx;
            int currentY = y + i * dy;
            if (currentX < 0 || currentX >= width || currentY < 0 || currentY >= height) {
                continue;
            }
            if (board[currentY][currentX] == player) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    public void clear() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[y][x] = 0;
            }
        }
    }

    @Override
    public void onCellClick(int column) {
        // ...existing code...
    }
    @Override
    public void notifyListeners(int x, int y, int player) {
        for (BoardChangeListener listener : listeners) {
            listener.onBoardChange(x, y, player);
        }
    }

    @Override
    public void addListener(BoardChangeListener listener) {
        listeners.add(listener);
    }

    // cloning
    @Override
    public Board clone() {
        Board clone = new Board(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                clone.board[y][x] = board[y][x];
            }
        }
        return clone;
    }
}
