package rezero4j.misc;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

public class JFreeChartSampeXYLineChart {
    public static void main(String args[]) {
        //(1)データセットの作成
        XYSeries xy1 = new XYSeries("Sample 1");
        xy1.add(1, 150);
        xy1.add(2, 160);
        xy1.add(3, 180);
        XYSeries xy2 = new XYSeries("Sample 2");
        xy2.add(1, 200);
        xy2.add(2, 180);
        xy2.add(3, 220);
        XYSeriesCollection ds_xy = new XYSeriesCollection();
        ds_xy.addSeries(xy1);
        ds_xy.addSeries(xy2);

        //(2)チャートの作成
        JFreeChart chart = ChartFactory.createXYLineChart("Sales", "Day", "Visitors", ds_xy,
                PlotOrientation.VERTICAL, true, false, false);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(4));
        chart.getXYPlot().getRenderer().setSeriesStroke(1, new BasicStroke(4));
        chart.getXYPlot().setBackgroundPaint(Color.white);
        chart.getXYPlot().setDomainGridlinePaint(Color.black);
        chart.getXYPlot().setRangeGridlinePaint(Color.black);
        //(3)出力処理
        ChartFrame frame = new ChartFrame("XY Plot Sample by JFreeChart", chart);
        frame.setVisible(true);
        frame.setSize(600, 400);

    }
}