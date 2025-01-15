package rezero4jv1.ch6;

import rezero4jv1.ch5.EpsGreedy;
import rezero4jv1.common.*;

import java.nio.IntBuffer;
import java.util.ArrayDeque;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class SarsaAgent extends GridAgent {
    double gamma = 0.9;
    double alpha = 0.8;
    double epsilon = 0.1;
    Policy pi;
    QValue q;
    double qDefault;

    ArrayDeque<AgentSnapshot> memory;
    final int memMax = 2;
    int height, width;
    double[] probDefault = {0.25, 0.25, 0.25, 0.25};

    public SarsaAgent() {
        super();
        pi = new Policy(probDefault);
        qDefault = 0.0;
        q = new QValue(qDefault);
        memory = new ArrayDeque<>();
        height = 0;
        width = 0;
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

    public void update(int[] state, int action, double reward, boolean done) {
        if (state[0] > height) {
            height = state[0];
        }
        if (state[1] > width) {
            width = state[1];
        }
        memory.add(new AgentSnapshot(state, action, reward, done));
        if (memory.size() < memMax) {
            return;
        } else if (memory.size() > memMax) {
            memory.removeFirst();
        }
        int[] state_;
        AgentSnapshot snapshot = memory.getFirst();
        done = snapshot.isDone();

        AgentSnapshot snapshotNext = memory.getLast();
        state_ = snapshotNext.getState();
        action = snapshotNext.getAction();

        double qNext;
        if (done) {
            qNext = 0.0;
        } else {
            qNext = q.get(state_, action);
        }

        state_ = snapshot.getState();
        action = snapshot.getAction();
        reward = snapshot.getReward();
        double target = reward + gamma * qNext;
        double qv = q.get(state_, action);
        q.put(state_, action, qv + (target - qv) * alpha);
        pi.put(state_, EpsGreedy.prob(q, state_, epsilon, numActions));
    }

    public void update() {
        memory.removeFirst();
        AgentSnapshot snapshot = memory.getFirst();

        double qNext = 0.0;
        double target = snapshot.getReward() + gamma * qNext;
        double qv = q.get(snapshot.getState(), snapshot.getAction());
        q.put(snapshot.getState(), snapshot.getAction(), qv + (target - qv) * alpha);
        pi.put(snapshot.getState(), EpsGreedy.prob(q, snapshot.getState(), epsilon, numActions));
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
                int[] state = new int[]{j, i};
                for (int a = 0; a < numActions; a++) {
                    qArray[j][i][a] = q.get(state, a);
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
                piArray[j][i] = pi.get(IntBuffer.wrap(new int[]{j, i}));
            }
        }
        height--;
        width--;
        return piArray;
    }

    public static void main(String[] args) {
        SarsaAgent agent = new SarsaAgent();
        Grid env = new Grid(agent);

        int episodes = 10000;
        for (int e = 0; e < episodes; e++) {
            env.reset();

            while (true) {
                int action = agent.getRandomAction();
                env.step(action);
                int[] state = agent.getState();
                double reward = env.getReward();
                boolean done = env.isDone();
                agent.update(state, action, reward, done);

                if (done) {
                    agent.update();
                    break;
                }
                agent.updateState();
            }
        }

        GridworldPainter painter = new GridworldPainter(720, 540, env);
        painter.setQ(agent.qArray(), agent.piArray());
        NvFrame frame = new NvFrame(painter);
        frame.setTitle("Q Function and Pi by Sarsa");
        frame.setVisible(true);
    }

}