package player;
import game.Board;
import GUI.*;

public abstract class Player {
    private String name;
    private int playerNumber;
    private int winsCnt = 0;
    private Board boardListener;
    private ConnectFourGUI GUIListener;

    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.winsCnt = 0;
    }

    public String getName() {
        return name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getWinsCnt() {
        return winsCnt;
    }

    public void incrementWinsCnt() {
        winsCnt++;
    }

    public void setWinsCnt(int winsCnt) {
        this.winsCnt = winsCnt;
    }

    public abstract int getMove(Board board);

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

    public void setName(String name) {
        this.name = name;
    }


}
