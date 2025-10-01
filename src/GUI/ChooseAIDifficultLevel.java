package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import player.AIPlayer;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class ChooseAIDifficultLevel extends Application {

    private List<ChooseAIDifficultyListener> listeners = new ArrayList<>();
    private int difficult;
    public void addListener(ChooseAIDifficultyListener listener) {listeners.add(listener);}
    public void removeListener(ChooseAIDifficultyListener listener) {listeners.remove(listener);}
    private void notifyListeners() {
        for (ChooseAIDifficultyListener listener : listeners) {
            listener.onAIDifficultyChosen(difficult);
        }
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Choose difficult level");

        Button easyButton = new Button("easy");
        Button normalButton = new Button("normal");
        Button hardButton = new Button("hard");

        easyButton.setOnAction(e -> {
            startAIGame(primaryStage, 1);
            System.out.println("easy difficult level was choosen");
        });
        normalButton.setOnAction(e -> {
            startAIGame(primaryStage, 3);
            System.out.println("normal difficult level was choosen");
        });
        hardButton.setOnAction(e -> {
            startAIGame(primaryStage, 5);
            System.out.println("hard difficult level was choosen");
        });

        Button backButton = new Button("back");
        backButton.setOnAction(e -> backToMenu(primaryStage));

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(easyButton, normalButton, hardButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startAIGame(Stage primaryStage, int difficulty) {
        this.difficult = difficulty;
        notifyListeners();
        System.out.println("starting AI game");
        primaryStage.close();
    }

    private void backToMenu(Stage primaryStage) {
        try {
            MainMenu mainMenu = new MainMenu();
            mainMenu.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
