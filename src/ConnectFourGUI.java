import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ConnectFourGUI extends javafx.application.Application {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private GridPane gridPane;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connect Four");

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-background-color: lightblue; -fx-border-color: black;");
                cell.setPrefSize(80, 80);
                gridPane.add(cell, col, row);
                int finalCol = col;
                cell.setOnMouseClicked(event ->handleMouseClick(finalCol));
            }
        }

        Scene scene= Scene(gridPane);

    }
}
