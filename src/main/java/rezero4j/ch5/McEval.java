package rezero4j.ch5;

import rezero4j.common.GridworldPainter;
import rezero4j.common.Grid;
import rezero4j.NsFrame;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class McEval {

    public static void main(String[] args) {
        RandomAgent agent = new RandomAgent();
        Grid env = new Grid(agent);

        int episodes = 1000;
        for (int e = 0; e < episodes; e++) {
            env.reset();
            agent.reset();

            while (true) {
                int action = agent.getRandomAction();
                env.step(action);
                double reward = env.getReward();
                agent.add(agent.getState(), action, reward);

                if (env.isDone()) {
                    agent.eval();
                    break;
                }
                agent.updateState();
            }
        }

        GridworldPainter painter = new GridworldPainter(720, 540, env);
        painter.setV(agent.valueArray());
        NsFrame frame = new NsFrame(painter);
        frame.setTitle("Value Function by MC");
        frame.setVisible(true);
    }
}
