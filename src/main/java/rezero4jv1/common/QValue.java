package rezero4jv1.common;


import rezero4j.common.QKey;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 * <a href="https://stackoverflow.com/questions/1786206/is-there-a-java-equivalent-of-pythons-defaultdict">...</a>
 */
public class QValue implements Cloneable {
    protected int numActions;
    protected double qDefault;
    private HashMap<IntBuffer, Double> q;

    public QValue() {
        this(0.0);
    }

    public QValue(double qDefault) {
        this.qDefault = qDefault;
        numActions = 0;
        q = new HashMap<>();
    }
    public static void main(String[] args) {
        QValue qValue = new QValue();
        int width = 4;
        int height = 3;
        int numActions = 4;
        Random rand = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] state = new int[]{i, j};
                for (int a = 0; a < numActions; a++) {
                    double value = rand.nextDouble();
                    qValue.put(state, a, value);
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] state = new int[]{i, j};
                for (int a = 0; a < numActions; a++) {
                    System.out.println(Arrays.toString(state) + "(" + a + ")\t" + qValue.get(state, a));
                }
            }
        }
        System.out.println();

        QValue qValue2 = qValue.clone();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] state = new int[]{i, j};
                for (int a = 0; a < numActions; a++) {
                    System.out.println(Arrays.toString(state) + "(" + a + ")\t" + qValue2.get(state, a));
                }
            }
        }
        System.out.println(qValue.equals(qValue2));
    }

    @Override
    public QValue clone() {
        QValue qValue;
        try {
            qValue = (QValue) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        qValue.q = (HashMap<IntBuffer, Double>) this.q.clone();
        return qValue;
    }

    public void put(int[] state, int action, double q) {
        IntBuffer key = new rezero4j.common.QKey(state, action).getKey();
        this.q.put(key, q);
    }

    public void put(rezero4j.common.QKey qKey, double q) {
        put(qKey.getState(), qKey.getAction(), q);
    }

    public double get(int[] state, int action) {
        IntBuffer key = new rezero4j.common.QKey(state, action).getKey();
        return q.getOrDefault(key, qDefault);
    }

    public double get(rezero4j.common.QKey qKey) {
        IntBuffer key = qKey.getKey();
        return q.getOrDefault(key, qDefault);
    }

    public boolean containsKey(rezero4j.common.QKey qKey) {
        return q.containsKey(qKey.getKey()) ;
    }

    public boolean containsKey(int[] state, int action) {
        return q.containsKey(QKey.key(state, action)) ;
    }
}
