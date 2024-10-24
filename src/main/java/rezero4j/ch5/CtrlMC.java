package rezero4j.ch5;

import rezero4j.common.Grid;
import rezero4j.common.GridworldPainter;
import rezero4j.NsFrame;

/**
 * @author Shin-Ichiro Serizawa <zawashin@icloud.com>
 */
public class CtrlMC {

    public static void main(String[] args) {
        MCAgent agent = new MCAgent();
        Grid env = new Grid(agent);

        int episodes = 10000;
        for (int e = 0; e < episodes; e++) {
            agent.state = env.getStateStart().clone();
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
            env.setDone(false);
        }

        GridworldPainter painter = new GridworldPainter(720, 540, env);
        painter.setQ(agent.qArray(), agent.piArray());
        NsFrame frame = new NsFrame(painter);
        frame.setTitle("Q Function and Pi by MC");
        frame.setVisible(true);
    }
}
