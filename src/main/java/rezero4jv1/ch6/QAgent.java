package rezero4jv1.ch6;

import rezero4j.ch5.EpsGreedy;
import rezero4j.common.*;
import rezero4j.NsFrame;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class QAgent extends GridAgent {
    double gamma = 0.9;
    double alpha = 0.8;
    double epsilon = 0.1;
    Policy pi;
    Policy b;
    QValue qValue;
    double qDefault;
    int height, width;
    double[] probDefault = {0.25, 0.25, 0.25, 0.25};

    public QAgent() {
        super();
        pi = new Policy(probDefault);
        b = new Policy(probDefault);
        qDefault = 0.0;
        qValue = new QValue(qDefault);
        height = 0;
        width = 0;
    }

	/**
	 *
	 * @return
	 */
	public int getRandomAction() {
        double[] probAction = b.get(state);
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

	/**
	 *
	 * @param state
	 * @param action
	 * @param reward
	 * @param stateNext
	 * @param done
	 */
	public void update(int[] state, int action, double reward, int[] stateNext, boolean done) {
        // Environmentの大きさを調べてる
        if (state[0] > height) {
            height = state[0];
        }
        if (state[1] > width) {
            width = state[1];
        }
        double qNextMax;
        if (done) {
            qNextMax = 0.0;
        } else {
            double[] qs = new double[numActions];
            for (int a = 0; a < numActions; a++) {
                qs[a] = qValue.get(stateNext, a);
            }
            qNextMax = ArrayUtils.max(qs);
        }
        double target = reward + gamma * qNextMax;
        double q = qValue.get(state, action);
        qValue.put(state, action, q + (target - q) * alpha);

        pi.put(state, EpsGreedy.prob(qValue, state, 0.0, numActions));
        b.put(state, EpsGreedy.prob(qValue, state, epsilon, numActions));
    }

    public double[][][] getQArray() {
        height++;
        width++;
        double[][][] qArray = new double[height][width][numActions];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int a = 0; a < numActions; a++) {
                qArray[i][j][a] = qValue.get(new int[]{i, j}, a);
                }
            }
        }
        height--;
        width--;
        return qArray;
    }

    public double[][][] getPiArray() {
        height++;
        width++;
        double[][][] piArray = new double[height][width][numActions];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] state = new int[]{i, j};
                piArray[i][j] = pi.get(new int[]{i, j});
            }
        }
        height--;
        width--;
        return piArray;
    }

    public static void main(String[] args) {
        QAgent agent = new QAgent();
        Grid env = new Grid(agent);

        int episodes = 10000;
        for (int e = 0; e < episodes; e++) {
            env.reset();

            while (true) {
                int action = agent.getRandomAction();
                env.step(action);
                int[] state = agent.getState();
                double reward = env.getReward();
                int[] stateNext = agent.getStateNext();
                boolean done = env.isDone();
                agent.update(state, action, reward, stateNext, done);

                if (done) {
                    break;
                }
                agent.updateState();
            }
        }

        GridworldPainter painter = new GridworldPainter(720, 540, env);
        painter.setQ(agent.getQArray(), agent.getPiArray());
        NsFrame frame = new NsFrame(painter);
        frame.setTitle("Q Function and Pi by Q Learning");
        frame.setVisible(true);
    }
}