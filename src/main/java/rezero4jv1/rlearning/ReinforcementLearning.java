package rezero4jv1.rlearning;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class ReinforcementLearning {
    protected Environment environment;
    protected Agent agent;

    protected int episodes;

    public ReinforcementLearning(Environment environment, Agent agent) {
        this.environment = environment;
        this.agent = agent;
    }

    public ReinforcementLearning(Environment environment) {
        this.environment = environment;
        this.agent = environment.agent;
    }

    public ReinforcementLearning(Agent agent) {
        this.environment = environment;
        this.agent = agent;
    }

    public abstract void train();
    public abstract void test();
}
