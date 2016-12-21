import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GatekeeperTest extends Application {

    TextArea data;
    ImageView imageView;

    TextField userNameField;
    PasswordField userPasswordField;

    static Gatekeeper gatekeeper;

    public static void main(String[] args) {
        gatekeeper = new Gatekeeper();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gatekeeper Pattern");
        stage.setResizable(false);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(25));

        Button getTextButton = new Button();
        getTextButton.setText("Get Text");
        getTextButton.setOnAction(event -> {
            if(gatekeeper.verifyCredentials(userNameField.getText(), userPasswordField.getText())) {
                data.setText(getDataFromFile(gatekeeper.getFileUrL()));
            }
        });

        gridPane.add(getUserDetailNode(), 0, 0);
        gridPane.add(getDataNode(), 1, 0);
        gridPane.add(getTextButton, 0, 2);

        Scene scene = new Scene(gridPane, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    private Node getUserDetailNode() {
        VBox userDetails = new VBox();
        userDetails.setSpacing(10);
        userDetails.setPadding(new Insets(5));

        Label userNameLabel = new Label("User Name");
        userNameField = new TextField();
        Label userPasswordLabel = new Label("Password");
        userPasswordField = new PasswordField();

        userDetails.getChildren().add(userNameLabel);
        userDetails.getChildren().add(userNameField);
        userDetails.getChildren().add(userPasswordLabel);
        userDetails.getChildren().add(userPasswordField);

        return userDetails;
    }

    private Node getDataNode() {
        data = new TextArea();
        data.setPrefWidth(250);
        data.setPrefRowCount(5);
        data.setWrapText(true);
        return data;
    }

    private String getDataFromFile(String fileUrl) {
        String data = "";

        FileInputStream reader;

        try {
            reader = new FileInputStream(new File(fileUrl));
            int size = reader.available();

            for(int i = 0 ; i < size ; i++) {
                data += ((char) reader.read());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
