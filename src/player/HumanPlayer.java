package player;
import game.Board;
import game.BoardChangeListener;
import game.CellClickListener;

import java.util.Scanner;

public class HumanPlayer extends Player implements CellClickListener {
    private Scanner scanner;
    private int move = -1;

    public HumanPlayer(String name, int playerNumber) {
        super(name, playerNumber);
        scanner = new Scanner(System.in);
    }

    @Override
    public int getMove(Board board) {
        return getMoveByGui(board);
    }

    public int getMoveByConsole(Board board) {
        System.out.println("Enter your move:");
        int move = scanner.nextInt();
        if (!board.isLegalMove(move)) {
            System.out.println("Illegal move");
            return getMoveByConsole(board);
        }
        return move;
    }

    public int getMoveByGui(Board board) {
        move = -1;
        while (move == -1) {
            try {
                Thread.sleep(100);
//                System.out.println("Waiting for move");
                if (Thread.interrupted()) {
                    System.out.println("Thread interrupted");
                    return -1;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int temp = move;
        move = -1;
        if (!board.isLegalMove(temp)) {
            System.out.println("Illegal move");
            return getMoveByGui(board);
        }
        return temp;
    }

    @Override
    protected void NotifiedBoardListener() {}

    @Override
    protected void NotifiedGuiListener() {}

    @Override
    protected void GetCellByGui() {
    }

    @Override
    public void onCellClick(int column) {
        System.out.println("HumanPlayer: onCellClick");
        move = column;
    }

    @Override
    public void notifyListeners(int x, int y, int player) {}

    @Override
    public void addListener(BoardChangeListener board) {}
}
