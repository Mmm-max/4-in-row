package player;

import game.Board;

public class Node {
    Node[] children = new Node[7];
    int value;
    Node parent;
    Board board;
    int move;
    int player;

    public Node(Board board) {
        this.board = board;
        value = 0;
    }

    public Node(Board board, int player) {
        this.board = board;
        this.player = player;
        this.value = 0;
    }

    public Node(Board board, int player, int move) {
        this.board = board;
        this.player = player;
        this.move = move;
        this.value = 0;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void addChild(Node child, int columnIndex) {
        if (columnIndex >= 0 && columnIndex < children.length) {
            children[columnIndex] = child;
            child.parent = this;
            child.move = columnIndex;
        }
    }

    public Node[] getChildren() {
        return children;
    }

    public boolean hasChildren() {
        for (Node child: children) {
            if (child != null) { return true;}
        }
        return false;
    }

    public Node getChild(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < children.length) {
            return children[columnIndex];
        }
        return null;
    }

    public int getChildrenCount() {
        int count = 0;
        for (Node child: children) {
            if (child != null) count++;
        }
        return count;
    }

    public boolean isLeaf() {
        return !hasChildren();
    }

    public Board getBoard() {return board;}
    public int getMove() {return move;}
    public int getPlayer() {return player;}
    public Node getParent() {return parent;}
}
