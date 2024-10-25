package rezero4jv1.ch5;

import rezero4j.ch5.MCAgent;
import rezero4j.common.Grid;
import rezero4j.common.GridworldPainter;
import rezero4j.NsFrame;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class McCtrl {

    public static void main(String[] args) {
        MCAgent agent = new MCAgent();
        Grid env = new Grid(agent);

        int episodes = 10000;
        for (int e = 0; e < episodes; e++) {
            env.reset();
            agent.reset();

            while (true) {
                int action = agent.getRandomAction();
                env.step(action);
                double reward = env.getReward();
                agent.add(agent.getState(), action, reward);

                if (env.isDone()) {
                    agent.update();
                    break;
                }
                agent.updateState();
            }
        }

        GridworldPainter painter = new GridworldPainter(720, 540, env);
        painter.setQ(agent.qArray(), agent.piArray());
        NsFrame frame = new NsFrame(painter);
        frame.setTitle("Q Function and Pi by MC");
        frame.setVisible(true);
    }
}