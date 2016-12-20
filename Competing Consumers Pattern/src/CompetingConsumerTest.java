/**
 * Created by Bilal on 12/21/2016.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CompetingConsumerTest extends Application {

    static ArrayList<Task> taskList;

    TextArea queue;
    TextArea consumer1;
    TextArea consumer2;
    TextArea consumer3;

    boolean consumer1Busy = false;
    boolean consumer2Busy = false;
    boolean consumer3Busy = false;

    Thread executeThread;

    public static void main(String[] args) {
        taskList = new ArrayList<>();
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

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        Label taskNameLabel = new Label("Task Name");
        taskNameLabel.setPadding(new Insets(3, 0, 0, 0));
        TextField taskNameField = new TextField();
        taskNameField.setPrefWidth(145);

        Button createTask = new Button();
        createTask.setText("Create Task");
        createTask.setOnAction(event -> {
            if(taskNameField.getText().trim().length() > 0) {
                Task task = new Task(taskNameField.getText().trim());
                taskList.add(task);
                queue.setText(queue.getText() + task.message + "\n");
            }
            taskNameField.setText("");
        });

        Button executeTask = new Button();
        executeTask.setText("Execute Tasks");
        executeTask.setOnAction(event -> {
            if(!taskList.isEmpty()) {
                if(executeThread == null) {
                    createExecuteThread();
                    executeThread.start();
                }
            }
        });

        hBox.getChildren().add(taskNameLabel);
        hBox.getChildren().add(taskNameField);
        hBox.getChildren().add(createTask);

        queue = new TextArea();
        queue.setEditable(false);
        queue.setPrefWidth(200);
        queue.setPrefRowCount(20);

        gridPane.add(executeTask, 2, 0);
        gridPane.add(hBox, 0, 0, 2, 1);
        gridPane.add(queue, 0, 1);

        Scene scene = new Scene(gridPane, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createExecuteThread() {
        executeThread = new Thread(() -> {
            while(!taskList.isEmpty()) {
                if(!consumer1Busy) {
                    Task task = taskList.get(0);
                    consumer1.setText(task.message + "\n");
                    //Start wait thread for consumer 1
                    taskList.remove(task);
                } else if(!consumer2Busy) {
                    Task task = taskList.get(0);
                    consumer2.setText(task.message + "\n");
                    //Start wait thread for consumer 2
                    taskList.remove(task);
                } else if(!consumer3Busy) {
                    Task task = taskList.get(0);
                    consumer3.setText(task.message + "\n");
                    //Start wait thread for consumer 3
                    taskList.remove(task);
                }
            }
        });
    }
}
