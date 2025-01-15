package rezero4jv1.ch6;

import rezero4jv1.common.GridworldPainter;
import rezero4jv1.common.Grid;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Sarsa {

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
