package rezero4j.misc;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

public class PlotUtils {

    public static XYSeriesCollection createXYSeriesCollection(String[] keys, double[][] y) {
        XYSeries[] xy = new XYSeries[keys.length];
        XYSeriesCollection ds_xy = new XYSeriesCollection();

        for(int i = 0; i < y.length; i++) {
            xy[i] = new XYSeries(keys[i]);
            for(int j = 0; j < y[i].length; j++) {
                xy[i].add(j, y[i][j]);
            }
            ds_xy.addSeries(xy[i]);
        }

        return ds_xy;
    }
    public static XYSeriesCollection createXYSeriesCollection(String[] keys, double[][] x, double[][] y) {
        XYSeries[] xy = new XYSeries[keys.length];
        XYSeriesCollection ds_xy = new XYSeriesCollection();

        for(int i = 0; i < y.length; i++) {
            xy[i] = new XYSeries(keys[i]);
            for(int j = 0; j < y[i].length; j++) {
                xy[i].add(x[i][j], y[i][j]);
            }
            ds_xy.addSeries(xy[i]);
        }

        return ds_xy;
    }

    public static void setFonts(String fontName, int[] fontSiee, JFreeChart chart) {
        Font font = new Font(fontName, Font.PLAIN, fontSiee[0]);
        chart.getXYPlot().getDomainAxis().setLabelFont(font);
        chart.getXYPlot().getRangeAxis().setLabelFont(font);

        font = new Font(fontName, Font.PLAIN, fontSiee[1]);
        chart.getXYPlot().getDomainAxis().setTickLabelFont(font);
        chart.getXYPlot().getRangeAxis().setTickLabelFont(font);

        font = new Font(fontName, Font.PLAIN | Font.ITALIC, fontSiee[2]);
        chart.getTitle().setFont(font);

        font = new Font(fontName, Font.PLAIN | Font.ITALIC, fontSiee[2]);
        chart.getLegend().setItemFont(font);
    }
    public static void setFonts(JFreeChart chart, String fontName) {
        Font font = new Font(fontName, Font.PLAIN, 18);
        chart.getXYPlot().getDomainAxis().setLabelFont(font);
        chart.getXYPlot().getRangeAxis().setLabelFont(font);

        font = new Font(fontName, Font.PLAIN, 12);
        chart.getXYPlot().getDomainAxis().setTickLabelFont(font);
        chart.getXYPlot().getRangeAxis().setTickLabelFont(font);

        font = new Font(fontName, Font.PLAIN | Font.ITALIC, 18);
        chart.getTitle().setFont(font);

        font = new Font(fontName, Font.PLAIN | Font.ITALIC, 20);
        font = new Font("Serif", Font.PLAIN | Font.ITALIC, 20);
        chart.getLegend().setItemFont(font);
    }

    public static void setFontsAsSerif(JFreeChart chart) {
        String fontName = "Serif";

        Font font = new Font(fontName, Font.PLAIN, 18);
        chart.getXYPlot().getDomainAxis().setLabelFont(font);
        chart.getXYPlot().getRangeAxis().setLabelFont(font);

        font = new Font(fontName, Font.PLAIN, 12);
        chart.getXYPlot().getDomainAxis().setTickLabelFont(font);
        chart.getXYPlot().getRangeAxis().setTickLabelFont(font);

        font = new Font(fontName, Font.PLAIN | Font.ITALIC, 18);
        chart.getTitle().setFont(font);

        font = new Font(fontName, Font.PLAIN | Font.ITALIC, 20);
        font = new Font("Serif", Font.PLAIN | Font.ITALIC, 20);
        chart.getLegend().setItemFont(font);
    }

    public static void main(String args[]) {
        //(1)データセットの作成
        String[] keys = {"Cos", "Sin"};
        double[][] x = new double[keys.length][360];
        double[][] y = new double[keys.length][360];
        for(int i = 0; i < 360; i++) {
            x[0][i] = (double) i;
            x[1][i] = (double) i;
            y[0][i] = Math.cos(Math.toRadians(x[0][i]));
            y[1][i] = Math.sin(Math.toRadians(x[1][i]));
        }
        XYSeriesCollection ds_xy = createXYSeriesCollection(keys, x, y);

        //(2)チャートの作成
        JFreeChart chart = ChartFactory.createXYLineChart("Cos-Sin", "theta", "Value", ds_xy);
        chart.getXYPlot().setBackgroundPaint(Color.white);
        chart.getXYPlot().setDomainGridlinePaint(Color.black);
        chart.getXYPlot().setRangeGridlinePaint(Color.black);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(2));
        chart.getXYPlot().getRenderer().setSeriesStroke(1, new BasicStroke(2));

        setFonts(chart, "Serif");

        //(3)出力処理
        ChartFrame frame = new ChartFrame("Cos-Sin Curve", chart);
        frame.setSize(640, 480);
        frame.setVisible(true);

    }
}