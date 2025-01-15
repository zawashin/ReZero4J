package rezero4jv1.common;

import rezero4jv1.rlearning.Agent;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class GridAgent extends Agent {

    protected final int up = 0;
    protected final int down = 1;
    protected final int left = 2;
    protected final int right = 3;

    public GridAgent() {
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
    public void nextState(int action) {
        ((Grid) environment).nextState(action);
    }

    @Override
    public void reward() {
        reward = ((Grid) environment).getReward();
    }
}
