package game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {

    @Test
    @Tag("horizontal")
    @DisplayName("Проверка, что горизонтальная победа работает корректно")
    public void testHorizontalWin() {
        System.out.println("testHorizontalWin");
        Board board = new Board();
        board.makeMove(0, 1);
        board.makeMove(1, 1);
        board.makeMove(2, 1);
        board.makeMove(3, 1);
//        board.printBoard();
        assertEquals(1, board.isWinningMove(3, 1));
    }

    @Test
    @Tag("vertical")
    @DisplayName("Проверка, что вертикальная победа работает корректно")
    public void testVerticalWin() {
        System.out.println("testVerticalWin");
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(0, 1);
        board.makeMove(0, 1);
        board.makeMove(0, 1);
        assertEquals(1, board.isWinningMove(0, 1));
    }

    @Test
    @Tag("diagonal-left-win")
    @DisplayName("Проверка, что диагональная победа работает корректно")
    public void testDiagonalLeftWin() {
        System.out.println("testDiagonalLeftWin");
        Board board = new Board(7, 6);
        board.makeMove(0, 2);
        board.makeMove(0, 2);
        board.makeMove(0, 2);
        board.makeMove(0, 1);

        board.makeMove(1, 2);
        board.makeMove(1, 2);
        board.makeMove(1, 1);

        board.makeMove(2, 2);
        board.makeMove(2, 1);

        board.makeMove(3, 1);
//        board.printBoard();
        assertEquals(1, board.isWinningMove(3, 1));
    }

    @Test
    @Tag("diagonal-right")
    @DisplayName("Проверка, что диагональная победа работает корректно")
    public void testDiagonalRightWin() {
        System.out.println("testDiagonalRightWin");
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(1, 2);
        board.makeMove(1, 1);
        board.makeMove(2, 2);
        board.makeMove(2, 2);
        board.makeMove(2, 1);
        board.makeMove(3, 2);
        board.makeMove(3, 2);
        board.makeMove(3, 2);
        board.makeMove(3, 1);
//        board.printBoard();
        assertEquals(1, board.isWinningMove(3, 1));
    }

    @Test
    @Tag("diagonal-left-win2")
    @DisplayName("Проверка, что диагональная победа работает корректно")
    public void testDiagonalLeftWin2() {
        System.out.println("testDiagonalLeftWin2");
        Board board = new Board(7, 6);
        board.makeMove(5, 1);
        board.makeMove(4, 2);

        board.makeMove(4, 1);
        board.makeMove(3, 2);

        board.makeMove(3, 1);
        board.makeMove(2, 2);

        board.makeMove(3, 1);
        board.makeMove(2, 2);

        board.makeMove(2, 1);
        board.makeMove(0, 2);

        board.makeMove(2, 1);
        board.printBoard();
        assertEquals(1, board.isWinningMove(2, 1));
    }

    @Test
    @Tag("no-win")
    @DisplayName("Проверка, что отсутствие победы работает корректно")
    public void testNoWin() {
        System.out.println("testNoWin");
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(1, 1);
        board.makeMove(2, 1);
        assertEquals(0, board.isWinningMove(2, 1));
    }
}