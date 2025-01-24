package GUI;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
//    private int x = 0;
//    private int y = 0;
//    private int player = 0;
    private List<CellClickListener> listeners = new ArrayList<>();
    private String name;
    private String player1 = "Player 1";
    private String player2 = "Player 2";
    private List<GameRestartListener> restartListeners = new ArrayList<>();

    private Label player1Count;
    private Label player2Count;

    ConnectFourGUI(String player1, String player2) {
        this.name = "Connect four";
        this.player1 = player1;
        this.player2 = player2;
    }
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

        // player 1
        Label player1Label = new Label(player1);
//        player1Label.setAlignment(Pos.TOP_CENTER);
        player1Label.setStyle("-fx-font-size: 20px");
        player1Count = new Label("0");
        player1Count.setStyle("-fx-font-size: 20px");
        VBox player1Layout = new VBox(10);
        player1Layout.setAlignment(Pos.TOP_CENTER);
        player1Layout.getChildren().addAll(player1Label, player1Count);

        // player 2
        Label player2Label = new Label(player2);
//        player2Label.setAlignment(Pos.TOP_CENTER);
        player2Label.setStyle("-fx-font-size: 20px");
        player2Count = new Label("0");
        player2Count.setStyle("-fx-font-size: 20px");
        VBox player2Layout = new VBox(10);
        player2Layout.setAlignment(Pos.TOP_CENTER);
        player2Layout.getChildren().addAll(player2Label, player2Count);

        HBox layout = new HBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(player1Layout, gridPane, player2Layout);

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updatePlayer1Count(Integer wins) {
        System.out.println("updatePlayer1Count: " + wins);
        player1Count.setText(wins.toString());
    }

    public void updatePlayer2Count(Integer wins) {
        player2Count.setText(wins.toString());
    }
    public void drawMove(int x, int y, int player) {
        System.out.println("Draw move: x: " + x + " y: " + y + " player: " + player);
        Circle circle = new Circle(30);
        circle.setFill(player == 1 ? Color.RED : Color.YELLOW);
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
    
    @Override
    public void onBoardChange(int x, int y, int player) {
        Platform.runLater(() ->drawMove(x, y, player));
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

    public void victoryWindow(String name) {
        Stage newStage = new Stage();

        Button restartButton = new Button("Restart");
        Button exitButton = new Button("Exit");

        restartButton.setOnAction(e -> {
            newStage.close();
            restart();
        });

        exitButton.setOnAction(e -> {
            exit();
        });

        StackPane root = new StackPane();
        root.getChildren().add(new Label("Победил " + name));

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.BOTTOM_CENTER);
        buttonLayout.setPadding(new Insets(20, 20, 20, 20));
        buttonLayout.getChildren().addAll(restartButton, exitButton);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(root, buttonLayout);

        Scene scene = new Scene(layout, 200, 100);

        newStage.setScene(scene);
        newStage.show();
    }

    private void restart() {
        notifyRestartListeners();

    }

    private void exit() {
        for (GameRestartListener listener : restartListeners) {
            listener.exit();
        }
        Platform.exit();
    }

    public void addRestartListeners(GameRestartListener listener) {
        restartListeners.add(listener);
    }

    private void notifyRestartListeners() {
        for (GameRestartListener listener : restartListeners) {
            listener.gameRestart();
        }
    }

    public void clear() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                StackPane cell = (StackPane) gridPane.getChildren().get(i * COLUMNS + j);
                cell.getChildren().clear();
            }
        }
    }

    public void drawWindow() {
        Stage newStage = new Stage();

        StackPane root = new StackPane();
        root.getChildren().add(new Label("Ничья"));
        Scene scene = new Scene(root, 200, 100);

        newStage.setScene(scene);
        newStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
