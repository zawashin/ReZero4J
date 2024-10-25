package rezero4jv1.rlearning;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class State {

    protected int numDim;
    protected int[] state;

    public State(int numDim) {
        this.numDim = numDim;
        state = new int[numDim];
    }

    public int[] getState() {
        return state;
    }
}
