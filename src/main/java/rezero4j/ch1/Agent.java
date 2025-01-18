package rezero4j.ch1;

import rezero4jv1.common.ArrayUtils;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Agent {
    int numArms;
    double epsilon;
    double[] expectedValues;
    int[] numPlayed;
    Random random;

    public Agent(int numArms, double epsilon) {
        this.numArms = numArms;
        this.epsilon = epsilon;
        this.expectedValues = new double[numArms];
        this.numPlayed = new int[numArms];
        for(int i = 0; i < numArms; i++) {
            expectedValues[i] = 0.0;
            numPlayed[i] = 0;
        }
        random = new Random(System.currentTimeMillis());
    }

    public void update(int action, double reward) {
        numPlayed[action] += 1;
        expectedValues[action] += (reward - expectedValues[action]) / numPlayed[action];
    }

    public int getAction() {
        if (random.nextDouble() < epsilon) {
            return random.nextInt(expectedValues.length);
        } else {
            return ArrayUtils.argmax(expectedValues);
        }
    }

    public double[] getExpectedValues() {
        return expectedValues;
    }

    public static void main(String[] args) {
        Agent agent = new Agent(10, 0.1);
        for(int i = 0; i < 100; i++) {
            System.out.println(agent.getAction());
        }
    }
}
