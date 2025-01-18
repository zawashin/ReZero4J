package rezero4j.ch1;


import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class BanditAve {
    int numArms;
    // 各台の確率
    double[] rates;
    Random random;

    public BanditAve(int numArms) {
        this.numArms = numArms;
        random = new Random(0);
        //random = new Random(System.currentTimeMillis());
        rates = new double[numArms];
        for (int i = 0; i < numArms; i++) {
            rates[i] = random.nextDouble();
        }
    }

    public int play(int arm) {
        double rate = rates[arm];
        if (rate > random.nextDouble()) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        int maxSteps = 1000;
        double epsilon = 0.1;
        int numArms = 10;

        BanditAve environment = new BanditAve(numArms);
        Agent agent = new Agent(numArms, epsilon);
        int totalReward = 0;
        //int[] totalRewards = new int[maxSteps];
        double[] rates = new double[maxSteps];

        for (int step = 0; step < maxSteps; step++) {
            int action = agent.getAction();
            int reward = environment.play(action);

            agent.update(action, reward);
            totalReward += reward;

            //totalRewards[step] = totalReward;
            rates[step] = ((double) totalReward / (step + 1));
        }
        for (int i = 0; i < rates.length; i++) {
            System.out.println(i + "\t" + rates[i]);
        }
        for (int i = 0; i < numArms; i++) {
            System.out.println(i + 1 + "\t" + agent.getExpectedValues()[i]);
        }
    }
}
