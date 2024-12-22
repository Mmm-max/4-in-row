package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {

    @Test
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
    public void testNoWin() {
        System.out.println("testNoWin");
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(1, 1);
        board.makeMove(2, 1);
        assertEquals(0, board.isWinningMove(2, 1));
    }
}