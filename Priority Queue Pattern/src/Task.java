/**
 * Created by Confiz-234 on 10/21/2016.
 */
public class Task {
    private String name;
    private int priority;

    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public String toString() {
        return "Task Name: " + name + "\nPriority: " + priority;
    }
}
