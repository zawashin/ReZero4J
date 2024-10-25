package rezero4jv1.common;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class AgentSnapshot {
    int[] state;
    int action;
    double reward;

    boolean done;

    public AgentSnapshot(int[] state, int action, double reward) {
        this.state = state.clone();
        this.action = action;
        this.reward = reward;
        done = false;
    }

    public AgentSnapshot(int[] state, int action, double reward, boolean done) {
        this.state = state.clone();
        this.action = action;
        this.reward = reward;
        this.done = done;
    }

    public int[] getState() {
        return state;
    }

    public int getAction() {
        return action;
    }

    public double getReward() {
        return reward;
    }

    public boolean isDone() {
        return done;
    }

}
