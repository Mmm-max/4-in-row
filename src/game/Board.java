package game;
import java.util.List;
import java.util.ArrayList;

public class Board implements CellClickListener, Cloneable{
    int[][] board;
    int height;
    int width;
    int player = 1;
    List<BoardChangeListener> listeners = new ArrayList<>();


    public static int min(int a, int b) {
        return a < b ? a : b;
    }

    public static int max(int a, int b) {return a > b ? a : b;
    }

//    @Override
//    public void addListener(BoardChangeListener listener) {
//        listeners.add(listener);
//    }

    public int getCurrPlayer() {
        return player;
    }
    public void switchPlayer() {
        player = player == 1 ? 2 : 1;
    }

    public Board(int x, int y) {
        board = new int[y][x];
        height = x;
        width = y;
    }

    public Board() {
        board = new int[8][8];
        height = 7;
        width = 6;
    }
    public int getLength() {
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
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                System.out.print(board[y][x] + " ");
            }
            System.out.println();
        }
    }

    private int getCell(int x, int y) {
        return board[y][x];
    }

    public int isLegalMove(int x) {
        if (x < 0 || x >= height) {
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
        for (int i = width - 1; i >= 0; i--) {
            if (board[i][x] == 0) {
                return i;
            }
        }
        return -1;
    }



    public int isWinningMove(int x, int player) {
        int y = get_y_coord(x) + 1;
//        System.out.println("isWinning y: " + y);
        if (checkLine(x, y, 1, 0, player, width)) return 1;    // Горизонталь
        if (checkLine(x, y, 0, 1, player, height)) return 1;   // Вертикаль
        if (checkLine(x, y, 1, 1, player, Math.min(width, height))) return 1;  // Правая диагональ
        if (checkLine(x, y, -1, 1, player, Math.min(width, height))) return 1; // Левая диагональ
        return 0;
    }

    private boolean checkLine(int x, int y, int dx, int dy, int player, int maxCheck) {
        int count = 0;

        // Проверяем линию в обоих направлениях от последней поставленной фишки
        for (int i = -3; i <= 3; i++) {
            int currentX = x + i * dx;
            int currentY = y + i * dy;

            // Проверка границ массива
            if (currentX < 0 || currentX >= board[0].length || currentY < 0 || currentY >= board.length) {
                continue; // Пропускаем эту итерацию, если вышли за границы
            }

            if (board[currentY][currentX] == player) {
                count++;
                if (count == 4) {
                    return true; // Найдена линия из 4 фишек
                }
            } else {
                count = 0; // Сбрасываем счетчик, если встретили фишку другого игрока или пустое место
            }
        }

        return false; // Линия из 4 фишек не найдена
    }

    public void clear() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = 0;
            }
        }
    }



    @Override
    public void onCellClick(int column) {
//        int row = get_y_coord(column);
//        if (row != -1) {
//            makeMove(column, getCurrPlayer());
//
//        }
    }
    @Override
    public  void notifyListeners(int x, int y, int player) {
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
        Board clone = new Board(height, width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                clone.board[i][j] = board[i][j];
            }
        }
        return clone;
    }
}
