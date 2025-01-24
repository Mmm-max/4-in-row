package game;

import GUI.ConnectFourGUI;
import javafx.application.Platform;
import javafx.stage.Stage;
import player.*;

import java.util.concurrent.CountDownLatch;


public class LocalGame implements GameRestartListener {
    private Board board;
    private ConnectFourGUI gui;
    private HumanPlayer player1;
    private HumanPlayer player2;
    private HumanPlayer currentPlayer;
    private int movesCnt = 0;
    private boolean isRunning = true;
    private Thread gameThread;

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
        gui.addRestartListeners(this);
        currentPlayer = player1;
    }

    public LocalGame(ConnectFourGUI gui, String player1Name, String player2Name) {
        this(gui);
        this.player1.setName(player1Name);
        this.player2.setName(player2Name);
    }

    @Override
    public void gameRestart() {
        System.out.println("restart game");
        board.clear();
        Platform.runLater(() -> gui.clear());
        System.out.println("restart game player1 wins: " + player1.getWinsCnt() + " player2 wins: " + player2.getWinsCnt());
        gui.updatePlayer1Count(player1.getWinsCnt());
        gui.updatePlayer2Count(player2.getWinsCnt());
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

    @Override
    public void exit() {
        System.out.println("exit game");
        isRunning = false;
        if (gameThread != null) {
            gameThread.interrupt();
        }
    }

    private void createPlayers(String name1, String name2) {
        player1.setName(name1);
        player2.setName(name2);
    }
    public void start() {
        gameThread = Thread.currentThread();

        while (isRunning) {
            System.out.println(currentPlayer.getName() + "'s turn");
            int move = ((HumanPlayer) currentPlayer).getMoveByGui(board);
            if (move == -1) {
                break;
            }
            board.makeMove(move, currentPlayer.getPlayerNumber());
            board.printBoard();
            movesCnt++;
            if (board.isWinningMove(move, currentPlayer.getPlayerNumber()) == 1) {
                System.out.println(currentPlayer.getName() + " wins!");
                String winner = currentPlayer.getName();
                currentPlayer.incrementWinsCnt();
                Platform.runLater(() -> gui.victoryWindow(winner));

//                break;
            } else if (board.isFull(movesCnt) == 1) {
                System.out.println("It's a draw!");
                Platform.runLater(() -> gui.drawWindow());
//                break;
            }
            switchPlayer();
        }
    }

//    public static void main(String[] args) {
//        // tests
//        // Запуск GUI в отдельном потоке
//        CountDownLatch latch = new CountDownLatch(1);
//        ConnectFourGUI gui = new ConnectFourGUI();
//        Platform.startup(() -> {
//            try {
//                Stage primaryStage = new Stage();
//                gui.start(primaryStage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            latch.countDown();
//        });
//
//        try {
//            Thread.sleep(500); // Пауза в полсекунды
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        // Запуск игровой логики в основном потоке
//        new Thread(() -> {
//            System.out.println("start game");
//            LocalGame game = new LocalGame(gui);
//            game.start();
//        }).start();
//    }
}
