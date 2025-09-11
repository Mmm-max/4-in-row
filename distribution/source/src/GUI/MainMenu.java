package GUI;

import game.LocalGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


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
            System.out.println("AI play");
            startAIGame(primaryStage);
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
        // проверка, чтобы не было пустых строк или повторяющихся имен
        player1 = !player1.isEmpty() ? player1 : "Player 1";
        player2 = !player2.isEmpty() ? player2 : "Player 2";
        if (player1 == player2) {
            System.out.println("Ошибка, у обоих одинаковые имена");
            player1 = "Player 1";
            player2 = "Player 2";
        }
        String player1Name = player1;
        String player2Name = player2;
        ConnectFourGUI gui = new ConnectFourGUI(player1, player2);
        gui.start(new Stage());
//        primaryStage.close();

        new Thread(() -> {
            System.out.println("start local game logic");
            LocalGame localGame = new LocalGame(gui, player1Name, player2Name);
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

    private void startAIGame(Stage primaryStage) {
        System.out.println("AI game");
        ChooseAIDifficultLevel chooseAIDifficultLevel = new ChooseAIDifficultLevel();
        chooseAIDifficultLevel.start(new Stage());
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
