import GUI.ConnectFourGUI;
import GUI.MainMenu;
import game.BoardTest;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args)  {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainMenu mainMenu = new MainMenu();
        try {
            mainMenu.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}