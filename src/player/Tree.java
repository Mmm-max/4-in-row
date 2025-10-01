package player;

import game.Board;
import org.jetbrains.annotations.NotNull;

import javax.naming.InitialContext;

public class Tree {
    private Node root;
    private int maxDepth;

    private static int WINNING_SCORE = 10000;
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

    public Node buildTree(@NotNull Board board, int player) {
        return new Node(board, player);
    }

    public int minimax(Node node, int depth, boolean maximizing, int alpha, int beta) {
        if (depth == 0 || node.getBoard().isFull() || node.getBoard().checkWin(1) || node.getBoard().checkWin(2)) {
            return evaluateBoard(node.getBoard());
        }

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < node.getBoard().getWidth(); col++) {
                if (node.getBoard().isLegalMove(col)) {
                    Board newBoard = node.getBoard().clone();
                    newBoard.makeMove(col, node.getPlayer());

                    Node child = new Node(newBoard, 3 - node.getPlayer(), col);
                    node.addChild(child, col);
                    int eval = minimax(child, depth - 1, false, alpha, beta);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            node.setValue(maxEval);
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < node.getBoard().getWidth(); col++) {
                if (node.getBoard().isLegalMove(col)) {
                    Board newBoard = node.getBoard().clone();
                    newBoard.makeMove(col, node.getPlayer());

                    Node child = new Node(newBoard, 3 - node.getPlayer(), col);
                    node.addChild(child, col);

                    int eval = minimax(child, depth - 1, true, alpha, beta);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            node.setValue(minEval);
            return minEval;
        }
    }

    public int findBestMove(Board currentBoard, int difficulty, int aiPlayer) {
        int maxDepth = difficulty;
        root = new Node(currentBoard, aiPlayer);
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int col = 0; col < currentBoard.getWidth(); col++) {
            if (currentBoard.isLegalMove(col)) {
                Board newBoard = currentBoard.clone();
                newBoard.makeMove(col, aiPlayer);
                Node child = new Node(newBoard, 3 - aiPlayer, col);
                root.addChild(child, col);

                int moveValue = minimax(child, maxDepth - 1, false, alpha, beta);
                if (moveValue > bestValue) {
                    bestValue = moveValue;
                    bestMove = col;
                }
                alpha = Math.max(alpha, moveValue);
            }
        }
        return bestMove;
    }

    private int evaluateBoard(Board board) {
        int score = 0;
        int aiPlayer = root.getPlayer();
        int opponentPlayer = 3 - aiPlayer;

        if (board.checkWin(aiPlayer)) {
            return WINNING_SCORE;
        } else if (board.checkWin(opponentPlayer)) {
            return -WINNING_SCORE;
        }
        // Если игра не окончена, возвращаем нейтральную оценку
        // TODO: Добавить более сложную оценочную функцию
        return score;
    }

    private int evaluateLine(Board board, int player, int count, boolean isOpen) {
        // TODO: [Priority] Доделать функцюи evaluateLine для усложения работы дерева и работы его с промежуточными условиями
        return 0;
    }

    public Board getBoard(@NotNull Node node) {
        return node.getBoard();

    }
}
