package zawashin.rlearning;

import net.goui.util.MTRandom;
import zawashin.numsim4j.NsModel;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Agent extends NsModel {
    public Environment environment;
    public int numDim;
    public int numActions;
    public int[] actions;
    // ダミー
    public int[] state;
    public int[] stateNext;
    public String[] actionLabels;
    public int[][] actionArray;
    protected double reward;
    protected MTRandom random;

    public int[] getState() {
        return state;
    }

    public int[] getStateNext() {
        return stateNext;
    }

    public abstract void nextState(int action);

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void updateState() {
        state = stateNext.clone();
    }

    public abstract void reward();

}
