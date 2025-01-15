package rezero4jv1.common;

import rezero4jv1.rlearning.RLAgent;
import rezero4jv1.rlearning.RLEnvironment;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Gridworld extends RLEnvironment {

    int numDim = 2;
    int[] actions;
    // ダミー
    double penaltyWall = -Double.MAX_VALUE;
    int[] stateStart = {2, 0};
    int[] stateGoal = {0, 3};
    int[] stateWall = {1, 1};
    int[][][] stateMap;
    Agent agent;
    boolean done = false;
    public double[][] rewardArray = {
            {0, 0, 0, 1.0},
            {0, penaltyWall, 0, -1.0},
            {0, 0, 0, 0}
    };

    int width;
    int height;
    private final int[] shape = new int[2];

    public Gridworld(Agent agent) {
        super(agent);
        this.agent = agent;
        actions = agent.actions;

        this.agent.env = this;
        width = rewardArray[0].length;
        height = rewardArray.length;
        numStates = width*height;
        this.agent.state = stateStart.clone();
        shape[0] = height;
        shape[1] = width;
        stateMap = new int[height][width][];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                stateMap[j][i] = new int[]{j, i};
            }
        }
    }

    public static void main(String[] args) {
        RLAgent agent = new Agent();
        Gridworld env = new Gridworld((Agent) agent);
        Random random = new Random(System.currentTimeMillis());

        int tMax = 1000;
        System.out.println("Test Move");
        for (int t = 0; t < tMax; t++) {
            System.out.print(t + " ");
            int a = random.nextInt(4);
            env.step(a);
            env.updateState();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getShape() {
        return shape;
    }

    public int getNumDim() {
        return numDim;
    }

    public int[] getActions() {
        return actions;
    }

    public double getPenaltyWall() {
        return penaltyWall;
    }

    public int[] getStateGoal() {
        return stateGoal;
    }

    public int[] getStateWall() {
        return stateWall;
    }

    public int[] getStateStart() {
        return stateStart;
    }

    public int[][][] getStateMap() {
        return stateMap;
    }

    public void nextState(int action) {
        int[] move = agent.actionArray[action];
        agent.stateNext = new int[]{agent.state[0] + move[0], agent.state[1] + move[1]};
        int m = agent.getStateNext()[0];
        int n = agent.getStateNext()[1];

        if (m < 0 || m >= height || n < 0 || n >= width) {
            // マス目から出た場合
            agent.stateNext = agent.state.clone();
        } else if (Arrays.equals(agent.stateNext, stateWall)) {
            // 壁にぶつかった場合
            agent.stateNext = agent.state.clone();
        }
    }
    public void updateState() {
        agent.state = agent.stateNext.clone();
    }

    public double getReward() {
        return rewardArray[agent.stateNext[0]][agent.stateNext[1]];
    }

    // スタート地点に戻る`
    public void reset() {
        done = false;
        agent.state = stateStart.clone();
    }

    public void step(int action) {
        agent.nextState(action);
        agent.reward();
        done = Arrays.equals(agent.stateNext, stateGoal);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Agent getAgent() {
        return agent;
    }

}
