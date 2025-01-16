package player;

import game.Board;

import java.util.ArrayList;

public class Node {
    Node[] children = new Node[6];
    int value;
    Node parent;
    Board board;
    int move;
    int player = 1;

    public Node(Board board) {
        this.board = board;
        value = 0;
    }

    public Node(Board board, int player) {
        this.board = board;
        this.player = player;
        this.value = 0;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
