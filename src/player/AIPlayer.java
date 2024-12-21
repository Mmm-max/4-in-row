package player;
import game.Board;

public class AIPlayer extends Player {
    public AIPlayer(String name, int playerNumber) {
        super(name, playerNumber);
    }

    @Override
    public int getMove(Board board) {
        return 0;
    }
}
