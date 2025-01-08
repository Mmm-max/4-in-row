package player;

import game.Board;

import java.util.ArrayList;

public class Node {
    ArrayList<Node> children;
    int value;
    Node parent;
    Board board;
}
