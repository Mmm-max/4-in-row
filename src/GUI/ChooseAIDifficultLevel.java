package GUI;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;

public class ChooseAIDifficultLevel extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Choose difficult level");

        Button easyButton = new Button("easy");
        Button normalButton = new Button("normal");
        Button hardButton = new Button("hard");

        easyButton.setOnAction(e -> {
            startEasyAi(primaryStage);
            System.out.println("easy difficult level was choosen");
        });
        normalButton.setOnAction(e -> {
            startMediumAi(primaryStage);
            System.out.println("normal difficult level was choosen");
        });
        hardButton.setOnAction(e -> {
            startHardAi(primaryStage);
            System.out.println("hard difficult level was choosen");
        });

    }

    private void startEasyAi(Stage primaryStage) {
        // pass
    }
    private void startMediumAi(Stage primaryStage) {
        // pass
    }
    private void startHardAi(Stage primaryStage) {
        // pass
    }
}
