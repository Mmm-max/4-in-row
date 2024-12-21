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
        board = new int[x][y];
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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(board[j][i] + " ");
            }
            System.out.println();
        }
    }

    private int getCell(int x, int y) {
        return board[x][y];
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
        System.out.println("Making move " + x + " for player " + player);
        if (isLegalMove(x) == 0) {
            return -1;
        }
        int y = get_y_coord(x);
        board[x][y] = player;
        return isWinningMove(x, player);
    }

    private int get_y_coord(int x) {
        for (int i = width - 1; i >= 0; i--) {
            if (board[x][i] == 0) {
                return i;
            }
        }
        return -1;
    }



    public int isWinningMove(int x, int player) {
        int y = get_y_coord(x);

        // Check horizontal
        for (int i = max(0, x - 3); i < length - 3; i++) {
            if (board[i][y] == player && board[i + 1][y] == player && board[i + 2][y] == player && board[i + 3][y] == player) {
                return 1;
            }
        }
        // Check vertical
        for (int i = max(0, y - 3); i < width - 3; i++) {
            if (board[x][i] == player && board[x][i + 1] == player && board[x][i + 2] == player && board[x][i + 3] == player) {
                return 1;
            }
        }

        // Check diagonal

        // Check diagonal left

        // Check diagonal right
        return 0;
    }
}
