package player;

import game.Board;
import org.jetbrains.annotations.NotNull;

public class Tree {
    private Node root;

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
        System.out.println("best move: " + bestMove + "best value: " + bestValue);
        return bestMove;
    }

    // В классе Tree.java

    private int evaluateBoard(Board board) {
        int aiPlayer = root.getPlayer();
        int opponentPlayer = 3 - aiPlayer;

        // 1. Сначала проверяем терминальные состояния (победа, поражение)
        if (board.checkWin(aiPlayer)) {
            return WINNING_SCORE;
        }
        if (board.checkWin(opponentPlayer)) {
            return -WINNING_SCORE;
        }
        if (board.isFull()) {
            return DRAW_SCORE;
        }

        // 2. Если игра не закончена, используем эвристическую оценку
        // Оцениваем позицию для AI и вычитаем оценку для оппонента
        return scorePosition(board, aiPlayer) - scorePosition(board, opponentPlayer);
    }

    /**
     * Вспомогательная функция для эвристической оценки доски для одного игрока.
     * Она подсчитывает количество потенциально выигрышных линий.
     * @param board Доска для оценки
     * @param player Игрок, для которого производится оценка
     * @return Числовая оценка "хорошести" позиции
     */
    private int scorePosition(Board board, int player) {
        int score = 0;

        // Оценка центральной колонки (ходы в центре обычно более ценные)
        int centerColumn = board.getWidth() / 2;
        for (int r = 0; r < board.getHeight(); r++) {
            if (board.getGrid()[r][centerColumn] == player) {
                score += 3; // Даем небольшой бонус за контроль центра
            }
        }

        // Оценка горизонтальных, вертикальных и диагональных линий
        // Горизонтальные
        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c <= board.getWidth() - 4; c++) {
                int[] line = new int[4];
                for (int i = 0; i < 4; i++) {
                    line[i] = board.getGrid()[r][c + i];
                }
                score += evaluateLine(line, player);
            }
        }

        // Вертикальные
        for (int c = 0; c < board.getWidth(); c++) {
            for (int r = 0; r <= board.getHeight() - 4; r++) {
                int[] line = new int[4];
                for (int i = 0; i < 4; i++) {
                    line[i] = board.getGrid()[r + i][c];
                }
                score += evaluateLine(line, player);
            }
        }

        // Диагональные (положительный наклон)
        for (int r = 0; r <= board.getHeight() - 4; r++) {
            for (int c = 0; c <= board.getWidth() - 4; c++) {
                int[] line = new int[4];
                for (int i = 0; i < 4; i++) {
                    line[i] = board.getGrid()[r + i][c + i];
                }
                score += evaluateLine(line, player);
            }
        }

        // Диагональные (отрицательный наклон)
        for (int r = 3; r < board.getHeight(); r++) {
            for (int c = 0; c <= board.getWidth() - 4; c++) {
                int[] line = new int[4];
                for (int i = 0; i < 4; i++) {
                    line[i] = board.getGrid()[r - i][c + i];
                }
                score += evaluateLine(line, player);
            }
        }

        return score;
    }

    /**
     * Оценивает одну линию из 4 ячеек.
     * @param line Массив из 4 ячеек
     * @param player Игрок, для которого оцениваем
     * @return Оценка линии
     */
    private int evaluateLine(int[] line, int player) {
        int score = 0;
        int playerCount = 0;
        int emptyCount = 0;
        int opponentPlayer = 3 - player;
        int opponentCount = 0;

        for (int piece : line) {
            if (piece == player) {
                playerCount++;
            } else if (piece == 0) { // Предполагаем, что 0 - это пустая ячейка
                emptyCount++;
            } else {
                opponentCount++;
            }
        }

        // Блокируем немедленный выигрыш оппонента (очень важно)
        if (opponentCount == 3 && emptyCount == 1) {
            return -50;
        }

        // Пытаемся выиграть сами
        if (playerCount == 3 && emptyCount == 1) {
            score += 100; // Очень высокая оценка за 3 в ряд
        } else if (playerCount == 2 && emptyCount == 2) {
            score += 10; // Средняя оценка за 2 в ряд
        }

        return score;
    }

    public Board getBoard(@NotNull Node node) {
        return node.getBoard();

    }
}
