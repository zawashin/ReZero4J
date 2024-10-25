package rezero4jv1.ch1;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Average {

    public static void main(String[] args) {
        Random random = new Random(0);
        ArrayList<Double> rewards = new ArrayList<>();
        final int N = 11;

        for (int n = 1; n <= N; n++) {
            double reward = random.nextDouble();
            rewards.add(reward);
            double sum = 0.0;
            for(int j = 0; j < n; j++) {
                sum += rewards.get(j);
            }
            double q = sum / (double) n;
            System.out.println(q);
        }
        System.out.println("---");

        // # incremental implementation
        random = new Random(0);
        double q = 0.0;

        for (int n = 1; n <= N; n++) {
            double reward = random.nextDouble();
            q = q + (reward - q) / n;
            System.out.println(q);
        }
        System.out.println("---");
    }
}
