package dlfs4.rlearning;

import numsim4j.NsModel;
import rezeo4j.rlearning.RLAgent;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class RLEnvironment extends NsModel {
    protected rezeo4j.rlearning.RLAgent agent;
    protected int numStates;

    public RLEnvironment(RLAgent agent) {
        this.agent = agent;
    }

    public abstract void reset();
}
