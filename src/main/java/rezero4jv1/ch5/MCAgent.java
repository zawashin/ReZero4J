package rezero4jv1.ch5;

import rezero4j.ch5.EpsGreedy;
import rezero4j.common.GridAgent;
import rezero4j.common.AgentSnapshot;
import rezero4j.common.Policy;
import rezero4j.common.QKey;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class MCAgent extends GridAgent {
    double gamma = 0.9;
    double alpha = 0.1;
    double epsilon = 0.1;
    Policy pi;
    HashMap<IntBuffer, Double> qMap;

    ArrayList<AgentSnapshot> memory;
    int height, width;

    double[] probDefault = {0.25, 0.25, 0.25, 0.25};
    double qDefault;

    public MCAgent() {
        super();
        pi = new Policy(probDefault);
        qMap = new HashMap<>();
        memory = new ArrayList<>();
        height = 0;
        width = 0;
        qDefault = 0.0;
    }

    public int getRandomAction() {
        double[] probAction = pi.get(state);
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

    public void add(int[] state, int action, double reward) {
        if (state[0] > height) {
            height = state[0];
        }
        if (state[1] > width) {
            width = state[1];
        }
        AgentSnapshot snapshot = new AgentSnapshot(state, action, reward);
        memory.add(snapshot);
    }

    public void reset() {
        memory.clear();
    }

    public double[][][] qArray() {
        height++;
        width++;
        double[][][] qArray = new double[height][width][numActions];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                for (int a = 0; a < numActions; a++) {
                    IntBuffer key = (new QKey(new int[]{j, i}, a)).getKey();
                    qArray[j][i][a] = qMap.getOrDefault(key, qDefault);
                }
            }
        }
        height--;
        width--;
        return qArray;
    }

    public double[][][] piArray() {
        height++;
        width++;
        double[][][] piArray = new double[height][width][numActions];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                IntBuffer key = IntBuffer.wrap(new int[]{j, i});
                if (pi.containsKey(key)) {
                    piArray[j][i] = pi.get(key).clone();
                }
            }
        }
        height--;
        width--;
        return piArray;
    }

    public void update() {

        double g = 0;
        int size = memory.size() - 1;
        // 逆向きにたどる
        for (int d = size; d >= 0; d--) {
            AgentSnapshot snapshot = memory.get(d);
            int[] state = snapshot.getState();
            int action = snapshot.getAction();
            double reward = snapshot.getReward();
            IntBuffer key = (new QKey(state, action)).getKey();
            g = gamma * g + reward;
            if (qMap.containsKey(key)) {
                qMap.put(key, qMap.get(key) + (g - qMap.get(key)) * alpha);
            } else {
                qMap.put(key, g * alpha);
            }
            pi.put(state, EpsGreedy.prob(qMap, state, epsilon, numActions).clone());
        }
    }
}
