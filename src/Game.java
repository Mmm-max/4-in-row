import game.*;
import player.*;


public class Game {
    private Board board;
    private ConnectFourGUI gui;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int movesCnt = 0;

    public Game() {
        board = new Board();
        ConnectFourGUI gui = new ConnectFourGUI();
        board.addListener(gui);
        gui.addListener(board);
        player1 = new HumanPlayer("Player 1", 1);
        player2 = new HumanPlayer("Player 2", 2);
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
            int move = currentPlayer.getMove(board);
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
        Game game = new Game();
        game.start();
    }
}
