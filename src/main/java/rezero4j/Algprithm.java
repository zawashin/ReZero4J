package rezero4j;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Algprithm {
    protected Environment environment;
    protected Agent agent;

    protected int episodes;

    public Algprithm(Environment environment, Agent agent) {
        this.environment = environment;
        this.agent = agent;
    }

    public abstract void train();
    public abstract void test();
}
