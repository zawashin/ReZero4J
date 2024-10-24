package rezero4j.rlearning;

import rezero4j.NsSolver;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class ReinforcementLearning extends NsSolver {
    protected Environment environment;
    protected Agent agent;

    protected int episodes;

    public ReinforcementLearning(Environment environment, Agent agent) {
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
