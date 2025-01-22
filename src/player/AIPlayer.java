package player;
import game.Board;

public class AIPlayer extends Player {
    private int totalDepth;
    Tree tree;
    private int AInumber;

    public AIPlayer(String name, int playerNumber) {
        super(name, playerNumber);
        totalDepth = 5;
        AInumber = playerNumber;
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

    private int buildTree(Board board, int depth, int playerNumber, Node parent) {
        if (depth == totalDepth) {
            return 0;
        }
        for (int i = 0; i < 7; i++) {
            if (board.isLegalMove(i) == 1) {
                Board newBoard = board.clone();
                newBoard.makeMove(i, playerNumber);
                parent.children[i] = new Node(newBoard, playerNumber);
                if (board.isWinningMove(i, playerNumber) == 1) {
                    if (playerNumber == AInumber) {
                        parent.children[i].setValue(100);
                    } else {
                        parent.children[i].setValue(-100);
                    }
                } else {
                    buildTree(newBoard, depth + 1, playerNumber == 1 ? 2 : 1, parent.children[i]);
                }
            }
        }
        return 0;
    }

}