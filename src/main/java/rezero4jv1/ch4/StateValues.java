package rezero4jv1.ch4;

import rezero4jv1.common.GridAgent;
import rezero4jv1.common.Grid;
import rezero4jv1.common.GridworldPainter;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class StateValues {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Grid grid = new Grid(new GridAgent());
        //MultiKeyMap vMap = MultiKeyMap.multiKeyMap(new LinkedMap());
        double[][] vMap = new double[grid.getHeight()][grid.getWidth()];

        GridworldPainter painter = new GridworldPainter(720, 540, grid);
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                double random = rand.nextDouble();
                double sign = rand.nextDouble() > 0.5 ? 1.0 : -1.0;
                random *= sign;
                vMap[i][j] = random;
            }
        }

        painter.setV(vMap);
        final NvFrame frame = new NvFrame(painter);
        frame.setVisible(true);
        frame.setTitle("Grid World State");
    }

}
