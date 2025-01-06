package player;
import game.Board;
import GUI.*;

public abstract class Player {
    private String name;
    private int playerNumber;
    private Board boardListener;
    private ConnectFourGUI GUIListener;

    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }

    public String getName() {
        return name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public abstract int getMoveByConsole(Board board);

//    public abstract int getMoveByGui(Board board);

    private void addBoardListener(Board board) {
        this.boardListener = board;
    }

    private void addGuiListener(ConnectFourGUI gui) {
        this.GUIListener = gui;
    }

    protected abstract void NotifiedBoardListener();
    protected abstract void NotifiedGuiListener();
    protected abstract void GetCellByGui();

}
