package game;

public class Board {
    int[][] board;
    int length;
    int width;

    public static int min(int a, int b) {
        return a < b ? a : b;
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }
    public Board(int x, int y) {
        board = new int[y][x];
        length = x;
        width = y;
    }

    public Board() {
        board = new int[8][8];
        length = 8;
        width = 8;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int isFull(int moves) {
        if (moves == length * width) {
            return 1;
        }
        return 0;
    }

    public void printBoard() {
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < length; x++) {
                System.out.print(board[y][x] + " ");
            }
            System.out.println();
        }
    }

    private int getCell(int x, int y) {
        return board[y][x];
    }

    public int isLegalMove(int x) {
        if (x < 0 || x >= length) {
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

        // Check horizontal
        for (int i = max(0, x - 3); i < length - 3; i++) {
            if (board[y][x] == player && board[y][i + 1] == player && board[y][i + 2] == player && board[y][i + 3] == player) {
                return 1;
            }
        }
        // Check vertical
        for (int i = max(0, y - 3); i < width - 3; i++) {
            if (board[i][x] == player && board[i + 1][x] == player && board[i + 2][x] == player && board[i + 3][x] == player) {
                return 1;
            }
        }

        // Check diagonal

        // Check diagonal left
        for (int i = 0; i <= 3; i++) {
            try {
                if (board[y - i][x - i] == player && board[y - i - 1][x - i - 1] == player && board[y - i - 2][x - i - 2] == player && board[y - i - 3][x - i - 3] == player) {
                    return 1;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }
        }

        // Check diagonal right
        for (int i = 0; i <= 3; i++) {
            try {
                if (board[y - i][x + i] == player && board[y - i + 1][x + i - 1] == player && board[y - i + 2][x + i - 2] == player && board[y - i + 3][x + i - 3] == player) {
                    return 1;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }
        }
        return 0;
    }
}
