package rezero4jv1.common;

import rezero4jv1.rlearning.RLAgent;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Agent extends RLAgent {

    protected final int up = 0;
    protected final int down = 1;
    protected final int left = 2;
    protected final int right = 3;

    public Agent() {
        super();
        numDim = 2;
        random = new Random(System.currentTimeMillis());
        actions = new int[]{up, down, left, right};
        numActions = actions.length;
        actionLabels = new String[]{"^", "v", "<", ">"};
        actionArray = new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
    }

    public int getNumActions() {
        return numActions;
    }

    @Override
    public void reset() {

    }

    @Override
    public void nextState(int action) {
        ((Gridworld)env).nextState(action);
    }

    @Override
    public void getReward() {

    }

    public void reward() {
        reward = ((Gridworld)env).getReward();
    }
}
