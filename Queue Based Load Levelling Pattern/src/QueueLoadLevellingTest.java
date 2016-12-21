
/**
 * Created by Confiz-234 on 12/21/2016.
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

public class QueueLoadLevellingTest extends Application {

    static ArrayList<Task> taskList;

    TextArea queue;
    TextArea consumer;

    Thread executeThread;

    boolean processing = false;

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
                setQueueText();
            }
            taskNameField.setText("");
        });

        hBox.getChildren().add(taskNameLabel);
        hBox.getChildren().add(taskNameField);
        hBox.getChildren().add(createTask);

        queue = new TextArea();
        queue.setEditable(false);
        queue.setPrefWidth(200);
        queue.setPrefRowCount(20);

        consumer = new TextArea();
        consumer.setEditable(false);
        consumer.setPrefWidth(200);
        consumer.setPrefRowCount(20);

        gridPane.add(hBox, 0, 0, 2, 1);
        gridPane.add(queue, 0, 1);
        gridPane.add(consumer, 1, 1);

        Scene scene = new Scene(gridPane, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        createExecuteThread();
        executeThread.start();
    }

    private void createExecuteThread() {
        executeThread = new Thread(() -> {
            while(true) {
                synchronized (taskList) {
                    if (!processing && !taskList.isEmpty()) {
                        Task task = taskList.get(0);
                        consumer.setText(consumer.getText() + "Executing Task: " + task.message + "\n");
                        processing = true;
                        Thread waitThread = new Thread(() -> {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            processing = false;
                            consumer.setText(consumer.getText() + "Execution Done\n");
                        });
                        waitThread.start();
                        taskList.remove(0);
                        setQueueText();
                    }
                }
            }
        });
    }

    private void setQueueText() {
        queue.setText("");
        for(Task task : taskList) {
            queue.setText(queue.getText() + task.message + "\n");
        }
    }
}