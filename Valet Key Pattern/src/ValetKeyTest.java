import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;

public class ValetKeyTest extends Application {

    Thread thread;

    TextField tokenField;
    TextArea data;

    String token = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Valet Key Pattern");
        stage.setResizable(false);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(25));

        Button saveData = new Button();
        saveData.setText("Save");
        saveData.setOnAction(event -> {
            if(!token.isEmpty()) {
                saveDataToFile();
            }
        });

        gridPane.add(getUserDetailNode(), 0, 0);
        gridPane.add(getDataNode(), 1, 0);
        gridPane.add(saveData, 1, 1);

        Scene scene = new Scene(gridPane, 500, 300);
        stage.setScene(scene);
        stage.show();

        createThread();
    }

    private String getAccessToken(String username, String password) {
        if(!username.isEmpty() && !password.isEmpty()) {
            return getTokenString();
        } else {
            return "";
        }
    }

    private String getTokenString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private void createThread() {
        thread = new Thread(() -> {
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            token = "";
            tokenField.setText(token);
            disableData();
        });
    }

    private Node getUserDetailNode() {
        VBox userDetails = new VBox();
        userDetails.setSpacing(10);
        userDetails.setPadding(new Insets(5));

        Label userNameLabel = new Label("User Name");
        TextField userNameField = new TextField();
        Label userPasswordLabel = new Label("Password");
        PasswordField userPasswordField = new PasswordField();
        Label tokenLabel = new Label("Token");
        tokenField = new TextField();
        tokenField.setEditable(false);
        Button getToken = new Button();
        getToken.setText("Get Access Token");
        getToken.setOnAction(event -> {
            token = getAccessToken(userNameField.getText(), userPasswordField.getText());
            tokenField.setText(token);
            enableData();
            thread.start();
        });

        userDetails.getChildren().add(userNameLabel);
        userDetails.getChildren().add(userNameField);
        userDetails.getChildren().add(userPasswordLabel);
        userDetails.getChildren().add(userPasswordField);
        userDetails.getChildren().add(tokenLabel);
        userDetails.getChildren().add(tokenField);
        userDetails.getChildren().add(getToken);

        return userDetails;
    }

    private Node getDataNode() {
        data = new TextArea();
        data.setPrefWidth(300);
        data.setWrapText(true);
        data.setText(getDataFromFile());
        disableData();
        return data;
    }

    private String getDataFromFile() {
        String data = "";

        FileInputStream reader;

        try {
            reader = new FileInputStream(new File("data.txt"));
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

    private void saveDataToFile() {
        String text = data.getText();

        FileOutputStream writer;

        try {
            writer = new FileOutputStream(new File("data.txt"));
            for(int i = 0 ; i < text.length() ; i++) {
                writer.write(text.charAt(i));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disableData() {
        data.setDisable(true);
    }

    private void enableData() {
        data.setDisable(false);
    }
}
