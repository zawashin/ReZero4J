package rezero4jv1.rlearning;

import rezero4j.NsSolver;
import rezero4j.rlearning.Agent;
import rezero4j.rlearning.Environment;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class ReinforcementLearning extends NsSolver {
    protected rezero4j.rlearning.Environment environment;
    protected rezero4j.rlearning.Agent agent;

    protected int episodes;

    public ReinforcementLearning(rezero4j.rlearning.Environment environment, rezero4j.rlearning.Agent agent) {
        super(environment, false);
        this.environment = environment;
        this.agent = agent;
    }

    public ReinforcementLearning(Environment environment) {
        super(environment, false);
        this.environment = environment;
        this.agent = environment.agent;
    }

    public ReinforcementLearning(Agent agent) {
        super(agent.environment, false);
        this.environment = environment;
        this.agent = agent;
    }

    public abstract void train();
    public abstract void test();
}
