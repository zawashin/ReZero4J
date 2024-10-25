package rezero4jv1.ch6;

import rezero4j.ch6.QAgent;
import rezero4j.common.Grid;
import rezero4j.common.GridworldPainter;
import rezero4j.NsFrame;
import rezero4j.rlearning.Environment;
import rezero4j.rlearning.ReinforcementLearning;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class QLearning2 extends ReinforcementLearning {
    rezero4j.ch6.QAgent agent;
    Grid grid;

    public QLearning2(Environment grid) {
        super(grid);
        this.grid = (Grid) grid;
        this.agent = (rezero4j.ch6.QAgent) grid.getAgent();
    }

    public static void main(String[] args) {
        rezero4j.ch6.QAgent agent = new QAgent();
        Grid grid = new Grid(agent);
        agent.setEnvironment(grid);
        grid.setAgent(agent);

        int episodes = 10000;
        QLearning2 qLearning = new QLearning2(grid);
        qLearning.setEpisodes(episodes);

        qLearning.train();
        /*
        for (int e = 0; e < episodes; e++) {
            grid.reset();

            while (true) {
                int action = agent.getRandomAction();
                grid.step(action);
                int[] state = agent.getState();
                double reward = grid.getReward();
                int[] stateNext = agent.getStateNext();
                boolean done = grid.isDone();
                agent.update(state, action, reward, stateNext, done);

                if (done) {
                    break;
                }
                agent.updateState();
            }
        }
         */

        qLearning.test();
        /*
        GridworldPainter painter = new GridworldPainter(720, 540, grid);
        painter.setQ(agent.getQArray(), agent.getPiArray());
        NsFrame frame = new NsFrame(painter);
        frame.setTitle("Q Function and Pi by Q Learning");
        frame.setVisible(true);

         */
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    @Override
    public void train() {
        for (int e = 0; e < episodes; e++) {
            grid.reset();

            while (true) {
                int action = agent.getRandomAction();
                grid.step(action);
                int[] state = agent.getState();
                double reward = grid.getReward();
                int[] stateNext = agent.getStateNext();
                boolean done = grid.isDone();
                agent.update(state, action, reward, stateNext, done);

                if (done) {
                    break;
                }
                agent.updateState();
            }
        }
    }

    @Override
    public void test() {
        GridworldPainter painter = new GridworldPainter(720, 540, grid);
        painter.setQ(agent.getQArray(), agent.getPiArray());
        NsFrame frame = new NsFrame(painter);
        frame.setTitle("Q Function and Pi by Q Learning");
        frame.setVisible(true);
    }

    @Override
    public void solve() throws CloneNotSupportedException {
        train();
        test();
    }
}
