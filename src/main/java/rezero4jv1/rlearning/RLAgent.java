package rezero4jv1.rlearning;

import rezero4j.NsModel;
import rezero4j.rlearning.RLEnvironment;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@icloud.com>
 */
public abstract class RLAgent extends NsModel {
    public RLEnvironment env;
    public int numDim;
    public int numActions;
    public int[] actions;
    // ダミー
    public int[] state;
    public int[] stateNext;
    public String[] actionLabels;
    public int[][] actionArray;
    protected Random random;
    protected double reward;

    public int[] getState() {
        return state;
    }

    public int[] getStateNext() {
        return stateNext;
    }

    public abstract void reset();
    public abstract void nextState(int action);
    public void updateState() {
        state = stateNext.clone();
    }

    public abstract void getReward();
}
