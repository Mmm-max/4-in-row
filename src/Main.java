import game.BoardTest;

public class Main {
    public static void main(String[] args)  {
        BoardTest boardTest = new BoardTest();
        boardTest.testHorizontalWin();
        boardTest.testVerticalWin();
        boardTest.testDiagonalLeftWin();
        boardTest.testDiagonalRightWin();
    }
}