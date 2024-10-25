package rezero4jv1.ch4.old;

import rezero4j.ch4.old.EvalPolicy0;
import rezero4j.common.Grid;
import rezero4j.common.GridAgent;
import rezero4j.common.GridworldPainter;
import rezero4j.NsFrame;

/**
 * @author Shin-Ichiro Serizawa <zawashin@icloud.com>
 */
public class IterPolicy0 {
    rezero4j.ch4.old.EvalPolicy0 evaluater;
    Grid env;
    GridAgent agent;

    public IterPolicy0(Grid env) {
        evaluater = new EvalPolicy0(env);
        this.env = env;
        this.agent = env.getAgent();
    }

    public static void main(String[] args) {
        Grid env = new Grid(new GridAgent());
        double gamma = 0.9;
        IterPolicy0 iterPolicy = new IterPolicy0(env);
        double[][][] pi = iterPolicy.search(gamma, 1.0e-3, false);

        GridworldPainter painter = new GridworldPainter(720, 540, env);
        painter.setV(iterPolicy.getvalueArray(),  pi);
        painter.setV(iterPolicy.getvalueArray());
        NsFrame frame = new NsFrame(painter);
        frame.setTitle("Policy Iteration");
        frame.setVisible(true);
    }

    private int argmax(double[] array) {
        int n = array.length;
        int max = 0;
        double maxValue = array[max];
        for (int i = 1; i < n; i++) {
            if (array[i] >= maxValue) {
                max = i;
                maxValue = array[i];
            }
        }
        return max;
    }

    public double[][][] greedyPolicy(double[][][] pi, double[][] valueArray, double gamma) {
        int height = env.getHeight();
        int width = env.getWidth();
        int numActions = agent.getNumActions();
        double[] valueAction = new double[numActions];
        double[] prob = new double[numActions];

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                agent.state = (env.getStateMap())[j][i].clone();
                for (int a = 0; a < numActions; a++) {
                    env.nextState(a);
                    double r = env.getReward();
                    int[] stateNext = agent.getStateNext();
                    valueAction[a] = r + gamma * valueArray[stateNext[0]][stateNext[1]];
                    prob[a] = 0.0;
                }
                int actionMax = argmax(valueAction);
                prob[actionMax] = 1.0;
                pi[j][i] = prob.clone();
            }

        }
        return pi;
    }

    public double[][][] search(double gamma, double threshold ,boolean visualize) {
        double[] probAction = {0.25, 0.25, 0.25, 0.25};
        int height = env.getHeight();
        int width = env.getWidth();
        double[][][] pi = new double[height][width][];
        double[][][] piOld = new double[height][width][];
        double[][][] piNew;
        int numActions = agent.getNumActions();
        final double eps = 1.0e-3;

        if(visualize) {
            //TBS
            // GraiworldPainter
        }

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                pi[j][i] = probAction.clone();
            }
        }

        int count = 0;
        while (true) {

            evaluater.evalPolicy(pi, gamma, threshold);
            for(int j = 0; j < height; j++) {
                //piOld[j] = pi[j].clone();
                for (int i = 0; i < width; i++) {
                     piOld[j][i] = pi[j][i].clone();
                }
            }
            piNew = greedyPolicy(pi, evaluater.valueArray, gamma);
            count ++;

            if (visualize) {
                // TBD
            }

            // 収束判定
            double delta = 0.0;
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    for (int a = 0; a < numActions; a++) {
                        double t = Math.abs(piOld[j][i][a] - piNew[j][i][a]);
                        if(delta < t) {
                            delta = t;
                        }
                    }
                }
            }
            if(delta < eps) {
                break;
            }
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    pi[j][i] = piNew[j][i].clone();
                }
            }
        }

        return pi;
    }

    public double[][][] search(double gamma) {
        return search(gamma, 0.001 ,false);
    }

    public double[][] getvalueArray() {
        return evaluater.valueArray;
    }
}