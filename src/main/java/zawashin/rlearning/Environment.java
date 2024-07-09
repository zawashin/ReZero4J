package zawashin.rlearning;

import zawashin.dlfs4.ch6.QAgent;
import zawashin.numsim4j.NsModel;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Environment extends NsModel {
    protected Agent agent;
    protected int numStates;
    protected int[] shape;

    public Environment(Agent agent) {
        this.agent = agent;
    }

    public abstract void reset();

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Agent getAgent() {
        return agent;
    }
}
