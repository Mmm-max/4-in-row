package player;

import game.Board;
import org.jetbrains.annotations.NotNull;

public class Tree {
    private Node root;
    private int maxDepth;

    private static int WINNING_SCORE = 100;
    private static int DRAW_SCORE = 0;


    public Tree(Node node) {
        root = node;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

//    public void addChild(@NotNull Node parent, int index, Node child) {
//        parent.children[index] = child;
//    }
//
//    public void removeChild(@NotNull Node node, int index) {
//        node.children[index] = null;
//    }
//
//    public Node getChild(@NotNull Node parent, int index) {
//        return parent.children[index];
//    }
//
//    public int getValue(@NotNull Node node) {
//        return node.value;
//    }
//
//    public void setValue(@NotNull Node node, int value) {
//        node.value = value;
//    }

    public Node buildTree(@NotNull Board board, int player) {
        return new Node(board, player);
    }

    public int minimax(Node node, int depth, boolean maximizing, int alpha, int beta) {
        return 0;
    }

    public int findBestMove(Board board, int difficulty) {
        return 0;
    }

    private int evaluateBoard(@NotNull Board board) {
        int score = 0;
        int aiPlayer = root.getPlayer();
        int opponentPlayer = 3 - aiPlayer;

        if (board.checkWin(aiPlayer)) {
            return 100;
        } else if (board.checkWin(opponentPlayer)) {
            return -100;
        }
        return score;
    }

    public Board getBoard(@NotNull Node node) {
        return node.board;
    }
}
