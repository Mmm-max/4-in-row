package game;

public interface CellClickListener {
    void onCellClick(int column);
    void addListener(BoardChangeListener listener);
    void notifyListeners(int x, int y);
}
