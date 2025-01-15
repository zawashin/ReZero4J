package rezero4jv1.rlearning;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class RLEnvironment {
    protected RLAgent agent;
    protected int numStates;

    public RLEnvironment(RLAgent agent) {
        this.agent = agent;
    }

    public abstract void reset();
}
