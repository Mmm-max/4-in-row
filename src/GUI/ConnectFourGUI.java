package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import game.*;

import java.util.List;
import java.util.ArrayList;

public class ConnectFourGUI extends javafx.application.Application implements BoardChangeListener {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private GridPane gridPane;
    private int x = 0;
    private int y = 0;
    private int player = 0;
    private List<CellClickListener> listeners = new ArrayList<>();
    private String name;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connect Four");

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));

        for (int row = 0; row < ROWS; row++) {
            System.out.println("Row: " + row);
            for (int col = 0; col < COLUMNS; col++) {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-background-color: lightblue; -fx-border-color: black;");
                cell.setPrefSize(80, 80);
                gridPane.add(cell, col, row);
                int finalCol = col;
                cell.setOnMouseClicked(event ->handleMouseClick(finalCol));
            }
        }

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void drawMove(int x, int y, int player) {
        Circle circle = new Circle(30);
        circle.setFill(player == 0 ? Color.RED : Color.YELLOW);
        StackPane cell = (StackPane) gridPane.getChildren().get(y * COLUMNS + x);
        cell.getChildren().add(circle);
    }
    @Override
    public void addListener(CellClickListener listener) {
        printListeners();
        listeners.add(listener);
        System.out.println("ConnectFourGUI: addListener");
    }
    private void handleMouseClick(int col) {
        System.out.println("Column " + col + " clicked");
        notifyListeners(col);
    }
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void onBoardChange(int x, int y, int player) {
        drawMove(x, y, player);
    }

    @Override
    public void notifyListeners(int x) {
        System.out.println("start notifyListeners");
        printListeners();
        for (CellClickListener listener : listeners) {
            System.out.println("ConnectFourGUI: notifyListeners");
            listener.onCellClick(x);
        }
    }

    @Override
    public void printListeners() {
        System.out.println("ConnectFourGUI: printListeners len:" + listeners.size());
        for (CellClickListener listener : listeners) {
            System.out.println(listener);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
