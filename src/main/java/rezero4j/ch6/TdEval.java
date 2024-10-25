package rezero4j.ch6;

import rezero4j.common.GridworldPainter;
import rezero4j.common.Grid;
import rezero4j.NsFrame;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class TdEval {

    public static void main(String[] args) {
        TDAgent agent = new TDAgent();
        Grid env = new Grid(agent);

        int episodes = 1000;
        for (int e = 0; e < episodes; e++) {
            env.reset();

            while (true) {
                int action = agent.getRandomAction();
                env.step(action);
                double reward = env.getReward();
                boolean done = env.isDone();
                agent.eval(agent.state, reward, agent.stateNext, done);

                if (done) {
                    break;
                }

                agent.updateState();
            }
        }

        GridworldPainter painter = new GridworldPainter(720, 540, env);
        painter.setV(agent.vArray());
        NsFrame frame = new NsFrame(painter);
        frame.setTitle("Value Function by TD");
        frame.setVisible(true);
    }
}
