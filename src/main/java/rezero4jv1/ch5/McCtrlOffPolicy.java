package rezero4jv1.ch5;

import rezero4j.ch5.McOffPolicyAgent;
import rezero4j.common.Grid;
import rezero4j.common.GridworldPainter;
import rezero4j.NsFrame;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class McCtrlOffPolicy {

    public static void main(String[] args) {
        McOffPolicyAgent agent = new McOffPolicyAgent();
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
        frame.setTitle("Q Function and Pi by MC Policy Off");
        frame.setVisible(true);
    }
}
