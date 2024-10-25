package rezero4j.ch6;

import rezero4j.common.GridworldPainter;
import rezero4j.common.Grid;
import rezero4j.NsFrame;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class QLearning {
    QAgent agent;
    Grid env;

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
