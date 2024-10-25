package rezero4jv1.rlearning;

import rezero4j.NsModel;
import rezero4j.rlearning.RLAgent;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class RLEnvironment extends NsModel {
    protected rezero4j.rlearning.RLAgent agent;
    protected int numStates;

    public RLEnvironment(RLAgent agent) {
        this.agent = agent;
    }

    public abstract void reset();
}
