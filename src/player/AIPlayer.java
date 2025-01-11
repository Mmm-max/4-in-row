package player;
import game.Board;

public class AIPlayer extends Player {
    private int totalDepth;
    Tree tree;
    public AIPlayer(String name, int playerNumber) {
        super(name, playerNumber);
        totalDepth = 5;
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

    private int buildTree(Board board, int depth, int playerNumber, int move, Node parent) {
        if (depth == totalDepth) {
            return 0;
        }
        board.makeMove(move, playerNumber);
        if
    }
}