/**
 * Created by Confiz-234 on 12/21/2016.
 */
public class Service {
    enum State {WORKING, ERROR}

    State currentState;

    public Service() {
        currentState = State.WORKING;
    }

    public void errored() {
        currentState = State.ERROR;
    }

    public void working() {
        currentState = State.WORKING;
    }
}
