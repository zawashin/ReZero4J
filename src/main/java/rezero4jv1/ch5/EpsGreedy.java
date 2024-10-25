package rezero4jv1.ch5;

import rezero4j.common.ArrayUtils;
import rezero4j.common.QKey;
import rezero4j.common.QValue;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class EpsGreedy {
    public static double[] prob(QValue qValue, int[] state, double epsilon, int numActions) {
        double[] qs = new double[numActions];
        for (int a = 0; a < numActions; a++) {
            qs[a] = qValue.get(state, a);
        }

        int actionMax = ArrayUtils.argmax(qs);
        double probBase = epsilon / (double)numActions;
        double[] probAction = new double[numActions];
        for (int a = 0; a < numActions; a++) {
            probAction[a] = probBase; //0:ε / 4, 1:ε / 4, 2:ε / 4, 3:ε / 4
        }
        probAction[actionMax] += (1.0 - epsilon);
        return probAction;
    }

    public static double[] prob(HashMap<IntBuffer, Double> qMap, int[] state, double epsilon, int numActions) {
        double[] qs = new double[numActions];
        for (int a = 0; a < numActions; a++) {
            IntBuffer key = QKey.key(state, a);
            if(qMap.containsKey(key)) {
                qs[a] = qMap.get(QKey.key(state, a));
            }
        }

        int actionMax = ArrayUtils.argmax(qs);
        double probBase = epsilon / (double)numActions;
        double[] probAction = new double[numActions];
        for (int a = 0; a < numActions; a++) {
            probAction[a] = probBase; //0:ε / 4, 1:ε / 4, 2:ε / 4, 3:ε / 4
        }
        probAction[actionMax] += (1.0 - epsilon);
        return probAction;
    }

    public static double[] prob(double[][][] qMap, int[] state, double epsilon, int numActions) {
        double[] qs = new double[numActions];
        int i = state[0];
        int j = state[1];
        System.arraycopy(qMap[i][j], 0, qs, 0, numActions);

        int actionMax = ArrayUtils.argmax(qs);
        double probBase = epsilon / (double) numActions;
        double[] probAction = new double[numActions];
        Arrays.fill(probAction, probBase);
        probAction[actionMax] += (1.0 - epsilon);
        return probAction;
    }

}
