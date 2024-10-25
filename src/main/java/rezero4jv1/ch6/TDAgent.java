package rezero4jv1.ch6;

import rezero4j.common.GridAgent;

import java.nio.IntBuffer;
import java.util.HashMap;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class TDAgent extends GridAgent {
    double gamma = 0.9;
    double alpha = 0.01;
    HashMap<IntBuffer, Double> vMap;

    int height, width;

    double[] probDefault = {0.25, 0.25, 0.25, 0.25};

    public TDAgent() {
        super();
        vMap = new HashMap<>();
        height = 0;
        width = 0;
    }

    public int getRandomAction() {
        double[] probAction = probDefault;
        double rand = random.nextDouble();
        double prob = 0.0;
        for (int a = 0; a < numActions; a++) {
            prob += probAction[a];
            if (rand < prob) {
                return a;
            }
        }
        return numActions; // 来ないはずだからエラーが起こるようにしてある
    }

    public double[][] vArray() {
        height++;
        width++;
        double[][] vArray = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                vArray[i][j] = vMap.getOrDefault(IntBuffer.wrap(new int[]{i, j}), 0.0);
            }
        }
        height--;
        width--;
        return vArray;
    }

    public void eval(int[] state, double reward, int[] stateNext, boolean done) {
        if (state[0] > height) {
            height = state[0];
        }
        if (state[1] > width) {
            width = state[1];
        }
        double vNext;
        IntBuffer key = IntBuffer.wrap(stateNext);
        if (done) {
            vNext = 0.0;
        } else {
            if (!vMap.containsKey(key)) {
                vMap.put(key, 0.0);
            }
            vNext = vMap.get(key);
            vNext = vMap.getOrDefault(IntBuffer.wrap(stateNext), 0.0);
        }
        double target = reward + gamma * vNext;
        double v = vMap.getOrDefault(IntBuffer.wrap(state), 0.0);
        vMap.put(key, vMap.get(key) + (target - vMap.get(key)) * alpha);
    }

}
