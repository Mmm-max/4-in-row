package GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.Button;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class ChoosePlayers extends Application {

    private String player1;
    private String player2;
    private List<ChoosePlayersListener> listeners = new ArrayList<>();

    public void addListener(ChoosePlayersListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ChoosePlayersListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (ChoosePlayersListener listener : listeners) {
            listener.onPlayersChosen(player1, player2);
        }
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Choose players");

        TextField textPlayer1 = new TextField();
        Label labelPlayer1 = new Label("Player 1");
        textPlayer1.setPromptText("Player 1");
        TextField textPlayer2 = new TextField();
        Label labelPlayer2 = new Label("Player 2");

        VBox player1Layout = new VBox(10);
        player1Layout.getChildren().addAll(labelPlayer1, textPlayer1);

        VBox player2Layout = new VBox(10);
        player2Layout.getChildren().addAll(labelPlayer2, textPlayer2);

        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(e -> {
            player1 = textPlayer1.getText();
            player2 = textPlayer2.getText();
            System.out.println("Player 1: " + player1 + " Player 2: " + player2);
            notifyListeners();
            primaryStage.close();
        });

        HBox layoutTextField = new HBox(10);
        layoutTextField.getChildren().addAll(player1Layout, player2Layout);
        layoutTextField.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(layoutTextField, acceptButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String getplayer1() {
        return player1;
    }

    public String getplayer2() {
        return player2;
    }
}
