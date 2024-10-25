package rezero4jv1.common;

/**
 * @author Shin-Ichiro Serizawa <zawashin@icloud.com>
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
