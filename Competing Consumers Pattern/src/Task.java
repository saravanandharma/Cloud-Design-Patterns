/**
 * Created by Bilal on 12/21/2016.
 */
public class Task {
    String message;

    public Task(String message) {
        this.message = message;
    }

    public void execute() {
        System.out.println("Executing Task\nMessage: " + message);
    }
}
