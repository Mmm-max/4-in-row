package player;
import game.Board;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private Scanner scanner;

    public HumanPlayer(String name, int playerNumber) {
        super(name, playerNumber);
        scanner = new Scanner(System.in);
    }

    @Override
    public int getMove(Board board) {
        int move = scanner.nextInt();
        if (board.isLegalMove(move) == 0) {
            System.out.println("Illegal move");
            return getMove(board);
        }
        return move;
    }
}
