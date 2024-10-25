package rezero4j.ch4;

import rezero4j.common.GridAgent;
import rezero4j.common.GridworldPainter;
import rezero4j.common.Grid;
import rezero4j.NsFrame;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class StateAndPolicy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Grid grid = new Grid(new GridAgent());
        //MultiKeyMap valueArray = MultiKeyMap.multiKeyMap(new LinkedMap());
        int width = grid.getWidth();
        int height = grid.getHeight();
        double[][] valueArray = new double[grid.getHeight()][grid.getWidth()];
        double[] probAction = {0.4, 0.15, 0.4, 0.05};
        //double[] probAction = {0.25, 0.25, 0.25, 0.25};

        GridworldPainter painter = new GridworldPainter(720, 540, grid);
        Random rand = new Random(System.currentTimeMillis());
        for (int j = 0; j < grid.getHeight(); j++) {
            for (int i = 0; i < grid.getWidth(); i++) {
                double random = rand.nextDouble();
                double sign = rand.nextDouble() > 0.5 ? 1.0 : -1.0;
                random *= sign;
                valueArray[j][i] = random;
            }
        }
        double[][][] pi = new double[height][width][];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                pi[j][i] = probAction.clone();
            }
        }
        painter.setV(valueArray, pi);
        final NsFrame frame = new NsFrame(painter);
        frame.setVisible(true);
        frame.setTitle("Grid World State");
    }

}
