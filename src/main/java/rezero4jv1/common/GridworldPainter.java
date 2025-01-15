package rezero4jv1.common;

import numviz.NvFrame;
import numviz.NvPainter;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class GridworldPainter extends NvPainter {

    Grid grid;
    GridAgent agent;
    double[][] valueArray = null;
    double[][][] qArray = null;
    double[][][] pi = null;

    boolean drawPi = false;
    boolean drawValue = false;
    boolean drawQ = false;

    int n, m;

    public GridworldPainter(int width, int height, Object model) {
        super(width, height, model);
        grid = (Grid) model;
        agent = grid.getAgent();
        m = grid.stateMap.length;
        n = grid.stateMap[0].length;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GridAgent agent = new GridAgent();
        Grid grid = new Grid(agent);
        int width = grid.getWidth();
        int height = grid.getHeight();
        double[][] valueArray = new double[grid.getHeight()][grid.getWidth()];
        double[] probAction = {0.4, 0.15, 0.4, 0.05};
        //double[] probAction = {0.25, 0.25, 0.25, 0.25};

        GridworldPainter painter = new GridworldPainter(720, 540, grid);
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double random = rand.nextDouble();
                double sign = rand.nextDouble() > 0.5 ? 1.0 : -1.0;
                random *= sign;
                valueArray[i][j] = random;
            }
        }
        double[][][] pi = new double[height][width][];
        double[][][] q = new double[height][width][];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                q[i][j] = probAction.clone();
                pi[i][j] = probAction.clone();
            }
        }
        //painter.setV(valueArray, pi);
        //painter.setV(valueArray);
        painter.setQ(q);
        painter.setPi(pi);
        final NvFrame frame = new NvFrame(painter);
        frame.setVisible(true);
        frame.setTitle("Grid World State");
    }

    public void setDrawV(boolean drawV) {
        this.drawValue = drawV;
    }

    public void setDrawQ(boolean drawQ) {
        this.drawQ = drawQ;
    }

    @Override
    public void paint(Graphics g) {

        clear(g);
        int numColorLevels = 8;
        Color[] red = new Color[numColorLevels];
        Color[] green = new Color[numColorLevels];
        //Color[] white = new Color[numColorLevels];
        for (int c = 0; c < numColorLevels; c++) {
            int level = 256 / numColorLevels * (c + 1) - 1;
            red[c] = new Color(255, 255 - level, 255 - level);
            green[c] = new Color(255 - level, 255, 255 - level);
        }
        xOffset = width / 2;
        yOffset = height / 2;
        int gridWidth = this.width * 4 / 5;
        int gridHeight = this.height * 4 / 5;
        int x1, y1, x2, y2;
        g.setColor(colorMap.getBlack());
        int xs = xOffset - gridWidth / 2;
        int ys = yOffset + gridHeight / 2;
        int xe = xOffset + gridWidth / 2;
        int ye = yOffset - gridHeight / 2;
        int dx = gridWidth / n;
        int dy = gridHeight / m;
        final int lenArrow = 30;

        if (drawQ) {
            //double qmax, qmin;
            double qmax;
            //qmax = qmin = 0.0;
            qmax = 0.0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    double amax = Math.abs(ArrayUtils.max(qArray[i][j]));
                    double amin = Math.abs(ArrayUtils.min(qArray[i][j]));
                    // バグっぽい
                    if (amax > amin) {
                        if(amax > qmax) {
                            qmax = amax;
                        }
                        //qmin = -amax;
                    } else {
                        if(amin > qmax) {
                            qmax = amin;
                        }
                        //qmin = -amin;
                    }
                }
            }
            int[] xt = new int[3];
            int[] yt = new int[3];
            double delta = qmax / numColorLevels;
            for (int i = 0; i < m; i++) {
                y1 = ys - dy * (i + 1);
                for (int j = 0; j < n; j++) {
                    x1 = xs + dx * j;
                    int[] state = new int[]{i, j};
                    if (Arrays.equals(state, grid.stateGoal)) {
                        continue;
                    }
                    if (Arrays.equals(grid.stateWall, state)) {
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(x1, y1, dx, dy);
                    } else {
                        for (int a = 0; a < agent.numActions; a++) {
                            int xl, yl; // Q値の位置
                            xl = yl = 0;
                            switch (a) {
                                case 0:
                                    xt[0] = x1;
                                    yt[0] = y1;
                                    xt[1] = x1 + dx;
                                    yt[1] = y1;
                                    xt[2] = x1 + dx / 2;
                                    yt[2] = y1 + dy / 2;
                                    xl = x1 + dx / 3;
                                    yl = y1 + dy / 4;
                                    //g.setColor(Color.RED);
                                    break;
                                case 1:
                                    xt[0] = x1;
                                    yt[0] = y1 + dy;
                                    xt[1] = x1 + dx;
                                    yt[1] = y1 + dy;
                                    xt[2] = x1 + dx / 2;
                                    yt[2] = y1 + dy / 2;
                                    xl = x1 + dx / 3;
                                    yl = y1 + dy / 2 + lenArrow + 20;
                                    //g.setColor(Color.BLUE);
                                    break;
                                case 2:
                                    xt[0] = x1;
                                    yt[0] = y1;
                                    xt[1] = x1;
                                    yt[1] = y1 + dy;
                                    xt[2] = x1 + dx / 2;
                                    yt[2] = y1 + dy / 2;
                                    xl = x1 + 10;
                                    yl = y1 + dy / 2;
                                    //g.setColor(Color.GREEN);
                                    break;
                                case 3:
                                    xt[0] = x1 + dx;
                                    yt[0] = y1;
                                    xt[1] = x1 + dx;
                                    yt[1] = y1 + dy;
                                    xt[2] = x1 + dx / 2;
                                    yt[2] = y1 + dy / 2;
                                    xl = x1 + dx / 2 + lenArrow + 10;
                                    yl = y1 + dy / 2;
                                    //g.setColor(Color.YELLOW);
                                    break;
                                default:
                                    break;
                            }
                            double val = qArray[i][j][a];
                            if (val > 0.0) {
                                int color = (int) (val / delta - 1);
                                // バグ対策
                                if (color >= numColorLevels) {
                                    System.out.println("Error:Green  " + qmax + " " + qArray[j][i][a] + "  " + color);
                                    color = numColorLevels - 1;
                                }
                                g.setColor(green[color]);
                            } else if (val < 0.0) {
                                int color = (int) (-val / delta - 1);
                                // バグ対策
                                if (color >= numColorLevels) {
                                    System.out.println("Error:Red    " + -qmax + " " + qArray[j][i][a] + "  " + color);
                                    color = numColorLevels - 1;
                                }
                                g.setColor(red[color]);
                            } else {
                                g.setColor(Color.WHITE);
                            }
                            g.fillPolygon(xt, yt, 3);

                            g.setFont(new Font("Serif", Font.PLAIN, 15));
                            g.setColor(Color.BLACK);
                            String qLabel = String.format("%.2f", qArray[i][j][a]);
                            g.drawString(qLabel, xl, yl);
                        }
                    }
                }
            }
        }

        if (drawValue) {
            double amax = Math.abs(ArrayUtils.max(valueArray));
            double amin = Math.abs(ArrayUtils.min(valueArray));
            double vmax, vmin;
            if (amax > amin) {
                vmax = amax;
                vmin = -amax;
            } else {
                vmax = amin;
                vmin = -amin;
            }
            for (int i = 0; i < m; i++) {
                y1 = ys - dy * (i + 1);
                for (int j = 0; j < n; j++) {
                    x1 = xs + dx * j;
                    int[] state = new int[]{i, j};
                    if (Arrays.equals(grid.stateWall, state)) {
                        g.setColor(Color.LIGHT_GRAY);
                    } else {
                        double val = valueArray[i][j];
                        if (val > 0.0) {
                            double delta = vmax / numColorLevels;
                            int color = (int) (valueArray[i][j] / delta - 1);
                            g.setColor(green[color]);
                        } else if (val < 0.0) {
                            double delta = -vmin / numColorLevels;
                            int color = (int) (-valueArray[i][j] / delta - 1);
                            g.setColor(red[color]);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                    }
                    g.fillRect(x1, y1, dx, dy);
                }
            }
        }
        if (drawPi) {
            g.setColor(Color.BLACK);
            //g.setFont(new Font("Serif", Font.PLAIN, 24));
            int[] xOffset = {0, 0, -lenArrow, lenArrow};
            int[] yOffset = {-lenArrow, lenArrow, 0, 0};
            int[] xp = new int[3];
            int[] yp = new int[3];
            double[] cos_a = new double[4];
            double[] sin_a = new double[4];
            double[] th = {210, -210, 30, -30};
            boolean[] drawArrow = new boolean[grid.getAgent().numActions];
            // Q値によって描いたり描かなかったりの設定が必要
            for (int a = 0; a < 4; a++) {
                cos_a[a] = Math.cos(Math.toRadians(th[a]));
                sin_a[a] = Math.sin(Math.toRadians(th[a]));
            }
            for (int i = 0; i < m; i++) {
                y1 = ys - dy * (i + 1) + dy / 2;
                for (int j = 0; j < n; j++) {
                    x1 = xs + dx * j + dx / 2;
                    int[] state = new int[]{i, j};
                    if (Arrays.equals(state, grid.stateGoal)) {
                        continue;
                    }
                    if (!Arrays.equals(grid.stateWall, state)) {
                        Arrays.fill(drawArrow, false);
                        int[] maxAction = ArrayUtils.argmaxima(pi[i][j]);
                        int m = drawArrow.length;
                        int n = maxAction.length;
                        for (int a = 0; a < m; a++) {
                            for (int value : maxAction) {
                                if (a == value) {
                                    drawArrow[a] = true;
                                }
                            }
                        }
                        g.fillOval(x1 - 5, y1 - 5, 10, 10);
                        for (int a = 0; a < m; a++) {
                            if (!drawArrow[a]) {
                                continue;
                            }
                            x2 = x1 + xOffset[a];
                            y2 = y1 + yOffset[a];
                            g.drawLine(x1, y1, x2, y2);

                            double dux = (double) (x2 - x1) / lenArrow;
                            double duy = (double) (y2 - y1) / lenArrow;

                            double dx3 = -dux * (0.4 * lenArrow);
                            double dy3 = duy * (0.4 * lenArrow);

                            xp[0] = x2;
                            yp[0] = y2;
                            xp[1] = (int) (cos_a[a] * dx3 - sin_a[a] * dy3 + x2);
                            yp[1] = (int) (sin_a[a] * dx3 + cos_a[a] * dy3 + y2);
                            xp[2] = (int) (cos_a[a] * dx3 + sin_a[a] * dy3 + x2);
                            yp[2] = (int) (-sin_a[a] * dx3 + cos_a[a] * dy3 + y2);
                            g.fillPolygon(xp, yp, 3);
                        }
                    }
                }
            }
        }
        // 線幅の変更
        Graphics2D g2 = (Graphics2D) g;
        BasicStroke bs = new BasicStroke(3);
        g2.setStroke(bs);
        g.setColor(Color.BLACK);

        g.drawLine(xs, ys, xs, ye);
        g.drawLine(xe, ys, xe, ye);
        g.drawLine(xs, ys, xe, ys);
        g.drawLine(xs, ye, xe, ye);

        // Y axis grid */
        for (int i = 0; i < m; i++) {
            x1 = xs;
            x2 = xe;
            y1 = y2 = yOffset - gridHeight / 2 + (int) ((double) gridHeight / m * i);
            g.drawLine(x1, y1, x2, y2);
        }
        // X axis grid */
        for (int j = 1; j < n; j++) {
            x1 = x2 = xs + (int) ((double) gridWidth / n * j);
            y1 = ye;
            y2 = ys;
            g.drawLine(x1, y1, x2, y2);
        }
        // 文字
        if (drawValue) {
            g.setFont(new Font("Serif", Font.PLAIN, 18));
            g.setColor(colorMap.getBlack());
            for (int i = 0; i < m; i++) {
                y1 = ys - dy * i;
                for (int j = 0; j < n; j++) {
                    /*
                     * 状態価値関数の値
                     */
                    x1 = xs + dx * j;
                    String statePos = "(" + i + "," + j + ")";
                    g.drawString(statePos, x1 + 10, y1 - 10);
                    x1 = xs + dx * j;
                    String stateFunc = String.format("%.2f", valueArray[i][j]);
                    g.drawString(stateFunc, x1 + dx / 2, y1 - 10);
                }
            }
        }
        if (drawValue || drawQ || drawPi) {
            g.setFont(new Font("Serif", Font.PLAIN, 16));
            g.setColor(colorMap.getBlack());
            for (int i = 0; i < m; i++) {
                y1 = ys - dy * i;
                for (int j = 0; j < n; j++) {
                    int[] state = new int[]{i, j};
                    x1 = xs + dx * j;
                    if (Arrays.equals(grid.stateStart, state)) {
                        g.drawString("Start", x1 + 10, y1 - dy + 20);
                    } else if (Arrays.equals(grid.stateGoal, state)) {
                        g.drawString("Goal", x1 + 10, y1 - dy + 20);
                    }
                }
            }
        }
    }

    public void setV(double[][] vMap) {
        this.valueArray = vMap;
        drawValue = true;
    }

    public void setV(double[][] vMap, double[][][] pi) {
        this.valueArray = vMap;
        this.pi = pi;

        drawValue = true;
        drawPi = true;
    }

    public void setPi(double[][][] pi) {
        this.pi = pi;
        drawPi = true;
    }

    public void setQ(double[][][] qArray) {
        this.qArray = qArray;
        drawQ = true;
    }

    public void setQ(double[][][] qArray, double[][][] piArray) {
        this.qArray = qArray;
        this.pi = piArray;
        drawQ = true;
        drawPi = true;
    }

}
