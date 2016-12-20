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
import javafx.scene.layout.VBox;
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

        VBox consumers = new VBox();
        consumers.setSpacing(10);

        consumer1 = new TextArea();
        consumer1.setEditable(false);
        consumer1.setPrefWidth(200);
        consumer1.setPrefRowCount(7);

        consumer2 = new TextArea();
        consumer2.setEditable(false);
        consumer2.setPrefWidth(200);
        consumer2.setPrefRowCount(7);

        consumer3 = new TextArea();
        consumer3.setEditable(false);
        consumer3.setPrefWidth(200);
        consumer3.setPrefRowCount(7);

        consumers.getChildren().add(consumer1);
        consumers.getChildren().add(consumer2);
        consumers.getChildren().add(consumer3);

        gridPane.add(executeTask, 2, 0);
        gridPane.add(hBox, 0, 0, 2, 1);
        gridPane.add(queue, 0, 1);
        gridPane.add(consumers, 2, 1);

        Scene scene = new Scene(gridPane, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createExecuteThread() {
        executeThread = new Thread(() -> {
            while(!taskList.isEmpty()) {
                if(!consumer1Busy) {
                    Task task = taskList.get(0);
                    consumer1.setText(consumer1.getText() + "Executing Task: " + task.message + "\n");
                    consumer1Busy = true;
                    Thread waitThread = new Thread(() -> {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        consumer1.setText(consumer1.getText() + "Execution Done\n");
                        consumer1Busy = false;
                    });
                    waitThread.start();
                    taskList.remove(0);
                    setQueueText();
                } else if(!consumer2Busy) {
                    Task task = taskList.get(0);
                    consumer2.setText(consumer2.getText() + "Executing Task: " + task.message + "\n");
                    Thread waitThread = new Thread(() -> {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        consumer2.setText(consumer1.getText() + "Execution Done\n");
                        consumer2Busy = false;
                    });
                    waitThread.start();
                    consumer2Busy = true;
                    taskList.remove(0);
                    setQueueText();
                } else if(!consumer3Busy) {
                    Task task = taskList.get(0);
                    consumer3.setText(consumer3.getText() + "Executing Task: " + task.message + "\n");
                    Thread waitThread = new Thread(() -> {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        consumer3.setText(consumer3.getText() + "Execution Done\n");
                        consumer3Busy = false;
                    });
                    waitThread.start();
                    consumer3Busy = true;
                    taskList.remove(0);
                    setQueueText();
                }

                System.out.println();
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
