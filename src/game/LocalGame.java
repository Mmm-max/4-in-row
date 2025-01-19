package game;

import GUI.ConnectFourGUI;
import javafx.application.Platform;
import javafx.stage.Stage;
import player.*;

import java.util.concurrent.CountDownLatch;


public class LocalGame {
    private Board board;
    private ConnectFourGUI gui;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int movesCnt = 0;

    // two players
    public LocalGame(ConnectFourGUI gui) {
        System.out.println("create local game");
        board = new Board();
        this.gui = gui;
        board.addListener(gui);
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

    public Board getBoard() {
        return board;
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
                Platform.runLater(() -> gui.victoryWindow(currentPlayer.getName()));
                break;
            } else if (board.isFull(movesCnt) == 1) {
                System.out.println("It's a draw!");
                Platform.runLater(() -> gui.drawWindow());
                break;
            }
            switchPlayer();
        }
    }

    public static void main(String[] args) {
        // tests
        // Запуск GUI в отдельном потоке
        CountDownLatch latch = new CountDownLatch(1);
        ConnectFourGUI gui = new ConnectFourGUI();
        Platform.startup(() -> {
            try {
                Stage primaryStage = new Stage();
                gui.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        });

        try {
            Thread.sleep(500); // Пауза в полсекунды
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Запуск игровой логики в основном потоке
        new Thread(() -> {
            System.out.println("start game");
            LocalGame game = new LocalGame(gui);
            game.start();
        }).start();
    }
}
