import java.util.Comparator;

/**
 * Created by Confiz-234 on 10/21/2016.
 */
public class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        if(o1.getPriority() > o2.getPriority()) {
            return 1;
        } else if(o1.getPriority() < o2.getPriority()) {
            return -1;
        } else {
            return 0;
        }
    }
}
