package GUI;

import game.LocalGame;
import javafx.application.Application;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

public class MainMenu extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main menu");

        Button localGameButtom =  new Button("Local game");
        Button playWithAIButton = new Button("Play with AI");
        Button newtworkPlayButton = new Button("Network Play");

        localGameButtom.setOnAction(e -> {
            startLocalGame();
            System.out.println("Local game");
        });
        playWithAIButton.setOnAction(e -> {
            // realisation of AIplay
            System.out.println("AIPlay");
        });
        newtworkPlayButton.setOnAction(e -> {
            // realisation of network game
            System.out.println("netwrok game");
        });


        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(localGameButtom, playWithAIButton, newtworkPlayButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startLocalGame() {
        System.out.println("Local game");
        ConnectFourGUI gui = new ConnectFourGUI();
        gui.start(new Stage());

        new Thread(() -> {
            System.out.println("start local game logic");
            LocalGame localGame = new LocalGame(gui);
            localGame.start();
        }).start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
