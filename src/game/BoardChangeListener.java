package game;

public interface BoardChangeListener {
    void onBoardChange(int x, int y, int player);
    void addListener(CellClickListener listener);
    void notifyListeners(int x, int y, int player);
}
