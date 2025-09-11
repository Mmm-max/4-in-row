package player;

import game.Board;
import org.jetbrains.annotations.NotNull;

public class Tree {
    private Node root;


    public Tree(Node node) {
        root = node;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void addChild(@NotNull Node parent, int index, Node child) {
        parent.children[index] = child;
    }

    public void removeChild(@NotNull Node node, int index) {
        node.children[index] = null;
    }

    public Node getChild(@NotNull Node parent, int index) {
        return parent.children[index];
    }

    public int getValue(@NotNull Node node) {
        return node.value;
    }

    public void setValue(@NotNull Node node, int value) {
        node.value = value;
    }

    public Board getBoard(@NotNull Node node) {
        return node.board;
    }
}
