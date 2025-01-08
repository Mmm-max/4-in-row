package player;

import game.Board;
import org.jetbrains.annotations.NotNull;

public class Tree {
    private Node root;


    public Tree() {
        root = new Node();
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void addChild(@NotNull Node node) {
        node.children.add(node);
    }

    public void removeChild(@NotNull Node node) {
        node.children.remove(node);
    }

    public Node getChild(@NotNull Node node) {
        return node.children.get(0);
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
