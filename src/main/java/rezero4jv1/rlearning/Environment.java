package rezero4jv1.rlearning;

import rezero4j.NsModel;
import rezero4j.rlearning.Agent;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Environment extends NsModel {
    protected rezero4j.rlearning.Agent agent;
    protected int numStates;
    protected int[] shape;

    public Environment(rezero4j.rlearning.Agent agent) {
        this.agent = agent;
    }

    public abstract void reset();

    public void setAgent(rezero4j.rlearning.Agent agent) {
        this.agent = agent;
    }

    public Agent getAgent() {
        return agent;
    }
}
