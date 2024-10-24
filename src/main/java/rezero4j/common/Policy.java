package rezero4j.common;


import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 * <a href="https://stackoverflow.com/questions/1786206/is-there-a-java-equivalent-of-pythons-defaultdict">...</a>
 */
public class Policy implements Cloneable {

    private int numActions;
    private HashMap<IntBuffer, double[]> pi;
    private double[] defaultValue;

    public Policy(int numActions) {
        this.numActions = numActions;
        pi = new HashMap<>();
        defaultValue = new double[numActions];
    }

    public Policy(double[] defaultValue) {
        this.defaultValue = defaultValue;
        this.numActions = defaultValue.length;
        pi = new HashMap<>();
    }

    public static void main(String[] args) {
        int width = 4;
        int height = 3;
        int numActions = 4;
        Policy policy = new Policy(numActions);
        Random rand = new Random(System.currentTimeMillis());
        double[] values = new double[numActions];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] state = new int[]{i, j};
                for (int a = 0; a < numActions; a++) {
                    values[a] = rand.nextDouble();
                }
                policy.put(state, values);
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] state = new int[]{i, j};
                System.out.println(Arrays.toString(state) + Arrays.toString(policy.get(state)));
            }
        }
        System.out.println();

        Policy policy2 = policy.clone();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] s = new int[]{i, j};
                values = policy2.get(s);
                System.out.println(Arrays.toString(s) + Arrays.toString(values));
            }
        }
        System.out.println(policy.equals(policy2));
    }

    public double[] get(int[] state) {
        return get(IntBuffer.wrap(state));
    }

    public double[] get(IntBuffer key) {
        return pi.getOrDefault(key, defaultValue);
    }

    public void put(int[] state, double[] values) {
        put(IntBuffer.wrap(state), values.clone());
    }

    public void put(IntBuffer key, double[] values) {
        pi.put(key, values.clone());
    }

    @Override
    public Policy clone() {
        Policy policy;
        try {
            policy = (Policy) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        policy.pi = (HashMap<IntBuffer, double[]>) pi.clone();
        policy.defaultValue = this.defaultValue.clone();
        return policy;
    }

    public boolean containsKey(int[] state) {
        return containsKey(IntBuffer.wrap(state));
    }

    public boolean containsKey(IntBuffer key) {
        return pi.containsKey(key);
    }

}
