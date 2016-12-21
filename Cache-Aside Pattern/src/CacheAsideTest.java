import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CacheAsideTest extends Application {

    TextArea log;
    TextArea data;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Competing Consumers Pattern");
        primaryStage.setResizable(false);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25));

        Button getTextButton = new Button();
        getTextButton.setText("Get Data");
        getTextButton.setOnAction(event -> {
            getData();
        });

        data = new TextArea();
        data.setPrefWidth(450);
        data.setEditable(false);
        data.setPrefRowCount(10);

        log = new TextArea();
        log.setPrefWidth(450);
        log.setEditable(false);
        log.setPrefRowCount(5);

        gridPane.add(getTextButton, 0, 0);
        gridPane.add(data, 0, 1);
        gridPane.add(log, 0, 2);

        Scene scene = new Scene(gridPane, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void getData() {

    }
}
