/**
 * Created by Confiz-234 on 12/21/2016.
 */
public class Task {

    String message;

    public Task(String message) {
        this.message = message;
    }

    public void execute() {
        System.out.println(message);
    }
}
