package player;
import game.Board;

public class AIPlayer extends Player {
    public AIPlayer(String name, int playerNumber) {
        super(name, playerNumber);
    }

    @Override
    public int getMoveByConsole(Board board) {
        return 0;
    }

    @Override
    protected void NotifiedBoardListener() {}

    @Override
    protected void NotifiedGuiListener() {}

    @Override
    protected void GetCellByGui() {}
}