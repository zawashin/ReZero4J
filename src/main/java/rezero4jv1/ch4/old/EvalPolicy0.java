package rezero4jv1.ch4.old;

import rezero4j.common.Grid;
import rezero4j.common.GridAgent;
import rezero4j.common.GridworldPainter;
import rezero4j.NsFrame;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@icloud.com>
 */
public class EvalPolicy0 {

    Grid env;
    GridAgent agent;
    double[][] valueArray;
    int width;
    int height;

    public EvalPolicy0(Grid env) {
        this.env = env;
        this.agent = env.getAgent();
        width = env.getWidth();
        height = env.getHeight();
        valueArray = new double[height][width];
    }

    public static void main(String[] args) {
        Grid env = new Grid(new GridAgent());
        double gamma = 0.9;
        double eps = 1.0e-3;
        double[] probAction = {0.25, 0.25, 0.25,0.25};
        double[][][] pi;
        int height = env.getHeight();;
        int width = env.getWidth();
        pi = new double[height][width][env.getActions().length];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                pi[j][i] = probAction.clone();
            }
        }

        EvalPolicy0 eval = new EvalPolicy0(env);
        eval.evalPolicy(pi, gamma, eps);

        GridworldPainter painter = new GridworldPainter(720, 540, env);
        painter.setV(eval.valueArray, pi);
        NsFrame frame = new NsFrame(painter);
        frame.setTitle("Value Function");
        frame.setVisible(true);
    }

    public double[][] getvalueArray() {
        return valueArray;
    }

    private void evalStep(double[][][] pi, double gamma) {
        double[][] valueArrayNew = new double[height][width];

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                double[] prob = pi[j][i].clone();
                if (Arrays.equals(env.getStateMap()[j][i], env.getStateGoal())) {
                    valueArray[j][i] = 0.0;
                    break;
                }
                for (int a = 0; a < agent.getNumActions(); a++) {
                    agent.state = (env.getStateMap())[j][i].clone();
                    agent.nextState(a);
                    int[] stateNext = agent.getStateNext();
                    double r = env.getReward();
                    valueArrayNew[j][i] += prob[a] * (r + gamma * valueArray[stateNext[0]][stateNext[1]]);
                }
                valueArray[j][i] = valueArrayNew[j][i];
            }
        }
    }

    public void evalPolicy(double[][][] pi,  double gamma, double threshold) {
        double[][] valueArrayOld = new double[height][width];
        while (true) {
            for (int j = 0; j < height; j++) {
                if (width >= 0) System.arraycopy(valueArray[j], 0, valueArrayOld[j], 0, width);
            }
            evalStep(pi, gamma);

            double delta = 0;
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    double t = Math.abs(valueArray[j][i] - valueArrayOld[j][i]);
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
