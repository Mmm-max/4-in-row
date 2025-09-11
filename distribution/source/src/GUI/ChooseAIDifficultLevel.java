package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

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

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(easyButton, normalButton, hardButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startEasyAi(Stage primaryStage) {
        // pass
        primaryStage.close();
    }
    private void startMediumAi(Stage primaryStage) {
        // pass
        primaryStage.close();
    }
    private void startHardAi(Stage primaryStage) {
        // pass
        primaryStage.close();
    }
}
