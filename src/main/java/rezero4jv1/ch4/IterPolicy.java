package rezero4jv1.ch4;

import rezero4jv1.common.GridAgent;
import rezero4jv1.common.GridworldPainter;
import rezero4jv1.common.Policy;
import rezero4jv1.common.Grid;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class IterPolicy {
    EvalPolicy evaluater;
    Grid env;
    GridAgent agent;

    public IterPolicy(Grid env) {
        evaluater = new EvalPolicy(env);
        this.env = env;
        this.agent = env.getAgent();
    }

    public static void main(String[] args) {
        Grid env = new Grid(new GridAgent());
        double gamma = 0.9;
        IterPolicy iterPolicy = new IterPolicy(env);
        Policy pi = iterPolicy.search(gamma, 1.0e-3);

        // 結果確認用緊急避難
        double[][][] piArray = new double[env.getHeight()][env.getWidth()][];
        for (int i = 0; i < env.getHeight(); i++) {
            for (int j = 0; j < env.getWidth(); j++) {
                piArray[i][j] = pi.get(new int[]{i, j});

            }
        }
        GridworldPainter painter = new GridworldPainter(720, 540, env);
        painter.setV(iterPolicy.getValueArray(), piArray);
        painter.setV(iterPolicy.getValueArray());
        NvFrame frame = new NvFrame(painter);
        frame.setTitle("Policy Iteration using Policy Class");
        frame.setVisible(true);
    }

    private int argmax(double[] array) {
        int n = array.length;
        int max = 0;
        double maxValue = array[max];
        for (int i = 1; i < n; i++) {
            if (array[i] > maxValue) {
                max = i;
                maxValue = array[i];
            }
        }
        return max;
    }

    public Policy greedyPolicy(Policy pi, double[][] valueArray, double gamma) {
        int height = env.getHeight();
        int width = env.getWidth();
        int numActions = agent.getNumActions();
        double[] valueAction = new double[numActions];
        double[] prob = new double[numActions];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] state = agent.state = (env.getStateMap())[i][j].clone();
                for (int a = 0; a < numActions; a++) {
                    env.nextState(a);
                    double r = env.getReward();
                    int[] stateNext = agent.getStateNext();
                    valueAction[a] = r + gamma * valueArray[stateNext[0]][stateNext[1]];
                    prob[a] = 0.0;
                }
                int actionMax = argmax(valueAction);
                prob[actionMax] = 1.0;
                pi.put(state, prob);
            }

        }
        return pi;
    }

    public Policy search(double gamma, double threshold) {
        double[] probAction = {0.25, 0.25, 0.25, 0.25};
        int height = env.getHeight();
        int width = env.getWidth();
        int numActions = agent.getNumActions();
        Policy pi = new Policy(probAction);
        Policy piOld = new Policy(numActions);
        Policy piNew;
        final double eps = 1.0e-3;

        while (true) {
            evaluater.evalPolicy(pi, gamma, threshold);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int[] state = new int[]{i, j};
                    piOld.put(state, pi.get(state).clone());
                }
            }
            piNew = greedyPolicy(pi, evaluater.vArray, gamma);

            // 収束判定
            double delta = 0.0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int[] state = env.getStateMap()[i][j];
                    for (int a = 0; a < numActions; a++) {
                        double t = Math.abs(piOld.get(state)[a] - piNew.get(state)[a]);
                        if (delta < t) {
                            delta = t;
                        }
                    }
                }
            }
            if (delta < eps) {
                break;
            }
            pi = piNew.clone();
        }

        return pi;
    }

    public double[][] getValueArray() {
        return evaluater.vArray;
    }
}