import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityQueueTest extends Application {

    static PriorityQueue<Task> priorityQueue;

    TextArea taskQueue;
    TextArea consumerList;

    Thread thread;
    boolean running = false;

    public static void main(String[] args) {
        priorityQueue = new PriorityQueue<>(new TaskComparator());

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Priority Queue Pattern");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25));

        HBox createTask = new HBox();
        createTask.setSpacing(10);

        Label taskNameLabel = new Label("Task Name");
        taskNameLabel.setPadding(new Insets(3, 0, 2, 0));
        TextField taskNameField = new TextField();
        Label taskPriorityLabel = new Label("Task Priority");
        taskPriorityLabel.setPadding(new Insets(3, 0, 2, 0));
        TextField taskPriorityField = new TextField();

        Button createTaskButton = new Button();
        createTaskButton.setText("Create Task");
        createTaskButton.setOnAction(event -> {
            addNewTask(taskNameField.getText(), taskPriorityField.getText());
            taskNameField.setText("");
            taskPriorityField.setText("");
        });

        createTask.getChildren().add(taskNameLabel);
        createTask.getChildren().add(taskNameField);
        createTask.getChildren().add(taskPriorityLabel);
        createTask.getChildren().add(taskPriorityField);
        createTask.getChildren().add(createTaskButton);

        HBox taskList = new HBox();
        taskList.setSpacing(10);
        taskList.setPadding(new Insets(10));

        taskQueue = new TextArea();
        taskQueue.setEditable(false);
        taskQueue.setPrefRowCount(50);
        taskQueue.setPrefWidth(300);

        consumerList = new TextArea();
        consumerList.setEditable(false);
        consumerList.setPrefRowCount(50);
        consumerList.setPrefWidth(300);

        taskList.getChildren().add(taskQueue);
        taskList.getChildren().add(consumerList);

        gridPane.add(createTask, 0, 0);
        gridPane.add(taskList, 0, 1);

        Scene scene = new Scene(gridPane, 640, 480);
        stage.setScene(scene);
        stage.show();

        startThread();
    }

    @Override
    public void stop() throws Exception {
        stopThread();
        super.stop();
    }

    private void addNewTask(String name, String priority) {
        int taskPriority = Integer.parseInt(priority);

        priorityQueue.add(new Task(name, taskPriority));

        setTaskQueueText();
    }

    private void setTaskQueueText() {
        List<Task> tasks = new ArrayList<>();

        String text = "";

        while(!priorityQueue.isEmpty()) {
            Task task = priorityQueue.remove();
            text = text + task.toString() + "\n";
            tasks.add(task);
        }

        taskQueue.setText(text);

        priorityQueue.addAll(tasks);
    }

    private void startThread() {
        thread = new Thread(() -> {
            while(running) {
                if(priorityQueue != null) {
                    if (!priorityQueue.isEmpty()) {
                        Task task = priorityQueue.remove();
                        consumerList.setText(task.toString() + "\n" + consumerList.getText());
                        setTaskQueueText();
                    } else {
                        consumerList.setText("No Task To Execute..." + "\n" + consumerList.getText());
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        running = true;
        thread.start();
    }

    private void stopThread() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
