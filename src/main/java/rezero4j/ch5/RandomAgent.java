package rezero4j.ch5;

import rezero4j.common.GridAgent;
import rezero4j.common.Policy;
import rezero4j.common.AgentSnapshot;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class RandomAgent extends GridAgent {
    double gamma = 0.9;
    Policy pi;
    HashMap<IntBuffer, Double> vMap;
    HashMap<IntBuffer, Integer> counts;
    ArrayList<AgentSnapshot> memory;
    int height, width;

    double[] probDefault = {0.25, 0.25, 0.25, 0.25};

    public RandomAgent() {
        super();
        pi = new Policy(probDefault);

        vMap = new HashMap<>();
        counts = new HashMap<>();
        memory = new ArrayList<>();
        height = 0;
        width = 0;
    }

    public int getRandomAction() {
        double rand = random.nextDouble();
        double prob = 0.0;
        double[] probAction;
        probAction = pi.get(state);
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

    public double[][] valueArray() {
        height++;
        width++;
        double[][] values = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                values[i][j] += vMap.getOrDefault(IntBuffer.wrap(new int[]{i, j}), 0.0);
            }
        }
        height++;
        width++;
        return values;
    }
    public void eval() {

        double g = 0;
        int size = memory.size() - 1;
        // 逆向きにたどる
        for (int d = size; d >= 0; d--) {
            AgentSnapshot snapshot = memory.get(d);
            //int[] state = snapshot.state;
            /*
            IntBuffer key = IntBuffer.wrap(snapshot.getState());
            double reward = snapshot.getReward();
            g = gamma * g + reward;
            if (counts.containsKey(state)) {
                counts.put(state, counts.get(state) + 1);
                vMap.put(state,
                        vMap.get(state) + (g - vMap.get(state)) / counts.get(state));
            } else {
                counts.put(state, 1);
                vMap.put(state, g);
            }
             */
            IntBuffer key = IntBuffer.wrap(snapshot.getState());
            double reward = snapshot.getReward();
            int count = counts.getOrDefault(key, 1);
            double v = vMap.getOrDefault(key, 0.0);
            g = gamma * g + reward;
            counts.put(key, count + 1);
            vMap.put(key, v + (g - v) / count);
        }
    }
}
