package player;
import game.Board;

public class AIPlayer extends Player {
    private  Tree tree;
    private int difficulty;

    public AIPlayer(String name, int playerNumber) {
        super(name, playerNumber);
        this.difficulty = 5;
        this.tree = new Tree(null);
    }

    public AIPlayer(String name, int playerNumber, int difficulty) {
        super(name, playerNumber);
        this.difficulty = difficulty;
        this.tree = new Tree(null);
    }

//    @Override
//    public int getMoveByConsole(Board board) {
//        return makeMove(board);
//    }
    @Override
    public int  getMove(Board board) {
        int bestMove = tree.findBestMove(board, difficulty, this.getPlayerNumber());
        if (bestMove == -1) {
            System.out.println("couldn't find best move");
        }
        System.out.println("bestMove: " + bestMove);
        return bestMove != -1 ? bestMove : findFirstLegalMove(board);
    }

    private int findFirstLegalMove(Board board) {
        for (int col = 0; col < board.getWidth(); col++) {
            if (board.isLegalMove(col)) {
                return col;
            }
        }
        return 0;
    }

    public int getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    protected void NotifiedBoardListener() {

    }

    @Override
    protected void NotifiedGuiListener() {}

    @Override
    protected void GetCellByGui() {}


}