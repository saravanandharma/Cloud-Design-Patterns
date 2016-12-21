
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class HealthEndpointMonitoringTest extends Application {

    Service service1;
    Service service2;
    Service service3;

    TextArea serviceNode1;
    TextArea serviceNode2;
    TextArea serviceNode3;

    TextArea healthMonitor;

    Thread monitorThread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Competing Consumers Pattern");
        primaryStage.setResizable(false);

        service1 = new Service();
        service2 = new Service();
        service3 = new Service();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25));

        serviceNode1 = new TextArea();
        serviceNode1.setEditable(false);
        serviceNode1.setPrefWidth(150);

        serviceNode2 = new TextArea();
        serviceNode2.setEditable(false);
        serviceNode2.setPrefWidth(150);

        serviceNode3 = new TextArea();
        serviceNode3.setEditable(false);
        serviceNode3.setPrefWidth(150);

        healthMonitor = new TextArea();
        healthMonitor.setEditable(false);
        healthMonitor.setPrefWidth(150);
        setHealthMonitorText();

        gridPane.add(healthMonitor, 0, 0, 3, 1);
        gridPane.add(serviceNode1, 0, 1);
        gridPane.add(serviceNode2, 1, 1);
        gridPane.add(serviceNode3, 2, 1);

        Scene scene = new Scene(gridPane, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        createMonitorThread();
        monitorThread.start();
    }

    public void createMonitorThread() {
        monitorThread = new Thread(() -> {
            Random random = new Random();
            while(true) {
                if(random.nextInt() % 3 == 0) {
                    if(service1.currentState == Service.State.ERROR) {
                        service1.working();
                    } else {
                        service1.errored();
                    }
                } else if (random.nextInt() % 4 == 0) {
                    if(service2.currentState == Service.State.ERROR) {
                        service2.working();
                    } else {
                        service2.errored();
                    }
                } else if (random.nextInt() % 7 == 0) {
                    if(service3.currentState == Service.State.ERROR) {
                        service3.working();
                    } else {
                        service3.errored();
                    }
                }
                setHealthMonitorText();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setHealthMonitorText() {
        String service1State;
        String service2State;
        String service3State;

        if(service1.currentState == Service.State.WORKING) {
            service1State = "Working";
        } else {
            service1State = "Error";
        }

        if(service2.currentState == Service.State.WORKING) {
            service2State = "Working";
        } else {
            service2State = "Error";
        }

        if(service3.currentState == Service.State.WORKING) {
            service3State = "Working";
        } else {
            service3State = "Error";
        }

        healthMonitor.setText("Service 1: " + service1State + "\nService 2: " + service2State + "\nService 3: " + service3State);
        serviceNode1.setText(service1State);
        serviceNode2.setText(service2State);
        serviceNode3.setText(service3State);
    }
}