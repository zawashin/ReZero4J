package rezero4j.rlearning;

import rezero4j.NsModel;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class RLEnvironment extends NsModel {
    protected RLAgent agent;
    protected int numStates;

    public RLEnvironment(RLAgent agent) {
        this.agent = agent;
    }

    public abstract void reset();
}
