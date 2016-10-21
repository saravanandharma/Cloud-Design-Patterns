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
import java.util.List;

public class RetryPatternTest extends Application {

    static List<Task> tasks;

    TextArea queue;
    TextArea consumer;
    TextArea errorLog;

    Thread consumerThread;
    Thread waitThread;

    boolean executing = false;
    boolean waiting = false;

    public static void main(String[] args) {
        tasks = new ArrayList<>();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Retry Pattern");
        stage.setResizable(false);

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
                tasks.add(task);
                queue.setText(queue.getText() + task.getName() + "\n");
            }
            taskNameField.setText("");
        });

        Button executeTask = new Button();
        executeTask.setText("Execute Task");
        executeTask.setOnAction(event -> {
            if(!tasks.isEmpty()) {
                if (!executing) {
                    createConsumerThread();
                    consumerThread.start();
                } else if(!waiting){
                    createWaitThread();
                    waitThread.start();
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

        consumer = new TextArea();
        consumer.setEditable(false);
        consumer.setPrefWidth(200);
        consumer.setPrefRowCount(20);

        errorLog = new TextArea();
        errorLog.setEditable(false);
        errorLog.setPrefWidth(200);
        errorLog.setPrefRowCount(20);

        gridPane.add(executeTask, 0, 0);
        gridPane.add(hBox, 1, 0, 2, 1);
        gridPane.add(queue, 0, 1);
        gridPane.add(consumer, 1, 1);
        gridPane.add(errorLog, 2, 1);

        Scene scene = new Scene(gridPane, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void createConsumerThread() {
        consumerThread = new Thread(() -> {
            if(!executing && !tasks.isEmpty()) {
                executing = true;
                setConsumerText();
                setQueueText();

                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executing = false;
                consumer.setText(consumer.getText() + "Execution completed\n");
            }
        });
    }

    private void createWaitThread() {
        waitThread = new Thread(() -> {
            int waitTime = 2;
            waiting = true;
            while(executing) {
                errorLog.setText(errorLog.getText() + "Consumer is busy.\nWaiting for " + waitTime + " seconds...\n");
                try {
                    Thread.sleep(waitTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                waitTime++;
            }
            createConsumerThread();
            consumerThread.start();
            waiting = false;
        });
    }

    private void setConsumerText() {
        consumer.setText(consumer.getText() + "Executing: " + tasks.remove(0).getName() + "...\n");
    }

    private void setQueueText() {
        queue.setText("");
        for(Task task : tasks) {
            queue.setText(queue.getText() + task.getName() + "\n");
        }
    }
}
