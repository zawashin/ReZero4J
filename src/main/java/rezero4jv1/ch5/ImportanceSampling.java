package rezero4jv1.ch5;

import rezero4jv1.common.ArrayUtils;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class ImportanceSampling {

    public static void main(String[] args) {

        int[] x = {1, 2, 3};
        double[] pi = {0.1, 0.1, 0.8};

        // =========== Expectation ==================
        double e = 0.0;
        for(int i = 0; i < 3; i++) {
            e += x[i] * pi[i];
        }
        System.out.println("E_pi[x] " +  e);

        // =========== Monte Carlo ==================
        int n = 100;
        double[] samples = new double[n];
        Random random = new Random();
        for(int i = 0; i < n; i++) {
            double prob = 0.0;
            double rand = random.nextDouble();
            double s = 0.0;
            for (int j = 0; j < 3; j++) {
                prob += pi[j];
                if (rand < prob) {
                    s = x[j];
                    break;
                }
            }

            samples[i] = s;
        }
        System.out.println("MC: " +  ArrayUtils.mean(samples) + " (var: " + ArrayUtils.variance(samples) + "}");

        // =========== Importance Sampling ===========
        double[] b = {0.2, 0.2, 0.6};
        //double[] b = {1.0/3.0, 1.0/3.0, 1.0/3.0};
        for(int i = 0; i < n; i++) {
            double prob = 0.0;
            double rand = random.nextDouble();
            int k = 0;
            for (int j = 0; j < 3; j++) {
                prob += b[j];
                if (rand < prob) {
                    k = j;
                    break;
                }
            }
            double s = x[k];
            double rho = pi[k] / b[k];
            samples[i] = rho * s;
        }
        System.out.println("IS: " + ArrayUtils.mean(samples) + " (var: " + ArrayUtils.variance(samples) + "}");
    }
}
