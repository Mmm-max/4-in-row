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

public class MainMenu extends Application implements ChoosePlayersListener {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main menu");

        Button localGameButtom =  new Button("Local game");
        Button playWithAIButton = new Button("Play with AI");
        Button newtworkPlayButton = new Button("Network Play");
        Button exitButton = new Button("Exit");

        localGameButtom.setOnAction(e -> {
            startLocalGame(primaryStage);
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

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });


        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(localGameButtom, playWithAIButton, newtworkPlayButton, exitButton);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void onPlayersChosen(String player1, String player2) {
        ConnectFourGUI gui = new ConnectFourGUI();
        gui.start(new Stage());
//        primaryStage.close();

        new Thread(() -> {
            System.out.println("start local game logic");
            LocalGame localGame = new LocalGame(gui, player1, player2);
            localGame.start();
        }).start();
    }

    private void startLocalGame(Stage primaryStage) {
        System.out.println("Local game");
        ChoosePlayers choosePlayers = new ChoosePlayers();
        choosePlayers.addListener(this);
        choosePlayers.start(new Stage());
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
