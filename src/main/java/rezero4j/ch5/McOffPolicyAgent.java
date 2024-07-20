package rezero4j.ch5;

import rezero4j.common.GridAgent;
import rezero4j.common.Policy;
import rezero4j.common.AgentSnapshot;
import rezero4j.common.QKey;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class McOffPolicyAgent extends GridAgent {
    double gamma = 0.9;
    double alpha = 0.2;
    double epsilon = 0.1;
    Policy pi;
    Policy b;
    HashMap<IntBuffer, Double> qMap;
    double[][][] qArray;
    double[][][] piArray;

    ArrayList<AgentSnapshot> memory;
    int height, width;

    double[] probDefault = {0.25, 0.25, 0.25, 0.25};

    public McOffPolicyAgent() {
        super();
        pi = new Policy(numActions);
        b = new Policy(numActions);
        qMap = new HashMap<>();
        memory = new ArrayList<>();
        height = 0;
        width = 0;
    }

    public int getRandomAction() {
        double[] probAction;
        if(b.containsKey(IntBuffer.wrap(state))) {
            probAction = b.get(IntBuffer.wrap(state));
        } else {
            probAction = probDefault.clone();
        }
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
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int a = 0; a < numActions; a++) {
                    IntBuffer key = (new QKey(new int[]{i, j}, a)).getKey();
                    if (qMap.containsKey(key)) {
                        qArray[i][j][a] = qMap.get(key);
                    }
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
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                IntBuffer key = IntBuffer.wrap(new int[]{i, j});
                if (pi.containsKey(key)) {
                    piArray[i][j] = pi.get(key).clone();
                }
            }
        }
        height--;
        width--;
        return piArray;
    }

    public double[][][] getqArray() {
        return qArray;
    }

    public double[][][] getPiArray() {
        return piArray;
    }

    public void update() {

        double g = 0;
        double rho = 1.0;
        int size = memory.size() - 1;
        // 逆向きにたどる
        for (int d = size; d >= 0; d--) {
            AgentSnapshot snapshot = memory.get(d);
            int[] state = snapshot.getState();
            int action = snapshot.getAction();
            double reward = snapshot.getReward();
            IntBuffer key = (new QKey(state, action)).getKey();
            IntBuffer sBuffer = IntBuffer.wrap(state);
            g = gamma * rho * g + reward;
            if (qMap.containsKey(key)) {
                qMap.put(key, qMap.get(key) + (g - qMap.get(key)) * alpha);
                rho *= pi.get(sBuffer)[action]/b.get(sBuffer)[action];
            } else {
                qMap.put(key, g * alpha);
                //rho *= probDefault[action]/probDefault[action];
                //rho *= 1.0;
            }
            pi.put(state, EpsGreedy.prob(qMap, state, 0.0, numActions).clone());
            b.put(state, EpsGreedy.prob(qMap, state, epsilon, numActions).clone());
        }
    }
}
