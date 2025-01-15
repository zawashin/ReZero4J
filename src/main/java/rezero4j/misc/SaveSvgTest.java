package rezero4j.misc;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */

public class SaveSvgTest {
    public static void main(String[] args) {
        //(1)データセットの作成
        String[] keys = {"Smaple 1", "Sample 2"};
        XYSeries xy1 = new XYSeries(keys[0]);
        xy1.add(1, 150);
        xy1.add(2, 160);
        xy1.add(3, 180);
        XYSeries xy2 = new XYSeries(keys[1]);
        xy2.add(1, 200);
        xy2.add(2, 180);
        xy2.add(3, 220);
        XYSeriesCollection ds_xy = new XYSeriesCollection();
        ds_xy.addSeries(xy1);
        ds_xy.addSeries(xy2);

        //(2)チャートの作成
        JFreeChart chart = ChartFactory.createXYLineChart("Sales", "Day", "# of Visitors", ds_xy,
                PlotOrientation.VERTICAL, true, false, false);
        chart.getXYPlot().setBackgroundPaint(Color.white);
        chart.getXYPlot().setDomainGridlinePaint(Color.black);
        chart.getXYPlot().setRangeGridlinePaint(Color.black);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(2));
        chart.getXYPlot().getRenderer().setSeriesStroke(1, new BasicStroke(2));

        Font font = new Font("Times New Roman", Font.PLAIN, 18);
        chart.getXYPlot().getDomainAxis().setLabelFont(font);
        chart.getXYPlot().getRangeAxis().setLabelFont(font);

        font = new Font("Times New Roman", Font.PLAIN, 12);
        chart.getXYPlot().getDomainAxis().setTickLabelFont(font);
        chart.getXYPlot().getRangeAxis().setTickLabelFont(font);

        font = new Font("Times New Roman", Font.PLAIN| Font.ITALIC, 18);
        chart.getTitle().setFont(font);

        font = new Font("Times New Roman", Font.PLAIN| Font.ITALIC, 20);
        chart.getLegend().setItemFont(font);

        //(3)出力処理
        // SVG保存
        SVGGraphics2D svg = new SVGGraphics2D(640, 480);
        Rectangle2D rectangle = new Rectangle(640, 480);
        chart.draw(svg, rectangle);

        try {
            SVGUtils.writeToSVG(new File("./SampleLineChart.svg"), svg.getSVGElement());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
