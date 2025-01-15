package rezero4jv1.ch4;

import rezero4jv1.common.GridAgent;
import rezero4jv1.common.Policy;
import rezero4jv1.common.Grid;
import rezero4jv1.common.GridworldPainter;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class EvalPolicy {

    Grid env;
    GridAgent agent;
    public double[][] vArray;
    int width;
    int height;

    public EvalPolicy(Grid env) {
        this.env = env;
        this.agent = env.getAgent();
        width = env.getWidth();
        height = env.getHeight();
        vArray = new double[height][width];
    }

    public static void main(String[] args) {
        Grid env = new Grid(new GridAgent());
        double gamma = 0.9;
        double eps = 1.0e-3;
        int height = env.getHeight();
        int width = env.getWidth();
        double[] probAction = {0.25, 0.25, 0.25, 0.25};
        Policy pi = new Policy(probAction);

        EvalPolicy eval = new EvalPolicy(env);

        eval.evalPolicy(pi, gamma, eps);

        GridworldPainter painter = new GridworldPainter(720, 540, env);
        painter.setV(eval.vArray);
        NvFrame frame = new NvFrame(painter);
        frame.setTitle("Value Function");
        frame.setVisible(true);
    }

    private void evalStep(Policy pi, double gamma) {
        double[][] valueArrayNew = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Arrays.equals(env.getStateMap()[i][j], env.getStateGoal())) {
                    vArray[i][j] = 0.0;
                    break;
                }
                int[] state = env.getStateMap()[i][j];
                double[] prob = pi.get(state);
                for (int a = 0; a < agent.getNumActions(); a++) {
                    agent.state = (env.getStateMap())[i][j].clone();
                    agent.nextState(a);
                    int[] stateNext = agent.getStateNext();
                    double r = env.getReward();
                    valueArrayNew[i][j] += prob[a] * (r + gamma * vArray[stateNext[0]][stateNext[1]]);
                }
                vArray[i][j] = valueArrayNew[i][j];
            }
        }
    }

    public void evalPolicy(Policy pi, double gamma, double threshold) {
        double[][] valueArrayOld = new double[height][width];
        while (true) {
            for (int i = 0; i < height; i++) {
                if (width >= 0) System.arraycopy(vArray[i], 0, valueArrayOld[i], 0, width);
            }
            evalStep(pi, gamma);

            double delta = 0;
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    double t = Math.abs(vArray[j][i] - valueArrayOld[j][i]);
                    if (delta < t) {
                        delta = t;
                    }
                }
            }

            if (delta < threshold) {
                break;
            }
        }
    }
}
