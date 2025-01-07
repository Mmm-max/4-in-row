import GUI.ConnectFourGUI;
import game.*;
import javafx.application.Platform;
import player.*;


public class Game {
    private Board board;
    private ConnectFourGUI gui;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int movesCnt = 0;

    // two players
    public Game() {
        System.out.println("create game");
        board = new Board();
        ConnectFourGUI gui = new ConnectFourGUI();
//        board.addListener(gui);
//        gui.addListener(board);
        player1 = new HumanPlayer("Player 1", 1);
        player2 = new HumanPlayer("Player 2", 2);
        gui.addListener((HumanPlayer) player1);
        gui.addListener((HumanPlayer) player2);
        currentPlayer = player1;
    }

    private void switchPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public void start() {
        while (true) {
            System.out.println(currentPlayer.getName() + "'s turn");
            int move = ((HumanPlayer) currentPlayer).getMoveByGui(board);
            board.makeMove(move, currentPlayer.getPlayerNumber());
            board.printBoard();
            movesCnt++;
            if (board.isWinningMove(move, currentPlayer.getPlayerNumber()) == 1) {
                System.out.println(currentPlayer.getName() + " wins!");
                break;
            } else if (board.isFull(movesCnt) == 1) {
                System.out.println("It's a draw!");
                break;
            }
            switchPlayer();
        }
    }

    public static void main(String[] args) {
        // Запуск GUI в отдельном потоке
        new Thread(() -> {
            ConnectFourGUI.launch(ConnectFourGUI.class);
        }).start();
        try {
            Thread.sleep(500); // Пауза в полсекунды
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Запуск игровой логики в основном потоке
        new Thread(() -> {
            System.out.println("start game");
            Game game = new Game();
            game.start();
        }).start();
    }
}
